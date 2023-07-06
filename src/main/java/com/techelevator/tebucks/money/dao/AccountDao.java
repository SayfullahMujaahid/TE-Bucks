package com.techelevator.tebucks.money.dao;

import com.techelevator.tebucks.money.model.Account;
import com.techelevator.tebucks.security.model.User;

public interface AccountDao {

    Account getAccountByUserName(String userName);
    Account createAccount(User user);
}
