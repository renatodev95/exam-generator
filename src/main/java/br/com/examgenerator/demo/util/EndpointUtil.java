package br.com.examgenerator.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static org.springframework.http.HttpStatus.*;

@Service
public class EndpointUtil implements Serializable {

    public ResponseEntity<?> returnObjectOrNotFound(Object object) {
        return object == null ? new ResponseEntity<>(NOT_FOUND) : new ResponseEntity<>(object, OK);
    }
}
