package com.rc.porms.data.prefect.communityservice;

import com.rc.porms.appl.model.communityservice.CommunityService;
import com.rc.porms.appl.model.student.Student;

import java.util.List;

/**
 * Interface for accessing and managing community service records in the database.
 */
public interface CommunityServiceDao {

    /**
     * Retrieves all community service records from the database.
     *
     * @return A list of all community service records.
     */
    List<CommunityService> getAllCs();

    List<CommunityService> getAllCSByClusterName(String clusterName);
}
