package br.com.bank.application.controller;

import br.com.bank.application.controller.representation.BankMapperRepresentation;
import br.com.bank.domain.exception.BankException;
import br.com.bank.domain.service.BankService;
import br.com.bank.v1.provider.api.V1Api;
import br.com.bank.v1.representation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BankController implements V1Api {
    private final BankService service;

    @Override
    public ResponseEntity<CheckingAccountRepresentation> v1CustomerPost(CustomerRepresentation body) {
        try {
            log.info("mapping request representation...");
            var customer = BankMapperRepresentation.mapperCustomer(body, body.getAddress());
            log.info("request mapped");

            var checkingAccount = service.createAccount(customer);

            log.info("mapping response...");
            var response = BankMapperRepresentation.mapperCheckingAccountRepresentation(checkingAccount);
            log.info("response mapped");

            log.info("Customer created!!");
            return ResponseEntity.ok(response);
        } catch (BankException e) {
          log.warn("{}", e.getMessage(), e);
          ErrorRepresentationRepresentation error = new ErrorRepresentationRepresentation();
          error.setMessage(e.getMessage());
          return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            log.error("Error creating customer: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@Override
    public ResponseEntity<TransferRepresentation> v1TransferPost(TransactionRepresentation body) {
        try {
            log.info("Transferring...");

        } catch (BankException e) {
            log.warn("{}", e.getMessage(), e);
            ErrorRepresentationRepresentation error = new ErrorRepresentationRepresentation();
            error.setMessage(e.getMessage());
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            log.error("Error creating customer: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
