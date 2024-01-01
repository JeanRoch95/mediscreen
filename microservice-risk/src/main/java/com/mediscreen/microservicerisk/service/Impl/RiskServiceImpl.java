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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RiskServiceImpl implements RiskService {

    private static final Logger logger = LoggerFactory.getLogger(RiskServiceImpl.class);

    private static final String RISK_NONE = "None";
    private static final String RISK_BORDERLINE = "Borderline";
    private static final String RISK_IN_DANGER = "In Danger";
    private static final String RISK_EARLY_ONSET = "Early onset";

    private final Set<String> normalizedTriggerTerms = new HashSet<>();


    // Normaliser les termes déclencheur une seule fois à l'initialisation pour green code
    // Utilisation HashSet pour recherche plus rapide et efficace
    public RiskServiceImpl() {
        List<String> triggerTerms = List.of(
                "Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Fumeuse",
                "Anormal", "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps"
        );
        triggerTerms.forEach(term -> normalizedTriggerTerms.add(normalizeString(term)));
    }
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
            for (String term : normalizedTriggerTerms) {
                if (normalizedNote.contains(term)) {
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
            return RISK_NONE;
        }

        boolean isMale = gender.equalsIgnoreCase("M");
        boolean isYoungerThan30 = age <= 30;

        if (isYoungerThan30) {
            if (isMale && triggerCount >= 5 || !isMale && triggerCount >= 7) {
                return RISK_EARLY_ONSET;
            }
            if (isMale && triggerCount >= 3 || !isMale && triggerCount >= 4) {
                return RISK_IN_DANGER;
            }
        } else {
            if (triggerCount >= 8) {
                return RISK_EARLY_ONSET;
            }
            if (triggerCount >= 6) {
                return RISK_IN_DANGER;
            }
            if (triggerCount >= 2) {
                return RISK_BORDERLINE;
            }
        }

        return RISK_NONE;
    }


}
