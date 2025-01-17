package com.rc.porms.data.user.dao.impl;

import com.rc.porms.appl.facade.user.impl.UserFacadeImpl;
import com.rc.porms.appl.model.user.User;
import com.rc.porms.data.user.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDaoImplTest {

    @Mock
    private UserDaoImpl userDao;

    @InjectMocks
    private UserFacadeImpl userFacade;

    /**
     * Initializes mock objects and the UserFacadeImpl instance before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByUsername() {
        User mockUser = new User();
        mockUser.setUsername("Kate");
        mockUser.setId(1);

        when(userDao.getUserByUsername("Kate")).thenReturn(mockUser);
        User user = userFacade.getUserByUsername("Kate");

        assertNotNull(user, "User should not be null");
        assertEquals("Kate", user.getUsername(), "Username should match");
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setUserId("CT21-0073");
        List<User> mockUsers = Arrays.asList(user1);

        when(userDao.getAllUsers()).thenReturn(mockUsers);

        List<User> users = userFacade.getAllUsers();

        assertNotNull(users, "The users list should not be null.");
        assertEquals(1, users.size(), "There should be 1 user.");
        assertEquals("CT21-0073", users.get(0).getUserId(), "The first user should have user ID USR21-0073.");

        verify(userDao, times(1)).getAllUsers();
    }
}

