package com.techelevator.tebucks.dao;

import ch.qos.logback.core.helpers.Transform;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.techelevator.tebucks.money.dao.JdbcTransferDao;
import com.techelevator.tebucks.money.dao.TransferDao;
import com.techelevator.tebucks.money.model.NewTransferDto;
import com.techelevator.tebucks.money.model.Transfer;
import com.techelevator.tebucks.security.dao.JdbcUserDao;
import com.techelevator.tebucks.security.dao.UserDao;
import com.techelevator.tebucks.security.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public class JdbcTransferDaoTests extends BaseDaoTests {

    protected static final Transfer TRANSFER_1 = new Transfer(1, "Send" , "Approved", 10);
    protected static final Transfer TRANSFER_2 = new Transfer(2, "Request", "Pending", 10);
    private JdbcTransferDao sut;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        UserDao userDao = new JdbcUserDao(jdbcTemplate);
        sut = new JdbcTransferDao(jdbcTemplate, userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTransfer_given_null_throws_exception() {
        sut.getTransfers(null);
    }

    @Test
    public void getTransfer_given_username() {
       List<Transfer> transfer = sut.getTransfers("user1");



    }
    @Test
    public void getTransferById_given_valid_transfer_id() {
        Transfer actualTransfer = sut.getTransferById(TRANSFER_1.getTransferId());

        Assert.assertEquals(TRANSFER_1, actualTransfer);
    }


    @Test
    public void getTransferById_given_invalid_id_returns_null() {

    }

    @Test
    public void createTransfer_creates_a_transfer() {
      //  Transfer newTransfer = sut.createTransfer();
    }

    @Test
    public void updateStatus_updates_the_status() {

    }
    @Test
    public void testGetTransferById() {
        Transfer expectedTransfer = TRANSFER_1;

        Transfer actualTransfer = sut.getTransferById(1);

        Assert.assertEquals(expectedTransfer, actualTransfer);
    }
}
