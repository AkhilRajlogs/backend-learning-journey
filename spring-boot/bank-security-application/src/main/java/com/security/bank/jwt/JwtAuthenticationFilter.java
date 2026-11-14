package com.security.bank.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.bank.security.CustomUserDetailService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtAuthenticationHelper jwtAuthenticationHelper;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        if (requestHeader != null
                && requestHeader.startsWith("Bearer ")) {

            token = requestHeader.substring(7);

            try {
                username =
                        jwtAuthenticationHelper.getUsernameFromToken(token);
            } catch (Exception e) {
                System.out.println("Invalid JWT Token");
            }

        }

        if (username != null
                && SecurityContextHolder.getContext()
                        .getAuthentication() == null) {

            UserDetails userDetails =
                    customUserDetailService
                            .loadUserByUsername(username);

            if (jwtAuthenticationHelper
                    .validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

            }
        }

        filterChain.doFilter(request, response);
    }
}