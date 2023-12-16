package com.mediscreen.microservicenote.service.impl;

import com.mediscreen.microservicenote.model.Note;
import com.mediscreen.microservicenote.repository.NoteRepository;
import com.mediscreen.microservicenote.service.NoteService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAllNote() {
        return noteRepository.findAllByOrderByPatientIdAsc();
    }

    @Override
    public Note addNote(Note note) {
        return noteRepository.save(note);
    }
}
