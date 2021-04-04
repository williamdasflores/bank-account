package br.com.bank.application.controller.representation;

import br.com.bank.application.controller.representation.ApiErrorRepresentation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorRepresentationHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.stream()
                .map(fieldError -> errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        globalErrors.stream()
                .map(globalError -> errors.add(globalError.getObjectName() + " " + globalError.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiErrorRepresentation apiError = new ApiErrorRepresentation(HttpStatus.BAD_REQUEST, errors);
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }

}
