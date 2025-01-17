package com.rc.porms.data.prefect.communityservice.dao.impl;


import com.rc.porms.EmployeeInfoMgtApplication;
import com.rc.porms.appl.facade.employee.EmployeeFacade;
import com.rc.porms.appl.facade.prefect.offense.OffenseFacade;
import com.rc.porms.appl.facade.prefect.offense.impl.OffenseFacadeImpl;
import com.rc.porms.appl.model.communityservice.CommunityService;
import com.rc.porms.appl.model.employee.Employee;
import com.rc.porms.appl.model.offense.Offense;
import com.rc.porms.appl.model.student.Student;
import com.rc.porms.appl.model.violation.Violation;
import com.rc.porms.data.connection.ConnectionHelper;
import com.rc.porms.data.prefect.communityservice.CommunityServiceDao;
import com.rc.porms.data.prefect.offense.OffenseDao;
import com.rc.porms.data.prefect.offense.dao.impl.OffenseDaoImpl;
import com.rc.porms.data.utils.prefect.QueryConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.rc.porms.data.utils.prefect.QueryConstants.*;


/**
 * Implements the CommunityServiceDao interface to interact with the database for managing community service records.
 */

public class CommunityServiceDaoImpl implements CommunityServiceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityServiceDaoImpl.class);


    @Override
    public List<CommunityService> getAllCs() {
        List<CommunityService> communityServices = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement statement = con.prepareStatement(GET_ALL_CS_STATEMENT);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("student_id"));
                student.setFirstName(resultSet.getString("student_first_name"));
                student.setLastName(resultSet.getString("student_last_name"));
                student.setMiddleName(resultSet.getString("student_middle_name"));

                CommunityService communityService = new CommunityService();
                communityService.setStudent(student);
                communityService.setStation_name(resultSet.getString("station_station_name"));
                communityService.setReason_of_cs(resultSet.getString("cs_reason_of_cs"));
                communityService.setNature_of_work(resultSet.getString("cr_nature_of_work"));
                communityService.setHours_completed(resultSet.getInt("cr_hours_completed"));
                communityService.setDate_rendered(resultSet.getTimestamp("cr_date_of_cs"));
                communityServices.add(communityService);
            }
            if (communityServices.isEmpty()) {
                LOGGER.debug("Community Service database is empty.");
            } else {
                LOGGER.info("Community Service retrieved successfully.");
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving all community services: " + ex.getMessage());
            ex.printStackTrace();
        }
        return communityServices;
    }

    @Override
    public List<CommunityService> getAllCSByClusterName(String clusterName) {
        List<CommunityService> communityServiceSlips = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement statement = con.prepareStatement(QueryConstants.GET_ALL_CS_BY_CLUSTER_NAME_STATEMENT)) {

            statement.setString(1, "%" + clusterName + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CommunityService communityService = new CommunityService();

                    communityService.setId(resultSet.getInt("cs_slip_id"));
                    communityService.setReason_of_cs(resultSet.getString("cs_reason_of_cs"));

                    Student student = new Student();
                    student.setId(resultSet.getInt("student_id"));
                    student.setFirstName(resultSet.getString("student_first_name"));
                    student.setLastName(resultSet.getString("student_last_name"));
                    student.setMiddleName(resultSet.getString("student_middle_name"));
                    communityService.setStudent(student);

                    communityService.setHours_completed(resultSet.getInt("cr_hours_completed"));
                    communityService.setNature_of_work(resultSet.getString("cr_nature_of_work"));
                    communityService.setDate_rendered(resultSet.getTimestamp("cr_date_of_cs"));
                    communityService.setStation_name(resultSet.getString("station_station_name"));

                    communityServiceSlips.add(communityService);
                }
                LOGGER.info("Community Service records retrieved successfully for cluster name: " + clusterName);
            } catch (SQLException ex) {
                LOGGER.error("Error processing result set: " + ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            LOGGER.error("Error preparing statement or establishing connection: " + ex.getMessage(), ex);
        }

        return communityServiceSlips;
    }
}




