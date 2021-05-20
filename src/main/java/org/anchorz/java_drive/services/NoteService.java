package org.anchorz.java_drive.services;

import org.anchorz.java_drive.mappers.NoteMapper;
import org.anchorz.java_drive.models.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private List<Note> notes;
    private final NoteMapper noteMapper;
    public List<Note> getAllNotes(int userId) {
        this.notes = noteMapper.getAllNotes(userId);
        return notes;
    }
    public void addNote(int userId, Note newNote) {
        newNote.setUserId(userId);
        this.noteMapper.createNote(newNote);
    }
    public Note getNote(int noteId) {
        return this.noteMapper.getNote(noteId);
    }
    public void updateNote(Note note) {
        this.noteMapper.updateNote(note);
    }
    public void deleteNote(int noteId) {
        this.noteMapper.deleteNote(noteId);
    }
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }
    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating Notes Service bean");
        this.notes = new ArrayList<>();
    }
}
