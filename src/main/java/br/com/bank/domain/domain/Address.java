package br.com.bank.domain.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Address {
    private Integer id;
    private String city;
    private String state;
    private String postalCode;
    private String information;
}
