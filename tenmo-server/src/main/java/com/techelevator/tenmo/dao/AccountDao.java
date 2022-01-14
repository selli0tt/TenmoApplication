package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfers;

import java.math.BigDecimal;
import java.text.Bidi;

public interface AccountDao {

    BigDecimal retrieveBalance(int userID);

    BigDecimal updateReceiverAccountBalance(int userId, BigDecimal transferAmount );

    BigDecimal updateSenderAccountBalance(int userId, BigDecimal transferAmount );
}
