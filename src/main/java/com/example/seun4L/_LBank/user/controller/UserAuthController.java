package com.example.seun4L._LBank.user.controller;

import com.example.seun4L._LBank.exception.UserNotFoundException;
import com.example.seun4L._LBank.user.dto.UserLoginDto;
import com.example.seun4L._LBank.user.dto.UserRegisterDto;
import com.example.seun4L._LBank.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserAuthController.class);
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserRegisterDto userRegisterDto){
        LOGGER.info(userRegisterDto.toString());
        return userService.createUser(userRegisterDto);
    }

    @PostMapping("/user-login")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginDto userLoginDto) throws UserNotFoundException {
        return userService.userLogin(userLoginDto);
    }
}
