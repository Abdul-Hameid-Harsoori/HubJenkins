package com.secant.dcm.dump.thread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.secant.dcm.dump.Service.DBService;
import com.secant.dcm.dump.Service.DcmExtractor;
import com.secant.dcm.dump.object.TapesDump;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Reusable thread class for the execution
 */
public class DcmDumpFailureFiles implements Runnable {

    /**
     * Log for logging purpose
     */
    private Log log = LogFactory.getLog(DcmDumpFailureFiles.class);
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

        TapesDump tapesDump;
        while (true) {
            tapesDump = dBService.getTapesDump();
            if (tapesDump == null) {
                log.info("All files are indexed....");
                break;
            }
            log.info("Processing File " + tapesDump.getCopiedPath());
            dcmExtractor.extractDicom(tapesDump, dBService);
            log.info("File Processed " + tapesDump.getCopiedPath());
        }
    }
}
