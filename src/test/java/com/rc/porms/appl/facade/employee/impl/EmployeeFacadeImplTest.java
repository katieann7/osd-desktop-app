package com.rc.porms.appl.facade.employee.impl;

import com.rc.porms.appl.model.employee.Employee;
import com.rc.porms.data.employee.dao.EmployeeDao;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeFacadeImplTest {

    private EmployeeDao employeeDao = mock(EmployeeDao.class);
    private EmployeeFacadeImpl employeeFacade = new EmployeeFacadeImpl(employeeDao);

    @Test
    void testGetAllEmployees() {
        Employee employee1 = new Employee();
        employee1.setEmployeeNo("EMP21-0143");
        employee1.setLastName("Magnaye");
        employee1.setFirstName("Justine");

        Employee employee2 = new Employee();
        employee2.setEmployeeNo("EMP21-0073");
        employee2.setLastName("Amulong");
        employee2.setFirstName("Kate Ann");

        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeDao.getAllEmployees()).thenReturn(employees);

        List<Employee> result = employeeFacade.getAllEmployees();

        assertNotNull(result, "Employee list should not be null.");
        assertEquals(2, result.size(), "There should be two employees in the list.");
        verify(employeeDao, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() {
        String employeeId = "EMP21-0143";
        Employee expectedEmployee = new Employee();
        expectedEmployee.setEmployeeNo(employeeId);
        expectedEmployee.setLastName("Amulong");
        expectedEmployee.setFirstName("Kate Ann");
        when(employeeDao.getEmployeeById(employeeId)).thenReturn(expectedEmployee);

        Employee result = employeeFacade.getEmployeeById(employeeId);

        assertNotNull(result, "Employee should not be null.");
        assertEquals(expectedEmployee, result, "Returned employee should match the expected employee.");
        verify(employeeDao, times(1)).getEmployeeById(employeeId);
    }

    @Test
    void testGetById() {
        int employeeId = 1;
        Employee expectedEmployee = new Employee();
        expectedEmployee.setEmployeeNo("EMP21-0143");
        expectedEmployee.setLastName("Magnaye");
        expectedEmployee.setFirstName("Justine");
        when(employeeDao.getById(employeeId)).thenReturn(expectedEmployee);

        Employee result = employeeFacade.getById(employeeId);

        assertNotNull(result, "Employee should not be null.");
        assertEquals(expectedEmployee, result, "Returned employee should match the expected employee.");
        verify(employeeDao, times(1)).getById(employeeId);
    }

    @Test
    void testAddEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setEmployeeNo("EMP21-0073");
        newEmployee.setLastName("Amulong");
        newEmployee.setFirstName("Kate Ann");

        when(employeeDao.addEmployee(newEmployee)).thenReturn(true);
        when(employeeDao.getEmployeeById(newEmployee.getEmployeeNo())).thenReturn(null);

        boolean result = employeeFacade.addEmployee(newEmployee);

        assertTrue(result, "Employee should be added successfully.");
        verify(employeeDao, times(1)).addEmployee(newEmployee);
    }

    @Test
    void testUpdateEmployee() {
        Employee existingEmployee = new Employee();
        existingEmployee.setEmployeeNo("EMP21-0143");
        existingEmployee.setLastName("Magnaye");
        existingEmployee.setFirstName("Justine");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setEmployeeNo("EMP21-0143");
        updatedEmployee.setLastName("Magnaye");
        updatedEmployee.setFirstName("Justine Updated");

        when(employeeDao.getEmployeeById(existingEmployee.getEmployeeNo())).thenReturn(existingEmployee);
        when(employeeDao.updateEmployee(updatedEmployee)).thenReturn(true);

        boolean result = employeeFacade.updateEmployee(updatedEmployee);

        assertTrue(result, "Employee should be updated successfully.");
        verify(employeeDao, times(1)).updateEmployee(updatedEmployee);
    }
}
