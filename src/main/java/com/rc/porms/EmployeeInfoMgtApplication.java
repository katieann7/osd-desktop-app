package com.rc.porms;

import com.rc.porms.appl.facade.employee.EmployeeFacade;
import com.rc.porms.appl.facade.employee.impl.EmployeeFacadeImpl;
import com.rc.porms.data.employee.dao.EmployeeDao;
import com.rc.porms.data.employee.dao.impl.EmployeeDaoImpl;

public class EmployeeInfoMgtApplication {
    private EmployeeFacade employeeFacade;
    /**
     * This creates a new EmployeeInfoMgtApplication
     * @return the employeeFacade this helps for managing employee data.
     */
    public EmployeeInfoMgtApplication() {
        EmployeeDao employeeDaoImpl = new EmployeeDaoImpl();
        this.employeeFacade = new EmployeeFacadeImpl(employeeDaoImpl);
    }
    /**
     * This gets the Employee Facade.
     * @return the employee facade.
     */
    public EmployeeFacade getEmployeeFacade() {
        return employeeFacade;
    }
}