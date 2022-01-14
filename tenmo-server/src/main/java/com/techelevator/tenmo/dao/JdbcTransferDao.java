package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransfersDao {

    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addToTransfersTable(User userId, User receiverId, BigDecimal amount) {

        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, " +
                "account_to, amount) VALUES (2, 2, (SELECT account_id FROM accounts WHERE user_id = ?), " +
                "(SELECT account_id FROM accounts WHERE user_id = ?), ?)";

        Integer transferId;
        try {
            transferId = jdbcTemplate.queryForObject(sql, Integer.class,
                    userId.getId(), receiverId.getId(), amount);

        } catch (DataAccessException e) {

            return false;
        }
        sql = "INSERT INTO transfers (transfer_id) VALUES(?)";

        try {
            jdbcTemplate.update(sql, transferId);
        } catch (DataAccessException e) {

            return false;
        }
        return true;
    }


    public List<Transfers> getTransferIdOfSender(String username) {

        String sql = "select transfer_id, account_from, account_to, amount " +
                "From transfers " +
                "Join accounts ON transfers.account_from = accounts.account_id " +
                "Join users ON accounts.user_id = users.user_id " +
                "Where username = ?";

        List<Transfers> myList = new ArrayList<>();

        SqlRowSet result = null;
        try {
            result = jdbcTemplate.queryForRowSet(sql, username);
        } catch (DataAccessException e) {
            System.out.println("What we got here is another failure to communicate.");
        }
        while (result.next()) {

            Transfers singleTransfer = mapRowToTransfers(result);
            myList.add(singleTransfer);
        }

        return myList;

    }

    public List<Transfers> getTransferIdOfReceiver(String username) {

        String sql = "select transfer_id, account_from, account_to, amount " +
                "From transfers " +
                "Join accounts ON transfers.account_to = accounts.account_id " +
                "Join users ON accounts.user_id = users.user_id " +
                "Where username = ?";

        List<Transfers> myList = new ArrayList<>();

        SqlRowSet result = null;
        try {
            result = jdbcTemplate.queryForRowSet(sql, username);
        } catch (DataAccessException e) {
            System.out.println("What we got here is another failure to communicate.");
        }
        while (result.next()) {

            Transfers singleTransfer = mapRowToTransfers(result);
            myList.add(singleTransfer);
        }
        return myList;

    }

    private Transfers mapRowToTransfers(SqlRowSet rs) {
        Transfers transfers = new Transfers();
        transfers.setTransferId(rs.getLong("transfer_id"));
        transfers.setSenderId(rs.getInt("account_from"));
        transfers.setRecieverId(rs.getInt("account_to"));
        transfers.setTransferAmount(rs.getBigDecimal("amount"));
        return transfers;
    }

}


