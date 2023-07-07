package com.techelevator.tebucks.money.dao;

import com.techelevator.tebucks.exception.DaoException;
import com.techelevator.tebucks.money.model.NewTransferDto;
import com.techelevator.tebucks.money.model.Transfer;
import com.techelevator.tebucks.money.model.TransferStatusUpdateDto;
import com.techelevator.tebucks.security.dao.UserDao;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransferDao  implements TransferDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;


    public JdbcTransferDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    public List<Transfer> getTransfers(String userName) {

        int userId = userDao.getUserByUsername(userName).getId();


        List<Transfer> transfers = new ArrayList<>();
        String sql = "select * from transfer where user_to_id = ? or user_from_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId, userId);
            while (results.next()) {
                Transfer transfer = mapRowToTransfer(results);
                transfers.add(transfer);
                }
            } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfers;

    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = null;
        String sql = "select * from transfer where transfer_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            if (results.next()) {
                transfer = mapRowToTransfer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } return transfer;
    }

    @Override
    public Transfer createTransfer(NewTransferDto newTransferDto) {
        Transfer newTransfer = null;
        String statusType = "";
        if (newTransferDto.getTransferType().equals("Send")) {
            statusType = "Approved";
        } else {
            statusType = "Pending";
        }
        String sql = "insert into transfer (user_from_id, user_to_id, amount, transfer_type, transfer_status) values (?, ?, ?, ?, ?) returning transfer_id";
        try {
            int transferId = jdbcTemplate.queryForObject(sql, int.class, newTransferDto.getUserFrom(), newTransferDto.getUserTo(), newTransferDto.getAmount(), newTransferDto.getTransferType(), statusType);
            newTransfer = getTransferById(transferId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return newTransfer;
    }

    @Override
    public Transfer updateStatus(TransferStatusUpdateDto transferStatusUpdateDto, int id) {
        Transfer updatedTransfer = null;
        String sql = "update transfer set transfer_status = ? where transfer_id = ?;";

         jdbcTemplate.update(sql, transferStatusUpdateDto.getTransferStatus(), id);
         updatedTransfer = getTransferById(id);
         return updatedTransfer;
    }


    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferType(rs.getString("transfer_type"));
        transfer.setTransferStatus(rs.getString("transfer_status"));
        transfer.setAmount(rs.getDouble("amount"));
        transfer.setUserFrom(userDao.getUserById(rs.getInt("user_from_id")));
        transfer.setUserTo(userDao.getUserById(rs.getInt("user_to_id")));
        return transfer;
    }
}
