package com.mediscreen.mediscreenwebapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.mediscreenwebapp.beans.PatientBean;
import com.mediscreen.mediscreenwebapp.proxies.MicroservicePatientProxy;
import feign.FeignException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebappController {

    private final MicroservicePatientProxy patientProxy;

    public WebappController(MicroservicePatientProxy patientProxy) {
        this.patientProxy = patientProxy;
    }

    @RequestMapping("/patient")
    public String home(Model model) {
        List<PatientBean> patientBeanList = patientProxy.getAllPatient();
        model.addAttribute("patients", patientBeanList);
        return "Home";
    }

    @RequestMapping("/patient/{id}")
    public String patientById(@PathVariable Long id, Model model) {
        PatientBean patient = patientProxy.getPatientById(id);
        model.addAttribute("patient", patient);
        return "Patient";
    }

    @GetMapping("/patient/search")
    public String searchPatient(@RequestParam("lastname") String lastName,
                                @RequestParam("firstname") String firstName,
                                Model model, RedirectAttributes redirectAttributes) {
        try {
            PatientBean patient = patientProxy.getPatientByLastNameAndFirstName(lastName, firstName);
            model.addAttribute("patients", Collections.singletonList(patient));
        } catch (FeignException.NotFound e) {
            redirectAttributes.addFlashAttribute("noPatientFound", true);
            return "redirect:/"; // Redirige vers la liste des patients
        }
        return "Home"; // Remplacez par le nom de votre vue qui affiche la liste des patients
    }


    @GetMapping("/patient/add")
    public String addPatient(Model model) {
        model.addAttribute("patientBean", new PatientBean());
        return "CreatePatient";
    }

    @PostMapping("patient/add")
    public String addPatient(@ModelAttribute PatientBean patientBean, BindingResult result, Model model, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (result.hasErrors()) {
            return "CreatePatient";
        }
        try {
            patientProxy.addPatient(patientBean);
            return "redirect:/";
        } catch (FeignException.BadRequest e) {
            // Convertir la réponse JSON en Map
            Map<String, String> apiErrors = new ObjectMapper().readValue(e.contentUTF8(), new TypeReference<Map<String, String>>() {});
            // Ajouter les erreurs de l'API au BindingResult
            apiErrors.forEach((field, message) -> result.rejectValue(field, "apiError", message));
            model.addAttribute("patientBean", patientBean);
            return "CreatePatient";
        } catch (Exception e) {
            // Gérer d'autres exceptions
            redirectAttributes.addFlashAttribute("error", "Une erreur inattendue s'est produite.");
            return "redirect:/patient/add";
        }
    }

    @GetMapping("/patient/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("patient", patientProxy.getPatientById(id));
        return "UpdatePatient";
    }

    @PutMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable("id") Long id, @ModelAttribute("patient") PatientBean patientBean,
                                BindingResult result, Model model, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (result.hasErrors()) {
            model.addAttribute("patient", patientBean);
            return "UpdatePatient";
        }

        try {
            patientProxy.updatePatient(id, patientBean);
        } catch (FeignException.BadRequest e) {
            // Convertir la réponse JSON en Map
            Map<String, String> apiErrors = new ObjectMapper().readValue(e.contentUTF8(), new TypeReference<Map<String, String>>() {});
            // Ajouter les erreurs de l'API au BindingResult
            apiErrors.forEach((field, message) -> result.rejectValue(field, "apiError", message));
            model.addAttribute("patient", patientBean);
            return "UpdatePatient";
        }

        redirectAttributes.addFlashAttribute("success", "Patient mis à jour avec succès.");
        return "redirect:/";
    }

    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable("id") long id) {
        patientProxy.deletePatient(id);
        return "redirect:/";
    }

}
