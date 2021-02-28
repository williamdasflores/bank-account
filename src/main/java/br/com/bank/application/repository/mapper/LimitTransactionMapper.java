package br.com.bank.application.repository.mapper;

import br.com.bank.domain.domain.LimitTransaction;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LimitTransactionMapper implements RowMapper<LimitTransaction> {
    @Override
    public LimitTransaction map(ResultSet rs, StatementContext ctx) throws SQLException {
        LimitTransaction limitTransaction = new LimitTransaction();
        limitTransaction.setLimitPerTransaction(rs.getInt("LIMIT_VALUE"));
        return limitTransaction;
    }
}
