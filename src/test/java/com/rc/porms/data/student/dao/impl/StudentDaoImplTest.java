package com.rc.porms.data.student.dao.impl;
import com.rc.porms.appl.model.student.Student;
import com.rc.porms.data.student.dao.StudentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This is to test the student dao impl.
 */
class StudentDaoImplTest {
    @Mock
    private StudentDao studentDao;
    private List<Student> students;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        students = new ArrayList<>();
    }
    /**
     * This is to test get all students.
     */
    @Test
    public void testGetAllStudents() {
        StudentDao studentDao = mock(StudentDao.class);

        Student student = new Student();
        List<Student> students = new ArrayList<>();
        students.add(student);

        when(studentDao.getAllStudents()).thenReturn(students);
        List<Student> studentList = studentDao.getAllStudents();
        assertEquals(studentList.size(), 1);
    }
    /**
     * This is to test get student by id.
     */
    @Test
    public void testGetStudentById() {
        StudentDao studentDao = mock(StudentDao.class);

        Student student1 = new Student();
        student1.setId(1);

        when(studentDao.getStudentById(1)).thenReturn(student1);

        Student result = studentDao.getStudentById(1);

        assertNotNull(result, "The student should not be null.");
        assertEquals(1, result.getId(), "The student ID should be 1.");

        verify(studentDao, times(1)).getStudentById(1);
    }


    @Test
    public void testGetStudentByNumber() {
        StudentDao studentDao = mock(StudentDao.class);

        Student student1 = new Student();
        student1.setStudentId("CT21-0073");

        when(studentDao.getStudentByNumber("CT21-0073")).thenReturn(student1);

        Student result = studentDao.getStudentByNumber("CT21-0073");

        assertNotNull(result);
        assertEquals("CT21-0073", result.getStudentId());
    }

    @Test
    public void testGetStudentByStudentNumber() {
        StudentDao studentDao = mock(StudentDao.class);

        Student student1 = new Student();
        student1.setStudentId("CT21-0073");

        when(studentDao.getStudentByNumber("CT21-0073")).thenReturn(student1);

        Student result = studentDao.getStudentByNumber("CT21-0073");

        assertNotNull(result);
        assertEquals("CT21-0073", result.getStudentId());
    }

    /**
     * This is to test add student.
     */
    @Test
    public void testAddStudent() {
        Student student = new Student();

        when(studentDao.addStudent(student)).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                    students.add(student);
                    return true;
                }
                return false;
            }
        });

        studentDao.addStudent(student);

        when(studentDao.getAllStudents()).thenReturn(students);
        List<Student> studentList = studentDao.getAllStudents();

        assertEquals(studentList.size(), 1);
    }

    /**
     * This is to test Update Student.
     */
    @Test
    void testUpdateStudent() {
        StudentDao studentDao = mock(StudentDao.class);

        Student testStudent = new Student();
        testStudent.setStudentId("CT21-0073");
        testStudent.setLastName("Amulong");

        when(studentDao.updateStudent(any(Student.class))).thenReturn(true);

        boolean result = studentDao.updateStudent(testStudent);

        assertTrue(result, "Update should be successful");

        verify(studentDao, times(1)).updateStudent(testStudent);

        when(studentDao.getStudentById(1)).thenReturn(testStudent);

        Student updatedStudent = studentDao.getStudentById(1);

        assertNotNull(updatedStudent, "Updated student should not be null");
        assertEquals(testStudent.getLastName(), updatedStudent.getLastName(), "Student's last name should be updated");
    }
    @Test
    public void testFindStudentByEmail() {
        StudentDao studentDao = mock(StudentDao.class);

        Student student1 = new Student();
        student1.setEmail("kateannamulong@gmail.com");

        when(studentDao.findStudentByEmail("kateannamulong@gmail.com")).thenReturn(student1);

        Student result = studentDao.findStudentByEmail("kateannamulong@gmail.com");

        assertNotNull(result);
        assertEquals("kateannamulong@gmail.com", result.getEmail());
    }

}