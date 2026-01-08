package com.jiujitsu.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<?> handleNaoEncontrado(RecursoNaoEncontradoException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidacao(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", "Erro de validação");
		body.put("errors", ex.getBindingResult().getFieldErrors().stream()
				.map(err -> Map.of("field", err.getField(), "error", err.getDefaultMessage())));
		return ResponseEntity.badRequest().body(body);
	}
}
