package com.mediscreen.microservicepatient.controller;

import com.mediscreen.microservicepatient.dto.PatientDTO;
import com.mediscreen.microservicepatient.exception.PatientNotFoundException;
import com.mediscreen.microservicepatient.model.Patient;
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
@RequestMapping("/api/")
public class PatientController {

    private final PatientService patientService;

    private final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public String homePatient() {
        LOGGER.info("Home OK :)");
        return "Hi!";
    }

    @GetMapping("/patients")
    public Iterable getAllPatients() {
        return patientService.getAllPatient();
    }

    @GetMapping("/patient/{id}")
    public PatientDTO getPatientById(@PathVariable long id) {
        LOGGER.info("Get patient with id : " + id);
        PatientDTO patientDTO = patientService.getPatientById(id);
        return patientDTO;
    }

    @GetMapping("/patient/by-name")
    public ResponseEntity<?> getPatientByLastNameAndFirstName(@RequestParam("lastname") String lastName, @RequestParam("firstname") String firstName) {
        try {
            LOGGER.info("Get patient with lastname : " + lastName + " and firstname : " + firstName);
            PatientDTO patientDTO = patientService.getPatientByFirstNameAndLastName(lastName, firstName);
            return ResponseEntity.ok(patientDTO);
        } catch (PatientNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

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

    @PutMapping("/update/{id}")
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


    @DeleteMapping("patient/{id}")
    public void deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
    }

}