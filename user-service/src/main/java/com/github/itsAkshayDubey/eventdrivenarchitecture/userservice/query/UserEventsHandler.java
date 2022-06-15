package com.github.itsAkshayDubey.eventdrivenarchitecture.userservice.query;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.github.itsAkshayDubey.eventdrivenarchitecture.core.payment.PaymentDetails;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.query.FetchUserPaymentDetailsQuery;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.user.User;

@Component
public class UserEventsHandler {
	
	@QueryHandler
	public User handle(FetchUserPaymentDetailsQuery fupdq) {
		PaymentDetails pd = PaymentDetails.builder()
				.cardNumber("12345")
				.name("John Doe")
				.validUntilMonth(12)
				.validUntilYear(2022)
				.cvv("123")
				.build();
		
		User userRest = User.builder()
				.firstName("John")
				.lastName("Doe")
				.userId(fupdq.getUserId())
				.paymentDetails(pd)
				.build();
		
		return userRest;
	}

}
