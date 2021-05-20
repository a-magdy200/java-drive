package org.anchorz.java_drive.controllers;

import org.anchorz.java_drive.models.Note;
import org.anchorz.java_drive.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    @GetMapping
    public String getAllNotes(Authentication authentication, @ModelAttribute Note noteForm, Model model) {
        int userId = (int) authentication.getPrincipal();
        List<Note> notes = this.noteService.getAllNotes(userId);
        model.addAttribute("notes", notes);
        model.addAttribute("noteForm", noteForm);
        return "redirect:/home";
    }
    @PostMapping
    public String addNote(Authentication authentication, @ModelAttribute Note noteForm, Model model, RedirectAttributes redirectAttributes) {
        int userId = (int) authentication.getPrincipal();
        try {
            this.noteService.addNote(userId, noteForm);
            redirectAttributes.addFlashAttribute("message", "Note Added successfully");
        } catch (Exception error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());
        }
        return "redirect:/home";
    }
    @GetMapping("/add")
    public String addNoteView(Authentication authentication, @ModelAttribute Note noteForm, Model model) {
        int userId = (int) authentication.getPrincipal();
        noteForm.setUserId(userId);
        model.addAttribute("noteForm", noteForm);
        return "noteForm";
    }
    @GetMapping("/{noteId}/edit")
    public String editNoteView(@PathVariable int noteId, Model model) {
        Note noteForm = this.noteService.getNote(noteId);//get note by id
        model.addAttribute("noteForm", noteForm);
        return "noteForm";
    }

    @PostMapping("/{noteId}/update")
    public String updateNote(@PathVariable int noteId, @ModelAttribute Note noteForm, Model model, RedirectAttributes redirectAttributes) {
        noteForm.setNoteId(noteId);
        try {
            noteService.updateNote(noteForm);
            redirectAttributes.addFlashAttribute("message", "Note Updated successfully");
        } catch (Exception error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());
        }
        return "redirect:/home";
    }
    @GetMapping("/{noteId}/delete")
    public String deleteNote(@PathVariable int noteId, Model model, RedirectAttributes redirectAttributes) {
        try {
            noteService.deleteNote(noteId);
            redirectAttributes.addFlashAttribute("message", "Note Deleted successfully");
        } catch (Exception error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());
        }
        return "redirect:/home";
    }
}
