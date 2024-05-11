//package com.example.demo.config;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.ConstraintViolationException;
//
//@ControllerAdvice
//@RequestMapping(produces = {"application/json"})
//public class ValidationExceptionHandler extends ResponseEntityExceptionHandler{
//	
//	// handle validation
//	@ExceptionHandler(Exception.class)
//	@ResponseBody
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(
//			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//	
//		Map<String, String> errors = new HashMap<>();
//
//		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
//			String fieldName = ((FieldError) error).getField();
//			String errorMessage = error.getDefaultMessage();
//			errors.put(fieldName, errorMessage);
//		}
//
//		return new ResponseEntity<>(errors, status);
//	}
//
//	// handle constraintviolation
//	@SuppressWarnings("rawtypes")
//	@ExceptionHandler(ConstraintViolationException.class)
//	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {
//		Map<String, String> errors = new HashMap<>();
//		
//		for (ConstraintViolation violation : exception.getConstraintViolations()) {
//			String fieldName = violation.getPropertyPath().toString();
//			String errorMessage = violation.getMessage();
//			errors.put(fieldName, errorMessage);
//		}
//		
//		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//	}
//}
