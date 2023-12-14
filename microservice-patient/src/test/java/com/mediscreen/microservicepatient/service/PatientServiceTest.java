package com.mediscreen.microservicepatient.service;

import com.mediscreen.microservicepatient.dto.PatientDTO;
import com.mediscreen.microservicepatient.exception.PatientNotFoundException;
import com.mediscreen.microservicepatient.mapper.PatientMapper;
import com.mediscreen.microservicepatient.model.Patient;
import com.mediscreen.microservicepatient.repository.PatientRepository;
import com.mediscreen.microservicepatient.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;


    @BeforeEach
    public void setUpBeforeEachTest(){ patientService = new PatientServiceImpl(patientRepository, patientMapper);
    }

    @Test
    public void testGetPatientByFirstNameAndLastName() {
        Patient mockPatient = new Patient();
        PatientDTO mockPatientDTO = new PatientDTO();

        when(patientRepository.findByLastNameAndFirstName("Doe", "John")).thenReturn(mockPatient);
        when(patientMapper.ToDTO(mockPatient)).thenReturn(mockPatientDTO);

        PatientDTO result = patientService.getPatientByFirstNameAndLastName("Doe", "John");

        assertEquals(mockPatientDTO, result);
        verify(patientRepository).findByLastNameAndFirstName("Doe", "John");
        verify(patientMapper).ToDTO(mockPatient);
    }

    @Test
    public void testGetPatientByFirstNameAndLastNameNotFound() {
        when(patientRepository.findByLastNameAndFirstName("Doe", "John")).thenReturn(null);

        assertThrows(PatientNotFoundException.class, () -> {
            patientService.getPatientByFirstNameAndLastName("Doe", "John");
        });
    }

    @Test
    public void testGetPatientById() {
        Optional<Patient> mockPatient = Optional.of(new Patient());
        PatientDTO mockPatientDTO = new PatientDTO();

        when(patientRepository.findById(1L)).thenReturn(mockPatient);
        when(patientMapper.ToDTO(mockPatient.get())).thenReturn(mockPatientDTO);

        PatientDTO result = patientService.getPatientById(1L);

        assertEquals(mockPatientDTO, result);
    }

    @Test
    public void testUpdatePatientWhenPatientExists() {
        Long patientId = 1L;
        PatientDTO patientDTO = new PatientDTO();
        Patient existingPatient = new Patient();

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));

        patientService.updatePatient(patientId, patientDTO);

        verify(patientRepository).findById(patientId);
        verify(patientRepository).save(existingPatient);
    }

    @Test
    public void testUpdatePatientWhenPatientDoesNotExist() {
        Long patientId = 1L;
        PatientDTO patientDTO = new PatientDTO();

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient(patientId, patientDTO));

        verify(patientRepository).findById(patientId);
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    public void testDeletePatientWhenPatientExists() {
        Long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(new Patient()));

        Boolean result = patientService.deletePatient(patientId);

        assertTrue(result);
        verify(patientRepository).deleteById(patientId);
    }

    @Test
    public void testGetAllPatient() {
        List<Patient> patientList = Arrays.asList(new Patient(), new Patient());
        when(patientRepository.findAll()).thenReturn(patientList);

        Iterable<Patient> result = patientService.getAllPatient();

        assertNotNull(result);
        assertEquals(patientList, result);
        verify(patientRepository).findAll();
    }

    @Test
    public void testCreatePatientWhenPatientDoesNotExist() {
        PatientDTO patientDTO = new PatientDTO();
        Patient patient = new Patient();

        when(patientRepository.findByLastNameAndFirstName(patientDTO.getLastName(), patientDTO.getFirstName())).thenReturn(null);
        when(patientMapper.ToDTO(any(Patient.class))).thenReturn(patientDTO);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientDTO result = patientService.createPatient(patientDTO);

        assertNotNull(result);
        verify(patientRepository).findByLastNameAndFirstName(patientDTO.getLastName(), patientDTO.getFirstName());
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    public void testCreatePatientWhenPatientAlreadyExists() {
        PatientDTO patientDTO = new PatientDTO();
        Patient existingPatient = new Patient();

        when(patientRepository.findByLastNameAndFirstName(patientDTO.getLastName(), patientDTO.getFirstName())).thenReturn(existingPatient);

        assertThrows(Exception.class, () -> patientService.createPatient(patientDTO));

        verify(patientRepository).findByLastNameAndFirstName(patientDTO.getLastName(), patientDTO.getFirstName());
        verify(patientRepository, never()).save(any(Patient.class));
    }

}
