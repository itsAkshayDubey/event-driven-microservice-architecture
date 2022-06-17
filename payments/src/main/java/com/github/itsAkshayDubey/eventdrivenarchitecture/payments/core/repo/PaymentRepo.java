package com.github.itsAkshayDubey.eventdrivenarchitecture.payments.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.itsAkshayDubey.eventdrivenarchitecture.payments.core.entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment, String>{

}
