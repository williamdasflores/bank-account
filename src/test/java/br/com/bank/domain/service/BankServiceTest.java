package br.com.bank.domain.service;

import br.com.bank.domain.domain.Address;
import br.com.bank.domain.domain.CheckingAccount;
import br.com.bank.domain.domain.Customer;
import br.com.bank.domain.domain.LimitTransaction;
import br.com.bank.domain.exception.BankException;
import br.com.bank.domain.helper.Validation;
import br.com.bank.domain.port.BankRepositoryOutputPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class BankServiceTest {
    private BankRepositoryOutputPort reposity;
    private Validation validation;
    private BankService service;

    @Before
    public void setUp() {
        reposity = mock(BankRepositoryOutputPort.class);
        validation = new Validation();
        service = new BankService(reposity, validation);
    }

    @Test
    public void customerAlreadyRegisteredTest() {
        Customer customer = new Customer();
        customer.setDocumentNumber(94357643600L);
        when(reposity.checkDocumentExist(94357643600L)).thenReturn(1);
        Assertions.assertThrows(BankException.class, () -> service.createAccount(customer));
    }

    @Test
    public void createCustomerErrorUnknownTest() {
        Customer customer = new Customer();
        customer.setDocumentNumber(94357643600L);
        when(reposity.checkDocumentExist(94357643600L)).thenThrow(new RuntimeException("Error"));
        Assertions.assertThrows(Exception.class, () -> service.createAccount(customer));
    }

    @Test
    public void createCustomerTest() throws BankException {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setBalance(0);
        checkingAccount.setAccountNumber(87434);

        when(reposity.insertAddress(new Address())).thenReturn(1);
        when(reposity.insertAccount()).thenReturn(87434);
        when(reposity.getAccount(any(Integer.class))).thenReturn(checkingAccount);

        Customer customer = new Customer();
        Address address = new Address();
        address.setCity("Rio de Janeiro");
        address.setState("RJ");
        address.setPostalCode("12476-090");
        address.setInformation("Av. Copacabana, 1849 Bloco 2 Apartamento 22");
        customer.setAddress(address);

        customer.setDocumentNumber(94357643600L);
        customer.setFirstName("Marie");
        customer.setLastName("Laurence");
        customer.setEmail("marie.laurence@yahoo.com");
        customer.setPhoneNumber(11931323400L);

        var result = service.createAccount(customer);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(87434, result.getAccountNumber());
        Assertions.assertEquals(0, result.getBalance());
    }

    @Test
    public void transferringSenderInvalidAccountTest() {
        Customer sender = new Customer();
        Customer payee = new Customer();

        sender.setDocumentNumber(12312312309L);
        CheckingAccount account = new CheckingAccount();
        account.setAccountNumber(21313);
        sender.setCheckingAccount(account);
        when(reposity.checkAccountExist(anyLong(), anyInt())).thenReturn(0);
        Assertions.assertThrows(BankException.class, () -> service.transfer(sender, payee, 1000));
    }

    @Test
    public void transferringSenderInvalidBalanceAccountTest() {
        Customer sender = new Customer();
        Customer payee = new Customer();

        sender.setDocumentNumber(12312312309L);
        CheckingAccount accountSender = new CheckingAccount();
        accountSender.setAccountNumber(21313);
        sender.setCheckingAccount(accountSender);

        payee.setDocumentNumber(94334399809L);
        CheckingAccount accountPayee = new CheckingAccount();
        accountPayee.setAccountNumber(123321);
        payee.setCheckingAccount(accountPayee);

        when(reposity.checkAccountExist(anyLong(), anyInt())).thenReturn(1);
        when(reposity.getBalanceAccount(anyInt())).thenReturn(new BigDecimal("-1"));
        Assertions.assertThrows(BankException.class, () -> service.transfer(sender, payee, 20000));
    }

    @Test
    public void transferringTest() throws BankException {
        Customer sender = new Customer();
        Customer payee = new Customer();

        sender.setDocumentNumber(12312312309L);
        CheckingAccount accountSender = new CheckingAccount();
        accountSender.setAccountNumber(21313);
        sender.setCheckingAccount(accountSender);

        payee.setDocumentNumber(94334399809L);
        CheckingAccount accountPayee = new CheckingAccount();
        accountPayee.setAccountNumber(123321);
        payee.setCheckingAccount(accountPayee);

        when(reposity.checkAccountExist(anyLong(), anyInt())).thenReturn(1);
        when(reposity.getBalanceAccount(anyInt())).thenReturn(new BigDecimal("10"));
        var result = service.transfer(sender, payee, 100000);

        Assertions.assertNotNull(result.getUuid());
        Assertions.assertEquals(100000, result.getAmount());
        Assertions.assertEquals(LocalDate.now(), result.getDateTransaction());
    }

    @Test
    public void transferringTestErrorUnknownTest() {
        Customer sender = new Customer();
        Customer payee = new Customer();

        sender.setDocumentNumber(12312312309L);
        CheckingAccount account = new CheckingAccount();
        account.setAccountNumber(21313);
        sender.setCheckingAccount(account);
        when(reposity.checkAccountExist(anyLong(), anyInt())).thenThrow(new RuntimeException("Error"));
        Assertions.assertThrows(Exception.class, () -> service.transfer(sender, payee, 1000));
    }

    @Test
    public void depositLimitTest() {
        Customer payee = new Customer();
        payee.setDocumentNumber(94334399809L);
        CheckingAccount accountPayee = new CheckingAccount();
        accountPayee.setAccountNumber(123321);
        payee.setCheckingAccount(accountPayee);

        LimitTransaction limitTransaction = new LimitTransaction();
        limitTransaction.setLimitPerTransaction(200000);

        when(reposity.getLimitTransaction()).thenReturn(limitTransaction);

        Assertions.assertThrows(BankException.class, () -> service.deposit(300000, payee));
    }

    @Test
    public void payeeAccountDoesNotExistTest() {
        Customer payee = new Customer();
        payee.setDocumentNumber(94334399809L);
        CheckingAccount accountPayee = new CheckingAccount();
        accountPayee.setAccountNumber(123321);
        payee.setCheckingAccount(accountPayee);

        LimitTransaction limitTransaction = new LimitTransaction();
        limitTransaction.setLimitPerTransaction(200000);

        when(reposity.getLimitTransaction()).thenReturn(limitTransaction);
        when(reposity.checkAccountExist(anyLong(), anyInt())).thenReturn(0);
        Assertions.assertThrows(BankException.class, () -> service.deposit(30000, payee));
    }

    @Test
    public void depositTest() throws BankException {
        Customer payee = new Customer();
        payee.setDocumentNumber(94334399809L);
        CheckingAccount accountPayee = new CheckingAccount();
        accountPayee.setAccountNumber(123321);
        payee.setCheckingAccount(accountPayee);

        LimitTransaction limitTransaction = new LimitTransaction();
        limitTransaction.setLimitPerTransaction(200000);

        when(reposity.getLimitTransaction()).thenReturn(limitTransaction);
        when(reposity.checkAccountExist(anyLong(), anyInt())).thenReturn(1);

        var result = service.deposit(20000, payee);
        Assertions.assertNotNull(result.getUuid());
        Assertions.assertEquals(20000, result.getAmount());
        Assertions.assertEquals(LocalDate.now(), result.getDateTransaction());
    }
}
