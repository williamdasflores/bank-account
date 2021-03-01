package br.com.bank.application.repository.mapper;

import br.com.bank.domain.domain.CheckingAccount;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CheckingAccountMapper implements RowMapper<CheckingAccount> {
    @Override
    public CheckingAccount map(ResultSet rs, StatementContext ctx) throws SQLException {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber(rs.getInt("ACCOUNT_NUMBER"));
        checkingAccount.setBalance(rs.getBigDecimal("BALANCE_ACCOUNT"));
        return checkingAccount;
    }
}
