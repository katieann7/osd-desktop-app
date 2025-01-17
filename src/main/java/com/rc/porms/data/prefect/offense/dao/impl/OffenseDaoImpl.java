package com.rc.porms.data.prefect.offense.dao.impl;

import com.rc.porms.appl.model.offense.Offense;
import com.rc.porms.data.connection.ConnectionHelper;
import com.rc.porms.data.prefect.offense.OffenseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.rc.porms.data.utils.prefect.QueryConstants.*;

/**
 * This is an implementation class of the OffenseDao
 */
public class OffenseDaoImpl implements OffenseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OffenseDaoImpl.class);

    /**
     * Retrieves an Offense from the database by its unique ID.
     *
     * @param id the unique identifier of the Offense to retrieve.
     * @return the Offense object if found, or null if no Offense with the given ID exists.
     */
    @Override
    public Offense getOffenseByID(int id) {

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_OFFENSE_BY_ID_STATEMENT)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("type");
                    String description = rs.getString("description");
                    Offense offense = new Offense(id, type, description);

                    return offense;
                } else {
                    LOGGER.warn("No Offense found with ID: " + id);
                }
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving Offense with ID " + id + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        LOGGER.debug("Offense not found.");
        return null;
    }

    /**
     * Retrieves an Offense from the database by its name or part of its name.
     *
     * @param offense the name or partial name of the Offense to search for.
     * @return the Offense object if found, or null if no matching Offense exists.
     */
    @Override
    public Offense getOffenseByName(String offense) {
        try (Connection connection = ConnectionHelper.getConnection()){

            PreparedStatement stmt = connection.prepareStatement(GET_OFFENSE_BY_NAME_STATEMENT);
            stmt.setString(1, "%" + offense + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String type = rs.getString("type");
                    String description = rs.getString("description");
                    Offense newOffense = new Offense(id, type, description);

                    return newOffense;
                } else {
                    LOGGER.warn("No Offense found: " + offense);
                }
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving Offense " + offense + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        LOGGER.debug("Offense not found.");
        return null;
    }

    /**
     * Adds a new Offense to the database.
     *
     * @param offense the Offense object to add.
     * @return true if the Offense was successfully added; false otherwise.
     */
    @Override
    public boolean addOffense(Offense offense) {
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_OFFENSE_STATEMENT)) {
            preparedStatement.setString(1, offense.getType());
            preparedStatement.setString(2, offense.getDescription());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException ex) {
            LOGGER.warn("Error adding offense: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing Offense in the database.
     *
     * @param offense the Offense object containing updated data.
     * @return true if the Offense was successfully updated; false otherwise.
     */
    @Override
    public boolean updateOffense(Offense offense) {
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_OFFENSE_STATEMENT)) {
            stmt.setString(1, offense.getType());
            stmt.setString(2, offense.getDescription());
            stmt.setInt(3, offense.getId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            LOGGER.warn("Error updating offense with ID " + offense.getId() + ": " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all Offenses from the database.
     *
     * @return a list of all Offense objects in the database, or an empty list if no offenses exist.
     */
    @Override
    public List<Offense> getAllOffense() {
        List<Offense> offenses = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_OFFENSE_STATEMENT))   {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Offense offense = new Offense();
                offense.setId(rs.getInt("id"));
                offense.setType(rs.getString("type"));
                offense.setDescription(rs.getString("description"));
                offenses.add(offense);
            }
            LOGGER.info("Offenses retrieved successfully.");
        } catch (Exception e) {
            LOGGER.warn("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("Offense database is empty.");
        return offenses;
    }

    /**
     * Retrieves all Offenses from the database that match a specific type.
     *
     * @param type the type of Offenses to search for.
     * @return a list of Offense objects matching the specified type, or an empty list if no matches are found.
     */
    @Override
    public List<Offense> getAllOffenseByType(String type) {
        List<Offense> offenses = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_OFFENSE_BY_TYPE_STATEMENT))   {
            preparedStatement.setString(1, type);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Offense offense = new Offense();
                    offense.setId(rs.getInt("id"));
                    offense.setType(rs.getString("type"));
                    offense.setDescription(rs.getString("description"));
                    offenses.add(offense);
                }
                LOGGER.info("Offenses retrieved successfully.");
            } catch (SQLException ex) {
                LOGGER.warn("Error retrieving all offense by type: " + ex.getMessage());
                ex.printStackTrace();
            }
            LOGGER.debug("Offense database is empty.");
        } catch (SQLException ex) {
            LOGGER.warn("Error preparing statement: " + ex.getMessage());
            ex.printStackTrace();
        }
        return offenses;
    }
}