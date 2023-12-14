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
    private int patientId;

    @Field(value = "lastName")
    private String patientLastName;

    @Field(value = "firstName")
    private String patientFirstName;

    @Field(value = "note")
    private String note;

    @Field(value = "dateNote")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Instant dateNote;

    public Note() {
    }

    public Note(String id, int patientId, String patientLastName, String patientFirstName, String note, Instant dateNote) {
        this.id = id;
        this.patientId = patientId;
        this.patientLastName = patientLastName;
        this.patientFirstName = patientFirstName;
        this.note = note;
        this.dateNote = dateNote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
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
