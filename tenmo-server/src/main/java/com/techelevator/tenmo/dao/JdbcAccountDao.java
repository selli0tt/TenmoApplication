package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal retrieveBalance(int userId) {

        String sql = "SELECT balance FROM accounts WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        BigDecimal balance = null;
        if (results.next()) {
            balance = results.getBigDecimal("balance");
        }
        return balance;
    }

    public BigDecimal updateReceiverAccountBalance(int userId, BigDecimal transferAmount) {

        BigDecimal updateBalance = retrieveBalance(userId).add(transferAmount);

        String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?";

        try {
            jdbcTemplate.update(sql, updateBalance, userId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updateBalance;
    }

    public BigDecimal updateSenderAccountBalance(int userId, BigDecimal transferAmount) {
        BigDecimal updatedBalance = retrieveBalance(userId).subtract(transferAmount);

        String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?";
        try {
            jdbcTemplate.update(sql, updatedBalance, userId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedBalance;
    }

    private Account mapRowToAccount(SqlRowSet results) {
        Account account = new Account();
        account.setAccountId(results.getLong("account_id"));
        account.setUserId(results.getInt("user_id"));
        account.setBalance(results.getBigDecimal("balance"));
        return account;
    }

}
