package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserDao implements UserDao {

    private static final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");
    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        String sql = "SELECT user_id FROM users WHERE username ILIKE ?;";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (id != null) {
            return id;
        } else {
            return -1;
        }
    }

    @Override
    public int givenUserIdFindAccountId(int userId) {
        String sql = "SELECT account_id FROM accounts WHERE user_id = ?; ";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        if (id != null) {
            return id;
        } else {
            return -1;
        }
    }

    @Override
    public List<User> findAllUsersExceptLoggedInUser(String name) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash FROM users WHERE username != ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);

        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }
        return users;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT user_id, username, password_hash FROM users WHERE username ILIKE ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()) {
            return mapRowToUser(rowSet);
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password) {

        // create user
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?) RETURNING user_id";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        Integer newUserId;
        try {
            newUserId = jdbcTemplate.queryForObject(sql, Integer.class, username, password_hash);
        } catch (DataAccessException e) {
            return false;
        }

        // create account
        sql = "INSERT INTO accounts (user_id, balance) values(?, ?)";
        try {
            jdbcTemplate.update(sql, newUserId, STARTING_BALANCE);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public int findUserIdByAccountId(int accountId) {
        String sql = "SELECT user_id FROM accounts WHERE account_id =?";

        int id = jdbcTemplate.queryForObject(sql, Integer.class, accountId);
        if (id != 0) {
            return id;
        }
        return -1;
    }

    public String findUsernameByUserId(int userId) {
        String sql = "SELECT username FROM users WHERE user_id =?";

        String name = jdbcTemplate.queryForObject(sql, String.class, userId);
        if (name != null) {
            return name;
        }
        return "Oops. Try Again.";
    }

        private User mapRowToUser (SqlRowSet rs){
            User user = new User();
            user.setId(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password_hash"));
            user.setActivated(true);
            user.setAuthorities("USER");
            return user;
        }
    }


