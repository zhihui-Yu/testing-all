package com.spring.service.controller;

import com.spi.test.api.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ServiceLoader;

/**
 * @author simple
 */
@RestController
public class ServiceController implements ServiceApi {
    @GetMapping("pay")
    @Override
    public void pay() {
        ServiceLoader<PaymentService> services = ServiceLoader.load(PaymentService.class);
        services.findFirst().ifPresent(service -> service.pay());
    }
}
