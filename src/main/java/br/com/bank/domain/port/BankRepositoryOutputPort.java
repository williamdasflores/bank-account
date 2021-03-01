package br.com.bank.domain.port;

import br.com.bank.domain.domain.Address;
import br.com.bank.domain.domain.CheckingAccount;
import br.com.bank.domain.domain.Customer;
import br.com.bank.domain.domain.LimitTransaction;
import org.jdbi.v3.sqlobject.customizer.BindBean;

import java.math.BigDecimal;

public interface BankRepositoryOutputPort {

    Integer insertAddress(@BindBean Address address);

    Integer insertAccount();

    Integer insertCustomer(@BindBean Customer customer);

    CheckingAccount getAccount(Integer accountNumber);

    LimitTransaction getLimitTransaction();

    Integer checkDocumentExist(Long documentNumber);

    Integer checkAccountExist(Long documentNumber, Integer accountNumber);

    void debtAccountSender(BigDecimal amount, Integer accountNumber);

    void creditAccountPayee(BigDecimal amount, Integer accountNumber);

    BigDecimal getBalanceAccount(Integer accountNumber);
}
