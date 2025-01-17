package com.rc.porms.appl.facade.prefect.violation.impl;


import com.rc.porms.appl.facade.prefect.violation.ViolationFacade;
import com.rc.porms.appl.model.student.Student;
import com.rc.porms.appl.model.violation.Violation;
import com.rc.porms.data.prefect.violation.ViolationDao;

import java.util.List;

/**
 * An implementation class of the Offence Facade.
 */

public class ViolationFacadeImpl implements ViolationFacade {
    private ViolationDao violationDao;

    /**
     * Constructs a new ViolationFacadeImpl with the provided ViolationDao.
     *
     * @param violationDao The data access object for managing violation data.
     */
    public ViolationFacadeImpl(ViolationDao violationDao) {
        this.violationDao = violationDao;
    }

    /**
     * Retrieves all violations from the database.
     *
     * @return A list of all violations.
     * @throws RuntimeException If an error occurs while retrieving violations.
     */
    public List<Violation> getAllViolation() throws RuntimeException {
        try {
            return violationDao.getAllViolation();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Violation: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a Violation by its ID.
     *
     * @param id the ID of the Violation
     * @return the Violation object with the specified ID, or null if not found
     */
    @Override
    public Violation getViolationByID(int id) {
        return violationDao.getViolationByID(id);
    }

    /**
     * Retrieves all Violations associated with a specific Student.
     *
     * @param studentId the Student whose Violations are to be retrieved
     * @return a list of Violations for the specified Student
     * @throws RuntimeException if an error occurs while retrieving violations
     */
    @Override
    public List<Violation> getAllViolationByStudent(Student studentId) {
        try {
            return violationDao.getAllViolationByStudent(studentId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Violation: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Violation> getAllViolationByClusterName(String clusterName) {
        try {
            return violationDao.getAllViolationByClusterName(clusterName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Violation: " + e.getMessage(), e);
        }
    }


    /**
     * Updates an existing Violation in the database.
     *
     * @param violation the Violation object containing updated data
     * @return true if the update is successful, false otherwise
     * @throws RuntimeException if the Violation does not exist or an error occurs during the update
     */
    @Override
    public boolean updateViolation(Violation violation) throws RuntimeException{
        boolean result = false;
        try {
            Violation targetViolation = getViolationByID(violation.getId());
            if (targetViolation == null) {
                throw new Exception("Violation to update not found.");
            }
            result = violationDao.updateViolation(violation);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage != null) {
                throw new RuntimeException(errorMessage);
            } else {
                throw new RuntimeException("An error occurred while updating the Violation information.");
            }
        }
        return result;
    }


    /**
     * Adds a new Violation to the database.
     *
     * @param violation the Violation object to be added
     * @return true if the Violation is successfully added, false otherwise
     * @throws RuntimeException if an error occurs during the addition
     */
    @Override
    public boolean addViolation(Violation violation) throws RuntimeException {
        try {
            return violationDao.addViolation(violation);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add Violation: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves Violations from the database by matching a Student's name.
     *
     * @param lastName  the last name of the Student
     * @param firstName the first name of the Student
     * @return a list of Violations matching the specified name
     */
    @Override
    public List<Violation> getViolationByStudentName(String lastName, String firstName) {
        return violationDao.findByStudentName(lastName, firstName);
    }
}