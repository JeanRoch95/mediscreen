package com.mediscreen.microservicerisk.service.Impl;

import com.mediscreen.microservicerisk.DTO.NoteDTO;
import com.mediscreen.microservicerisk.DTO.RiskAssessmentResponse;
import com.mediscreen.microservicerisk.controller.RiskController;
import com.mediscreen.microservicerisk.service.RiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
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
            "Anormal", "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps"
    );
    @Override
    public RiskAssessmentResponse evaluateRisk(NoteDTO noteDTO) {
        int age = calculateAge(noteDTO.getBirthDate());
        logger.info("Age du patient : {}", age);
        logger.info("Sexe du patient : {}", noteDTO.getGender());
        int triggerCount = countTriggerTerms(noteDTO.getNoteContent());

        String riskLevel = determineRiskLevel(age, noteDTO.getGender(), triggerCount);
        logger.info("RiskLevel 1: {}", riskLevel);

        RiskAssessmentResponse riskAssessmentResponse = new RiskAssessmentResponse();
        riskAssessmentResponse.setRiskLevel(riskLevel);
        riskAssessmentResponse.setPatientId(noteDTO.getPatientId());

        logger.info("riskLevel2 : {}", riskAssessmentResponse.getRiskLevel());

        return riskAssessmentResponse;
    }

    @Override
    public int calculateAge(Date birthdate) {
        LocalDate birthLocalDate = birthdate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthLocalDate, currentDate).getYears();
    }

    @Override
    public String normalizeString(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return normalized.toLowerCase();
    }

    @Override
    public int countTriggerTerms(List<String> notes) {
        int count = 0;
        for (String note : notes) {
            String normalizedNote = normalizeString(note);
            for (String term : triggerTerms) {
                if (normalizedNote.contains(normalizeString(term))) {
                    count++;
                    logger.info("Terme déclencheur détecté : {}", term);
                }
            }
        }
        logger.info("Nombre de trigger détecté : {}", count);
        return count;
    }

    @Override
    public String determineRiskLevel(int age, String gender, int triggerCount) {
        if (triggerCount == 0) {
            return "None";
        }

        if (age > 30 && triggerCount >= 2 && triggerCount <= 5) {
            return "Borderline";
        }

        if (age <= 30) {
            if (gender.equalsIgnoreCase("M")) {
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
        } else {
            if (triggerCount >= 8) {
                return "Early onset";
            } else if (triggerCount >= 6) {
                return "In Danger";
            }
        }
        return "None";
    }

}
