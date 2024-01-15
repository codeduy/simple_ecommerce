package com.example.demo.services;

import com.example.demo.dto.RegistrationDTO;
import com.example.demo.forms.RegistrationForm;
import com.example.demo.models.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService extends UserDetailsService {
    void register(RegistrationForm form);

    UserEntity findByUsername(String userName);

    PasswordEncoder getPasswordEncoder();
}
