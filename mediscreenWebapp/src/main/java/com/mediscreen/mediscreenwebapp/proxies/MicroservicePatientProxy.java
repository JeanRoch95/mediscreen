package com.mediscreen.mediscreenwebapp.proxies;

import com.mediscreen.mediscreenwebapp.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Proxy interface for interacting with the Patient microservice through the gateway service.
 */
@FeignClient(name = "gateway-service", url = "http://gatewayservice:9002")
public interface MicroservicePatientProxy {

        /**
         * Retrieves all patients.
         *
         * @return A list of PatientBean objects representing all patients.
         */
        @GetMapping(value = "/api/patient/all")
        List<PatientBean> getAllPatient();

        /**
         * Retrieves a patient by their ID.
         *
         * @param id The ID of the patient.
         * @return The PatientBean object representing the patient.
         */
        @GetMapping(value = "api/patient/{id}")
        PatientBean getPatientById(@PathVariable("id") Long id);

        /**
         * Retrieves a patient by their last name and first name.
         *
         * @param lastName The last name of the patient.
         * @param firstName The first name of the patient.
         * @return The PatientBean object representing the patient.
         */
        @GetMapping(value ="api/patient/by-name")
        PatientBean getPatientByLastNameAndFirstName(@RequestParam("lastname") String lastName, @RequestParam("firstname") String firstName);

        /**
         * Adds a new patient.
         *
         * @param patientBean The PatientBean object containing the patient's information.
         * @return A ResponseEntity representing the result of the operation.
         */
        @PostMapping(value = "/api/patient")
        ResponseEntity<?> addPatient(@RequestBody PatientBean patientBean);

        /**
         * Updates an existing patient's information.
         *
         * @param id The ID of the patient to update.
         * @param patientBean The PatientBean object containing the updated information.
         * @return A ResponseEntity representing the result of the operation.
         */
        @PutMapping(value = "api/patient/{id}")
        ResponseEntity<?> updatePatient(@PathVariable("id") long id, @RequestBody PatientBean patientBean);

        /**
         * Deletes a patient.
         *
         * @param id The ID of the patient to delete.
         */
        @DeleteMapping(value = "api/patient/{id}")
        void deletePatient(@PathVariable("id")long id);
}
