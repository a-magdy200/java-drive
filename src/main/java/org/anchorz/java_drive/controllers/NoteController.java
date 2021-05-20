package org.anchorz.java_drive.controllers;

import org.anchorz.java_drive.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    @GetMapping
    public String getAllNotes(Model model) {
        model.addAttribute("notes", this.noteService.getAllNotes(""));
        return "notes";
    }
    @PostMapping
    public String addNote(@RequestParam("note") String note, Model model) {
        this.noteService.addNote("", note);
        System.out.println(this.noteService.getAllNotes(""));
        model.addAttribute("notes", this.noteService.getAllNotes(""));
        return "notes";
    }
}
