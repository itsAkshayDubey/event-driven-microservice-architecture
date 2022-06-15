package com.github.itsAkshayDubey.eventdrivenarchitecture.core.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchUserPaymentDetailsQuery {

	private String userId;
	
}
