package com.mediscreen.microservicenote.service;

import com.mediscreen.microservicenote.dto.RiskLevelDTO;
import com.mediscreen.microservicenote.model.Note;

import java.util.List;

/**
 * Interface for the service layer handling note-related operations.
 */
public interface NoteService {

     /**
      * Retrieves all notes from the database.
      *
      * @return A list of all notes.
      */
     List<Note> getAllNote();

     /**
      * Retrieves all notes associated with a specific patient by their ID.
      *
      * @param id The ID of the patient.
      * @return A list of notes associated with the patient.
      */
     List<Note> getAllNoteByPatientId(long id);

     /**
      * Adds a new note to the database.
      *
      * @param note The note to be added.
      * @return The added note.
      */
     Note addNote(Note note);

     /**
      * Updates the risk level of notes for a specific patient.
      *
      * @param riskLevelDTO The DTO containing the patient ID and the new risk level.
      */
     void updateRiskLevel(RiskLevelDTO riskLevelDTO);
}

