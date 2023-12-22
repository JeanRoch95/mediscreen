package com.mediscreen.mediscreenwebapp.proxies;

import com.mediscreen.mediscreenwebapp.model.UserCredential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gateway-service", url = "http://gatewayservice:9002")
public interface LoginProxy {


    @PostMapping("/login")
    String login(@RequestBody UserCredential userCredential);
}
