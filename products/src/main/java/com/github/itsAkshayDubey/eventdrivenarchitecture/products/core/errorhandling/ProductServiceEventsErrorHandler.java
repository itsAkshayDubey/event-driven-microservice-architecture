package com.github.itsAkshayDubey.eventdrivenarchitecture.products.core.errorhandling;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

public class ProductServiceEventsErrorHandler implements ListenerInvocationErrorHandler{

	@Override
	public void onError(Exception arg0, EventMessage<?> arg1, EventMessageHandler arg2) throws Exception {

		throw arg0;
		
	}

}
