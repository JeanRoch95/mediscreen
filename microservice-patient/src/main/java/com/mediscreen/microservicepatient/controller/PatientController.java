package com.mediscreen.microservicepatient.controller;

import com.mediscreen.microservicepatient.dto.PatientDTO;
import com.mediscreen.microservicepatient.exception.PatientNotFoundException;
import com.mediscreen.microservicepatient.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:9002")
@RequestMapping("/api/")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Retrieves all patients.
     *
     * @return An iterable list of all patients.
     */
    @GetMapping("/patient/all")
    public Iterable getAllPatients() {
        return patientService.getAllPatient();
    }

    /**
     * Retrieves a patient by their ID.
     *
     * @param id The ID of the patient.
     * @return The patient DTO.
     */
    @GetMapping("/patient/{id}")
    public PatientDTO getPatientById(@PathVariable long id) {
        return patientService.getPatientById(id);
    }

    /**
     * Retrieves a patient by their last name and first name.
     *
     * @param lastName The last name of the patient.
     * @param firstName The first name of the patient.
     * @return A response entity containing the patient DTO or an error message.
     */
    @GetMapping("/patient/by-name")
    public ResponseEntity<?> getPatientByLastNameAndFirstName(@RequestParam("lastname") String lastName, @RequestParam("firstname") String firstName) {
        try {
            PatientDTO patientDTO = patientService.getPatientByFirstNameAndLastName(lastName, firstName);
            return ResponseEntity.ok(patientDTO);
        } catch (PatientNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    /**
     * Creates a new patient.
     *
     * @param patientDTO The patient DTO.
     * @param bindingResult The binding result for validation.
     * @return A response entity indicating the result of the operation.
     */
    @PostMapping("/patient")
    public ResponseEntity<?> createPatient(@Valid @RequestBody PatientDTO patientDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });

            return ResponseEntity.badRequest().body(errors);
        }

        patientService.createPatient(patientDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates an existing patient.
     *
     * @param id The ID of the patient to update.
     * @param patientDTO The updated patient DTO.
     * @param bindingResult The binding result for validation.
     * @return A response entity indicating the result of the operation.
     */
    @PutMapping("/patient/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDTO patientDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });

            return ResponseEntity.badRequest().body(errors);
        }

        patientService.updatePatient(id, patientDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a patient by their ID.
     *
     * @param id The ID of the patient to delete.
     */
    @DeleteMapping("patient/{id}")
    public void deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
    }

}