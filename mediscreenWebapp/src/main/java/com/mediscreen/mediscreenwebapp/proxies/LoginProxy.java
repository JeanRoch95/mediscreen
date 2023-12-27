package com.mediscreen.mediscreenwebapp.proxies;

import com.mediscreen.mediscreenwebapp.beans.CredentialBean;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "gateway-service", url = "http://gatewayservice:9002")
public interface LoginProxy {

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void authenticate(@RequestBody CredentialBean credentialBean);

    @GetMapping("/logout")
    void logout();

}
