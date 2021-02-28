package br.com.bank.domain.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@Setter
@ToString
public class Customer {
    private Integer customerId;
    private BigInteger documentNumber;
    private String firstName;
    private String lastName;
    private BigInteger phoneNumber;
    private String email;
    private Address address;
    private CheckingAccount checkingAccount;
}
