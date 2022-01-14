package com.techelevator.tenmo.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AuthenticatedUserTest {

    @Test
    public void authenticated_user_test(){

        AuthenticatedUser authenticatedUser = new AuthenticatedUser();

        String token1 = "token";
        User user = new User();

        authenticatedUser.setToken(token1);
        authenticatedUser.setUser(user);

        assertEquals(token1, authenticatedUser.getToken());
        assertEquals(user, authenticatedUser.getUser());
    }


}