package com.rc.porms.appl.facade.prefect.offense.impl;

import com.rc.porms.appl.facade.prefect.offense.OffenseFacade;
import com.rc.porms.appl.model.offense.Offense;
import com.rc.porms.data.prefect.offense.OffenseDao;
import java.util.List;

/**
 * This is an implementation class of the OffenseFacade
 */
public class OffenseFacadeImpl implements OffenseFacade {
    private OffenseDao offenseDAO;

    /**
     * This is a constructor for new OffenseFacadeImpl object
     * This initializes the OffenseDao
     */
    public OffenseFacadeImpl(OffenseDao offenseDao) { this.offenseDAO = offenseDao;}

    /**
     * Adds a new Offense to the database.
     *
     * @param offense The `Offense` object containing the details to be added.
     * @return `true` if the Offense is successfully added; otherwise, throws a `RuntimeException`.
     * @throws RuntimeException If an error occurs while adding the Offense.
     * Delegates the operation to the `OffenseDAO` implementation.
     */
    @Override
    public boolean addOffense(Offense offense) throws RuntimeException{
        try {
            return offenseDAO.addOffense(offense);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add Offense: " + e.getMessage(), e);
        }
    }

    /**
     * Updates the details of an existing Offense in the database.
     *
     * @param offense The `Offense` object containing the updated details.
     * @return `true` if the Offense is successfully updated; otherwise, throws a `RuntimeException`.
     * @throws RuntimeException If the Offense to be updated is not found or an error occurs during the update process.
     * Checks if the Offense exists before attempting an update and delegates the update operation to the `OffenseDAO` implementation.
     */
    @Override
    public boolean updateOffense(Offense offense) throws RuntimeException{
        boolean result = false;
        try {
            Offense targetOffense = getOffenseByID(offense.getId());
            if (targetOffense == null) {
                throw new Exception("Offense to update not found. ");
            }
            result = offenseDAO.updateOffense(offense);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * Retrieves an Offense by its unique ID.
     *
     * @param id The ID of the Offense to retrieve.
     * @return The `Offense` object if found, or `null` if no matching record exists.
     * Delegates the operation to the `OffenseDAO` implementation.
     */
    @Override
    public Offense getOffenseByID(int id) {
        return offenseDAO.getOffenseByID(id);
    }

    /**
     * Retrieves an Offense by its description.
     *
     * @param description The description (or partial description) of the Offense to search for.
     * @return The `Offense` object if a match is found, or `null` if no matching record exists.
     * Delegates the operation to the `OffenseDAO` implementation.
     */
    @Override
    public Offense getOffenseByName(String description) {
        return offenseDAO.getOffenseByName(description);
    }

    /**
     * Retrieves all Offenses from the database.
     *
     * @return A `List` of `Offense` objects containing all records in the database.
     * Delegates the operation to the `OffenseDAO` implementation.
     */
    @Override
    public List<Offense> getAllOffense() {
        return offenseDAO.getAllOffense();
    }

    /**
     * Retrieves all Offenses that match a specific type.
     *
     * @param type The type of Offense to search for.
     * @return A `List` of `Offense` objects that match the given type.
     * @throws RuntimeException If an error occurs while retrieving the records.
     * Delegates the operation to the `OffenseDAO` implementation.
     */
    @Override
    public List<Offense> getAllOffenseByType(String type) {
        try {
            return offenseDAO.getAllOffenseByType(type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Offense by type: " + e.getMessage(), e);
        }
    }

}