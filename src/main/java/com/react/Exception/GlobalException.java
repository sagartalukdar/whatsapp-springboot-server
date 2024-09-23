package com.react.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> userExceptionHandler(WebRequest req,UserException ue){
		ErrorDetails err=new ErrorDetails(ue.getMessage(), req.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MessageException.class)
	public ResponseEntity<ErrorDetails> messageExceptionHandler(MessageException me,WebRequest req){
		return new ResponseEntity<ErrorDetails>(new ErrorDetails(me.getMessage(), req.getDescription(false), LocalDateTime.now()),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ChatException.class)
	public ResponseEntity<ErrorDetails> chatExceptionHandler(ChatException ce,WebRequest req){
		return new ResponseEntity<ErrorDetails>(new ErrorDetails(ce.getMessage(), req.getDescription(false), LocalDateTime.now()),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> methodArgumentNotValidException(MethodArgumentNotValidException methArgNotVldExc,WebRequest req){
		String errorMessage=methArgNotVldExc.getBindingResult().getFieldError().getDefaultMessage();
		return new ResponseEntity<ErrorDetails>(new ErrorDetails("method argument not valid", errorMessage, LocalDateTime.now()),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> noHandlerFoundException(NoHandlerFoundException ne,WebRequest req){
		return new ResponseEntity<ErrorDetails>(new ErrorDetails("endpoint not found", ne.getMessage(), LocalDateTime.now()),HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> exceptionHandler(Exception e,WebRequest req){
		return new ResponseEntity<ErrorDetails>(new ErrorDetails(e.getMessage(), req.getDescription(false), LocalDateTime.now()),HttpStatus.BAD_REQUEST);
	}
}
