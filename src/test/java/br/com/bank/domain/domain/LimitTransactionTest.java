package br.com.bank.domain.domain;

import br.com.bank.domain.domain.LimitTransaction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class LimitTransactionTest {

    @Test
    public void test() {
        LimitTransaction limitTransaction = new LimitTransaction();
        limitTransaction.setLimitPerTransaction(2000);
        Assertions.assertNotNull(limitTransaction.toString());
    }
}
