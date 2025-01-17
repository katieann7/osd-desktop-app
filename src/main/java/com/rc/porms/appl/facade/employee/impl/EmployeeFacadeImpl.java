/**
 * The com.system.demo.appl.facade.employee.impl package contains implementations
 * of facades for managing employee-related operations.
 */
package com.rc.porms.appl.facade.employee.impl;

import com.rc.porms.appl.facade.employee.EmployeeFacade;
import com.rc.porms.appl.model.employee.Employee;
import com.rc.porms.data.employee.dao.EmployeeDao;

import java.util.List;

/**
 * The EmployeeFacadeImpl class is an implementation of the EmployeeFacade interface.
 * It provides methods for managing employee data.
 */
public class EmployeeFacadeImpl implements EmployeeFacade {

    private EmployeeDao employeeDao;

    /**
     * Constructs a new EmployeeFacadeImpl with the provided EmployeeDao.
     *
     * @param employeeDao The data access object for managing employee data.
     */
    public EmployeeFacadeImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }


    /**
     * Retrieve all employees from the data source.
     * This method delegates the retrieval of all employee records to the {@code employeeDao}.
     * @return A list of all {@code Employee} objects in the data source, or an empty list if none exist.
     */
    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    /**
     * Retrieve an employee by their unique employee number.
     * This method fetches an employee using their unique employee number by delegating to the {@code employeeDao}.
     * @param employeeId The unique identifier of the employee.
     * @return The {@code Employee} object matching the provided employee number, or {@code null} if not found.
     */
    @Override
    public Employee getEmployeeById(String employeeId) {
        return employeeDao.getEmployeeById(employeeId);
    }

    /**
     * Retrieve an employee by their unique integer ID.
     * This method fetches an employee using their unique database ID by delegating to the {@code employeeDao}.
     * @param id The unique integer ID of the employee.
     * @return The {@code Employee} object matching the provided ID, or {@code null} if not found.
     */
    @Override
    public Employee getById(int id) {
        return employeeDao.getById(id);
    }

    /**
     * Add a new employee to the data source.
     * This method checks if the employee to be added already exists. If not, it delegates the addition to the {@code employeeDao}.
     * @param employee The {@code Employee} object containing details of the new employee to add.
     * @return {@code true} if the employee was added successfully, {@code false} otherwise.
     * @throws RuntimeException if the employee already exists or if an error occurs during the addition process.
     */
    @Override
    public boolean addEmployee(Employee employee) throws RuntimeException {
        boolean result = false;
        try {
            Employee targetEmployee = getEmployeeById(employee.getEmployeeNo());
            if (targetEmployee != null) {
                throw new Exception("Employee to add already exists.");
            }
            result = employeeDao.addEmployee(employee);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * Update an existing employee's details in the data source.
     * This method checks if the employee exists before updating their details. It delegates the update operation to the {@code employeeDao}.
     * @param employee The {@code Employee} object containing updated details.
     * @return {@code true} if the employee was updated successfully, {@code false} otherwise.
     * @throws RuntimeException if the employee does not exist or if an error occurs during the update process.
     */
    @Override
    public boolean updateEmployee(Employee employee) throws RuntimeException {
        try {
            Employee existingEmployee = getEmployeeById(employee.getEmployeeNo());
            if (existingEmployee == null) {
                throw new RuntimeException("Employee to update does not exist.");
            }
            return employeeDao.updateEmployee(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error updating employee", e);
        }
    }
}
