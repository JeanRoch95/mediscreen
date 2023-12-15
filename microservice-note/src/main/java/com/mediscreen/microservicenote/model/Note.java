package com.mediscreen.microservicenote.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;


@Document(collection = "notes")
public class Note {

    @Id
    private String id;

    @Field(value = "patientId")
    private long patientId;

    private String patient;

    @Field(value = "note")
    private String note;

    @Field(value = "dateNote")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Instant dateNote;

    public Note() {
    }

    public Note(String id, long patientId, String patient, String note, Instant dateNote) {
        this.id = id;
        this.patientId = patientId;
        this.note = note;
        this.dateNote = dateNote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getDateNote() {
        return dateNote;
    }

    public void setDateNote(Instant dateNote) {
        this.dateNote = dateNote;
    }
}
