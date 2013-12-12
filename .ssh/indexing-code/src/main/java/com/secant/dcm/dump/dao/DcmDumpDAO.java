package com.secant.dcm.dump.dao;

import com.secant.dcm.dump.object.CorruptFile;
import com.secant.dcm.dump.object.ExceptionFile;
import com.secant.dcm.dump.object.Patient;
import com.secant.dcm.dump.object.TapesDump;
import org.springframework.dao.DataAccessException;

/**
 * Data access object.
 */
public interface DcmDumpDAO {
    /**
     * Inserts file information.
     * @param tapesDump Tape object with information
     * @return <code> ture </code> if next tape is available. <code>false</code> otherwise.
     * @throws DataAccessException If there is an issue accessing DB.
     */
    boolean insertTapeDump(TapesDump tapesDump) throws DataAccessException;

    /**
     * Updates the file status  with given status.
     * @param tapesDump Tapes object with updated status.
     * @throws DataAccessException If there is an issue accessing DB.
     */
    void updateFileStatus(TapesDump tapesDump) throws DataAccessException;

    /**
     * Inserts the exception in reading file to database.
     * @param exceptionFile Exception object for file
     * @throws DataAccessException If there is an issue accessing DB.
     */
    void insertExcpetion(ExceptionFile exceptionFile) throws DataAccessException;

    /**
     * Inserts the corrupted file information in DB.
     * @param corruptFile Corrupted file info object.
     * @throws DataAccessException If there is an issue accessing DB.
     */
    void insertCurropted(CorruptFile corruptFile) throws DataAccessException;

    /**
     * Inserts DICOM header information.
     * @param patient Patient information object
     * @throws DataAccessException If there is an issue accessing DB.
     */
    void insertDcm(Patient patient) throws DataAccessException;
}
