package com.rc.porms.data.user.dao.impl;

import com.rc.porms.appl.model.user.User;
import com.rc.porms.data.connection.ConnectionHelper;
import com.rc.porms.data.user.dao.UserDao;
import com.rc.porms.data.utils.user.QueryConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the UserDao interface to perform CRUD operations for User entities.
 * Provides methods for saving, updating, retrieving, and modifying User information in a database.
 */
public class UserDaoImpl implements UserDao {

    /**
     * Logger for logging events in the UserDaoImpl class.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    /**
     * Retrieves a user from the database by their username.
     *
     * Establishes a connection to the database and executes a query to find the user
     * with the specified username. If the user is found, a User object is created
     * and populated with the user's data.
     *
     * @param username the username of the user to retrieve
     * @return the User object associated with the given username, or null if the user is not found
     */
    @Override
    public User getUserByUsername(String username) {
        User user = null;

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_USERS_BY_USERNAME_STATEMENT);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("USERNAME"));
                user.setIsActive(rs.getInt("IS_ACTIVE"));
                user.setIsLocked(rs.getInt("IS_LOCKED"));
                user.setJoinDate(rs.getTimestamp("JOIN_DATE"));
                user.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE"));
                user.setAuthorities(rs.getString("AUTHORITIES"));
                user.setOtp(rs.getString("OTP"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setRole(rs.getString("ROLE"));
            }
        } catch (Exception e) {
            LOGGER.error("Error retrieving user with User name " + username + ": " + e.getMessage());
            e.printStackTrace();
        }

        LOGGER.debug("Username not found.");
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> login = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_ALL_LOGIN_STATEMENT);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                login.add(mapResultSetToUser(rs));
            }
            return login;

        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("Student database is empty.");
        return login;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setIsActive(rs.getInt("IS_ACTIVE"));
        user.setIsLocked(rs.getInt("IS_LOCKED"));
        user.setId(rs.getLong("ID"));
        user.setJoinDate(rs.getTimestamp("JOIN_DATE"));
        user.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE"));
        user.setAuthorities(rs.getString("AUTHORITIES"));
        user.setOtp(rs.getString("OTP"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setRole(rs.getString("ROLE"));
        user.setUsername(rs.getString("USERNAME"));

        return user;
    }

    @Override
    public void setLocked(String username) {
        String sql = "UPDATE login SET IS_LOCKED = ? WHERE USERNAME = ?";

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, 1); // Assuming `newLockStatus` is the updated lock status (0 or 1)
            stmt.setString(2, username); // Use the appropriate variable or parameter for the username

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.debug("User locked status updated successfully.");
            } else {
                LOGGER.debug("No user found with the specified username.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error updating lock status for user: " + e.getMessage());
        }
    }

    @Override
    public void setUnLocked(String username) {
        String sql = "UPDATE login SET IS_LOCKED = ? WHERE USERNAME = ?";

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, 0); // Assuming `newLockStatus` is the updated lock status (0 or 1)
            stmt.setString(2, username); // Use the appropriate variable or parameter for the username

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.debug("User locked status updated successfully.");
            } else {
                LOGGER.debug("No user found with the specified username.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error updating lock status for user: " + e.getMessage());
        }
    }

}
