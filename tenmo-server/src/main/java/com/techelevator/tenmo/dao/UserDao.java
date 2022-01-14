package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAllUsersExceptLoggedInUser(String name);

    int givenUserIdFindAccountId(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    String findUsernameByUserId(int userId);

    int findUserIdByAccountId(int accountId);
}
