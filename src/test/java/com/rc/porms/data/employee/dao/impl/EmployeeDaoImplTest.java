package com.rc.porms.data.employee.dao.impl;

import com.rc.porms.appl.model.employee.Employee;
import com.rc.porms.appl.model.offense.Offense;
import com.rc.porms.appl.model.violation.Violation;
import com.rc.porms.data.connection.ConnectionHelper;
import com.rc.porms.data.employee.dao.EmployeeDao;
import com.rc.porms.data.prefect.violation.ViolationDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeDaoImplTest {
    @Mock
    private EmployeeDao employeeDaoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        Employee employee1 = new Employee();
        employee1.setEmployeeNo("EMP21-0073");
        when(employeeDaoMock.getAllEmployees()).thenReturn(Arrays.asList(employee1));

        List<Employee> employees = employeeDaoMock.getAllEmployees();

        assertNotNull(employees, "The employees list should not be null.");
        assertEquals(1, employees.size(), "There should be 1 employee.");
        assertEquals("EMP21-0073", employees.get(0).getEmployeeNo(), "The first employee should have employee number EMP21-0073.");

        verify(employeeDaoMock, times(1)).getAllEmployees();
    }


    @Test
    void testGetEmployeeById() {
        Employee employee1 = new Employee();
        employee1.setId(1);

        when(employeeDaoMock.getEmployeeById("1")).thenReturn(employee1);

        Employee employee = employeeDaoMock.getEmployeeById("1");

        assertNotNull(employee, "The employee should not be null.");
        assertEquals(1, employee.getId(), "The employee ID should be 1.");

        verify(employeeDaoMock, times(1)).getEmployeeById("1");
    }

    @Test
    void testGetById() {
        Employee employee1 = new Employee();
        employee1.setId(1);

        when(employeeDaoMock.getById(1)).thenReturn(employee1);

        Employee employee = employeeDaoMock.getById(1);

        assertNotNull(employee, "The employee should not be null.");
        assertEquals(1, employee.getId(), "The employee ID should be 1.");

        verify(employeeDaoMock, times(1)).getById(1);
    }

    @Test
    void testAddEmployee() {
        Employee employee = new Employee();
        employee.setId(1);

        when(employeeDaoMock.addEmployee(employee)).thenReturn(true);

        boolean result = employeeDaoMock.addEmployee(employee);

        assertTrue(result, "The violation should be successfully added.");

        verify(employeeDaoMock, times(1)).addEmployee(employee);
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setId(1);

        when(employeeDaoMock.addEmployee(employee)).thenReturn(true);
        when(employeeDaoMock.updateEmployee(employee)).thenReturn(true);
        when(employeeDaoMock.getById(1)).thenReturn(employee);

        Employee expectedEmployee = employeeDaoMock.getById(1);
        assertEquals(expectedEmployee.getId(), employee.getId());
    }
}
