package com.addalia.simplemonitor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.addalia.simplemonitor.services.dto.MessageDTO;

@RestControllerAdvice
public class ControllerAdvice {

	
	/*
	@ExceptionHandler(value = RequestException.class)
	public ResponseEntity<ErrorDTO> requestExceptionHandler(RequestException ex){
		ErrorDTO error = new ErrorDTO(ex.getCode(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	*/
	
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<MessageDTO> handleAccessDeniedException(AccessDeniedException ex) {
    	MessageDTO message = new MessageDTO(String.valueOf(HttpStatus.FORBIDDEN.value()),ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(value = RequestException.class)
	public ResponseEntity<MessageDTO> requestExceptionHandler(RequestException ex){
		MessageDTO message = new MessageDTO(ex.getCode(), ex.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}	
		
	@ExceptionHandler(value = BusinessLogicException.class)
	public ResponseEntity<ErrorDTO> businessLogicExceptionHandler(BusinessLogicException ex){
		ErrorDTO error = new ErrorDTO(ex.getCode(), ex.getMessage());
		return new ResponseEntity<>(error, ex.getStatus());
	}
	
	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<ErrorDTO> runtimeExceptionHandler(RuntimeException ex){
		ErrorDTO error = new ErrorDTO(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	/*
	@ExceptionHandler(value = org.springframework.security.access.AccessDeniedException.class)
	public ResponseEntity<ErrorDTO> accessDeniedExceptionHandler(AccessDeniedException ex){
		ErrorDTO error = new ErrorDTO(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}	
	*/
	
}

