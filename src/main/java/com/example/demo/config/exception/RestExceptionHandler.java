package com.example.demo.config.exception;

import java.util.HashMap;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
//import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@RequestMapping(produces = {"application/json"})
//@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	private String getMessage(String key) {
		return messageSource.getMessage(
				key, 
				null, 
				"Default message", 
				LocaleContextHolder.getLocale());
	}
	
	// Default exception
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception exception) {
		
		String message = getMessage("Exception.message");
		String detailMessage = exception.getLocalizedMessage();
		int code = 500;
		String moreInformation = "http://localhost:8080/api/v1/exception/500";
			
		ErrorResponse response = new ErrorResponse(message, detailMessage, null, code, moreInformation);
//		log.error(detailMessage, exception);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// Not found url handler
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException exception,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
			
		String message = getMessage("NoHandlerFoundException.message") 
				+ exception.getHttpMethod() + " " + exception.getRequestURL();
		String detailMessage = exception.getLocalizedMessage();
		int code = 404;
		String moreInformation = "http://localhost:8080/api/v1/exception/404";
			
		ErrorResponse response = new ErrorResponse(message, detailMessage, null, code, moreInformation);
//		log.error(detailMessage, exception);	
		return new ResponseEntity<>(response, status);
	}

	// Not support HTTP Method
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException exception, 
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String message = getMessageFromHttpRequestMethodNotSupportedException(exception);
		String detailMessage = exception.getLocalizedMessage();
		int code = 405;
		String moreInformation = "http://localhost:8080/api/v1/exception/405";
		
		ErrorResponse response = new ErrorResponse(message, detailMessage, null, code, moreInformation);
//		log.error(detailMessage, exception);
		return new ResponseEntity<>(response, status);
	}
	
	private String getMessageFromHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException exception) {
		String message = exception.getMethod() + " " + getMessage("HttpRequestMethodNotSupportedException.message");
		for (HttpMethod method : exception.getSupportedHttpMethods()) {
			message += method + " ";
		}
		return message;
	}

	// Not support media type
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		String message = getMessageFromHttpMediaTypeNotSupportedException(exception);
		String detailMessage = exception.getLocalizedMessage();
		int code = 415;
		String moreInformation = "http://localhost:8080/api/v1/exception/415";
		
		ErrorResponse response = new ErrorResponse(message, detailMessage, null, code, moreInformation);
//		log.error(detailMessage, exception);
		return new ResponseEntity<>(response, status);
	}

	private String getMessageFromHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
		String message = exception.getContentType() + " " + getMessage("HttpMediaTypeNotSupportedException.message");
		for (MediaType method : exception.getSupportedMediaTypes()) {
			message += method + ", ";
		}
		return message.substring(0, message.length() - 2);
	}
	
	// BindException: This exception is thrown when fatal binding errors occur.
	
// xu li voi annotaion mac dinh
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String message = getMessage("MethodArgumentNotValidException.message");
		String detailMessage = exception.getLocalizedMessage();
		// error
		Map<String, String> errors = new HashMap<>();
		for (ObjectError error : exception.getBindingResult().getAllErrors()) {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		}
		int code = 400;
		String moreInformation = "http://localhost:8080/api/v1/exception/400";
		
		ErrorResponse response = new ErrorResponse(message, detailMessage, errors, code, moreInformation);
//		log.error(detailMessage + "\n" + errors.toString(), exception);
		return new ResponseEntity<>(response, status);
	}
	
	// bean validation error // validate voi annotaion tu dinh nghia
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {
			
		String message = getMessage("MethodArgumentNotValidException.message");
		String detailMessage = exception.getLocalizedMessage();
		// error
		Map<String, String> errors = new HashMap<>();
		for (ConstraintViolation violation : exception.getConstraintViolations()) {
			String fieldName = violation.getPropertyPath().toString();
			String errorMessage = violation.getMessage();
			errors.put(fieldName, errorMessage);
		}
		int code = 400;
		String moreInformation = "http://localhost:8080/api/v1/exception/400";
			
		ErrorResponse response = new ErrorResponse(message, detailMessage, errors, code, moreInformation);
//		log.error(detailMessage + "\n" + errors.toString(), exception);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	// MissingServletRequestPartException: This exception is thrown when when the part of a multipart request not found
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException exception, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		String message = exception.getParameterName() + " " + getMessage("MissingServletRequestParameterException.message");
		String detailMessage = exception.getLocalizedMessage();
		int code = 400;
		String moreInformation = "http://localhost:8080/api/v1/exception/400";
		
		ErrorResponse response = new ErrorResponse(message, detailMessage, null, code, moreInformation);
//		log.error(detailMessage, exception);
		return new ResponseEntity<>(response, status);
	}
	
	// TypeMismatchException: This exception is thrown when try to set bean property with wrong type.
	// MethodArgumentTypeMismatchException: This exception is thrown when method argument is not the expected type:
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {

		String message = exception.getName() + " " + getMessage("MethodArgumentTypeMismatchException.message") 
			+ exception.getRequiredType().getName();
		String detailMessage = exception.getLocalizedMessage();
		int code = 400;
		String moreInformation = "http://localhost:8080/api/v1/exception/400";
		
		ErrorResponse response = new ErrorResponse(message, detailMessage, null, code, moreInformation);
//		log.error(detailMessage, exception);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
}

