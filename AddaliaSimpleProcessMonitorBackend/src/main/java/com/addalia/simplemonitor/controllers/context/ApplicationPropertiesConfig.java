package com.addalia.simplemonitor.controllers.context;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationPropertiesConfig {

	@Value("${app.ignore.invalid.ssl.certificates}")
	private boolean ignoreInvalidSSLCertificates;	
	public boolean getIgnoreInvalidSSLCertificates() {
		return ignoreInvalidSSLCertificates;
	}

	@Value("${app.elapsedtime.between.executions}")
	private Long timeBetweenExecutions;
	public Long getTimeBetweenExecutions() {
		return timeBetweenExecutions;
	}
	
}
