package com.rc.porms.appl.model.communityservice;

import com.rc.porms.appl.model.student.Student;

import java.sql.Timestamp;

/**
 * Represents a record of community service rendered by a student.
 */
public class CommunityService {
    private int id;
    private Student student;
    private int studentId;
    private String studentName;
    private Timestamp date_rendered;
    private int hours_completed;
    private String station_name;
    private String reason_of_cs;
    private String nature_of_work;

    /**
     * Constructs a new CommunityService object with default values.
     */
    public CommunityService() {

    }

    /**
     * Constructs a new CommunityService object.
     *
     * @param id             The unique identifier of the community service record.
     * @param student        The student who rendered the service.
     * @param studentId      The ID of the student who rendered the service.
     * @param date_rendered  The timestamp indicating the date and time of service.
     * @param hours_completed The number of hours of community service rendered.
     */
    public CommunityService(int id, Student student, int studentId, Timestamp date_rendered, int hours_completed, String station_name, String reason_of_cs, String nature_of_work) {
        this.id = id;
        this.student = student;
        this.studentId = studentId;
        this.date_rendered = date_rendered;
        this.hours_completed = hours_completed;
        this.station_name = station_name;
        this.reason_of_cs = reason_of_cs;
        this.nature_of_work = nature_of_work;
    }

    /**
     * Retrieves the unique identifier of the community service record.
     *
     * @return The unique identifier of the community service record.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the community service record.
     *
     * @param id The unique identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the student who rendered the community service.
     *
     * @return The student.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student who rendered the community service.
     *
     * @param student The student to set.
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Retrieves the ID of the student who rendered the community service.
     *
     * @return The ID of the student.
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets the ID of the student who rendered the community service.
     *
     * @param studentId The ID of the student to set.
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Retrieves the name of the student who rendered the community service.
     *
     * @return The name of the student.
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Sets the name of the student who rendered the community service.
     *
     * @param studentName The name of the student to set.
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Retrieves the timestamp of when the community service was rendered.
     *
     * @return The timestamp indicating the date and time.
     */
    public Timestamp getDate_rendered() {
        return date_rendered;
    }

    /**
     * Sets the timestamp indicating the date and time when the community service was rendered.
     *
     * @param date_rendered The timestamp to set.
     */
    public void setDate_rendered(Timestamp date_rendered) {
        this.date_rendered = date_rendered;
    }

    /**
     * Retrieves the number of hours of community service rendered.
     *
     * @return The number of hours rendered.
     */
    public int getHours_completed() {
        return hours_completed;
    }

    /**
     * Sets the number of hours of community service rendered.
     *
     * @param hours_completed The number of hours to set.
     */
    public void setHours_completed(int hours_completed) {
        this.hours_completed = hours_completed;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getReason_of_cs() {
        return reason_of_cs;
    }

    public void setReason_of_cs(String reason_of_cs) {
        this.reason_of_cs = reason_of_cs;
    }

    public String getNature_of_work() {
        return nature_of_work;
    }

    public void setNature_of_work(String nature_of_work) {
        this.nature_of_work = nature_of_work;
    }
}
