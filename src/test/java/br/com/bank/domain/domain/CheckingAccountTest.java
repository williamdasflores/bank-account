package br.com.bank.domain.domain;

import br.com.bank.domain.domain.CheckingAccount;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

public class CheckingAccountTest {

    @Test
    public void test() {
        CheckingAccount account = new CheckingAccount();
        account.setAccountNumber(23294);
        account.setBalance(new BigDecimal("2000"));

        Assertions.assertNotNull(account.toString());
    }
}
