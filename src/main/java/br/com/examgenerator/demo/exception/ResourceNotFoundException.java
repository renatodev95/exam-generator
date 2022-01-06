package br.com.examgenerator.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    // Quando essa exception for lancada, automaticamente o Spring vai
    // retornar o status HttpStatus.NOT_FOUND
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
