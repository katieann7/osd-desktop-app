package com.rc.porms.appl.model.student;

import java.sql.Timestamp;

/**
 * POJO class for Student objects. This class represents the student information.
 * */
public class Student {
    private int id;

    /** A unique student id of the student. */
    private String studentId;

    /** The last Name of the student. */
    private String lastName;
    /** The first Name of the student. */
    private String firstName;
    /** The middle Name of the student. */
    private String middleName;
    /** The sex of the student. */
    private String sex;
    /** It is the birthday of the student. */
    private Timestamp birthday;

    private String birthplace;

    /** The religion of the student. */
    private String religion;
    /** A unique student email of the student. */
    private String email;
    /** The complete address of the student. */
    private String address;
    /** A unique contact number of the student. */
    private String contactNumber;

    private String citizenship;
    private String civilStatus;
    private int sectionId;

    public int getUserId() {
        return userId;
    }

    public Student(int id, String studentId, String lastName, String firstName, String middleName, String sex, Timestamp birthday, String birthplace, String religion, String email, String address, String contactNumber, String citizenship, String civilStatus, int sectionId, int userId) {
        this.id = id;
        this.studentId = studentId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.sex = sex;
        this.birthday = birthday;
        this.birthplace = birthplace;
        this.religion = religion;
        this.email = email;
        this.address = address;
        this.contactNumber = contactNumber;
        this.citizenship = citizenship;
        this.civilStatus = civilStatus;
        this.sectionId = sectionId;
        this.userId = userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;


    /***
     * Default constructor of the Student class.
     */
    public Student() {

    }

    /**
     * This gets the Student id.
     * @return the student's id.
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * This sets the student id
     * @param studentId is the ID to be set.
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * This gets the Last Name.
     * @return the Last Name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This sets the Last Name.
     * @param lastName is the Last Name to be set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This gets the First Name.
     * @return The First Name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This sets for First Name.
     * @param firstName is the First Name to be set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This gets for Middle Name.
     * @return The Middle Name.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * This sets for Middle Name.
     * @param middleName is the Middle Name to be set.
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * This gets the Sex.
     * @return The Sex.
     */
    public String getSex() {
        return sex;
    }

    /**
     * This sets for Sex.
     * @param sex is the Sex to be set.
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * This gets the Birthday.
     * @return The Birthday.
     */
    public Timestamp getBirthday() {
        return birthday;
    }

    /**
     * This sets for Birthday.
     * @param birthday is the Birthday to be set.
     */
    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    /**
     * This gets for Religion.
     * @return The Religion.
     */
    public String getReligion() {
        return religion;
    }

    /**
     * This sets for Religion.
     * @param religion is the Religion to be set.
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * This gets for Email.
     * @return The Email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * This sets for Email.
     * @param email is the Email to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This gets for Address.
     * @return The Address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * This sets for Address.
     * @param address is the Address to be set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This gets for Contact Number.
     * @return The Contact Number.
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * This gets for Contact Number.
     * @param contactNumber is the Contact Number to be set.
     */
    /**
     * Set the contact number for the user.
     * Validation for contact number should be implemented externally.
     * @param contactNumber The contact number to be assigned to the user.
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * Get the ID associated with the user.
     * @return The ID of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID for the user.
     * This can be used to uniquely identify the user.
     * @param id The ID to be assigned to the user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the birthplace of the user.
     * @return The birthplace of the user.
     */
    public String getBirthplace() {
        return birthplace;
    }

    /**
     * Set the birthplace of the user.
     * This can be used to store the user's place of birth.
     * @param birthplace The birthplace to be assigned to the user.
     */
    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    /**
     * Get the citizenship of the user.
     * @return The citizenship of the user.
     */
    public String getCitizenship() {
        return citizenship;
    }

    /**
     * Set the citizenship of the user.
     * This can be used to specify the nationality of the user.
     * @param citizenship The citizenship to be assigned to the user.
     */
    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    /**
     * Get the civil status of the user.
     * @return The civil status of the user.
     */
    public String getCivilStatus() {
        return civilStatus;
    }

    /**
     * Set the civil status of the user.
     * This can be used to store the user's marital status.
     * @param civilStatus The civil status to be assigned to the user.
     */
    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    /**
     * Get the section ID associated with the user.
     * @return The section ID of the user.
     */
    public int getSectionId() {
        return sectionId;
    }

    /**
     * Set the section ID for the user.
     * This can be used to associate the user with a specific section.
     * @param sectionId The section ID to be assigned to the user.
     */
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}
