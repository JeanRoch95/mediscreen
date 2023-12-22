package com.mediscreen.microservicepatient.service;

import com.mediscreen.microservicepatient.dto.PatientDTO;

/**
 * Service interface for managing patient-related operations.
 */
public interface PatientService {

    /**
     * Retrieves a patient by their first name and last name.
     *
     * @param lastName The last name of the patient.
     * @param firstName The first name of the patient.
     * @return The patient DTO.
     */
    PatientDTO getPatientByFirstNameAndLastName(String lastName, String firstName);

    /**
     * Retrieves a patient by their ID.
     *
     * @param id The ID of the patient.
     * @return The patient DTO.
     */
    PatientDTO getPatientById(long id);

    /**
     * Retrieves all patients.
     *
     * @return An iterable list of all patients.
     */
    Iterable getAllPatient();

    /**
     * Creates a new patient.
     *
     * @param patientDTO The patient DTO.
     * @return The created patient DTO.
     */
    PatientDTO createPatient(PatientDTO patientDTO);

    /**
     * Updates an existing patient.
     *
     * @param id The ID of the patient to update.
     * @param patientDTO The updated patient DTO.
     */
    void updatePatient(Long id, PatientDTO patientDTO);

    /**
     * Deletes a patient by their ID.
     *
     * @param id The ID of the patient to delete.
     * @return A boolean indicating whether the deletion was successful.
     */
    Boolean deletePatient(Long id);
}
