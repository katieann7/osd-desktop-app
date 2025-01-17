package com.rc.porms.appl.facade.user.impl;

import com.rc.porms.appl.model.offense.Offense;
import com.rc.porms.appl.model.user.User;
import com.rc.porms.data.user.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Unit test for UserFacadeImpl's getUserByUsername method.
 */
class UserFacadeImplTest {

    @Mock
    private UserDao userDaoMock;

    @InjectMocks
    private UserFacadeImpl userFacade;

    /**
     * Initializes mock objects and the UserFacadeImpl instance before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test the getUserByUsername method.
     */
    @Test
    void testGetUserByUsername() {
        String username = "kate";
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername(username);
        mockUser.setPassword("password@123");
        when(userDaoMock.getUserByUsername(username)).thenReturn(mockUser);

        User result = userFacade.getUserByUsername(username);

        assertEquals(mockUser, result);
        verify(userDaoMock).getUserByUsername(username);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1);
        List<User> mockUsers = Arrays.asList(user1);

        when(userDaoMock.getAllUsers()).thenReturn(mockUsers);
        List<User> users = userFacade.getAllUsers();

        assertNotNull(users, "The users list should not be null.");
        assertEquals(1, users.size(), "There should be 1 users.");
        assertEquals(1, users.get(0).getId(), "The first user should have ID 1.");

        verify(userDaoMock, times(1)).getAllUsers(); // Ensure the mock method is called once
    }
}
