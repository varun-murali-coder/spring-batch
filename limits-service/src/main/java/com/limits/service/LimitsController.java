package com.limits.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

	@Autowired
	private LimitsConfiguration limitsConfiguration;
	
	@GetMapping("/limits-service/limits")
	public Limits getLimits() {
		
		return new Limits(limitsConfiguration.getMinimum(),limitsConfiguration.getMaximum());
		
	}
}
