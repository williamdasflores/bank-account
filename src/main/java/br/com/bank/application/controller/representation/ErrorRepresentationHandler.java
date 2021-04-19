package br.com.bank.application.controller.representation;

import br.com.bank.domain.exception.BankException;
import br.com.bank.v1.representation.ErrorInnerRepresentation;
import br.com.bank.v1.representation.ErrorRepresentation;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
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
        List<ErrorInnerRepresentation> errors = new ArrayList<>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.stream()
                .map(fieldError -> {
                            ErrorInnerRepresentation innerError = new ErrorInnerRepresentation();
                            innerError.setError(HttpStatus.BAD_REQUEST.name());
                            innerError.setMessage(fieldError.getField() + " " + fieldError.getDefaultMessage());
                            errors.add(innerError);
                            return errors;
                        })
                .collect(Collectors.toList());
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        globalErrors.stream()
                .map(globalError -> {
                    ErrorInnerRepresentation innerError = new ErrorInnerRepresentation();
                    innerError.setError(HttpStatus.BAD_REQUEST.name());
                    innerError.setMessage(globalError.getObjectName() + " " + globalError.getDefaultMessage());
                    errors.add(innerError);
                    return errors;
                })
                .collect(Collectors.toList());

        ErrorRepresentation errorRepresentation = new ErrorRepresentation();
        errorRepresentation.addAll(errors);
        return handleExceptionInternal(
                ex, errorRepresentation, headers, HttpStatus.BAD_REQUEST, request);
    }

    public ResponseEntity handleUnprocessableEntityError(BankException ex) {
        ErrorInnerRepresentation innerError = new ErrorInnerRepresentation();
        innerError.setError(HttpStatus.UNPROCESSABLE_ENTITY.name());
        innerError.setMessage(ex.getMessage());
        ErrorRepresentation errorRepresentation = new ErrorRepresentation();
        errorRepresentation.add(innerError);
        return new ResponseEntity<>(errorRepresentation, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public ResponseEntity handleInternalServerError() {
        ErrorInnerRepresentation innerError = new ErrorInnerRepresentation();
        innerError.setError(HttpStatus.INTERNAL_SERVER_ERROR.name());
        innerError.setMessage("Server error");
        ErrorRepresentation errorRepresentation = new ErrorRepresentation();
        errorRepresentation.add(innerError);
        return new ResponseEntity<>(errorRepresentation, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
