package com.s13sh.jwt.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateRequest {
    private String username;
    private String password;
    private List<String> roles;
}
