package br.com.bank.domain.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Transaction {
    private String uuid;
    private LocalDate dateTransaction;
    private Integer amount;
}
