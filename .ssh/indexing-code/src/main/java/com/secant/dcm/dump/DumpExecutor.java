package com.secant.dcm.dump;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

/**
 * Class holding the threads
 */
public class DumpExecutor {

    /**
     * Log for logging purpose
     */
    private Log log = LogFactory.getLog(DumpExecutor.class);
    /**
     * Spring application context
     */
    private ApplicationContext ctx;

    /**
     * Starts the main process for dumping the DICOM header to database
     * @param numThreads Number of threads to be executed
     * @param appCtx Spring application context
     */
    public final void dumpFiles(final int numThreads, final ApplicationContext appCtx, String excteWithTapes) {
        this.ctx = appCtx;
        log.info("Executing threads " + numThreads);
        if (excteWithTapes.equalsIgnoreCase("YES")) {
            executeThreads(numThreads);
        } else {
            executeThreadsWithoutTapes(numThreads);
        }
    }

    /**
     * Executes the threads.
     * @param numThreads number of threads to be executed
     */
    private void executeThreadsWithoutTapes(final int numThreads) {
        for (int i = 0; i < numThreads; i++) {
            new Thread((Runnable) ctx.getBean("dcmDumpFailureFiles")).start();
        }
    }

    private void executeThreads(final int numThreads) {
        for (int i = 0; i < numThreads; i++) {
            new Thread((Runnable) ctx.getBean("dcmDumpThread")).start();
        }
    }
}
