package com.example.demo.controllers;

import com.example.demo.dto.RegistrationDTO;
import com.example.demo.forms.RegistrationForm;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegister(
            @Valid @ModelAttribute("form") RegistrationForm form,
            BindingResult bindingResult) {
        if (!form.isPasswordMatch()) {
            bindingResult.rejectValue(
                    "rePassword",
                    "password.mismatch",
                    "Mật khẩu không khớp");
        }

        if (userService.findByUsername(form.getUsername()) != null) {
            bindingResult.rejectValue(
                    "username",
                    "username.duplicate",
                    "User name already exist");
        }

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        userService.register(form);
        return "redirect:/login";
    }
}
