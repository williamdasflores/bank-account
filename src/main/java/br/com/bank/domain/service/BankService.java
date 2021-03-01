package br.com.bank.domain.service;

import br.com.bank.domain.domain.*;
import br.com.bank.domain.exception.BankException;
import br.com.bank.domain.helper.Validation;
import br.com.bank.domain.port.BankRepositoryOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class BankService {
    private final BankRepositoryOutputPort repository;
    private final Validation validation;

    public CheckingAccount createAccount(Customer customer) throws BankException{
        try {
            validation.customerExist(repository.checkDocumentExist(customer.getDocumentNumber()));

            log.info("Creating new account");
            Integer adddressId = repository.insertAddress(customer.getAddress());
            customer.getAddress().setId(adddressId);

            Integer accountNumber = repository.insertAccount();
            customer.setCheckingAccount(new CheckingAccount());
            customer.getCheckingAccount().setAccountNumber(accountNumber);

            repository.insertCustomer(customer);
            CheckingAccount account = repository.getAccount(accountNumber);
            return account;
        } catch (BankException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error creating customer! {}", e.getMessage());
            throw e;
        }
    }

    public Transaction transfer(Customer sender,
                                    Customer payee, BigDecimal amount) throws BankException {
        try {
            validation.accountExist(repository.checkAccountExist(sender.getDocumentNumber(),
                    sender.getCheckingAccount().getAccountNumber()));
            validation.accountExist(repository.checkAccountExist(payee.getDocumentNumber(),
                    payee.getCheckingAccount().getAccountNumber()));

            repository.debtAccountSender(amount, sender.getCheckingAccount().getAccountNumber());
            BigDecimal balanceAccount = repository
                    .getBalanceAccount(sender.getCheckingAccount().getAccountNumber());
            validation.checkBalance(balanceAccount);

            repository.creditAccountPayee(amount, payee.getCheckingAccount().getAccountNumber());

            Transaction transaction = new Transaction();
            transaction.setDateTransaction(LocalDate.now());
            transaction.setAmount(amount);
            transaction.setUuid(UUID.randomUUID().toString());
            return transaction;
        } catch (BankException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error transferring! {}", e.getMessage());
            throw e;
        }
    }

    public Transaction deposit(BigDecimal amount, Customer payee) throws BankException {
        try {
            var limitTransaction = repository.getLimitTransaction();
            validation.limitTransactionAllowed(amount,
                    limitTransaction.getLimitPerTransaction());

            validation.accountExist(repository.checkAccountExist(payee.getDocumentNumber(),
                    payee.getCheckingAccount().getAccountNumber()));

            repository.creditAccountPayee(amount, payee.getCheckingAccount().getAccountNumber());

            Transaction transaction = new Transaction();
            transaction.setUuid(UUID.randomUUID().toString());
            transaction.setDateTransaction(LocalDate.now());
            transaction.setAmount(amount);

            return transaction;
        } catch (BankException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error depositing! {}", e.getMessage());
            throw e;
        }
    }
}
