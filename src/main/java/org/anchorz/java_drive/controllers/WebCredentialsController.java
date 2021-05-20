package org.anchorz.java_drive.controllers;

import org.anchorz.java_drive.models.Credential;
import org.anchorz.java_drive.models.Note;
import org.anchorz.java_drive.services.CredentialService;
import org.anchorz.java_drive.services.HashService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
    @RequestMapping("/credentials")
public class WebCredentialsController {
    private final CredentialService credentialService;
    private final HashService hashService;
    public WebCredentialsController(CredentialService credentialService, HashService hashService) {
        this.hashService = hashService;
        this.credentialService = credentialService;
    }
    @GetMapping
    public String getAllCredentials(@ModelAttribute Credential credentialsForm, Authentication authentication, Model model) {
        int userId = (int) authentication.getPrincipal();
        List<Credential> credentials = this.credentialService.getAllCredentials(userId);
        model.addAttribute("credentials", credentials);
        model.addAttribute("credentialsForm", credentialsForm);
        return "home";
    }
    @PostMapping
    public String addCredential(Authentication authentication, RedirectAttributes redirectAttributes, @ModelAttribute Credential credentialsForm, Model model) {
        try {
            int userId = (int) authentication.getPrincipal();
            String encodedSalt = hashService.getEncodedSalt();
            credentialsForm.setKey(hashService.getHashedValue(credentialsForm.getPassword(), encodedSalt));
            credentialsForm.setUserId(userId);
            credentialService.addCredential(credentialsForm);
            model.addAttribute("credentialsForm", new Credential());
            model.addAttribute("credentials", this.credentialService.getAllCredentials(userId));
            redirectAttributes.addFlashAttribute("message", "Credentials Added successfully");
        } catch (Exception error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());
        }
        return "redirect:/home";
    }
    @GetMapping("/add")
    public String addCredentialsView(@ModelAttribute Credential credentialsForm, Authentication authentication, Model model) {
        int userId = (int) authentication.getPrincipal();
        credentialsForm.setUserId(userId);
        model.addAttribute("credentialsForm", credentialsForm);
        return "credentialsForm";
    }
    @GetMapping("/{credentialId}/edit")
    public String editCredentialsView(@PathVariable int credentialId, Model model) {
        Credential credentialsForm = this.credentialService.getCredential(credentialId);
        model.addAttribute("credentialsForm", credentialsForm);
        return "credentialsForm";
    }
    @PostMapping("/{credentialId}/update")
    public String updateCredential(RedirectAttributes redirectAttributes, @PathVariable int credentialId, @ModelAttribute Credential credentialsForm, Model model) {
        try {
            credentialsForm.setCredentialId(credentialId);
            Credential oldCredentialData = this.credentialService.getCredential(credentialId);
            credentialsForm.setKey(oldCredentialData.getKey());
            if (!oldCredentialData.getPassword().equals(credentialsForm.getPassword())) {
                String encodedSalt = hashService.getEncodedSalt();
                credentialsForm.setKey(hashService.getHashedValue(credentialsForm.getPassword(), encodedSalt));
            }
            this.credentialService.updateCredential(credentialsForm);
            redirectAttributes.addFlashAttribute("message", "Credentials Updated successfully");
        } catch (Exception error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());
        }
        return "redirect:/home";
    }
    @GetMapping("/{credentialId}/delete")
    public String deleteCredential(RedirectAttributes redirectAttributes, @PathVariable int credentialId, Authentication authentication, Model model) {
        try {
            this.credentialService.deleteCredential(credentialId);
            redirectAttributes.addFlashAttribute("message", "Credentials Deleted successfully");
        } catch (Exception error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());
        }
        return "redirect:/home";
    }
}
