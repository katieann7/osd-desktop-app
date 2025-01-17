/**
 * The com.system.demo.appl.facade.prefect.communityservice.impl package contains implementations
 * of facades for managing community service-related operations.
 */
package com.rc.porms.appl.facade.prefect.communityservice.impl;

import com.rc.porms.appl.facade.prefect.communityservice.CommunityServiceFacade;
import com.rc.porms.appl.model.communityservice.CommunityService;
import com.rc.porms.data.prefect.communityservice.CommunityServiceDao;

import java.util.List;

/**
 * The implementation of the CommunityServiceFacade interface.
 */
public class CommunityServiceFacadeImpl implements CommunityServiceFacade {

    private CommunityServiceDao communityServiceDao;

    /**
     * Constructs a new CommunityServiceFacadeImpl with the provided CommunityServiceDao.
     *
     * @param communityServiceDao The data access object for managing community service data.
     */
    public CommunityServiceFacadeImpl(CommunityServiceDao communityServiceDao) {
        this.communityServiceDao = communityServiceDao;
    }

    @Override
    public List<CommunityService> getAllCSByClusterName(String clusterName) {
        try {
            return communityServiceDao.getAllCSByClusterName(clusterName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Violation: " + e.getMessage(), e);
        }
    }


    @Override
    public List<CommunityService> getAllCs() {
        return communityServiceDao.getAllCs();
    }
}