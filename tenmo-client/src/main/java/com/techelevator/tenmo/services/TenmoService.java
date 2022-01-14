package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import org.apiguardian.api.API;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class TenmoService {

    private static final String API_BASE_URL = "http://localhost:8080/";


    private RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public User[] displayAllUsers(){
        User[] users = restTemplate.exchange(API_BASE_URL + "allusers",
                HttpMethod.GET,
                makeAuthEntity(),
                User[].class).getBody();
        return users;
    }

    public Transfers[] displayTransferId(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        Transfers[] transfers = restTemplate.exchange(API_BASE_URL + "detail",
                HttpMethod.GET,
                entity,
                Transfers[].class).getBody();

        return transfers;
    }

    public BigDecimal retrieveBalance(){

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        Balance balance = restTemplate.exchange(API_BASE_URL + "balance",
                HttpMethod.GET,
                entity,
                Balance.class).getBody();

        return balance.getBalance();
    }

    public Transfers addTransaction(Transfers transfers){

        Transfers conductedTransfer = null;

        try{
            conductedTransfer = restTemplate.exchange(API_BASE_URL + "transaction",
                    HttpMethod.POST,
                    makeTransferEntity(transfers),
                    Transfers.class).getBody();
        } catch(RestClientResponseException | ResourceAccessException e){
            System.out.println("Please enter valid ID.");
        }
        return conductedTransfer;
    }


    private HttpEntity<Transfers> makeTransferEntity(Transfers enterAmount) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(this.authToken);

        HttpEntity<Transfers> entity = new HttpEntity<>(enterAmount, headers);
        return entity;
    }

    private HttpEntity<Void> makeAuthEntity() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return entity;
    }


}
