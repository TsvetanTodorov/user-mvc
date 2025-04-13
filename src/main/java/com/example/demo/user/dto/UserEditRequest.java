package com.example.demo.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserEditRequest {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;
    
    private String phoneNumber;
}
