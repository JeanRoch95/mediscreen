package com.mediscreen.microservicerisk.service.Impl;

import com.mediscreen.microservicerisk.DTO.NoteDTO;
import com.mediscreen.microservicerisk.DTO.RiskAssessmentResponse;
import com.mediscreen.microservicerisk.controller.RiskController;
import com.mediscreen.microservicerisk.service.RiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class RiskServiceImpl implements RiskService {

    private static final Logger logger = LoggerFactory.getLogger(RiskServiceImpl.class);

    private final List<String> triggerTerms = List.of(
            "Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Fumeuse",
            "Anormal", "Cholestérol", "Vertiges", "Rechute", "Réaction", "Anticorps"
    );
    @Override
    public RiskAssessmentResponse evaluateRisk(NoteDTO noteDTO) {
        int age = calculateAge(noteDTO.getBirthDate());
        int triggerCount = countTriggerTerms(noteDTO.getNoteContent());

        String riskLevel = determineRiskLevel(age, noteDTO.getGender(), triggerCount);

        RiskAssessmentResponse riskAssessmentResponse = new RiskAssessmentResponse();
        riskAssessmentResponse.setRiskLevel(riskLevel);
        riskAssessmentResponse.setPatientId(noteDTO.getPatientId());

        logger.info("riskLevel : {}", riskAssessmentResponse.getRiskLevel());

        return riskAssessmentResponse;
    }

    @Override
    public int calculateAge(Date birthdate) {
        if (birthdate == null) {
            return 0;
        }
        LocalDate birthLocalDate = birthdate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthLocalDate, currentDate).getYears();
    }

    @Override
    public int countTriggerTerms(List<String> notes) {
        int count = 0;
        for (String note : notes) {
            for (String term : triggerTerms) {
                if (note.contains(term)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String determineRiskLevel(int age, String gender, int triggerCount) {
        if (triggerCount == 0) {
            return "None";
        }

        if (age > 30) {
            if (triggerCount >= 8) {
                return "Early onset";
            } else if (triggerCount >= 6) {
                return "In Danger";
            } else if (triggerCount >= 2) {
                return "Borderline";
            }
        } else {
            if (gender.equalsIgnoreCase("male")) {
                if (triggerCount >= 5) {
                    return "Early onset";
                } else if (triggerCount >= 3) {
                    return "In Danger";
                }
            } else {
                if (triggerCount >= 7) {
                    return "Early onset";
                } else if (triggerCount >= 4) {
                    return "In Danger";
                }
            }
        }

        return "None";
    }
}
