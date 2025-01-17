/**
 * The com.system.demo.appl.facade.prefect.communityservice package contains interfaces and classes
 * related to the prefect community service facade.
 */
package com.rc.porms.appl.facade.prefect.communityservice;

import com.rc.porms.appl.model.communityservice.CommunityService;
import com.rc.porms.appl.model.student.Student;

import java.util.List;

/**
 * The CommunityServiceFacade interface defines methods for managing community service data.
 */
public interface CommunityServiceFacade {

    List<CommunityService> getAllCSByClusterName(String clusterName);

    /**
     * Retrieves all community services from the database.
     *
     * @return A list of all community services.
     */
    List<CommunityService> getAllCs();
}
