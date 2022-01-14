package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfers {


    private long transferId;

    private int recieverId;

    private BigDecimal transferAmount;

    private int senderId;

    private String transferTypeId;

    private String transferStatusId;

    private String transferFromName;

    private String transferToName;


    public Transfers(){ }


    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public int getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(int recieverId) {
        this.recieverId = recieverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(String transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(String transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferFromName() {
        return transferFromName;
    }

    public void setTransferFromName(String transferFromName) {
        this.transferFromName = transferFromName;
    }

    public String getTransferToName() {
        return transferToName;
    }

    public void setTransferToName(String transferToName) {
        this.transferToName = transferToName;
    }

    @Override
    public String toString() {
        return transferId + "   " + "From: " + transferFromName + "     $"+transferAmount;

    }

    public String toStringTwo() {
        return  "-------------------------------------\n"+
                "Transfer Details\n" +
                "-------------------------------------\n"+
                "Id: " + transferId + "\n" +
                "From: " + transferFromName + "\n" +
                "To: " + transferToName + "\n" +
                "Type: " + "Send" + "\n" +
                "Status: " + "Approved" + "\n" +
                "Amount: " + "$" + transferAmount;
    }
}
