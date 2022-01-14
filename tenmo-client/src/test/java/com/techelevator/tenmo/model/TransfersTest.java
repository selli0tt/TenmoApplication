package com.techelevator.tenmo.model;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TransfersTest {

    @Test
    public void void_transfer_test(){

        Transfers transfers = new Transfers();

        transfers.setTransferId(1);
        transfers.setTransferAmount(new BigDecimal("10"));
        transfers.setTransferTypeId("Send");
        transfers.setTransferStatusId("Approved");
        transfers.setTransferFromName("Sam");
        transfers.setTransferToName("Cam");

        String expected = "Transfers{" +
                "transferId=" + "1" +
                ", transferAmount=" + "10" +
                ", transferTypeId='" + "Send" + '\'' +
                ", transferStatusId='" + "Approved" + '\'' +
                ", transferFromName='" + "Sam" + '\'' +
                ", transferToName='" + "Cam" + '\'' +
                '}';

        String actual = transfers.toStringTwo();

        assertEquals(expected, actual);



    }


}