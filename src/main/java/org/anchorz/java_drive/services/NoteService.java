package org.anchorz.java_drive.services;

import org.anchorz.java_drive.models.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private List<Note> notes;
    public List<Note> getAllNotes(String userId) {
        return notes;
    }
    public void addNote(String userId, String note) {
        Note newNote = new Note();
        newNote.text = note;
        this.notes.add(newNote);
    }
    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating MessageService bean");
        this.notes = new ArrayList<>();
    }
}
