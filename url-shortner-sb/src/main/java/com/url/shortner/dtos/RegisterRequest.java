package com.url.shortner.dtos;


import lombok.Data;

import java.util.Set;

//dto defining structure of a request for registration
@Data
public class RegisterRequest {
    private String username;
    private String email;
    private Set<String> role;
    private String password;
}
