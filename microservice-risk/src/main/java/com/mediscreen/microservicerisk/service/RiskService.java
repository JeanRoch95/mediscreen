package com.mediscreen.microservicerisk.service;

import com.mediscreen.microservicerisk.DTO.NoteDTO;
import com.mediscreen.microservicerisk.DTO.RiskAssessmentResponse;

import java.util.Date;
import java.util.List;

public interface RiskService {

    RiskAssessmentResponse evaluateRisk(NoteDTO noteDTO);

    int calculateAge(Date birthdate);

    int countTriggerTerms(List<String> notes);

    String normalizeString(String input);

    String determineRiskLevel(int age, String gender, int triggerCount);
}
