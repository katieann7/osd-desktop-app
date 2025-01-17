package com.rc.porms.data.prefect.communityservice.dao;

import com.rc.porms.appl.model.communityservice.CommunityService;
import com.rc.porms.data.prefect.communityservice.CommunityServiceDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CommunityServiceDaoImplTest {
    @Mock
    private CommunityServiceDao communityServiceDaoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllViolation() {
        CommunityService communityService1 = new CommunityService();
        communityService1.setId(1);
        when(communityServiceDaoMock.getAllCs()).thenReturn(Arrays.asList(communityService1));

        List<CommunityService> communityServices = communityServiceDaoMock.getAllCs();

        assertNotNull(communityServices, "The community services list should not be null.");
        assertEquals(1, communityServices.size(), "There should be 1 community service.");
        assertEquals(1, communityServices.get(0).getId(), "The first community service should have ID 1.");

        verify(communityServiceDaoMock, times(1)).getAllCs();
    }

    @Test
    void testGetAllCSByClusterName() {
        CommunityService communityService1 = new CommunityService();
        communityService1.setId(1);
        when(communityServiceDaoMock.getAllCSByClusterName("CETE")).thenReturn(Arrays.asList(communityService1));

        List<CommunityService> communityServices = communityServiceDaoMock.getAllCSByClusterName("CETE");

        assertNotNull(communityServices, "The community services list should not be null.");
        assertEquals(1, communityServices.size(), "There should be 1 community service.");
        assertEquals(1, communityServices.get(0).getId(), "The first community service should have ID 1.");

        verify(communityServiceDaoMock, times(1)).getAllCSByClusterName("CETE");
    }
}
