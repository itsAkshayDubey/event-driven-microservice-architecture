package com.github.itsAkshayDubey.eventdrivenarchitecture.paymentservice.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.itsAkshayDubey.eventdrivenarchitecture.paymentservice.core.entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment, String>{

}
