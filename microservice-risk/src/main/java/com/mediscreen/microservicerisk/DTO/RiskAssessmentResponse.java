package com.mediscreen.microservicerisk.DTO;

public class RiskAssessmentResponse {

    private long patientId;

    private String riskLevel;

    public RiskAssessmentResponse() {
    }

    public RiskAssessmentResponse(long patientId, String riskLevel) {
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
