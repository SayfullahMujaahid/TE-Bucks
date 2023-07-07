package com.techelevator.tebucks.money.controller;

import com.techelevator.tebucks.money.dao.AccountDao;
import com.techelevator.tebucks.money.dao.TransferDao;
import com.techelevator.tebucks.money.model.NewTransferDto;
import com.techelevator.tebucks.money.model.Transfer;
import com.techelevator.tebucks.money.model.TransferStatusUpdateDto;
import com.techelevator.tebucks.security.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

    private final UserDao userDao;
    private final AccountDao accountDao;
    private final TransferDao transferDao;

    @Autowired
    public TransferController(UserDao userDao, AccountDao accountDao, TransferDao transferDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }

    @RequestMapping(path = "/api/account/transfers", method = RequestMethod.GET)
    public List<Transfer> getTransfer(Principal principal) {
        return transferDao.getTransfers(principal.getName());
    }

    @RequestMapping(path = "/api/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int id) {
        Transfer transfer = transferDao.getTransferById(id);
        if (transfer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found.");
        } else {
            return transfer;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "api/transfers", method = RequestMethod.POST)
    public Transfer createTransfer (@Valid @RequestBody NewTransferDto newTransferDto, Principal principal) {

        double senderAccountBalance = accountDao.getAccountByUserId(newTransferDto.getUserFrom()).getBalance();
        double transferAmount = newTransferDto.getAmount();
        int sender = newTransferDto.getUserFrom();
        int receiver = newTransferDto.getUserTo();
        String transferUsername = userDao.getUserById(newTransferDto.getUserTo()).getUsername();

        //transferUsername.equals(principal.getName()


        if (newTransferDto.getTransferType().equals("Send")) {
            if ((senderAccountBalance >= transferAmount) && (transferAmount > 0) && (sender != receiver)) {
                accountDao.updateAccountBalance(sender, -transferAmount); //decreases sender's balance
                accountDao.updateAccountBalance(receiver, transferAmount); // increase receiver's balance

                //else throw new 400 bad request
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Try Again");
            }

        } else if (!transferUsername.equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Try Again");
        }

        if (newTransferDto.getTransferType().equals("Request")) {
            if (sender == receiver) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Try Again");
            }
        }
        return transferDao.createTransfer(newTransferDto);
    }

    @RequestMapping(path = "/api/transfers/{id}/status", method = RequestMethod.PUT)
    public Transfer updateStatus (@PathVariable int id, @Valid @RequestBody TransferStatusUpdateDto statusToUpdate, Principal principal) {

        Transfer transfer = transferDao.getTransferById(id);

        if(!principal.getName().equals(transfer.getUserFrom().getUsername())) {

            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        String incomingTransferStatus = statusToUpdate.getTransferStatus();
        String incomingTransferType = transfer.getTransferType();
        String receiverUsername = transfer.getUserFrom().getUsername();
        double transferAmount = transfer.getAmount();
        int sender_id = transfer.getUserFrom().getId();
        int receiver_id = transfer.getUserTo().getId();
        boolean isThereEnoughMoney = accountDao.getAccountByUserName(principal.getName()).getBalance() >= transferDao.getTransferById(id).getAmount();

        //if(incomingTransferType.equals("Send") && incomingTransferStatus.equals("Rejected")){
            // Check if account that recieved the send, has enough money to give it back


        if (incomingTransferType.equals("Request") && incomingTransferStatus.equals("Rejected")){
            transferDao.updateStatus(statusToUpdate, id);
        } else if (incomingTransferType.equals("Request") && incomingTransferStatus.equals("Approved")){
            if (isThereEnoughMoney) {
                transferDao.updateStatus(statusToUpdate, id);
                accountDao.updateAccountBalance(sender_id, -transferAmount);
                accountDao.updateAccountBalance(receiver_id, transferAmount);
            }
        }

        return transfer;

        }


}
