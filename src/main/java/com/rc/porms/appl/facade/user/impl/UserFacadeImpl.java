package com.rc.porms.appl.facade.user.impl;

import com.rc.porms.appl.facade.user.UserFacade;
import com.rc.porms.appl.model.user.User;
import com.rc.porms.data.user.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * An implementation class of the User Facade.
 */
public class UserFacadeImpl implements UserFacade {

        /**
         * The logger for this class.
         */
        public static final Logger LOGGER = LoggerFactory.getLogger(UserFacadeImpl.class);

        private UserDao userDao;

        /**
         * Constructs a new UserFacadeImpl with the provided UserDao.
         *
         * @param userDao The data access object for user operations.
         */
        public UserFacadeImpl(UserDao userDao) {
            this.userDao = userDao;
        }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return the User object associated with the given username, or null if not found
     */
    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void setLocked(String username) {
        userDao.setLocked(username);
    }

    @Override
    public void setUnLocked(String username) {
        userDao.setUnLocked(username);
    }

}
