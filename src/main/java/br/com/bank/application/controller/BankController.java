package br.com.bank.application.controller;

import br.com.bank.application.controller.representation.BankMapperRepresentation;
import br.com.bank.domain.domain.Customer;
import br.com.bank.domain.domain.Transaction;
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
    private final BankMapperRepresentation mapper;

    @Override
    public ResponseEntity<CheckingAccountRepresentation> v1CustomerPost(CustomerRepresentation body) {
        try {
            log.info("mapping request representation...");
            var customer = mapper.mapperCustomer(body, body.getAddress());
            log.info("request mapped");

            var checkingAccount = service.createAccount(customer);

            log.info("mapping response...");
            var response = mapper.mapperCheckingAccountRepresentation(checkingAccount);
            log.info("response mapped");

            log.info("Customer created!!");
            return ResponseEntity.ok(response);
        } catch (BankException e) {
          log.warn("{}", e.getMessage(), e);
          ErrorRepresentationRepresentation error = new ErrorRepresentationRepresentation();
          error.setMessage(e.getMessage());
          return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            log.error("Error creating customer: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<TransactionRepresentation> v1TransferPost(TransferRepresentation body) {
        try {
            log.info("Transferring...");
            log.info("Mapping Sender and Payee.");
            Customer sender = mapper.mapperSenderToCustomer(body);
            Customer payee = mapper.mapperPayeeToCustomer(body);
            log.info("mapped");

            Transaction transaction = service.transfer(sender, payee, body.getAmount());
            var response = mapper.mapperTransactionRepresentation(transaction);
            log.info("Transfer Ok!!");
            return ResponseEntity.ok(response);
        } catch (BankException e) {
            log.warn("{}", e.getMessage(), e);
            ErrorRepresentationRepresentation error = new ErrorRepresentationRepresentation();
            error.setMessage(e.getMessage());
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            log.error("Error creating customer: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<TransactionRepresentation> v1DepositPost(DepositRepresentation body) {
        try {
            log.info("Depositing...");
            log.info("Mapping Payee");
            Customer payee = mapper.mapperToDeposit(body);
            log.info("mapped");

            Transaction transaction = service.deposit(body.getAmount(), payee);
            var response = mapper.mapperTransactionRepresentation(transaction);
            log.info("Deposit Ok!!");

            return ResponseEntity.ok(response);
        } catch (BankException e) {
            log.warn("{}", e.getMessage());
            ErrorRepresentationRepresentation error = new ErrorRepresentationRepresentation();
            error.setMessage(e.getMessage());
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            log.error("Error creating customer: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
