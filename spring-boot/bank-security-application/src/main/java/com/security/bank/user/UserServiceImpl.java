package com.security.bank.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.bank.dto.JwtRequest;
import com.security.bank.dto.JwtResponse;
import com.security.bank.dto.UserDto;
import com.security.bank.entity.Role;
import com.security.bank.entity.User;
import com.security.bank.jwt.JwtAuthenticationHelper;
import com.security.bank.repository.UserRepository;
import com.security.bank.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtAuthenticationHelper jwtHelper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private UserDetailsService userDetailsService;

    
    @Override
    public User registerUser(UserDto userDto) {
    	

    	User user = new User();
    	user.setName(userDto.getName());
		user.setUsername(userDto.getUsername());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setAddress(userDto.getAddress());
		user.setNumber(userDto.getNumber());
		user.setIdentityProof(userDto.getIdentityProof());
		user.setInvestmentList(userDto.getInvestmentList());
		user.setAccountList(userDto.getAccountList());
		Role role = new Role();
		role.setRoleName("ROLE_CUSTOMER");
				
		user.setRoles(role);
		
		return userRepository.save(user);
    }

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
		this.doAuthenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = jwtHelper.generateToken(userDetails);
		
		JwtResponse response = JwtResponse.builder().jwtToken(token).build();
		return response;
    }

    @Override
    public void doAuthenticate(String username, String password) {
    	UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			manager.authenticate(authenticationToken);

		}catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Username or Password");
		}

    }
}