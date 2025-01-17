package com.rc.porms.appl.facade.prefect.violation;


import com.rc.porms.appl.model.student.Student;
import com.rc.porms.appl.model.violation.Violation;
import java.util.List;

/**
 * An interface to the Violation Facade.
 */

public interface ViolationFacade {

    /**
     * Retrieves all Violation from the database.
     *
     * @return list of all Violation.
     * */
    List<Violation> getAllViolation();

    /**
     * Retrieves a Violation from the database with specified id.
     *
     * @param id the id of the Violation.
     * @return the Violation.
     * */
    Violation getViolationByID(int id);

    /**
     * Retrieves all Violation from the database with specified student id.
     *
     * @param studentId the id of the students.
     * @return the Violation.
     * */
    List<Violation> getAllViolationByStudent(Student studentId);

    List<Violation> getAllViolationByClusterName(String clusterName);
    /**
     * Updates a Student in the database.
     *
     * @param violation  Violation to update.
     * @return true if update is successful.
     * */
   boolean updateViolation(Violation violation);

    /**
     * Adds a Student to the database.
     *
     * @param violation Violation to add.
     * @return true if adding is successful.
     * */
    boolean addViolation(Violation violation);

    /**
     * This retrieves all violation by type in the database
     * @return list of violation by type from the database
     */
    List<Violation> getViolationByStudentName(String lastName, String firstName);



}