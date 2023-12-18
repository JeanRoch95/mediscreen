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


    @PostMapping("/risk/evaluate")
    public RiskAssessmentResponse evaluateRisk(@RequestBody NoteDTO noteDTO) {

        RiskAssessmentResponse response = riskService.evaluateRisk(noteDTO);
        return response;
    }
}
