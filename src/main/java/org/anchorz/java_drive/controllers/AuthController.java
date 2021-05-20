package org.anchorz.java_drive.controllers;

import org.anchorz.java_drive.models.SignUpForm;
import org.anchorz.java_drive.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }
//    @GetMapping("/signup")
//    public String getSignUpForm(@ModelAttribute SignUpForm signUpForm, Model model) {
//        return "signup";
//    }
//    @PostMapping("/signup")
//    public String registerUser(SignUpForm signUpForm, Model model) {
//        this.authService.registerUser(signUpForm);
//        return "redirect:/";
//    }
}
