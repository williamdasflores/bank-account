package br.com.bank.application.repository;

import br.com.bank.domain.domain.Address;
import br.com.bank.domain.domain.CheckingAccount;
import br.com.bank.domain.domain.Customer;
import br.com.bank.domain.port.BankOutputPort;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@UseClasspathSqlLocator
public interface BankRepository extends BankOutputPort {

    @SqlUpdate
    @GetGeneratedKeys
    Integer insertAddress(@BindBean Address address);

    @SqlUpdate
    @GetGeneratedKeys
    Integer insertAccount(@BindBean CheckingAccount account);

    @SqlUpdate
    @GetGeneratedKeys
    Integer insertCustomer(@BindBean Customer customer);

}
