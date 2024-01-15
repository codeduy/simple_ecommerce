package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegistrationDTO {
    private long id;
    @NotEmpty
    private String userName;
    private String password;
}
