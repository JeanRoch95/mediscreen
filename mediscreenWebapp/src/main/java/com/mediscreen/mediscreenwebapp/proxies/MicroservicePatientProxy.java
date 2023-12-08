package com.mediscreen.mediscreenwebapp.proxies;

import com.mediscreen.mediscreenwebapp.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "microservice-patients", url = "localhost:9000")
public interface MicroservicePatientProxy {

        @GetMapping(value = "/api/patients")
        List<PatientBean> getAllPatient();

        @GetMapping(value = "api/patient/{id}")
        PatientBean getPatientById(@PathVariable("id") Long id);

        @GetMapping(value ="api/patient/by-name")
        PatientBean getPatientByLastNameAndFirstName(@RequestParam("lastname") String lastName, @RequestParam("firstname") String firstName);

        @PostMapping(value = "/api/patient")
        ResponseEntity<?> addPatient(@RequestBody PatientBean patientBean);

        @PutMapping(value = "api/patient/{id}")
        ResponseEntity<?> updatePatient(@PathVariable("id") long id, @RequestBody PatientBean patientBean);

        @DeleteMapping(value = "api/patient/{id}")
        void deletePatient(@PathVariable("id")long id);
}
