package br.com.bank.domain.helper;

import br.com.bank.domain.exception.BankException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class Validation {

    public boolean customerExist(Integer qtdCustomer) throws BankException {
        if (qtdCustomer > 0) {
            log.warn("Customer already registered!");
            throw new BankException("Customer already registered!");
        }

        return false;
    }

    public boolean accountExist(Integer qtdAccount) throws BankException {
        if (qtdAccount == 0) {
            log.warn("Sender account does not exist!");
            throw new BankException("Sender account does not exist!");
        }

        return true;
    }

    public void checkBalance(BigDecimal currentBalance) throws BankException {
        if (currentBalance.compareTo(BigDecimal.ZERO) < 0) {
            log.error("Balance account negative!");
            throw new BankException("Balance account negative.");
        }
    }

    public boolean limitTransactionAllowed(Integer amount, Integer limitAmount) throws BankException {
        if (amount > limitAmount) {
            log.warn("Amount greater than limit amount(R$ 2.000)!");
            throw new BankException("Amount greater than limit amount(R$ 2.000)");
        }
        return true;
    }
}
