package com.techelevator.tebucks.money.dao;

import com.techelevator.tebucks.money.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao {

    public List<Transfer> getTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "select * from transfer where user_to_id = ? and user_from_id = user_to_id";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Transfer transfer = map
            }

        }
}
