package br.com.bank.domain.domain;

import br.com.bank.domain.domain.Transaction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class TransactionTest {

    @Test
    public void test() {
        Transaction transaction = new Transaction();
        transaction.setUuid(UUID.randomUUID().toString());
        transaction.setDateTransaction(LocalDate.now());
        transaction.setAmount(new BigDecimal("4000"));
        Assertions.assertNotNull(transaction.toString());
    }
}
