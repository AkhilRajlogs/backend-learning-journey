package com.security.bank.service;

import com.security.bank.dto.JwtRequest;
import com.security.bank.dto.JwtResponse;
import com.security.bank.dto.UserDto;
import com.security.bank.entity.User;

public interface UserService {

    User registerUser(UserDto userDto);

    JwtResponse login(JwtRequest jwtRequest);

    void doAuthenticate(String username, String password);

}