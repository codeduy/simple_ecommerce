package com.example.demo.services.imp;

import com.example.demo.dto.RegistrationDTO;
import com.example.demo.forms.RegistrationForm;
import com.example.demo.models.Role;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImp(
            UserRepository userRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(@NotNull RegistrationForm form) {
        UserEntity user = new UserEntity();
        user.setUserName(form.getUsername());
        String hashedPassword = encoder.encode(form.getPassword());
        user.setPassword(hashedPassword);
        Role role = roleRepository.findByName("USER");
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);
    }

    @Override
    public UserEntity findByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return this.encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found " + username);
        }
        return user;
    }

}
