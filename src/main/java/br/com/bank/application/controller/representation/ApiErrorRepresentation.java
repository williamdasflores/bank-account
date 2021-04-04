package br.com.bank.application.controller.representation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApiErrorRepresentation {
    private HttpStatus status;
    private List<String> errors;

}
