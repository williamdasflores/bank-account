package br.com.bank.application.service;

import br.com.bank.domain.domain.CheckingAccount;
import br.com.bank.domain.domain.Customer;
import br.com.bank.domain.domain.Transaction;
import br.com.bank.domain.exception.BankException;
import br.com.bank.domain.helper.Validation;
import br.com.bank.domain.port.BankRepositoryOutputPort;
import br.com.bank.domain.service.BankService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionalBankService extends BankService {
    public TransactionalBankService(BankRepositoryOutputPort repository, Validation validation) {
        super(repository, validation);
    }

    @Override
    @Transactional(rollbackFor = { Exception.class, BankException.class})
    public CheckingAccount createAccount(Customer customer) throws BankException {
        return super.createAccount(customer);
    }

    @Override
    @Transactional(rollbackFor = { Exception.class, BankException.class})
    public Transaction transfer(Customer sender,
                                Customer payee, BigDecimal amount) throws BankException {
        return super.transfer(sender, payee, amount);
    }

    @Override
    @Transactional(rollbackFor = { Exception.class, BankException.class})
    public Transaction deposit(BigDecimal amount, Customer payee) throws BankException {
        return super.deposit(amount, payee);
    }
}
