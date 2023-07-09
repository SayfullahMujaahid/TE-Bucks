package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.money.dao.JdbcAccountDao;
import com.techelevator.tebucks.money.model.Account;
import com.techelevator.tebucks.security.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcAccountDaoTests extends BaseDaoTests {

    protected static final Account ACCOUNT_1 = new Account(1, 1);
    protected static final Account ACCOUNT_2 = new Account(2, 2);

    private JdbcAccountDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getAccountById_given_invalid_account_id() {
        Account actualAccount = sut.getAccountId(-1);

        Assert.assertNull(actualAccount);
    }

    @Test
    public void getAccount_given_username() {

        Account expectedAccount = sut.getAccountByUserName("user1");

        Assert.assertEquals(ACCOUNT_1, expectedAccount);
    }

    @Test
    public void getAccount_by_id() {
        Account expectedAccount1 = sut.getAccountByUserId(1);
        Assert.assertEquals(ACCOUNT_1, expectedAccount1);

        Account expectedAccount2 = sut.getAccountByUserId(2);
        Assert.assertEquals(ACCOUNT_2, expectedAccount2);
    }

//    @Test
//    public void update_account_balance() {
//        Account updatedBalance = sut.updateAccountBalance(1,10);
//
//        Assert.assertEquals(ACCOUNT_1, updatedBalance);
//    }


}
