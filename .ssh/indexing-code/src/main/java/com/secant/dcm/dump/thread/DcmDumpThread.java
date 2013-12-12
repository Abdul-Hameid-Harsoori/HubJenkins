package com.secant.dcm.dump.thread;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.secant.dcm.dump.Service.DBService;
import com.secant.dcm.dump.Service.DcmExtractor;
import com.secant.dcm.dump.object.TapesDump;
import com.secant.dcm.dump.object.UnqTapeName;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Reusable thread class for the execution 
 */
public class DcmDumpThread implements Runnable {

    /**
     * Log for logging purpose
     */
    private Log log = LogFactory.getLog(DcmDumpThread.class);
    /**
     * Data format for DB fields
     */
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * DB service
     */
    private DBService dBService;
    /**
     * DCM Extractor
     */
    private DcmExtractor dcmExtractor;

    /**
     * Sets the DB service object.
     * @param dBService DB service object
     */
    public final void setdBService(final DBService dBService) {
        this.dBService = dBService;
    }

    /**
     * Sets the DCMExtractor. Used by spring context.
     * @param dcmExtractor DCM Extractor object
     */
    public final void setDcmExtractor(final DcmExtractor dcmExtractor) {
        this.dcmExtractor = dcmExtractor;
    }

    /**
     * Run method for the thread
     */
    public final void run() {
        UnqTapeName tapeName;
        List<String> files;
        TapesDump tapesDump;
        while (true) {
            tapeName = dBService.getNextTape();
            if (tapeName == null) {
                log.info("All tapes are read");
                try {
                    Thread.sleep(600000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DcmDumpThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                continue;
            }
            log.info("Processing tape " + tapeName.getTapeName());
            files = getAllFiles(tapeName);
            for (String fileName : files) {
                tapesDump = new TapesDump(tapeName.getTapeName(), fileName, fileName, "INPROGRESS", df.format(Calendar.getInstance().getTime()), null);
                long starttime=System.currentTimeMillis();
                if (dBService.insertTapeDump(tapesDump)) {
                    log.debug("End time for insertion into tapes_dump"+(System.currentTimeMillis()-starttime));
                    continue;
                }
                log.info("Processing File " + tapesDump.getCopiedPath());
                dcmExtractor.extractDicom(tapesDump, dBService);
                log.info("File Processed " + tapesDump.getCopiedPath());
            }
            dBService.processTapeStatus(tapeName);
        }
    }

    /**
     * Gets the all file in a list.
     * 
     * @param tapeName Directory name.
     * @return List of files in all directories
     */
    private List<String> getAllFiles(final UnqTapeName tapeName) {
        final List<String> files = new ArrayList<String>();
        final File tn = new File(tapeName.getTapeName());
        if (tn.isDirectory()) {
                log.info("Inside main directory listing");
                setInternalFiles(tn.list(), files, tapeName.getTapeName());
        }
        return files;
    }

    /**
     * Sets the files inside the directories in a list. Its a recursive method.
     *
     * @param filesList List of the files.
     * @param files list where file names to be added
     * @param dirName Which directory to look in
     */
    private void setInternalFiles(final String[] filesList, final List<String> files, final String dirName) {
        final int totalFile = filesList.length;
        File file;
        for (int i = 0; i < totalFile; i++) {
            file = new File(dirName + "/" + filesList[i]);
            if (file.isDirectory()) {
                    setInternalFiles(file.list(), files, file.getAbsolutePath());
                } else {
                    files.add(file.getAbsolutePath());
                }
            }
        }
    }
