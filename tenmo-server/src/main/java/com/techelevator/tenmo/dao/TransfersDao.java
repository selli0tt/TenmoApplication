package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransfersDao {

    boolean addToTransfersTable(User accountFrom, User accountTo, BigDecimal amount);

    List<Transfers> getTransferIdOfSender(String username);

    List<Transfers> getTransferIdOfReceiver(String username);


}
