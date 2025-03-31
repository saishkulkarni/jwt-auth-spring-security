package com.s13sh.jwt.controller;

import com.s13sh.jwt.dto.UserCreateRequest;
import com.s13sh.jwt.dto.UserResponse;
import com.s13sh.jwt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserCreateRequest userCreateRequest) {
        UserResponse userResponse = userService.registerUser(userCreateRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
        String jwt = userService.loginUser(username, password);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestHeader("Authorization") String authorizationHeader) {
        List<UserResponse> users = userService.getAllUsers(authorizationHeader);
        return ResponseEntity.ok(users);
    }
}
