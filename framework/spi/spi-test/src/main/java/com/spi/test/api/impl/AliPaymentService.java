package com.spi.test.api.impl;

import com.spi.test.api.PaymentService;

/**
 * @author simple
 */
public class AliPaymentService implements PaymentService {
    @Override
    public void pay() {
        System.out.println("ali paid");
    }
}
