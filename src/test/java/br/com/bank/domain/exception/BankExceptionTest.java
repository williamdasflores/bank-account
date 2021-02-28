package br.com.bank.domain.exception;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BankExceptionTest {

    @Test
    public void test() {
        BankException exception = new BankException("Error");
        Assertions.assertEquals("Error", exception.getMessage());
    }
}
