package org.anchorz.java_drive.controllers;

import org.anchorz.java_drive.models.SignUpForm;
import org.anchorz.java_drive.models.UserProfile;
import org.anchorz.java_drive.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/signup")
    public String getSignUpForm(@ModelAttribute SignUpForm signUpForm, Model model) {
        return "signup";
    }
    @PostMapping("/signup")
    public String registerUser(SignUpForm signUpForm, Model model, RedirectAttributes redirectAttributes) {
        boolean isAvailable;
        try {
            if (!signUpForm.getPassword().equals(signUpForm.getConfirmPassword())) {
                redirectAttributes.addFlashAttribute("error", "Password and confirm password does not match");
                model.addAttribute("signUpForm", signUpForm);
                return "redirect:/signup";
            }
            isAvailable = this.authService.usernameIsAvailable(signUpForm.getUsername());
            if (isAvailable) {
                int userId = this.authService.createUser(signUpForm);
                if (userId > 0) {
                    redirectAttributes.addFlashAttribute("message", "Account created successfully, you may now login.");
                    return "redirect:/login";
                } else {
                    model.addAttribute("signUpForm", signUpForm);
                    redirectAttributes.addFlashAttribute("error", "An error has occurred, please try again");
                   return "redirect:/signup";
                }
            } else {
                model.addAttribute("signUpForm", signUpForm);
                redirectAttributes.addFlashAttribute("error", "Username already exists!");
               return "redirect:/signup";
            }
        } catch (Exception error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());
        }
        return "redirect:/signup";
    }
}
