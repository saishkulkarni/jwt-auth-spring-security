package com.s13sh.jwt.entity;

import com.s13sh.jwt.dto.UserCreateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<UserRole> roles;

    public User(UserCreateRequest request) {
        this.username = request.getUsername();
        this.password = request.getPassword();
        this.roles = request.getRoles().stream()
                .map(UserRole::valueOf)
                .collect(Collectors.toList());
    }

}