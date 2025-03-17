package com.addalia.simplemonitor.security;


import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private static Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		
		AuthCredentials authCredentials = new AuthCredentials();
		try {
			authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
				authCredentials.getUsername(),
				authCredentials.getPassword(),
				Collections.emptyList());
				
		return getAuthenticationManager().authenticate(usernamePAT);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
						
		UserDetailsImpl userDetails = (UserDetailsImpl)authResult.getPrincipal();
		
		logger.info("Autenticacion exitosa para usuario " + userDetails.getUsername());
		
		String token = TokenUtils.createToken(userDetails, authResult);
		
		Map<String, String> user = new HashMap<>();
		user.put("fullname", userDetails.getFullname());
		user.put("username", userDetails.getUsername());
		//user.put("password", "");
		user.put("token", token);
		ObjectMapper mapper = new ObjectMapper();
		String jsonUser = mapper.writerWithDefaultPrettyPrinter()
		  .writeValueAsString(user);
		
		//response.addHeader("Authorization", "Bearer " + token); - ahora lo lo mando en el body
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonUser);
	    response.getWriter().flush();
		
								
		super.successfulAuthentication(request, response, chain, authResult);
	}
	
	
}
