package com.rc.porms.data.student.dao;


import com.rc.porms.appl.model.student.Student;

import java.sql.ResultSet;
import java.util.List;

/**
 * Interface for Student Data Access Object.
 * */
public interface StudentDao {
    /**
     * Retrieves all Students from the database.
     *
     * @return list of all students.
     * */
    List<Student> getAllStudents();

    /**
     * Retrieves a Student from the database with specified id.
     *
     * @param studentId the id of the students.
     * @return the Student.
     * */
    Student getStudentByNumber(String studentId);

    Student getStudentById(int id);

    /**
     * Adds a Student to the database.
     *
     * @param student student to add.
     * @return true if adding is successful.
     * */
    boolean addStudent(Student student);

    /**
     * Adds Students to the database.
     *
     * @param rs student to add.
     * @return list of  students.
     * */
    List<Student> addStudents(ResultSet rs);

    /**
     * Updates a Student in the database.
     *
     * @param student  student to update.
     * @return true if update is successful.
     * */
    boolean updateStudent(Student student) ;

    /**
     * Finds an email in the database.
     *
     * @param email email to find.
     * @return true if finding email is successful.
     * */
    Student findStudentByEmail(String email);

    /**
     * Retrieve a student based on their student number.
     * This method is used to fetch the details of a specific student using their unique identifier.
     * @param studentNumber The unique student number to search for.
     * @return The {@code Student} object corresponding to the given student number, or {@code null} if not found.
     */
    Student getStudentByStudentNumber(String studentNumber);

}
