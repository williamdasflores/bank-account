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
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@UseClasspathSqlLocator
public interface BankRepository extends BankRepositoryOutputPort {

    @SqlUpdate
    @GetGeneratedKeys
    @Override
    Integer insertAddress(@BindBean Address address);

    @SqlUpdate
    @GetGeneratedKeys
    Integer insertAccount();

    @SqlUpdate
    @GetGeneratedKeys
    @Override
    Integer insertCustomer(@BindBean Customer customer);

    @SqlQuery
    @Override
    CheckingAccount getAccount(@Bind("accountNumber") Integer accountNumber);

    @SqlQuery
    @Override
    LimitTransaction getLimitTransaction();

    @SqlQuery
    @Override
    Integer checkDocumentExist(@Bind("document") Long documentNumber);

    @SqlQuery
    @Override
    Integer checkAccountExist(@Bind("document") Long documentNumber,
                              @Bind("accountNumber") Integer accountNumber);

    @SqlUpdate
    @Override
    void debtAccountSender(@Bind("amount") BigDecimal amount,
                           @Bind("accountNumber") Integer accountNumber);

    @SqlUpdate
    @Override
    void creditAccountPayee(@Bind("amount") BigDecimal amount,
                            @Bind("accountNumber") Integer accountNumber);

    @SqlQuery
    @Override
    BigDecimal getBalanceAccount(@Bind("accountNumber") Integer accountNumber);

}
