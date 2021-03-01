package br.com.bank.application.controller;

import br.com.bank.application.controller.representation.BankMapperRepresentation;
import br.com.bank.domain.domain.CheckingAccount;
import br.com.bank.domain.domain.Customer;
import br.com.bank.domain.exception.BankException;
import br.com.bank.domain.service.BankService;
import br.com.bank.v1.representation.AddressRepresentation;
import br.com.bank.v1.representation.CustomerRepresentation;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankControllerTest {

    @InjectMocks
    BankController controller;

    @Mock
    BankService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new BankController(service, new BankMapperRepresentation());
    }

    @Test
    public void createCustomer() throws BankException {
        CustomerRepresentation representation = new CustomerRepresentation();
        representation.setDocumentNumber(36499908484L);
        representation.setName("John");
        representation.setLastName("Doe");
        representation.setPhoneNumber(11990875547L);
        representation.setEmail("email@email.com");
        AddressRepresentation addressRepresentation = new AddressRepresentation();
        addressRepresentation.setCity("Belo Horizonte");
        addressRepresentation.setState(AddressRepresentation.StateEnum.MG);
        addressRepresentation.setPostalCode("33309-090");
        addressRepresentation.setInfo("Rua Jorge Amado, 3848");
        representation.setAddress(addressRepresentation);

        CheckingAccount account = new CheckingAccount();
        account.setAccountNumber(1324);
        account.setBalance(BigDecimal.ZERO);

        when(service.createAccount(any(Customer.class))).thenReturn(account);
        var result = controller.v1CustomerPost(representation);
        Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(1324, result.getBody().getAccountNumber());
        Assertions.assertEquals(BigDecimal.ZERO, result.getBody().getBalanceAccount());
    }

    @Test
    public void customerDocumentAlreadyExistTest() throws BankException {
        CustomerRepresentation representation = new CustomerRepresentation();
        representation.setDocumentNumber(36499908484L);
        representation.setName("John");
        representation.setLastName("Doe");
        representation.setPhoneNumber(11990875547L);
        representation.setEmail("email@email.com");
        AddressRepresentation addressRepresentation = new AddressRepresentation();
        addressRepresentation.setCity("Belo Horizonte");
        addressRepresentation.setState(AddressRepresentation.StateEnum.MG);
        addressRepresentation.setPostalCode("33309-090");
        addressRepresentation.setInfo("Rua Jorge Amado, 3848");
        representation.setAddress(addressRepresentation);

        CheckingAccount account = new CheckingAccount();
        account.setAccountNumber(1324);
        account.setBalance(BigDecimal.ZERO);

        when(service.createAccount(any(Customer.class))).thenThrow(new BankException("Document already exist"));
        var result = controller.v1CustomerPost(representation);
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
    }

    @Test
    public void errorCreateCustomerTest() throws BankException {
        CustomerRepresentation representation = new CustomerRepresentation();
        representation.setDocumentNumber(36499908484L);
        representation.setName("John");
        representation.setLastName("Doe");
        representation.setPhoneNumber(11990875547L);
        representation.setEmail("email@email.com");
        AddressRepresentation addressRepresentation = new AddressRepresentation();
        addressRepresentation.setCity("Belo Horizonte");
        addressRepresentation.setState(AddressRepresentation.StateEnum.MG);
        addressRepresentation.setPostalCode("33309-090");
        addressRepresentation.setInfo("Rua Jorge Amado, 3848");
        representation.setAddress(addressRepresentation);

        CheckingAccount account = new CheckingAccount();
        account.setAccountNumber(1324);
        account.setBalance(BigDecimal.ZERO);

        when(service.createAccount(any(Customer.class))).thenThrow(new RuntimeException("Error"));
        var result = controller.v1CustomerPost(representation);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}
