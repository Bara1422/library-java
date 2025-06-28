package com.example.SimpleCrud.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException ex) {
        return ResponseEntity.status(ex.getStatus()).body(Map.of("error", ex.getMessage()));
    }

   /*  @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error interno del servidor: " + ex.getMessage()));
    } */

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidFormatException(InvalidFormatException ex) {
        if (ex.getTargetType().isEnum()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("error",
                            "Valor inválido para el campo 'estado'. Debe ser uno de: LEIDO, NO_LEIDO o LEYENDO."));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error", "Formato de valor inválido."));
    }

}
