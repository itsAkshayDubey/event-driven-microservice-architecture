package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.errorhandling;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
	
	private String message;
	private Date timestamp;

}
