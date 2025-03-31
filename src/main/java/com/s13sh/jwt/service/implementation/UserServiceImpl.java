package com.s13sh.jwt.service.implementation;

import com.s13sh.jwt.dto.UserCreateRequest;
import com.s13sh.jwt.dto.UserResponse;
import com.s13sh.jwt.entity.User;
import com.s13sh.jwt.entity.UserRole;
import com.s13sh.jwt.exception.UserExistsException;
import com.s13sh.jwt.repository.UserRepository;
import com.s13sh.jwt.security.jwt.JwtUtil;
import com.s13sh.jwt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(UserCreateRequest userCreateRequest) {
        userCreateRequest.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        if (userRepository.findByUsername(userCreateRequest.getUsername()).isPresent()) {
            throw new UserExistsException("User already exists");
        }
        User newUser = new User(userCreateRequest);
        userRepository.save(newUser);
        return new UserResponse(newUser.getUsername(), newUser.getRoles().stream().map(Enum::name).collect(Collectors.toList()));
    }

    @Override
    public String loginUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(username, user.getRoles().stream().map(UserRole::name).collect(Collectors.toList()));
    }

    @Override
    public List<UserResponse> getAllUsers(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String loggedInUsername = jwtUtil.extractUsername(token);

        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (loggedInUser.getRoles().contains(UserRole.ADMIN)) {
            return userRepository.findAll()
                    .stream()
                    .map(user -> new UserResponse(user.getUsername(), user.getRoles().stream().map(Enum::name).collect(Collectors.toList())))
                    .collect(Collectors.toList());
        } else {
            return List.of(new UserResponse(loggedInUser.getUsername(), loggedInUser.getRoles().stream().map(Enum::name).collect(Collectors.toList())));
        }
    }

}

