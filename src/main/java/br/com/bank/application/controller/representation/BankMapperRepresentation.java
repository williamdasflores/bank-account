package br.com.bank.application.controller.representation;

import br.com.bank.domain.domain.Address;
import br.com.bank.domain.domain.CheckingAccount;
import br.com.bank.domain.domain.Customer;
import br.com.bank.v1.representation.AddressRepresentation;
import br.com.bank.v1.representation.CheckingAccountRepresentation;
import br.com.bank.v1.representation.CustomerRepresentation;

import java.math.BigInteger;

public class BankMapperRepresentation {

    public static Customer mapperCustomer(CustomerRepresentation customerRepresentation,
                                                          AddressRepresentation addressRepresentation) {
        Address address = new Address();
        address.setCity(addressRepresentation.getCity());
        address.setState(addressRepresentation.getState().toString());
        address.setPostalCode(addressRepresentation.getPostalCode());
        address.setInformation(address.getInformation());

        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setDocumentNumber(customerRepresentation.getDocumentNumber());
        customer.setFirstName(customerRepresentation.getName());
        customer.setLastName(customerRepresentation.getLastName());
        customer.setPhoneNumber(customerRepresentation.getPhoneNumber());
        customer.setEmail(customerRepresentation.getEmail());

        return customer;
    }

    public static CheckingAccountRepresentation mapperCheckingAccountRepresentation(CheckingAccount checkingAccount) {
        CheckingAccountRepresentation representation = new CheckingAccountRepresentation();
        representation.setAccountNumber(checkingAccount.getAccountNumber());
        representation.setBalanceAccount(checkingAccount.getBalance());
        return representation;
    }
}
