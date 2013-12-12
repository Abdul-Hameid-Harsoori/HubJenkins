package com.secant.dcm.dump.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.secant.dcm.dump.dao.DcmDumpDAO;
import com.secant.dcm.dump.dao.NextTapeDAO;
import com.secant.dcm.dump.object.CorruptFile;
import com.secant.dcm.dump.object.ExceptionFile;
import com.secant.dcm.dump.object.Patient;
import com.secant.dcm.dump.object.TapesDump;
import com.secant.dcm.dump.object.UnqTapeName;


/**
 * Service class for database operations
 */
public class DBService {

    /**
     * Date format for database date fields
     */
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * DICOM dump DAOs
     */
    private DcmDumpDAO dcmDumpDAO;
    /**
     * Next tape DAO
     */
    private NextTapeDAO nextTapeDAO;

    /**
     * Sets the DAO object.
     * 
     * @param dcmDumpDAO Data access object
     */
    public final void setDcmDumpDAO(final DcmDumpDAO dcmDumpDAO) {
        this.dcmDumpDAO = dcmDumpDAO;
    }

    /**
     * Sets the DAO object.
     *
     * @param nextTapeDAO next tape data access object
     */
    public final void setNextTapeDAO(final NextTapeDAO nextTapeDAO) {
        this.nextTapeDAO = nextTapeDAO;
    }

    /**
     * Sets the tape status to INPROGRESS
     * @param tapeName Information with tape drive
     */
    public final void inprogressTapeStatus(final UnqTapeName tapeName) {
        tapeName.setStatus("INPROGRESS");
        tapeName.setStartTime(df.format(Calendar.getInstance().getTime()));
        nextTapeDAO.updateTapeStatus(tapeName);
    }

    /**
     * Sets the tape status to PROCESSED
     * @param tapeName Information with tape drive
     */
    public final void processTapeStatus(final UnqTapeName tapeName) {
        tapeName.setStatus("PROCESSED");
        tapeName.setEndTime(df.format(Calendar.getInstance().getTime()));
        nextTapeDAO.updateTapeStatus(tapeName);
    }

    /**
     * Gets the next tape information from DB.
     * @return UnqTapeName information of tape directory
     */
    public final UnqTapeName getNextTape() {
        final UnqTapeName tapeName = nextTapeDAO.getNextTape();
        if (tapeName != null) {
            inprogressTapeStatus(tapeName);
        }
        return tapeName;
    }
    public final TapesDump getTapesDump() {
        final TapesDump tapesDump = nextTapeDAO.getTapesDump();
        return tapesDump;
    }

    /**
     * Inserts the information of current file into DB.
     * @param tapesDump Tapes information object
     * @return checks <code> true </code> if file information already exists in database.
     *	<code>false</code> otherwise.
     */
    public final synchronized boolean insertTapeDump(final TapesDump tapesDump) {
        final boolean exists = dcmDumpDAO.insertTapeDump(tapesDump);
        if (!exists) {
            inprogressFileStatus(tapesDump);
        }
        return exists;
    }

    /**
     * Sets the status of current file to INPROGRESS.
     * @param tapesDump Tapes information object
     */
    public final void inprogressFileStatus(final TapesDump tapesDump) {
        if (tapesDump.getStatus() == null || "".equals(tapesDump.getStatus().trim())) {
            tapesDump.setStatus("INPROGRESS");
        }
        tapesDump.setStartTime(df.format(Calendar.getInstance().getTime()));
        dcmDumpDAO.updateFileStatus(tapesDump);
    }

    /**
     * Sets the status of current file to PROCESSED.
     * @param tapesDump Tapes information object
     */
    public final void processFileStatus(final TapesDump tapesDump) {
        tapesDump.setStatus("PROCESSED");
        tapesDump.setEndTime(df.format(Calendar.getInstance().getTime()));
        dcmDumpDAO.updateFileStatus(tapesDump);
    }

    /**
     * Sets the status of current file to FAILURE
     * @param tapesDump Tapes information object
     */
    public final void failureFileStatus(final TapesDump tapesDump) {
        tapesDump.setStatus("FAILED");
        tapesDump.setEndTime(df.format(Calendar.getInstance().getTime()));
        dcmDumpDAO.updateFileStatus(tapesDump);
    }

    /**
     * Inserts the exception information when patient id or study instance uid is null.
     * @param exceptionFile Object with DICOM header information.
     */
    public final void insertExcpetion(final ExceptionFile exceptionFile) {
        dcmDumpDAO.insertExcpetion(exceptionFile);
    }

    /**
     * Inserts information for corrupted files found.
     * @param corruptFile Corrupt file information
     */
    public final void insertCurFile(final CorruptFile corruptFile) {
        dcmDumpDAO.insertCurropted(corruptFile);
    }

    /**
     * Inserts the DICOM header information in database
     * @param patient Patient object holding required information
     */
    public final void insertDcm(final Patient patient) {
        dcmDumpDAO.insertDcm(patient);
    }
}

