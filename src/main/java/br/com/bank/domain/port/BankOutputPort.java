package br.com.bank.domain.port;

import br.com.bank.domain.domain.Address;
import br.com.bank.domain.domain.CheckingAccount;
import br.com.bank.domain.domain.Customer;
import org.jdbi.v3.sqlobject.customizer.BindBean;

public interface BankOutputPort {

    Integer insertAddress(@BindBean Address address);

    Integer insertAccount(@BindBean CheckingAccount account);

    Integer insertCustomer(@BindBean Customer customer);
}
