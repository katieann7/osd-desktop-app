package com.rc.porms.appl.facade.user.impl;

import com.rc.porms.appl.model.user.User;
import com.rc.porms.data.user.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class UserFacadeImplTest {

    @Mock
    private UserDao userDaoMock;

    @InjectMocks
    private UserFacadeImpl userFacade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByUsername() {
        String username = "Josh";
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername(username);
        mockUser.setPassword("Changeme@0");
        when(userDaoMock.getUserByUsername(username)).thenReturn(mockUser);

        User result = userFacade.getUserByUsername(username);

        assertEquals(mockUser, result);
        verify(userDaoMock).getUserByUsername(username);
    }
}
