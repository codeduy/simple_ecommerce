package com.example.demo.controllers;

import com.example.demo.forms.RegistrationForm;
import com.example.demo.forms.SignInForm;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String login(Model model) {
        SignInForm form = new SignInForm();
        model.addAttribute("form", form);
        return "auth/login";
    }

    @PostMapping("/process/login")
    public String login(
            @ModelAttribute("form") RegistrationForm form
    ) {
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
                    "Password incorrect");
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
