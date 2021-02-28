package br.com.bank.domain.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jdbi.v3.core.mapper.Nested;

import java.math.BigInteger;

@Getter
@Setter
@ToString
public class Customer {
    private Integer customerId;
    private Long documentNumber;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String email;
    private Address address;
    private CheckingAccount checkingAccount;

}
