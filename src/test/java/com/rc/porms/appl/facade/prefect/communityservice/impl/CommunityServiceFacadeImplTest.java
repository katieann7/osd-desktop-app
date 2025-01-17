package com.rc.porms.appl.facade.prefect.communityservice.impl;

import com.rc.porms.appl.model.communityservice.CommunityService;
import com.rc.porms.data.prefect.communityservice.CommunityServiceDao;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CommunityServiceFacadeImplTest {
    private CommunityServiceDao communityServiceDao = mock(CommunityServiceDao.class);
    private CommunityServiceFacadeImpl communityServiceFacade = new CommunityServiceFacadeImpl(communityServiceDao);

    @Test
    void testGetAllCS() {
        CommunityService communityService1 = new CommunityService();
        communityService1.setId(1);
        CommunityService communityService2 = new CommunityService();
        communityService2.setId(2);
        List<CommunityService> communityServices = Arrays.asList(communityService1, communityService2);
        when(communityServiceDao.getAllCs()).thenReturn(communityServices);

        List<CommunityService> result = communityServiceDao.getAllCs();

        assertNotNull(result, "Community Service list should not be null.");
        assertEquals(2, result.size(), "There should be two community service in the list.");
        verify(communityServiceDao, times(1)).getAllCs();
    }

    @Test
    void testGetAllCSByClusterName() {
        CommunityService communityService1 = new CommunityService();
        communityService1.setId(1);
        CommunityService communityService2 = new CommunityService();
        communityService2.setId(2);
        List<CommunityService> communityServices = Arrays.asList(communityService1, communityService2);
        when(communityServiceDao.getAllCSByClusterName("CETE")).thenReturn(communityServices);

        List<CommunityService> result = communityServiceDao.getAllCSByClusterName("CETE");

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(communityServiceDao, times(1)).getAllCSByClusterName("CETE");
    }
}
