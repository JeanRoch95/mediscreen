package com.mediscreen.microservicerisk.controller;

import com.mediscreen.microservicerisk.DTO.NoteDTO;
import com.mediscreen.microservicerisk.DTO.RiskAssessmentResponse;
import com.mediscreen.microservicerisk.service.Impl.RiskServiceImpl;
import com.mediscreen.microservicerisk.service.RiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class RiskController {

    private final RiskServiceImpl riskService;

    private static final Logger logger = LoggerFactory.getLogger(RiskController.class);


    public RiskController(RiskServiceImpl riskService) {
        this.riskService = riskService;
    }

    /**
     * Endpoint for evaluating the risk level based on note data.
     * Accepts a NoteDTO object and returns a RiskAssessmentResponse.
     *
     * @param noteDTO Data transfer object containing note details for risk evaluation.
     * @return A response object containing the evaluated risk level.
     */
    @PostMapping("/risk/evaluate")
    public RiskAssessmentResponse evaluateRisk(@RequestBody NoteDTO noteDTO) {

        return riskService.evaluateRisk(noteDTO);
    }
}
