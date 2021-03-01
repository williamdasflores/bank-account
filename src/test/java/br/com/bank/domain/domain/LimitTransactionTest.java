package br.com.bank.domain.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

public class LimitTransactionTest {

    @Test
    public void test() {
        LimitTransaction limitTransaction = new LimitTransaction();
        limitTransaction.setLimitPerTransaction(new BigDecimal("2000"));
        Assertions.assertNotNull(limitTransaction.toString());
    }
}
