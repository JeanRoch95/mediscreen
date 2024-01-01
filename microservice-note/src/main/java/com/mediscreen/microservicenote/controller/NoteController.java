package com.mediscreen.microservicenote.controller;

import com.mediscreen.microservicenote.dto.RiskLevelDTO;
import com.mediscreen.microservicenote.model.Note;
import com.mediscreen.microservicenote.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class NoteController {

    private final NoteService noteService;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);


    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Retrieves all notes from the database.
     *
     * @return A list of all notes.
     */
    @GetMapping("/notes")
    public List<Note> getAllNotes() {
        return noteService.getAllNote();
    }

    /**
     * Creates a new note in the database.
     *
     * @param note The note to be created.
     * @return The created note.
     */
    @PostMapping("/notes")
    public Note createNote(@RequestBody Note note) {
        return noteService.addNote(note);
    }

    /**
     * Retrieves all notes for a specific patient by their ID.
     *
     * @param id The ID of the patient.
     * @return A list of notes associated with the patient.
     */
    @GetMapping("/notes/{id}")
    public List<Note> getAllNotesById(@PathVariable long id) {
        logger.info("note : {}", noteService.getAllNoteByPatientId(id).size());
        return noteService.getAllNoteByPatientId(id);
    }

    /**
     * Updates the risk level of notes for a specific patient.
     *
     * @param riskLevelDTO The DTO containing the patient ID and the new risk level.
     * @return A ResponseEntity indicating the outcome of the operation.
     */
    @PostMapping("/notes/updatesRiskLevel")
    public ResponseEntity<?> updateRiskLevel(@RequestBody RiskLevelDTO riskLevelDTO) {
        logger.info("Reçu mise à jour du niveau de risque pour patientId: {}, niveau de risque: {}",
                riskLevelDTO.getPatientId(), riskLevelDTO.getRiskLevel());
        noteService.updateRiskLevel(riskLevelDTO);
        return ResponseEntity.ok().build();
    }
}
