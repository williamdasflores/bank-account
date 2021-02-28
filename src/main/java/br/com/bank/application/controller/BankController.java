package br.com.bank.application.controller;

import br.com.bank.domain.exception.BankException;
import br.com.bank.domain.service.BankService;
import br.com.bank.v1.provider.api.V1Api;
import br.com.bank.v1.representation.CheckingAccountRepresentation;
import br.com.bank.v1.representation.CustomerRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BankController implements V1Api {
    private BankService service;

    @Override
    public ResponseEntity<CheckingAccountRepresentation> v1CustomerPost(CustomerRepresentation body) {
        try {

        } catch (Exception e) {

        }
        return null;
    }
}
