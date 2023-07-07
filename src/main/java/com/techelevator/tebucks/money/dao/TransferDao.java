package com.techelevator.tebucks.money.dao;

import com.techelevator.tebucks.money.model.NewTransferDto;
import com.techelevator.tebucks.money.model.Transfer;
import com.techelevator.tebucks.money.model.TransferStatusUpdateDto;

import java.util.List;

public interface TransferDao {

    List<Transfer> getTransfers(String userName);

    Transfer getTransferById(int transferId);
    Transfer createTransfer(NewTransferDto newTransferDto);

    Transfer updateStatus(TransferStatusUpdateDto transferStatusUpdateDto, int id);


}
