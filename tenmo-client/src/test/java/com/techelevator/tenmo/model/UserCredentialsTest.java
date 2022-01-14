package com.techelevator.tenmo.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserCredentialsTest {

    @Test
    public void user_credentials(){

        UserCredentials userCredentials = new UserCredentials("babaganoush", "Walnut2");

        assertEquals("babaganoush", userCredentials.getUsername());
        assertEquals("Walnut2", userCredentials.getPassword());

    }

}