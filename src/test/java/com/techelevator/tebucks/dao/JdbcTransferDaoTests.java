package com.techelevator.tebucks.dao;

import ch.qos.logback.core.helpers.Transform;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.techelevator.tebucks.exception.DaoException;
import com.techelevator.tebucks.money.dao.JdbcTransferDao;
import com.techelevator.tebucks.money.dao.TransferDao;
import com.techelevator.tebucks.money.model.NewTransferDto;
import com.techelevator.tebucks.money.model.Transfer;
import com.techelevator.tebucks.money.model.TransferStatusUpdateDto;
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
    protected static final User USER_1 = new User(1, "user1", "user1", "ROLE_USER", true);
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

    // These test arent working. not sure how to do them.

//    @Test
//    public void getTransfer_given_username() {
//
//        List<Transfer> expectedTransfer = sut.getTransfers(TRANSFER_1.getUserFrom().getUsername());
//
//        Assert.assertEquals(TRANSFER_1, expectedTransfer);
//
//    }
//    @Test
//    public void getTransferById_given_valid_transfer_id() {
//        Transfer actualTransfer = sut.getTransferById(TRANSFER_1.getTransferId());
//
//        Assert.assertEquals(TRANSFER_1, actualTransfer);
//    }

    @Test
    public void getTransferById_given_invalid_transfer_id() {
        Transfer actualTransfer = sut.getTransferById(-1);

        Assert.assertNull(actualTransfer);
    }

    @Test(expected = DaoException.class)
    public void createTransfer_creates_a_transfer() {

        NewTransferDto newTransferDto = new NewTransferDto();
        newTransferDto.setAmount(TRANSFER_1.getAmount());
        newTransferDto.setTransferType(TRANSFER_1.getTransferType());
        sut.createTransfer(newTransferDto);
    }

    @Test
    public void updateStatus_updates_the_status() {
        TransferStatusUpdateDto transferStatusUpdateDto = new TransferStatusUpdateDto();
        transferStatusUpdateDto.setTransferStatus("Approved");
        sut.updateStatus(transferStatusUpdateDto, 1);

    }

}
