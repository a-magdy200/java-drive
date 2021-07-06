package org.anchorz.java_drive.controllers;

import org.anchorz.java_drive.models.Credential;
import org.anchorz.java_drive.models.File;
import org.anchorz.java_drive.models.Note;
import org.anchorz.java_drive.services.CredentialService;
import org.anchorz.java_drive.services.FileService;
import org.anchorz.java_drive.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final FileService fileService;
    public HomeController(NoteService noteService, FileService fileService, CredentialService credentialService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService= credentialService;
    }
    @GetMapping("/" )
    public String getIndex() {
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String getHome(Authentication authentication, @ModelAttribute Credential credentialsForm, @ModelAttribute Note noteForm, Model model) {
        int userId = (int) authentication.getPrincipal();
        List<Note> notes = this.noteService.getAllNotes(userId);
        List<Credential> credentials = this.credentialService.getAllCredentials(userId);
        List<File> files = this.fileService.getAllFiles(userId);
        model.addAttribute("notes", notes);
        model.addAttribute("files", files);
        model.addAttribute("noteForm", noteForm);
        model.addAttribute("credentials", credentials);
        model.addAttribute("credentialsForm", credentialsForm);
        return "home";
    }
    @RequestMapping("**")
    public String fallback404() {
        return "error";
    }
}
