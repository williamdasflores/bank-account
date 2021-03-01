package br.com.bank.domain.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CustomerTest {

    @Test
    public void test() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setDocumentNumber(14309544489L);
        customer.setEmail("john.doe@gmail.com");
        customer.setPhoneNumber(11974447600L);
        customer.setAddress(new Address());
        customer.setCheckingAccount(new CheckingAccount());

        Assertions.assertNotNull( customer.getCustomerId());
        Assertions.assertNotNull(customer.getFirstName());
        Assertions.assertNotNull(customer.getLastName());
        Assertions.assertEquals(14309544489L, customer.getDocumentNumber());
        Assertions.assertNotNull("john.doe@gmail.com", customer.getEmail());
        Assertions.assertNotNull(customer.getAddress());
        Assertions.assertNotNull(customer.getCheckingAccount());
    }
}
