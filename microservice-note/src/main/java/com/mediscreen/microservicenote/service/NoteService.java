package com.mediscreen.microservicenote.service;

import com.mediscreen.microservicenote.model.Note;

import java.util.List;

public interface NoteService {

     List<Note> getAllNote();

     Note addNote(Note note);

}
