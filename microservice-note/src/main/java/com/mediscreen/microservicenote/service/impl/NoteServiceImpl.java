package com.mediscreen.microservicenote.service.impl;

import com.mediscreen.microservicenote.dto.RiskLevelDTO;
import com.mediscreen.microservicenote.model.Note;
import com.mediscreen.microservicenote.repository.NoteRepository;
import com.mediscreen.microservicenote.service.NoteService;
import org.springframework.stereotype.Service;

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
    public List<Note> getAllNoteByPatientId(long id) {
        return noteRepository.findAllByPatientId(id);
    }

    @Override
    public Note addNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public void updateRiskLevel(RiskLevelDTO riskLevelDTO) {
        List<Note> noteList = noteRepository.findAllByPatientId(riskLevelDTO.getPatientId());
        for(Note note : noteList) {
            note.setRiskLevel(riskLevelDTO.getRiskLevel());
            noteRepository.save(note);
        }
    }

}
