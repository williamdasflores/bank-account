package br.com.bank.domain.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class AddressTest {

    @Test
    public void test() {
        Address address = new Address();
        address.setId(1);
        address.setCity("São Paulo");
        address.setState("SP");
        address.setPostalCode("03387-123");
        address.setInformation("Rua Direita, 1093 AP 54");

        Assertions.assertNotNull(address.toString());
    }
}
