package com.mediscreen.microservicepatient.controller;

import com.mediscreen.microservicepatient.dto.PatientDTO;
import com.mediscreen.microservicepatient.model.Patient;
import com.mediscreen.microservicepatient.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
    public PatientDTO getPatientByLastNameAndFirstName(@RequestParam("lastname") String lastName, @RequestParam("firstname") String firstName) {
        LOGGER.info("Get patient with lastname : " + lastName + " and firstname : " + firstName);
        PatientDTO patientDTO = patientService.getPatientByFirstNameAndLastName(lastName, firstName);
        return patientDTO;
    }

    @PostMapping("/patient")
    public void createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        // TODO -
        //  Try - Catch
        //  Ajouter bindig result
        patientService.createPatient(patientDTO);
    }

    @PutMapping("/update/{id}")
    public void updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDTO patientDTO) {
        // TODO -
        //  Try - Catch
        //  Ajouter bindig result
        patientService.updatePatient(id, patientDTO);
    }

    @DeleteMapping("patient/{id}")
    public void deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
    }

}