package com.mediscreen.mediscreenwebapp.DTO;


import com.mediscreen.mediscreenwebapp.beans.NoteBean;

import java.util.Date;
import java.util.List;

public class RiskAssessmentRequest {

    private long patientId;

    private List<String> noteContent;

    private String gender;

    private Date birthDate;

    public RiskAssessmentRequest() {
    }

    public RiskAssessmentRequest(long patientId, List<String> noteContent, String gender, Date birthDate) {
        this.patientId = patientId;
        this.noteContent = noteContent;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public List<String> getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(List<String> noteContent) {
        this.noteContent = noteContent;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
