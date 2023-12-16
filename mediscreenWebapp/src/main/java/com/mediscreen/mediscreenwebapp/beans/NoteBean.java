package com.mediscreen.mediscreenwebapp.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

public class NoteBean {

    private String id;

    private long patientId;

    private String patient;

    private String note;

    public NoteBean() {
    }

    public NoteBean(String id, long patientId, String patient, String note) {
        this.id = id;
        this.patientId = patientId;
        this.patient = patient;
        this.note = note;
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
}
