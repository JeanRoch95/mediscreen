package com.mediscreen.mediscreenwebapp.proxies;

import com.mediscreen.mediscreenwebapp.DTO.RiskAssessmentRequest;
import com.mediscreen.mediscreenwebapp.DTO.RiskAssessmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Proxy interface for interacting with the Risk microservice through the gateway service.
 */
@FeignClient(name = "gateway-service", url = "http://gatewayservice:9002")
public interface MicroserviceRiskProxy {

    /**
     * Evaluates the risk level for a patient based on their medical notes.
     *
     * @param riskAssessmentRequest The RiskAssessmentRequest object containing patient details and medical notes.
     * @return A RiskAssessmentResponse object containing the evaluated risk level.
     */
    @PostMapping("/api/risk/evaluate")
    RiskAssessmentResponse evaluateRisk(@RequestBody RiskAssessmentRequest riskAssessmentRequest);
}
