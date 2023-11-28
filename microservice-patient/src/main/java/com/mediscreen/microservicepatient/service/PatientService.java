package com.mediscreen.microservicepatient.service;

import com.mediscreen.microservicepatient.dto.PatientDTO;

public interface PatientService {

    PatientDTO getPatientByFirstNameAndLastName(String lastName, String firstName);

    PatientDTO getPatientById(long id);

    Iterable getAllPatient();

    PatientDTO createPatient(PatientDTO patientDTO);

    void updatePatient(Long id, PatientDTO patientDTO);

    Boolean deletePatient(Long id);
}
