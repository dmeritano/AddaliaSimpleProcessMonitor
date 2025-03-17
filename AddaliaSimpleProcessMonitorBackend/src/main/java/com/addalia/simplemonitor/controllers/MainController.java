package com.addalia.simplemonitor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.addalia.simplemonitor.controllers.context.StaticData;
import com.addalia.simplemonitor.services.DmsDataCollectorService;
import com.addalia.simplemonitor.services.dto.RetrievedDataDto;


@RestController
public class MainController {

	@Autowired
	BuildProperties buildProperties;	
	
	@Autowired
	DmsDataCollectorService service;
	
	@GetMapping(value = {"", "/"})
	@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
	public String rootMessage() {			
		StringBuilder sb = new StringBuilder();
		sb.append("<h2")
			.append(" style=")
			.append("color:#070252;")
			.append(">AddaliaSimpleProcessMonitorBackend - Versi&oacute;n: ")
			.append(buildProperties.getVersion())
			.append("</h2>");
		return sb.toString();					
	}		
	
	@GetMapping(value = {"/api/data"})	// '/data' esta como permitAll en config. seguridad
	public RetrievedDataDto getData(@RequestParam(name = "force", required = false, defaultValue = "false") boolean force) {
		service.run(force);
		return StaticData.retrievedDataDto;
	}
	
	@GetMapping(value = {"/api/datasecured"})
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public RetrievedDataDto getDataSecured(@RequestParam(name = "force", required = false, defaultValue = "false") boolean force) {
		service.run(force);
		return StaticData.retrievedDataDto;
	}
	
	
}
