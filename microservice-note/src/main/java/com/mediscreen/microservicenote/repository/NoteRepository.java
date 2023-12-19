package com.mediscreen.microservicenote.repository;

import com.mediscreen.microservicenote.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findAllByOrderByPatientIdAsc();

    List<Note> findAllByPatientId(long id);
}
