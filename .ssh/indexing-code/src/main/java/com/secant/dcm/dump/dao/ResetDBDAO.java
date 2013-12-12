package com.secant.dcm.dump.dao;

import org.springframework.dao.DataAccessException;

/**
 * DAO for reseting the DB if program was stopped in between
 */
public interface ResetDBDAO {

    /**
     * Reset the database
     * @throws DataAccessException If there is an issue accessing DB.
     */
    void resetDB() throws DataAccessException;

    void resetForFailure() throws DataAccessException;
}
