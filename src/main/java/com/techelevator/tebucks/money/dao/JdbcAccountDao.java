package com.techelevator.tebucks.money.dao;

import com.techelevator.tebucks.exception.DaoException;
import com.techelevator.tebucks.money.model.Account;
import com.techelevator.tebucks.money.model.Transfer;
import com.techelevator.tebucks.security.model.RegisterUserDto;
import com.techelevator.tebucks.security.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccountByUserName(String userName) {
        if (userName == null) throw new IllegalArgumentException("Username cannot be null");
        Account account = null;
        String sql = "Select *  from account join users using (user_id) where username = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userName);
            if (results.next()) {
                account = mapRowToAccount(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } return account;
    }


    public Account getAccountId(int accountId) {
        if (accountId < 0) throw new IllegalArgumentException("Account Id cannot be empty");
        Account account = null;
        String sql = "select * from account where account_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            if (results.next()) {
                account = mapRowToAccount(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } return account;
    }



    public Account createAccount(User user) {
        final int INITIAL_BALANCE = 1000;
        Account newAccount = null;
        String sql = "insert into account (user_id, balance) values (?, ?) returning account_id";
        try {
            int accountId = jdbcTemplate.queryForObject(sql, int.class, user.getId(), INITIAL_BALANCE);
            newAccount = getAccountId(accountId);

        } catch (CannotGetJdbcConnectionException e) {
        throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
        throw new DaoException("Data integrity violation", e);
        } return newAccount;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getDouble("balance"));
        return account;
    }
}
