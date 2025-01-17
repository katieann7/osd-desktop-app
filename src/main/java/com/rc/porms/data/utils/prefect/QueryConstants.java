package com.rc.porms.data.utils.prefect;

public class QueryConstants {
    public static final String ADD_OFFENSE_STATEMENT = "INSERT INTO offense (type, description) VALUES (?, ?)";

    /**
     * SQL query to retrieve all offenses from the database.
     */
    public static final String GET_ALL_OFFENSE_STATEMENT = "SELECT * FROM offense";

    /**
     * SQL query to retrieve all offenses by type from the database.
     */
    public static final String GET_ALL_OFFENSE_BY_TYPE_STATEMENT = "SELECT * FROM offense WHERE type = ?";

    /**
     * SQL query to retrieve an offense by its offense id from the database.
     */
    public static final String GET_OFFENSE_BY_ID_STATEMENT = "SELECT * FROM offense WHERE id = ?";

    /**
     * SQL query to retrieve an offense by its description from the database.
     */
    public static final String GET_OFFENSE_BY_NAME_STATEMENT = "SELECT * FROM offense WHERE description LIKE ?";

    /**
     * SQL query to update an existing offense in the database.
     */
    public static final String UPDATE_OFFENSE_STATEMENT = "UPDATE offense SET type = ?, description = ? WHERE id = ?";

    //for violation query

    /**
     * SQL query to retrieve all violations from the database.
     */
    public static final String GET_ALL_VIOLATION_STATEMENT = "SELECT * FROM violation";

    /**
     * SQL query to retrieve all violations by student ID from the database.
     */
    public static final String GET_ALL_VIOLATION_BY_STUDENT_ID_STATEMENT = "SELECT * FROM violation WHERE student_id = ?";

    public static final String GET_ALL_VIOLATION_BY_CLUSTER_NAME_STATEMENT =
            "SELECT v.id AS violation_id, v.student_id, v.offense_id, v.warning_number, v.cs_hours, " +
                    "v.disciplinary_action, v.date_of_notice, v.approved_by_id, " +
                    "stud.id AS student_id, stud.first_name AS student_first_name, stud.last_name AS student_last_name, " +
                    "sec.cluster_name " +
                    "FROM violation v " +
                    "JOIN student stud ON v.student_id = stud.id " +
                    "JOIN section sec ON stud.section_section_id = sec.section_id " +
                    "WHERE sec.cluster_name LIKE ?";

    /**
     * SQL query to retrieve a violation by its ID from the database.
     */
    public static final String GET_VIOLATION_BY_ID_STATEMENT = "SELECT * FROM violation WHERE id = ?";

    /**
     * SQL query to update an existing violation in the database.
     */
    public static final String UPDATE_VIOLATION_STATEMENT = "UPDATE violation SET student_id = ?, offense_id = ?, warning_number = ?, cs_hours = ?, disciplinary_action = ?, date_of_notice = ?, approved_by_id = ? WHERE id = ?";

    /**
     * SQL query that adds a new violation to the database.
     */
    public static final String ADD_VIOLATION_STATEMENT = "INSERT INTO violation (student_id, offense_id, warning_number, cs_hours, disciplinary_action, date_of_notice, approved_by_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String FIND_VIOLATION_BY_STUDENT_NAME_STATEMENT = "SELECT violation.*, s.first_name AS student_first_name, s.last_name AS student_last_name " +
            "FROM VIOLATION violation " +
            "JOIN STUDENT s ON violation.STUDENT_ID = s.ID " +
            "WHERE s.LAST_NAME = ? AND s.FIRST_NAME = ?";

    public static final String GET_ALL_CS_STATEMENT =
            "SELECT " +
                    "csr.cs_slip_id, " +
                    "csr.reports_id, " +
                    "cs.reason_of_cs AS cs_reason_of_cs, " +
                    "state.station_name AS station_station_name, " +
                    "stud.id AS student_id, " +
                    "stud.first_name AS student_first_name, " +
                    "stud.last_name AS student_last_name, " +
                    "stud.middle_name AS student_middle_name, " +
                    "cr.hours_completed AS cr_hours_completed, " +
                    "cr.nature_of_work AS cr_nature_of_work, " +
                    "cr.date_of_cs AS cr_date_of_cs " +
                    "FROM cs_slip_reports csr " +
                    "JOIN cs_slip cs ON csr.cs_slip_id = cs.id " +
                    "JOIN cs_report cr ON csr.reports_id = cr.id " +
                    "JOIN student stud ON cs.student_id = stud.id " +
                    "JOIN station state ON cs.area_of_comm_serv_id = state.id";

    public static final String GET_ALL_CS_BY_CLUSTER_NAME_STATEMENT =
            "SELECT " +
                    "csr.cs_slip_id, " +
                    "csr.reports_id, " +
                    "cs.reason_of_cs AS cs_reason_of_cs, " +
                    "state.station_name AS station_station_name, " +
                    "stud.id AS student_id, " +
                    "stud.first_name AS student_first_name, " +
                    "stud.last_name AS student_last_name, " +
                    "stud.middle_name AS student_middle_name, " +
                    "cr.hours_completed AS cr_hours_completed, " +
                    "cr.nature_of_work AS cr_nature_of_work, " +
                    "cr.date_of_cs AS cr_date_of_cs " +
                    "FROM cs_slip_reports csr " +
                    "JOIN cs_slip cs ON csr.cs_slip_id = cs.id " +
                    "JOIN cs_report cr ON csr.reports_id = cr.id " +
                    "JOIN student stud ON cs.student_id = stud.id " +
                    "JOIN station state ON cs.area_of_comm_serv_id = state.id " +
                    "JOIN section sec ON stud.section_section_id = sec.section_id " +
                    "WHERE sec.cluster_name LIKE ?";

}