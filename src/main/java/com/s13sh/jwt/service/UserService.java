package com.s13sh.jwt.service;

import com.s13sh.jwt.dto.UserCreateRequest;
import com.s13sh.jwt.dto.UserResponse;
import java.util.List;

public interface UserService {
    UserResponse registerUser(UserCreateRequest userCreateRequest);
    String loginUser(String username, String password);
    List<UserResponse> getAllUsers(String authorizationHeader);
}
