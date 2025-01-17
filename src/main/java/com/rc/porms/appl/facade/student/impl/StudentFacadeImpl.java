package com.rc.porms.appl.facade.student.impl;

import com.rc.porms.appl.facade.student.StudentFacade;
import com.rc.porms.appl.model.student.Student;
import com.rc.porms.data.student.dao.StudentDao;

import java.util.List;

/**
 * An implementation class of the StudentFacade.
 */
public class StudentFacadeImpl implements StudentFacade {
    private StudentDao studentDao;

    /**
     * This creates a new StudentFacadeImpl
     * @param studentDao this helps for managing student data.
     */
    public StudentFacadeImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    /**
     * Retrieve all students from the data source.
     * This method delegates the retrieval of all student records to the {@code studentDao}.
     * @return A list of all {@code Student} objects in the data source, or an empty list if none exist.
     */
    @Override
    public List<Student> getAllStudents() { return studentDao.getAllStudents(); }

    /**
     * Retrieve a student by their unique student number.
     * This method fetches a student using their unique student number by delegating to the {@code studentDao}.
     * @param student_id The unique identifier of the student.
     * @return The {@code Student} object matching the provided student number, or {@code null} if not found.
     */
    @Override
    public Student getStudentByNumber(String student_id) {
        return studentDao.getStudentByNumber(student_id);
    }

    /**
     * Retrieve a student by their unique integer ID.
     * This method fetches a student using their unique database ID by delegating to the {@code studentDao}.
     * @param id The unique integer ID of the student.
     * @return The {@code Student} object matching the provided ID, or {@code null} if not found.
     */
    @Override
    public Student getStudentById(int id) {
        return studentDao.getStudentById(id);
    }

    /**
     * Add a new student to the data source.
     * This method checks if the student to be added already exists. If not, it delegates the addition to the {@code studentDao}.
     * @param student The {@code Student} object containing details of the new student to add.
     * @return {@code true} if the student was added successfully, {@code false} otherwise.
     * @throws RuntimeException if the student already exists or if an error occurs during the addition process.
     */
    @Override
    public boolean addStudent(Student student) {
        boolean result = false;
        try {
            Student targetStudent = getStudentByNumber(student.getStudentId());
            if(targetStudent != null) {
                throw new Exception("Student to add already exists. ");
            }
            result = studentDao.addStudent(student);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * Update an existing student's details in the data source.
     * This method checks if the student exists before updating their details. It delegates the update operation to the {@code studentDao}.
     * @param student The {@code Student} object containing updated details.
     * @return {@code true} if the student was updated successfully, {@code false} otherwise.
     * @throws RuntimeException if the student does not exist or if an error occurs during the update process.
     */
    @Override
    public boolean updateStudent(Student student) {
        boolean result = false;
        try {
            Student targetStudent = getStudentByNumber(student.getStudentId());
            if (targetStudent == null) {
                throw new Exception("Student to update not found. ");
            }
            result = studentDao.updateStudent(student);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * Find a student by their email address.
     * This method delegates the retrieval of a student by email to the {@code studentDao}.
     * @param email The email address of the student.
     * @return The {@code Student} object matching the provided email address, or {@code null} if not found.
     */
    @Override
    public Student findStudentByEmail(String email) {
        return  studentDao.findStudentByEmail(email);
    }

    /**
     * Retrieve a student by their unique student number.
     * This method fetches a student using their unique student number by delegating to the {@code studentDao}.
     * @param studentNumber The unique identifier of the student.
     * @return The {@code Student} object matching the provided student number, or {@code null} if not found.
     */
    @Override
    public Student getStudentByStudentNumber(String studentNumber) {
        return studentDao.getStudentByStudentNumber(studentNumber);
    }

}
