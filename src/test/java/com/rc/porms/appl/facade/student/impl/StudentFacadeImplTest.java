package com.rc.porms.appl.facade.student.impl;

import com.rc.porms.appl.model.student.Student;
import com.rc.porms.data.student.dao.StudentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentFacadeImplTest {

    private StudentDao studentDaoMock;
    private StudentFacadeImpl studentFacade;

    @BeforeEach
    void setUp() {
        studentDaoMock = mock(StudentDao.class);
        studentFacade = new StudentFacadeImpl(studentDaoMock);
    }

    @Test
    void testGetAllStudents() {
        Student student1 = new Student();
        Student student2 = new Student();
        List<Student> mockStudents = new ArrayList<>();
        mockStudents.add(student1);
        mockStudents.add(student2);
        when(studentDaoMock.getAllStudents()).thenReturn(mockStudents);

        List<Student> students = studentFacade.getAllStudents();

        assertNotNull(students);
        assertEquals(2, students.size());
        verify(studentDaoMock, times(1)).getAllStudents();
    }

    @Test
    void testGetStudentByNumber() {
        String studentId = "CT21-0073";
        Student mockStudent = new Student();
        mockStudent.setStudentId(studentId);
        when(studentDaoMock.getStudentByNumber(studentId)).thenReturn(mockStudent);

        Student student = studentFacade.getStudentByNumber(studentId);

        assertNotNull(student);
        assertEquals(studentId, student.getStudentId());
        verify(studentDaoMock, times(1)).getStudentByNumber(studentId);

        when(studentDaoMock.getStudentByNumber("CT21-0073")).thenReturn(null);
        Student nonExistingStudent = studentFacade.getStudentByNumber("CT21-0073");
        assertNull(nonExistingStudent);
    }

    @Test
    void testGetStudentById() {
        int studentId = 1;
        Student mockStudent = new Student();
        mockStudent.setId(studentId);
        when(studentDaoMock.getStudentById(studentId)).thenReturn(mockStudent);

        Student student = studentFacade.getStudentById(studentId);

        assertNotNull(student);
        assertEquals(studentId, student.getId());
        verify(studentDaoMock, times(1)).getStudentById(studentId);

        when(studentDaoMock.getStudentById(1)).thenReturn(null);
        Student nonExistingStudent = studentFacade.getStudentById(1);
        assertNull(nonExistingStudent);
    }

    @Test
    void testGetStudentByStudentNumber() {
        String studentNumber = "CT21-0073";

        Student mockStudent = new Student();
        mockStudent.setStudentId(studentNumber);

        when(studentDaoMock.getStudentByStudentNumber(studentNumber)).thenReturn(mockStudent);

        Student student = studentDaoMock.getStudentByStudentNumber(studentNumber);

        assertNotNull(student, "The student should not be null.");
        assertEquals(studentNumber, student.getStudentId(), "The student number should match.");

        verify(studentDaoMock, times(1)).getStudentByStudentNumber(studentNumber);
    }

    @Test
    void testAddStudent() {
        Student newStudent = new Student();
        newStudent.setStudentId("CT21-0073");
        newStudent.setLastName("Amulong");
        newStudent.setFirstName("Kate");

        when(studentDaoMock.addStudent(newStudent)).thenReturn(true);

        boolean result = studentFacade.addStudent(newStudent);

        assertTrue(result);
        verify(studentDaoMock, times(1)).addStudent(newStudent);
    }

    @Test
    void testUpdateStudent() {
        Student existingStudent = new Student();
        existingStudent.setStudentId("CT21-0073");
        existingStudent.setLastName("Amulong");
        existingStudent.setFirstName("Kate Ann");

        Student updatedStudent = new Student();
        updatedStudent.setStudentId("CT21-0073");
        updatedStudent.setLastName("Amulong");
        updatedStudent.setFirstName("Kate Ann Updated");

        when(studentDaoMock.getStudentByNumber("CT21-0073")).thenReturn(existingStudent);
        when(studentDaoMock.updateStudent(updatedStudent)).thenReturn(true);

        boolean result = studentFacade.updateStudent(updatedStudent);

        assertTrue(result, "Employee should be updated successfully.");
        verify(studentDaoMock, times(1)).updateStudent(updatedStudent);
    }

    @Test
    void testFindStudentByEmail() {

        String email = "kate.ann@example.com";
        Student mockStudent = new Student();
        mockStudent.setEmail(email);
        when(studentDaoMock.findStudentByEmail(email)).thenReturn(mockStudent);

        Student student = studentFacade.findStudentByEmail(email);

        assertNotNull(student);
        assertEquals(email, student.getEmail());
        verify(studentDaoMock, times(1)).findStudentByEmail(email);
    }
}
