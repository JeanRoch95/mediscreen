package com.mediscreen.microservicenote.controller;

import com.mediscreen.microservicenote.model.Note;
import com.mediscreen.microservicenote.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/notes")
    public List<Note> getAllNotes(Model model) {
        return noteService.getAllNote();
    }

    @PostMapping("/notes")
    public Note createNote(@RequestBody Note note) {
        return createNote(note);
    }
}
