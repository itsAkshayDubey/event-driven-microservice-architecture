package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.command.rest;

import java.util.Optional;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.springboot.autoconfig.EventProcessingAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class EventsReplayController {

	@Autowired
	private EventProcessingConfiguration epc; 
	
	@PostMapping("/eventProcessor/{processorName}/reset")
	public ResponseEntity<String> replayEvents(@PathVariable String processorName) {
		Optional<TrackingEventProcessor> otep = epc.eventProcessor(processorName, TrackingEventProcessor.class);
		
		if(otep.isPresent()) {
			TrackingEventProcessor tep = otep.get();
			tep.shutDown();
			tep.resetTokens();
			tep.start();
			
			return ResponseEntity.ok().body("Reset done");
		}
		else {
			return ResponseEntity.badRequest().body("Bad request");
		}
	}
}
