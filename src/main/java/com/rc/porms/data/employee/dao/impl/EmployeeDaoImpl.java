package com.rc.porms.data.employee.dao.impl;

import com.rc.porms.appl.model.employee.Employee;
import com.rc.porms.data.connection.ConnectionHelper;
import com.rc.porms.data.employee.dao.EmployeeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.rc.porms.data.utils.employee.QueryConstants.*;

/**
 * The EmployeeDaoImpl class implements the EmployeeDao interface
 * it provides methods for interacting with the employee database.
 * It includes methods for retrieving, adding, and updating employee records.
 */
public class EmployeeDaoImpl implements EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDaoImpl.class);

    /**
     * Constructor for EmployeeDaoImpl.
     * */
    public EmployeeDaoImpl() {
    }

    /**
     * Retrieve all employees from the database.
     * This method queries the database to fetch all employee records.
     * @return A list of all {@code Employee} objects in the database, or an empty list if none exist.
     */
    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = ConnectionHelper.getConnection()){
            PreparedStatement stmt = connection.prepareStatement(GET_ALL_EMPLOYEE_STATEMENT);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                employees.add(setEmployee(rs));
            }
        } catch (SQLException e) {
            LOGGER.warn("Error retrieving all Employees." + e.getMessage());
            e.printStackTrace();
        }
        LOGGER.debug("Employee database is empty.");
        return employees;
    }

    /**
     * Retrieve an employee by their unique ID.
     * This method fetches a specific employee using their unique identifier as a string.
     * @param id The unique identifier of the employee.
     * @return The {@code Employee} object matching the provided ID, or {@code null} if not found.
     */
    @Override
    public Employee getEmployeeById(String id) {
        try (Connection connection = ConnectionHelper.getConnection()){
            PreparedStatement stmt = connection.prepareStatement(GET_EMPLOYEE_BY_ID_STATEMENT);
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    LOGGER.debug("Employee retrieved successfully.");
                    return setEmployee(rs);
                }
            }
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("Employee not found.");
        return null;
    }

    /**
     * Retrieve an employee by their integer ID.
     * This method fetches a specific employee using their unique database ID.
     * @param id The unique integer ID of the employee.
     * @return The {@code Employee} object matching the provided ID, or {@code null} if not found.
     */
    @Override
    public Employee getById(int id) {
        try (Connection connection = ConnectionHelper.getConnection()){
            PreparedStatement stmt = connection.prepareStatement(GET_BY_ID_STATEMENT);
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    LOGGER.debug("Employee retrieved successfully.");
                    return setEmployee(rs);
                }
            }
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("Employee not found.");
        return null;
    }

    /**
     * Add a new employee to the database.
     * This method inserts a new employee record into the database.
     * @param employee The {@code Employee} object containing the details to be added.
     * @return {@code true} if the employee was added successfully, {@code false} otherwise.
     */
    @Override
    public boolean addEmployee(Employee employee) {
        try (Connection connection = ConnectionHelper.getConnection()){
            PreparedStatement stmt = connection.prepareStatement(ADD_EMPLOYEE_STATEMENT);
            stmt.setString(1, employee.getLastName());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getMiddleName());
            stmt.setString(4, employee.getPositionInRC());
            stmt.setTimestamp(5, employee.getDateEmployed());
            stmt.setTimestamp(6, employee.getBirthdate());
            stmt.setString(7, employee.getBirthplace());
            stmt.setString(8, employee.getSex());
            stmt.setString(9, employee.getAddress());
            stmt.setString(10, employee.getContactNo());
            stmt.setString(11, employee.getCivilStatus());
            stmt.setString(12, employee.getCitizenship());
            stmt.setString(13, employee.getReligion());
            stmt.setString(14, employee.getEmail());
            stmt.setString(15, employee.getSssNo());
            stmt.setString(16, employee.getTinNo());
            stmt.setString(17, employee.getPagibigNo());
            stmt.setInt(18, employee.getStationId());
            stmt.setString(19, employee.getEmployeeNo());

            int result = stmt.executeUpdate();
            return result == 1;
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("Adding employee failed.");
        return false;
    }

    /**
     * Update an existing employee's details in the database.
     * This method updates the information of a specific employee record.
     * @param employee The {@code Employee} object containing updated details.
     * @return {@code true} if the employee was updated successfully, {@code false} otherwise.
     */
    @Override
    public boolean updateEmployee(Employee employee) {
        try (Connection connection = ConnectionHelper.getConnection()){
            PreparedStatement stmt = connection.prepareStatement(UPDATE_STATEMENT);
            stmt.setString(1, employee.getLastName());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getMiddleName());
            stmt.setString(4, employee.getPositionInRC());
            stmt.setTimestamp(5, employee.getDateEmployed());
            stmt.setTimestamp(6, employee.getBirthdate());
            stmt.setString(7, employee.getBirthplace());
            stmt.setString(8, employee.getSex());
            stmt.setString(9, employee.getAddress());
            stmt.setString(10, employee.getContactNo());
            stmt.setString(11, employee.getCivilStatus());
            stmt.setString(12, employee.getCitizenship());
            stmt.setString(13, employee.getReligion());
            stmt.setString(14, employee.getEmail());
            stmt.setString(15, employee.getSssNo());
            stmt.setString(16, employee.getTinNo());
            stmt.setString(17, employee.getPagibigNo());
            stmt.setInt(18, employee.getStationId());
            stmt.setString(19, employee.getEmployeeNo());

            int result = stmt.executeUpdate();
            return result == 1;
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("Updating employee failed.");
        return false;
    }

    /**
     * Map a {@code ResultSet} to an {@code Employee} object.
     * This method parses the current row of a {@code ResultSet} into an {@code Employee} object.
     * @param rs The {@code ResultSet} containing employee data.
     * @return An {@code Employee} object mapped from the {@code ResultSet}, or {@code null} if an error occurs.
     */
    private Employee setEmployee(ResultSet rs) {
        try {
            Employee employee = new Employee();
            employee.setLastName(rs.getString("last_name"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setMiddleName(rs.getString("middle_name"));
            employee.setPositionInRC(rs.getString("position_in_rc"));
            employee.setDateEmployed(rs.getTimestamp("date_employed"));
            employee.setBirthdate(rs.getTimestamp("birthdate"));
            employee.setBirthplace(rs.getString("birthplace"));
            employee.setSex(rs.getString("sex"));
            employee.setAddress(rs.getString("address"));
            employee.setContactNo(rs.getString("contact_number"));
            employee.setCivilStatus(rs.getString("civil_status"));
            employee.setCitizenship(rs.getString("citizenship"));
            employee.setReligion(rs.getString("religion"));
            employee.setEmail(rs.getString("email"));
            employee.setSssNo(rs.getString("sss_no"));
            employee.setTinNo(rs.getString("tin_no"));
            employee.setPagibigNo(rs.getString("pagibig_no"));
            employee.setEmployeeNo(rs.getString("employee_number"));
            employee.setStationId(rs.getInt("station_id"));
            employee.setId(rs.getInt("id"));


            return employee;
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("No employee was set.");
        return null;
    }
}
