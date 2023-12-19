package com.mediscreen.microservicenote.dto;

public class RiskLevelDTO {

    private long patientId;

    private String riskLevel;

    public RiskLevelDTO() {
    }

    public RiskLevelDTO(long patientId, String riskLevel) {
        this.patientId = patientId;
        this.riskLevel = riskLevel;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
