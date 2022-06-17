package com.github.itsAkshayDubey.eventdrivenarchitecture.payments.query;

import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.itsAkshayDubey.eventdrivenarchitecture.core.events.PaymentProcessedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.payments.core.entity.Payment;
import com.github.itsAkshayDubey.eventdrivenarchitecture.payments.core.repo.PaymentRepo;

@Component
public class PaymentProjection {
	
	

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentProjection.class);

    @Autowired
    private PaymentRepo paymentRepository;


    @EventHandler
    public void on(PaymentProcessedEvent event) {
        LOGGER.info("PaymentProcessedEvent is called for orderId: " + event.getOrderId());

        Payment payment = new Payment();
        BeanUtils.copyProperties(event, payment);

        paymentRepository.save(payment);

    }

}
