package com.mediscreen.microservicepatient.service.impl;

import com.mediscreen.microservicepatient.dto.PatientDTO;
import com.mediscreen.microservicepatient.exception.PatientNotFoundException;
import com.mediscreen.microservicepatient.mapper.PatientMapper;
import com.mediscreen.microservicepatient.model.Patient;
import com.mediscreen.microservicepatient.repository.PatientRepository;
import com.mediscreen.microservicepatient.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    private PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public PatientDTO getPatientByFirstNameAndLastName(String lastName, String firstName) {
        Patient patient = patientRepository.findByLastNameAndFirstName(lastName, firstName);
        if(patient == null) {
            throw new PatientNotFoundException("Aucun patient trouvé avec le nom: " + lastName + ", et le prénom: " + firstName);
        }
        return patientMapper.ToDTO(patient);
    }

    @Override
    public PatientDTO getPatientById(long id) {
        Optional<Patient> patient = patientRepository.findById(id);
            PatientDTO patientDTO = patientMapper.ToDTO(patient.get());
            return patientDTO;
}

    @Override
    public Iterable getAllPatient() {
        return patientRepository.findAll();
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {

        Patient existingPatient = patientRepository.findByLastNameAndFirstName(patientDTO.getLastName(), patientDTO.getFirstName());
        if (existingPatient != null) {
            throw new PatientNotFoundException("Le patient que vous essayé de créer existe déjà.");
        }

        Patient patient = new Patient();
        patient.setLastName(patientDTO.getLastName());
        patient.setFirstName(patientDTO.getFirstName());
        patient.setAddress(patientDTO.getAddress());
        patient.setGender(patientDTO.getGender());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());

        patientRepository.save(patient);

        return patientMapper.ToDTO(patient);
    }

    @Override
    public void updatePatient(Long id, PatientDTO patientDTO) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if(optionalPatient.isPresent()) {

            optionalPatient.get().setLastName(patientDTO.getLastName());
            optionalPatient.get().setFirstName(patientDTO.getFirstName());
            optionalPatient.get().setAddress(patientDTO.getAddress());
            optionalPatient.get().setGender(patientDTO.getGender());
            optionalPatient.get().setDateOfBirth(patientDTO.getDateOfBirth());
            optionalPatient.get().setPhoneNumber(patientDTO.getPhoneNumber());

            patientRepository.save(optionalPatient.get());
        } else {
            throw new PatientNotFoundException("Le patient que vous essayé de modifier n'existe pas.");
        }
    }

    @Override
    public Boolean deletePatient(Long id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (optionalPatient.isPresent()) {
            patientRepository.deleteById(id);
            return true;
        } else {
            throw new PatientNotFoundException("Le patient que vous essayé de supprimer n'existe pas.");
        }
    }
}
