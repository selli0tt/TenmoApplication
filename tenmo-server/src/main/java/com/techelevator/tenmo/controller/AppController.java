package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransfersDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AppController {

    @Autowired
    AccountDao accountDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TransfersDao transfersDao;

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public Balance obtainBalance(Principal principal) {

        String name = principal.getName();
        int userId = userDao.findIdByUsername(name);

        BigDecimal balance = accountDao.retrieveBalance(userId);

        Balance balanceObject = new Balance();
        balanceObject.setBalance(balance);
        return balanceObject;
    }

    @RequestMapping(path = "/allusers", method = RequestMethod.GET)
    public List<User> getAllUsers(Principal principal) {

        String name = principal.getName();
        return userDao.findAllUsersExceptLoggedInUser(name);
    }

    @RequestMapping(path = "/detail", method = RequestMethod.GET)
    public List<Transfers> getReceipt(Principal principal) {

        String name = principal.getName();
        List<Transfers> allData = transfersDao.getTransferIdOfSender(name);

        for (Transfers receipt : allData) {
            int userId = userDao.findUserIdByAccountId(receipt.getRecieverId());
            String Username = userDao.findUsernameByUserId(userId);
            receipt.setTransferToName(Username);
            receipt.setTransferFromName(name);
        }

        List<Transfers> detail = transfersDao.getTransferIdOfReceiver(name);
        for (Transfers receipt : detail) {
            int userId = userDao.findUserIdByAccountId(receipt.getSenderId());
            String username = userDao.findUsernameByUserId(userId);
            receipt.setTransferToName(name);
            receipt.setTransferFromName(username);
        }

        allData.addAll(detail);

        return allData;
    }


    @RequestMapping(path = "/transaction", method = RequestMethod.POST)
    public Transfers updateBalanceOfSenderAndReceiver(@RequestBody Transfers transfers, Principal principal) {
 //@RequestBody deserialize
        String name = principal.getName();
        int userIDOfSender = userDao.findIdByUsername(name);
        int accountIDOfSender = userDao.givenUserIdFindAccountId(userIDOfSender);
        int userIDOfReceiver = transfers.getRecieverId();
        int accountIDOfReceiver = userDao.givenUserIdFindAccountId(userIDOfReceiver);

        Transfers updates = new Transfers();
        updates.setTransferAmount(transfers.getTransferAmount());
        updates.setRecieverId(userIDOfReceiver);
        updates.setSenderId(userIDOfSender);

        User senderA = new User();
        senderA.setId((long) userIDOfSender);
        User senderB = new User();
        senderB.setId((long) userIDOfReceiver);

        boolean info = transfersDao.addToTransfersTable(senderA, senderB, transfers.getTransferAmount());

        BigDecimal balanceOfReceiver = accountDao.retrieveBalance(userIDOfReceiver);
        BigDecimal receiverUpdated = accountDao.updateReceiverAccountBalance(userIDOfReceiver, transfers.getTransferAmount());
        BigDecimal balanceOfSender = accountDao.retrieveBalance(userIDOfSender);
        BigDecimal senderUpdated = accountDao.updateSenderAccountBalance(userIDOfSender, transfers.getTransferAmount());

        return updates;
    }

}
