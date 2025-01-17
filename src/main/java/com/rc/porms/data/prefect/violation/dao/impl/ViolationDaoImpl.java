package com.rc.porms.data.prefect.violation.dao.impl;


import com.rc.porms.EmployeeInfoMgtApplication;
import com.rc.porms.StudentInfoMgtApplication;
import com.rc.porms.appl.facade.employee.EmployeeFacade;
import com.rc.porms.appl.facade.prefect.offense.OffenseFacade;
import com.rc.porms.appl.facade.prefect.offense.impl.OffenseFacadeImpl;
import com.rc.porms.appl.facade.student.StudentFacade;
import com.rc.porms.appl.model.employee.Employee;
import com.rc.porms.appl.model.offense.Offense;
import com.rc.porms.appl.model.student.Student;
import com.rc.porms.appl.model.violation.Violation;
import com.rc.porms.data.connection.ConnectionHelper;
import com.rc.porms.data.prefect.offense.OffenseDao;
import com.rc.porms.data.prefect.offense.dao.impl.OffenseDaoImpl;
import com.rc.porms.data.prefect.violation.ViolationDao;
import com.rc.porms.data.utils.prefect.QueryConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an implementation class of the ViolationDao
 */
public class ViolationDaoImpl implements ViolationDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViolationDaoImpl.class);

    /**
     * Retrieves a Violation from the database using a specific ID.
     * @param id the ID of the Violation
     * @return the Violation object with the specified ID, or null if not found
     */
    @Override
    public Violation getViolationByID(int id) {
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(QueryConstants.GET_VIOLATION_BY_ID_STATEMENT)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int studentId = rs.getInt("student_id");

                    StudentInfoMgtApplication app = new StudentInfoMgtApplication();
                    StudentFacade studentFacade = app.getStudentFacade();
                    Student student = studentFacade.getStudentById(studentId);

                    int offenseId = rs.getInt("offense_id");
                    OffenseDao offenseDao = new OffenseDaoImpl();
                    OffenseFacade offenseFacade = new OffenseFacadeImpl(offenseDao);
                    Offense offense = offenseFacade.getOffenseByID(offenseId);

                    int warningNumber = rs.getInt("warning_number");
                    int csHours = rs.getInt("cs_hours");
                    String disciplinaryAction = rs.getString("disciplinary_action");
                    Timestamp dateOfNotice = rs.getTimestamp("date_of_notice");

                    int approvedById = rs.getInt("approved_by_id");
                    EmployeeInfoMgtApplication appl = new EmployeeInfoMgtApplication();
                    EmployeeFacade employeeFacade = appl.getEmployeeFacade();
                    Employee approvedBy = employeeFacade.getById(approvedById);

                    Violation violation = new Violation(id, student, offense, csHours, warningNumber, disciplinaryAction, dateOfNotice, approvedBy, student.getLastName() + ", " + student.getFirstName(), offenseId, approvedById, studentId);
                    return violation;
                } else {
                    LOGGER.warn("No violation found with ID: " + id);
                }
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving violation with ID " + id + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        LOGGER.debug("Violation not found.");
        return null;
    }

    /**
     * Updates an existing Violation in the database.
     * @param violation the Violation object containing updated data
     * @return true if the update is successful, false otherwise
     */
    @Override
    public boolean updateViolation(Violation violation) {
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(QueryConstants.UPDATE_VIOLATION_STATEMENT)) {

            Offense offense = violation.getOffense();
            Student student = violation.getStudent();
            Employee employee = violation.getApprovedBy();

            stmt.setInt(1, student.getId());
            stmt.setInt(2, offense.getId());
            stmt.setInt(3, violation.getWarningNum());
            stmt.setInt(4, violation.getCommServHours());
            stmt.setString(5, violation.getDisciplinaryAction());
            stmt.setTimestamp(6, violation.getDateOfNotice());
            stmt.setInt(7, employee.getId());
            stmt.setInt(8, violation.getId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            LOGGER.warn("Error updating Violation with ID " + violation.getId() + ": " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all Violations from the database.
     * @return a list of all Violation objects, or an empty list if no records are found
     */
    @Override
    public List<Violation> getAllViolation() {
        List<Violation> violations = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement statement = con.prepareStatement(QueryConstants.GET_ALL_VIOLATION_STATEMENT);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Violation violation = new Violation();
                violation.setId(resultSet.getInt("id"));

                StudentInfoMgtApplication app = new StudentInfoMgtApplication();
                StudentFacade studentFacade = app.getStudentFacade();
                Student student = studentFacade.getStudentById(resultSet.getInt("student_id"));
                violation.setStudent(student);

                OffenseDao offenseDao = new OffenseDaoImpl();
                OffenseFacade offenseFacade = new OffenseFacadeImpl(offenseDao);
                Offense offense = offenseFacade.getOffenseByID(resultSet.getInt("offense_id"));
                violation.setOffense(offense);

                violation.setWarningNum(resultSet.getInt("warning_number"));
                violation.setCommServHours(resultSet.getInt("cs_hours"));
                violation.setDisciplinaryAction(resultSet.getString("disciplinary_action"));
                violation.setDateOfNotice(resultSet.getTimestamp("date_of_notice"));

                EmployeeInfoMgtApplication appl = new EmployeeInfoMgtApplication();
                EmployeeFacade employeeFacade = appl.getEmployeeFacade();
                Employee employee = employeeFacade.getById(resultSet.getInt("approved_by_id"));
                violation.setApprovedBy(employee);

                violations.add(violation);
            }
            LOGGER.info("Violation retrieved successfully.");
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving all Violation: " + ex.getMessage());
            ex.printStackTrace();
        }
        LOGGER.debug("Violation database is empty.");
        return violations;
    }

    /**
     * Retrieves all Violations associated with a specific Student.
     * @param student the Student whose Violations are to be retrieved
     * @return a list of Violations for the specified Student, or an empty list if none are found
     */
    @Override
    public List<Violation> getAllViolationByStudent(Student student) {
        List<Violation> violations = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement statement = con.prepareStatement(QueryConstants.GET_ALL_VIOLATION_BY_STUDENT_ID_STATEMENT)) {

            statement.setInt(1, student.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Violation violation = new Violation();
                    violation.setId(resultSet.getInt("id"));

                    // Set student
                    violation.setStudent(student);

                    // Retrieve offense
                    OffenseDao offenseDao = new OffenseDaoImpl();
                    OffenseFacade offenseFacade = new OffenseFacadeImpl(offenseDao);
                    Offense offense = offenseFacade.getOffenseByID(resultSet.getInt("offense_id"));
                    violation.setOffense(offense);

                    violation.setWarningNum(resultSet.getInt("warning_number"));
                    violation.setCommServHours(resultSet.getInt("cs_hours"));
                    violation.setDisciplinaryAction(resultSet.getString("disciplinary_action"));
                    violation.setDateOfNotice(resultSet.getTimestamp("date_of_notice"));

                    // Retrieve approved by employee
                    EmployeeInfoMgtApplication appl = new EmployeeInfoMgtApplication();
                    EmployeeFacade employeeFacade = appl.getEmployeeFacade();
                    Employee employee = employeeFacade.getById(resultSet.getInt("approved_by_id"));
                    violation.setApprovedBy(employee);

                    violations.add(violation);
                }
                LOGGER.info("Violations retrieved successfully.");
            } catch (SQLException ex) {
                LOGGER.warn("Error retrieving all violations: " + ex.getMessage());
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error preparing statement: " + ex.getMessage());
            ex.printStackTrace();
        }
        return violations;
    }

    @Override
    public List<Violation> getAllViolationByClusterName(String clusterName) {
        List<Violation> violations = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement statement = con.prepareStatement(QueryConstants.GET_ALL_VIOLATION_BY_CLUSTER_NAME_STATEMENT)) {

            statement.setString(1, "%" + clusterName + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Violation violation = new Violation();
                    violation.setId(resultSet.getInt("violation_id"));

                    Student student = new Student();
                    student.setId(resultSet.getInt("student_id"));
                    student.setFirstName(resultSet.getString("student_first_name"));
                    student.setLastName(resultSet.getString("student_last_name"));
                    violation.setStudent(student);

                    OffenseDao offenseDao = new OffenseDaoImpl();
                    OffenseFacade offenseFacade = new OffenseFacadeImpl(offenseDao);
                    Offense offense = offenseFacade.getOffenseByID(resultSet.getInt("offense_id"));
                    violation.setOffense(offense);

                    violation.setWarningNum(resultSet.getInt("warning_number"));
                    violation.setCommServHours(resultSet.getInt("cs_hours"));
                    violation.setDisciplinaryAction(resultSet.getString("disciplinary_action"));
                    violation.setDateOfNotice(resultSet.getTimestamp("date_of_notice"));

                    EmployeeInfoMgtApplication appl = new EmployeeInfoMgtApplication();
                    EmployeeFacade employeeFacade = appl.getEmployeeFacade();
                    Employee employee = employeeFacade.getById(resultSet.getInt("approved_by_id"));
                    violation.setApprovedBy(employee);

                    violations.add(violation);
                }
                LOGGER.info("Violations retrieved successfully for cluster name: " + clusterName);
            } catch (SQLException ex) {
                LOGGER.error("Error processing result set: " + ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            LOGGER.error("Error preparing statement or establishing connection: " + ex.getMessage(), ex);
        }

        return violations;
    }



    /**
     * Adds a new Violation to the database.
     * @param violation the Violation object to be added
     * @return true if the addition is successful, false otherwise
     */
    @Override
    public boolean addViolation(Violation violation) {
        if (violation == null || violation.getOffense() == null || violation.getStudent() == null) {
            LOGGER.warn("Invalid violation object: " + violation);
            return false;
        }

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(QueryConstants.ADD_VIOLATION_STATEMENT)) {

            Offense offense = violation.getOffense();
            Student student = violation.getStudent();
            Employee employee = violation.getApprovedBy();

            stmt.setInt(1, student.getId());
            stmt.setInt(2, offense.getId());
            stmt.setInt(3, violation.getWarningNum());
            stmt.setInt(4, violation.getCommServHours());
            stmt.setString(5, violation.getDisciplinaryAction());
            stmt.setTimestamp(6, violation.getDateOfNotice());
            stmt.setInt(7, employee.getId());

            int affectedRows = stmt.executeUpdate();
            //return affectedRows > 0;
            return true;
        } catch (SQLException ex) {
            LOGGER.warn("Error adding violation: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves Violations from the database by matching a Student's name.
     * @param lastName the last name of the Student
     * @param firstName the first name of the Student
     * @return a list of Violations matching the specified name, or an empty list if none are found
     */
    @Override
    public List<Violation> findByStudentName(String lastName, String firstName) {
        List<Violation> violations = new ArrayList<>();
        String query = QueryConstants.FIND_VIOLATION_BY_STUDENT_NAME_STATEMENT;

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, lastName);
            stmt.setString(2, firstName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Violation violation = new Violation();

                    violation.setCommServHours(rs.getInt("CS_HOURS"));
                    violation.setWarningNum(rs.getInt("WARNING_NUMBER"));
                    violation.setApprovedById(rs.getInt("APPROVED_BY_ID"));
                    violation.setDateOfNotice(rs.getTimestamp("DATE_OF_NOTICE"));
                    violation.setId(rs.getInt("ID"));
                    violation.setOffenseId(rs.getInt("OFFENSE_ID"));
                    violation.setStudentId(rs.getInt("STUDENT_ID"));
                    violation.setDisciplinaryAction(rs.getString("DISCIPLINARY_ACTION"));

                    violation.setStudentName(rs.getString("student_last_name") + ", " + rs.getString("student_first_name"));

                    Student student = new Student();
                    student.setId(rs.getInt("STUDENT_ID"));
                    student.setFirstName(rs.getString("student_first_name"));
                    student.setLastName(rs.getString("student_last_name"));

                    violation.setStudent(student);

                    violations.add(violation);
                }
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving violations by student name: " + ex.getMessage());
            ex.printStackTrace();
        }
        return violations;
    }
}
