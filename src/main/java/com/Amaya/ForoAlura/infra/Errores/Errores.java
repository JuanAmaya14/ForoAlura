package com.Amaya.ForoAlura.infra.Errores;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Errores {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity badRequestModificarCorreo(ConstraintViolationException  e){

        return ResponseEntity.badRequest().body(e.getConstraintViolations().toString());

    }

    @ExceptionHandler(BeanCreationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void errorDeMierda(BeanCreationException e){

        System.out.println(("ERROR DE MIERDA \n" +
                e.getMessage()));

    }

}
