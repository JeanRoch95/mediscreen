package com.mediscreen.mediscreenwebapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.mediscreenwebapp.DTO.RiskAssessmentRequest;
import com.mediscreen.mediscreenwebapp.DTO.RiskAssessmentResponse;
import com.mediscreen.mediscreenwebapp.DTO.RiskLevelDTO;
import com.mediscreen.mediscreenwebapp.beans.CredentialBean;
import com.mediscreen.mediscreenwebapp.beans.NoteBean;
import com.mediscreen.mediscreenwebapp.beans.PatientBean;
import com.mediscreen.mediscreenwebapp.proxies.LoginProxy;
import com.mediscreen.mediscreenwebapp.proxies.MicroserviceNoteProxy;
import com.mediscreen.mediscreenwebapp.proxies.MicroservicePatientProxy;
import com.mediscreen.mediscreenwebapp.proxies.MicroserviceRiskProxy;
import com.mediscreen.mediscreenwebapp.service.UserCredentialsService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class WebappController {

    private final MicroservicePatientProxy patientProxy;

    private final MicroserviceNoteProxy noteProxy;

    private final MicroserviceRiskProxy riskProxy;

    private final LoginProxy loginProxy;

    private final UserCredentialsService userCredentialsService;

    private static final Logger logger = LoggerFactory.getLogger(WebappController.class);


    public WebappController(MicroservicePatientProxy patientProxy, MicroserviceNoteProxy noteProxy, MicroserviceRiskProxy riskProxy, LoginProxy loginProxy, UserCredentialsService userCredentialsService) {
        this.patientProxy = patientProxy;
        this.noteProxy = noteProxy;
        this.riskProxy = riskProxy;
        this.loginProxy = loginProxy;
        this.userCredentialsService = userCredentialsService;
    }

    /**
     * Displays the home page with a list of patients and notes.
     *
     * @param model The model to add attributes to the view.
     * @return The name of the view to display.
     */
    @RequestMapping("/patient")
    public String home(Model model) {
        List<PatientBean> patientBeanList = patientProxy.getAllPatient();
        List<NoteBean> noteBeanList = noteProxy.getAllNote();
        logger.info("PatientId : {}, ", noteBeanList.get(3).getPatientId());
        logger.info("note : {}, ", noteBeanList.get(1).getRiskLevel());
        model.addAttribute("patients", patientBeanList);
        model.addAttribute("notes", noteBeanList);
        return "Home";
    }

    /**
     * Displays details of a specific patient.
     *
     * @param id The ID of the patient to display.
     * @param model The model to add attributes to the view.
     * @return The name of the view to display.
     */
    @RequestMapping("/patient/{id}")
    public String patientById(@PathVariable Long id, Model model) {
        PatientBean patient = patientProxy.getPatientById(id);
        model.addAttribute("patient", patient);
        return "Patient";
    }

    /**
     * Searches for a patient by last name and first name.
     *
     * @param lastName The last name of the patient.
     * @param firstName The first name of the patient.
     * @param model The model to add attributes to the view.
     * @param redirectAttributes Redirect attributes for displaying messages.
     * @return The name of the view to display or a redirection if no patient is found.
     */
    @GetMapping("/patient/search")
    public String searchPatient(@RequestParam("lastname") String lastName,
                                @RequestParam("firstname") String firstName,
                                Model model, RedirectAttributes redirectAttributes) {
        try {
            PatientBean patient = patientProxy.getPatientByLastNameAndFirstName(lastName, firstName);
            model.addAttribute("patients", Collections.singletonList(patient));
        } catch (FeignException.NotFound e) {
            redirectAttributes.addFlashAttribute("noPatientFound", true);
            return "redirect:/";
        }
        return "Home";
    }

    /**
     * Displays the form to add a new patient.
     *
     * @param model The model to add attributes to the view.
     * @return The name of the view to display.
     */
    @GetMapping("/patient/add")
    public String addPatient(Model model) {
        model.addAttribute("patientBean", new PatientBean());
        return "CreatePatient";
    }

    /**
     * Displays the form to add a new note.
     *
     * @param model The model to add attributes to the view.
     * @return The name of the view to display.
     */
    @GetMapping("note/add")
    public String addNote(Model model) {
        model.addAttribute("noteBean", new NoteBean());
        return "CreateNote";
    }

    /**
     * Adds a new note and updates the risk level for the associated patient.
     *
     * @param noteBean The NoteBean object containing the note information.
     * @return Redirects to the patient list page.
     */
    @PostMapping("note/add")
    public String addNote(@ModelAttribute NoteBean noteBean) {
        noteProxy.createNote(noteBean);

        PatientBean patientBean = patientProxy.getPatientById(noteBean.getPatientId());

        List<NoteBean> allNotes = noteProxy.getAllNotesById(patientBean.getId());
        List<String> noteContents = allNotes.stream()
                .map(NoteBean::getNote)
                .collect(Collectors.toList());
        RiskAssessmentRequest request = new RiskAssessmentRequest();
        request.setPatientId(noteBean.getPatientId());
        request.setNoteContent(noteContents);
        request.setGender(patientBean.getGender());
        request.setBirthDate(patientBean.getDateOfBirth());

        RiskAssessmentResponse response = riskProxy.evaluateRisk(request);

        logger.info("response risk: {}",response.getRiskLevel());
        logger.info("response id: {}",response.getPatientId());
        RiskLevelDTO riskLevelDTO = new RiskLevelDTO();
        riskLevelDTO.setPatientId(noteBean.getPatientId());
        riskLevelDTO.setRiskLevel(response.getRiskLevel());

        noteProxy.updateRiskLevel(riskLevelDTO);
        return "redirect:/patient";
    }

    /**
     * Adds a new patient.
     *
     * @param patientBean The PatientBean object containing the patient information.
     * @param result BindingResult to handle validation errors.
     * @param model Model to pass data to the view.
     * @param redirectAttributes RedirectAttributes to pass flash attributes.
     * @return Redirects to the patient list page or stays on the create patient page in case of errors.
     * @throws JsonProcessingException If parsing of API errors fails.
     */
    @PostMapping("patient/add")
    public String addPatient(@ModelAttribute PatientBean patientBean, BindingResult result, Model model, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (result.hasErrors()) {
            return "CreatePatient";
        }
        try {
            patientProxy.addPatient(patientBean);
            return "redirect:/patient";
        } catch (FeignException.BadRequest e) {
            Map<String, String> apiErrors = new ObjectMapper().readValue(e.contentUTF8(), new TypeReference<Map<String, String>>() {});
            apiErrors.forEach((field, message) -> result.rejectValue(field, "apiError", message));
            model.addAttribute("patientBean", patientBean);
            return "CreatePatient";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur inattendue s'est produite.");
            return "redirect:/patient/add";
        }
    }

    /**
     * Displays the form to update a patient.
     *
     * @param id The ID of the patient to update.
     * @param model Model to pass data to the view.
     * @return The update patient page.
     */
    @GetMapping("/patient/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("patient", patientProxy.getPatientById(id));
        return "UpdatePatient";
    }

    /**
     * Updates a patient's information.
     *
     * @param id The ID of the patient to update.
     * @param patientBean The PatientBean object containing the updated patient information.
     * @param result BindingResult to handle validation errors.
     * @param model Model to pass data to the view.
     * @param redirectAttributes RedirectAttributes to pass flash attributes.
     * @return Redirects to the patient list page or stays on the update patient page in case of errors.
     * @throws JsonProcessingException If parsing of API errors fails.
     */
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
            Map<String, String> apiErrors = new ObjectMapper().readValue(e.contentUTF8(), new TypeReference<Map<String, String>>() {});
            apiErrors.forEach((field, message) -> result.rejectValue(field, "apiError", message));
            model.addAttribute("patient", patientBean);
            return "UpdatePatient";
        }

        redirectAttributes.addFlashAttribute("success", "Patient mis à jour avec succès.");
        return "redirect:/patient";
    }

    /**
     * Deletes a patient.
     *
     * @param id The ID of the patient to delete.
     * @return Redirects to the patient list page.
     */
    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable("id") long id) {
        patientProxy.deletePatient(id);
        return "redirect:/patient";
    }

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    // TODO JavaDoc
    @PostMapping("/login")
    public String performLogin(@RequestParam String username, @RequestParam String password) {
        CredentialBean credentialBean = new CredentialBean(username, password);
        userCredentialsService.saveCredentials(credentialBean);
        loginProxy.authenticate(credentialBean);
        return "redirect:/patient";
    }

}
