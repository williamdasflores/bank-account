package br.com.bank.domain.helper;

import br.com.bank.domain.exception.BankException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

public class ValidationTest {
    private Validation validation;

    @Before
    public void setUp() {
        validation = new Validation();
    }

    @Test
    public void customerExistTest()  {
        Assertions.assertThrows(BankException.class, () -> validation.customerExist(1));
    }

    @Test
    public void customerDoesNotExistTest() throws BankException{
        var exist = validation.customerExist(0);
        Assertions.assertFalse(exist);
    }

    @Test
    public void accountDoesNotExistTest() {
        Assertions.assertThrows(BankException.class, () -> validation.accountExist(0));
    }

    @Test
    public void accountExistTest() throws BankException{
        var exist = validation.accountExist(1);
        Assertions.assertTrue(exist);
    }

    @Test
    public void balanceAcountIsEqualToZeroTest() throws BankException {
        validation.checkBalance(BigDecimal.ZERO);
    }

    @Test
    public void balanceAcountIsNegativeTest() {
        Assertions.assertThrows(BankException.class, () -> validation.checkBalance(new BigDecimal("-10")));
    }

    @Test
    public void balanceAccountIsOkTest() throws BankException {
        validation.checkBalance(BigDecimal.TEN);
    }

    @Test
    public void transactionLimitNotAllowedTest() {
        Assertions.assertThrows(BankException.class, () -> validation.limitTransactionAllowed(new BigDecimal("2000"), new BigDecimal("1000")));
    }

    @Test
    public void transactionLimitAllowedTest() throws BankException {
       var limitAllowed = validation.limitTransactionAllowed(new BigDecimal("100"), new BigDecimal("1000"));
       Assertions.assertTrue(limitAllowed);
    }


}
