package br.com.bank.application.repository;

import br.com.bank.domain.domain.Address;
import br.com.bank.domain.domain.CheckingAccount;
import br.com.bank.domain.domain.Customer;
import br.com.bank.domain.domain.LimitTransaction;
import br.com.bank.domain.port.BankRepositoryOutputPort;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.math.BigDecimal;

@UseClasspathSqlLocator
public interface BankRepository extends BankRepositoryOutputPort {

    @SqlUpdate
    @GetGeneratedKeys
    Integer insertAddress(@BindBean Address address);

    @SqlUpdate
    @GetGeneratedKeys
    Integer insertAccount();

    @SqlUpdate
    @GetGeneratedKeys
    Integer insertCustomer(@BindBean Customer customer);

    @SqlQuery
    CheckingAccount getAccount(@Bind("accountNumber") Integer accountNumber);

    @SqlQuery
    LimitTransaction getLimitTransaction();

    @SqlQuery
    Integer checkDocumentExist(@Bind("document") Long documentNumber);

    @SqlQuery
    Integer checkAccountExist(@Bind("document") Long documentNumber,
                              @Bind("accountNumber") Long accountNumber);

    @SqlUpdate
    void debtAccountSender(@Bind("amount") Integer amount,
                           @Bind("accountNumber") Integer accountNumber);

    @SqlUpdate
    void creditAccountPayee(@Bind("amount") Integer amount,
                            @Bind("accountNumber") Integer accountNumber);

    @SqlQuery
    BigDecimal getBalanceAccount(@Bind("accountNumber") Integer accountNumber);

}
