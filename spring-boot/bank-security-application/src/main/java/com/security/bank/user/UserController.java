package com.security.bank.user;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.security.bank.dto.JwtRequest;
import com.security.bank.dto.JwtResponse;
import com.security.bank.dto.UserDto;
import com.security.bank.entity.User;
import com.security.bank.service.UserService;



@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser( @RequestBody UserDto userDto) {

        return userService.registerUser(userDto);
    }

    @PostMapping("/login")
    public JwtResponse login( @RequestBody JwtRequest jwtRequest) {

        return userService.login(jwtRequest);
    }
}