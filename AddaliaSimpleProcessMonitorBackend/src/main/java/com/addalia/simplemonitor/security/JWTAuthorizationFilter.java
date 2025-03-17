package com.addalia.simplemonitor.security;



import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
//Se anota como @Component por si mas tarde queremos inyectar, por ejemplo con autowired, el IAppUserService u otro para hacer validaciones sobre el usuario
public class JWTAuthorizationFilter extends OncePerRequestFilter {


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
						
		String bearerToken = request.getHeader("Authorization");
				
		//Validacion minima, pero sencilla: aqui se podrian meter mas validaciones contra tabla usuarios u otros datos
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			bearerToken = bearerToken.replace("Bearer ", "");
			UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.getAuthentication(bearerToken);
			SecurityContextHolder.getContext().setAuthentication(usernamePAT);	
		}
		filterChain.doFilter(request, response);
				
	}

}