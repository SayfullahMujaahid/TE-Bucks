package com.techelevator.tebucks.money.controller;

import com.techelevator.tebucks.money.dao.AccountDao;
import com.techelevator.tebucks.money.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AccountController {

    private final AccountDao accountDao;

    @Autowired
    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/api/account/balance", method = RequestMethod.GET)
    public Account getBalance(Principal principal) {
        String userName = principal.getName();
        return accountDao.getAccountByUserName(userName);

    }
}
