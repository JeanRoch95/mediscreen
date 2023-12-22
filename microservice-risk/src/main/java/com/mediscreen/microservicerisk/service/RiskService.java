package com.mediscreen.microservicerisk.service;

import com.mediscreen.microservicerisk.DTO.NoteDTO;
import com.mediscreen.microservicerisk.DTO.RiskAssessmentResponse;

import java.util.Date;
import java.util.List;

/**
 * Service interface for managing risk assessment operations.
 */
public interface RiskService {

    /**
     * Evaluates the risk level based on the provided note data.
     *
     * @param noteDTO Data transfer object containing note details.
     * @return A response object containing the evaluated risk level.
     */
    RiskAssessmentResponse evaluateRisk(NoteDTO noteDTO);

    /**
     * Calculates the age of a person based on their birthdate.
     *
     * @param birthdate The birthdate of the person.
     * @return The calculated age.
     */
    int calculateAge(Date birthdate);

    /**
     * Counts the number of trigger terms present in a list of notes.
     *
     * @param notes A list of notes to be analyzed.
     * @return The count of trigger terms found.
     */
    int countTriggerTerms(List<String> notes);

    /**
     * Normalizes a string by removing accents and converting to lower case.
     *
     * @param input The string to be normalized.
     * @return The normalized string.
     */
    String normalizeString(String input);

    /**
     * Determines the risk level based on age, gender, and the count of trigger terms.
     *
     * @param age The age of the individual.
     * @param gender The gender of the individual.
     * @param triggerCount The count of trigger terms found in the notes.
     * @return The determined risk level.
     */
    String determineRiskLevel(int age, String gender, int triggerCount);
}
