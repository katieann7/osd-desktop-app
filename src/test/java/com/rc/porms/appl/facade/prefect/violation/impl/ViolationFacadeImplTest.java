package com.rc.porms.appl.facade.prefect.violation.impl;

import com.rc.porms.appl.model.communityservice.CommunityService;
import com.rc.porms.appl.model.student.Student;
import com.rc.porms.appl.model.violation.Violation;
import com.rc.porms.data.prefect.violation.ViolationDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViolationFacadeImplTest {

    private ViolationDao violationDaoMock;
    private ViolationFacadeImpl violationFacade;

    @BeforeEach
    void setUp() {
        violationDaoMock = mock(ViolationDao.class);
        violationFacade = new ViolationFacadeImpl(violationDaoMock);
    }

    @Test
    void testGetAllViolation() {
        Violation violation1 = new Violation();
        Violation violation2 = new Violation();
        List<Violation> mockViolations = new ArrayList<>();
        mockViolations.add(violation1);
        mockViolations.add(violation2);
        when(violationDaoMock.getAllViolation()).thenReturn(mockViolations);

        List<Violation> violations = violationFacade.getAllViolation();

        assertNotNull(violations);
        assertEquals(2, violations.size());
        verify(violationDaoMock, times(1)).getAllViolation();
    }

    @Test
    void testGetViolationByID() {
        Violation mockViolation = new Violation();
        mockViolation.setId(1);
        when(violationDaoMock.getViolationByID(1)).thenReturn(mockViolation);

        Violation violation = violationFacade.getViolationByID(1);

        assertNotNull(violation);
        assertEquals(1, violation.getId());
        verify(violationDaoMock, times(1)).getViolationByID(1);

        when(violationDaoMock.getViolationByID(1)).thenReturn(null);
        Violation invalidViolation = violationFacade.getViolationByID(1);
        assertNull(invalidViolation);
    }

    @Test
    void testGetAllViolationByStudent() {
        Student mockStudent = new Student();
        mockStudent.setId(1);

        Violation violation1 = new Violation();
        violation1.setStudent(mockStudent);
        Violation violation2 = new Violation();
        violation2.setStudent(mockStudent);
        List<Violation> mockViolations = new ArrayList<>();
        mockViolations.add(violation1);
        mockViolations.add(violation2);
        when(violationDaoMock.getAllViolationByStudent(mockStudent)).thenReturn(mockViolations);

        List<Violation> violations = violationFacade.getAllViolationByStudent(mockStudent);

        assertNotNull(violations);
        assertEquals(2, violations.size());
        verify(violationDaoMock, times(1)).getAllViolationByStudent(mockStudent);
    }

    @Test
    void testUpdateViolation() {
        Violation violationUpdate = new Violation();
        violationUpdate.setId(1);
        when(violationDaoMock.getViolationByID(1)).thenReturn(violationUpdate);
        when(violationDaoMock.updateViolation(violationUpdate)).thenReturn(true);

        boolean result = violationFacade.updateViolation(violationUpdate);

        assertTrue(result);
        verify(violationDaoMock, times(1)).updateViolation(violationUpdate);

        when(violationDaoMock.getViolationByID(2)).thenReturn(null);
    }

    @Test
    void testAddViolation() {
        Violation violation = new Violation();
        when(violationDaoMock.addViolation(violation)).thenReturn(true);

        boolean result = violationFacade.addViolation(violation);

        assertTrue(result);
        verify(violationDaoMock, times(1)).addViolation(violation);

        when(violationDaoMock.addViolation(violation)).thenThrow(new RuntimeException("Failed to add violation"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            violationFacade.addViolation(violation);
        });
        assertEquals("Failed to add Violation: Failed to add violation", exception.getMessage());
    }

    @Test
    void testGetViolationByStudentName() {
        String lastName = "Amulong";
        String firstName = "Kate Ann";
        Violation violation1 = new Violation();
        Violation violation2 = new Violation();
        List<Violation> mockViolations = new ArrayList<>();
        mockViolations.add(violation1);
        mockViolations.add(violation2);
        when(violationDaoMock.findByStudentName(lastName, firstName)).thenReturn(mockViolations);

        List<Violation> violations = violationFacade.getViolationByStudentName(lastName, firstName);

        assertNotNull(violations);
        assertEquals(2, violations.size());
        verify(violationDaoMock, times(1)).findByStudentName(lastName, firstName);
    }

    @Test
    void testGetAllViolationByClusterName() {
        Violation mockViolation = new Violation();
        mockViolation.setId(1);
        when(violationDaoMock.getViolationByID(1)).thenReturn(mockViolation);

        List<Violation> violations = Arrays.asList(mockViolation);
        when(violationDaoMock.getAllViolationByClusterName("CETE")).thenReturn(violations);

        List<Violation> result = violationDaoMock.getAllViolationByClusterName("CETE");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(violationDaoMock, times(1)).getAllViolationByClusterName("CETE");
    }
}
