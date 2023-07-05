package com.techelevator.tebucks.money.dao;

import com.techelevator.tebucks.money.model.Account;

public interface AccountDao {

    Account getAccountByUserName(String userName);
    //Account createAccount(User user);
}
