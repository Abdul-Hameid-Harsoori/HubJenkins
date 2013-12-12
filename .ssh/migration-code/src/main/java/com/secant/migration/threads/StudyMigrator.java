/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.threads;

import com.secant.migration.Enums.MigrationStatus;
import static com.secant.migration.Enums.Constants.*;
import com.secant.migration.beans.*;
import com.secant.migration.dao.MigrationDAO;
import com.secant.migration.service.CleansingService;
import com.secant.migration.utility.BaseUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UIDDictionary;
import org.dcm4che2.io.*;
import org.dcm4che2.net.*;
import org.dcm4che2.net.service.StorageCommitmentService;
import org.dcm4che2.util.CloseUtils;
import org.dcm4che2.util.StringUtils;

/**
 *
 * @author Seyfert
 * This is where all the migration related functionality is covered.
 * Add the list of available physical images to the list of migratable image files
 * Handle the results of image cleansing service.
 * Image migration based upon the cleansing status and on-disk availability of the image
 * Listen to the responses from the Pacs archive , post CStore to verify image migration and update the database accordingly
 * Compute the migration history for the study.
 */
public class StudyMigrator extends StorageCommitmentService {

    private static Log log = LogFactory.getLog(StudyMigrator.class);
    private int noOfImagesInStudy = 0;
    private MigrationDAO mdao;
    private final int PEEK_LEN = 1024;
    private ArrayList<FileInfo> filesToSend;
    private Map<String, Set<String>> as2ts = new HashMap<String, Set<String>>();
    private NetworkApplicationEntity ae = new NetworkApplicationEntity();
    private int association_max_retry = 3;
    private int association_try_count = 0;
    private Executor executor = new NewThreadExecutor("MatrixMigrator");
    private NetworkApplicationEntity remoteAE = new NetworkApplicationEntity();
    private Association assoc;
    private NetworkConnection remoteConn = new NetworkConnection();
    private Device device = new Device("MatrixMigrator");
    private NetworkConnection conn = new NetworkConnection();
    private int transcoderBufferSize = 1024;
    private int timeout;
    private Routing route;
    private Study study;
    private int failToSend = 0;
    private int cleansingFailed = 0;
    private int skipped = 0;
    private int filesSent = 0;
    private int priority = 0;
    private StringBuffer privateTagData = null;
    private boolean headerModified = false;
    private long totalSize = 0L;
    private boolean dbFinalStatusUpdated = false;
    private Instance instance;
    boolean resetMSGID = true;
    private boolean sameStudy = false;
    private StudyCleasing cleansing;
    //private SwapBytes swapBytes;
    private boolean isCleansing = false;
    private boolean isSwapByteForUS = false;
    private CleansingService cleansingService = null;
    private final String[] sopUIDArray = {"1.2.840.10008.1.1", "1.2.840.10008.1.20.1", "1.2.840.10008.1.3.10", "1.2.840.10008.1.40", "1.2.840.10008.3.1.2.3.3", "1.2.840.10008.3.1.2.3.4", "1.2.840.10008.3.1.2.3.5", "1.2.840.10008.4.2", "1.2.840.10008.5.1.1.1", "1.2.840.10008.5.1.1.14", "1.2.840.10008.5.1.1.15", "1.2.840.10008.5.1.1.16", "1.2.840.10008.5.1.1.16.376", "1.2.840.10008.5.1.1.18", "1.2.840.10008.5.1.1.2", "1.2.840.10008.5.1.1.22", "1.2.840.10008.5.1.1.23", "1.2.840.10008.5.1.1.33", "1.2.840.10008.5.1.1.4", "1.2.840.10008.5.1.1.4.1", "1.2.840.10008.5.1.1.9", "1.2.840.10008.5.1.4.1.1.1", "1.2.840.10008.5.1.4.1.1.1.1", "1.2.840.10008.5.1.4.1.1.1.1.1", "1.2.840.10008.5.1.4.1.1.1.2", "1.2.840.10008.5.1.4.1.1.1.2.1", "1.2.840.10008.5.1.4.1.1.1.3", "1.2.840.10008.5.1.4.1.1.1.3.1", "1.2.840.10008.5.1.4.1.1.104.1", "1.2.840.10008.5.1.4.1.1.11.1", "1.2.840.10008.5.1.4.1.1.11.2", "1.2.840.10008.5.1.4.1.1.11.3", "1.2.840.10008.5.1.4.1.1.11.4", "1.2.840.10008.5.1.4.1.1.12.1", "1.2.840.10008.5.1.4.1.1.12.1.1", "1.2.840.10008.5.1.4.1.1.12.2", "1.2.840.10008.5.1.4.1.1.12.2.1", "1.2.840.10008.5.1.4.1.1.2", "1.2.840.10008.5.1.4.1.1.2.1", "1.2.840.10008.5.1.4.1.1.20", "1.2.840.10008.5.1.4.1.1.3.1", "1.2.840.10008.5.1.4.1.1.4", "1.2.840.10008.5.1.4.1.1.4.1", "1.2.840.10008.5.1.4.1.1.4.2", "1.2.840.10008.5.1.4.1.1.481.1", "1.2.840.10008.5.1.4.1.1.481.2", "1.2.840.10008.5.1.4.1.1.481.3", "1.2.840.10008.5.1.4.1.1.481.4", "1.2.840.10008.5.1.4.1.1.481.5", "1.2.840.10008.5.1.4.1.1.481.6", "1.2.840.10008.5.1.4.1.1.481.7", "1.2.840.10008.5.1.4.1.1.481.8", "1.2.840.10008.5.1.4.1.1.481.9", "1.2.840.10008.5.1.4.1.1.6.1", "1.2.840.10008.5.1.4.1.1.66", "1.2.840.10008.5.1.4.1.1.66.1", "1.2.840.10008.5.1.4.1.1.66.2", "1.2.840.10008.5.1.4.1.1.66.3", "1.2.840.10008.5.1.4.1.1.66.4", "1.2.840.10008.5.1.4.1.1.67", "1.2.840.10008.5.1.4.1.1.7", "1.2.840.10008.5.1.4.1.1.7.1", "1.2.840.10008.5.1.4.1.1.7.2", "1.2.840.10008.5.1.4.1.1.7.3", "1.2.840.10008.5.1.4.1.1.7.4", "1.2.840.10008.5.1.4.1.1.77.1.1", "1.2.840.10008.5.1.4.1.1.77.1.1.1", "1.2.840.10008.5.1.4.1.1.77.1.2", "1.2.840.10008.5.1.4.1.1.77.1.2.1", "1.2.840.10008.5.1.4.1.1.77.1.3", "1.2.840.10008.5.1.4.1.1.77.1.4", "1.2.840.10008.5.1.4.1.1.77.1.4.1", "1.2.840.10008.5.1.4.1.1.77.1.5.1", "1.2.840.10008.5.1.4.1.1.77.1.5.2", "1.2.840.10008.5.1.4.1.1.77.1.5.3", "1.2.840.10008.5.1.4.1.1.88.11", "1.2.840.10008.5.1.4.1.1.88.22", "1.2.840.10008.5.1.4.1.1.88.33", "1.2.840.10008.5.1.4.1.1.88.40", "1.2.840.10008.5.1.4.1.1.88.50", "1.2.840.10008.5.1.4.1.1.88.59", "1.2.840.10008.5.1.4.1.1.88.65", "1.2.840.10008.5.1.4.1.1.88.67", "1.2.840.10008.5.1.4.1.1.9.1.1", "1.2.840.10008.5.1.4.1.1.9.1.2", "1.2.840.10008.5.1.4.1.1.9.1.3", "1.2.840.10008.5.1.4.1.1.9.2.1", "1.2.840.10008.5.1.4.1.1.9.3.1", "1.2.840.10008.5.1.4.1.1.9.4.1", "1.2.840.10008.5.1.4.31", "1.2.840.10008.5.1.4.32", "1.2.840.10008.5.1.4.32.2", "1.2.840.10008.5.1.4.32.3", "1.2.840.10008.5.1.4.33", "1.2.840.10008.5.1.4.37.1", "1.2.840.10008.5.1.4.37.2", "1.2.840.10008.5.1.4.37.3", "1.2.840.10008.5.1.4.38.1"};
    private String migrationLevel;
    private int noOfImagesMissingInStudy; // no of images in the study that are not phyically absent
    private int prevTotalImgCnt = 0; // total migratable images in the last migration
    private int prevSentImgCnt = 0; // total images sent in the last migration
    private int prevFailedImgCnt = 0; // total images failed in the last migration

    public StudyMigrator(MigrationDAO mdao, Instance instance, CleansingService cleansingService) {
        this.mdao = mdao;
        this.instance = instance;
        this.cleansingService = cleansingService;
    }

    public void sendStudy(Routing route, Study study) {
        this.route = route;
        this.study = study;
        noOfImagesInStudy = 0;
        failToSend = 0;
        filesSent = 0;

        try {
            filesToSend = new ArrayList<FileInfo>();
            List<Instance> instanceList = null;
            //fetch the instances based upon the migration level
            if ("FAILED_INSTANCES_ONLY".equalsIgnoreCase(migrationLevel)) {
                log.info("Migrating only the failed instances of the study :: " + study.getStudyPK());
                instanceList = mdao.getFailedInstances(study.getStudyPK());

                log.info("Original values before this migration ::");


                prevTotalImgCnt = Integer.parseInt(route.getTotalImgToMig());
                prevSentImgCnt = Integer.parseInt(route.getSentImages());
                prevFailedImgCnt = Integer.parseInt(route.getFailedToSndImg());

                log.info("From the Previous Migration :: Total images = " + prevTotalImgCnt
                        + " , Sent images = " + prevSentImgCnt
                        + " , Failed images = " + prevFailedImgCnt);

            } else {
                instanceList = mdao.getInstances(study.getStudyPK());
            }
            if (instanceList.isEmpty()) {
                log.info("Study PK " + study.getStudyPK() + " has no Instance records. Cannot process Study");

                route.setSenderStatus(MigrationStatus.NO_IMAGES.toString());
                route.setNoOfImagesInStudy(noOfImagesInStudy);
                route.setFailToSend(failToSend);
                route.setFilesSent(filesSent);

                route.setSenderEndTime("now");
                mdao.updateRouteStatus(route);
                route.setSenderEndTime("done");
                return;

            } else {
                for (Instance eachInstance : instanceList) {
                    File newFile = new File(eachInstance.getImage_path());
                    //if a file exists , then it is added to the list of files-to-send.
                    if (newFile.exists()) {
                        addFile(newFile, eachInstance.getInstance_id());
                    } else {
                        // keep a check on the number of "physical" images missing for the study
                        noOfImagesMissingInStudy++;
                        log.info("Image " + eachInstance.getImage_path() + " is missing");
                        eachInstance.setSenderStatus(MigrationStatus.MISSING_IMAGE.toString());
                        mdao.updateInstanceStatus(eachInstance);

                    }
                }
                //Skip migration if there are 'physical' images missing in the study
                if (noOfImagesMissingInStudy > 0) {
                    log.info("The study misses some physical images.Will be skipped migration.");
                    //when all the images are missing , its because the mount is offline.
                    if (noOfImagesInStudy == noOfImagesMissingInStudy) {
                        route.setSenderStatus(MigrationStatus.NO_IMAGES.toString());
                    } else {
                        route.setSenderStatus(MigrationStatus.INCOMPLETE_STUDY.toString());
                    }
                    route.setNoOfImagesInStudy(noOfImagesInStudy);
                    route.setFailToSend(noOfImagesMissingInStudy);
                    route.setFilesSent(0);
                    String oldHistory = route.getHistory();
                    log.info("Old History :: " + oldHistory);
                    route.setHistory(computeNewHistory(oldHistory));
                    mdao.updateRouteStatus(route);
                    return;
                }

                if (filesToSend.isEmpty()) {
                    if (!("FAILED_INSTANCES_ONLY".equalsIgnoreCase(migrationLevel))) {
                        log.info("Study PK " + study.getStudyPK() + " has no Images. Cannot process Study");
                        route.setSenderStatus(MigrationStatus.NO_IMAGES.toString());
                        route.setNoOfImagesInStudy(noOfImagesInStudy);
                        route.setFailToSend(failToSend);
                        route.setFilesSent(filesSent);

                        route.setSenderEndTime("now");
                        String oldHistory = route.getHistory();
                        log.info("Old History :: " + oldHistory);
                        route.setHistory(computeNewHistory(oldHistory));
                        mdao.updateRouteStatus(route);
                        route.setSenderEndTime("done");
                        return;
                    }
                }
            }

            log.info("Total Images for Study PK " + study.getStudyPK() + " is " + noOfImagesInStudy);
            configureTransferCapability();
        } catch (Exception e) {
            log.info("Error while Configuring Transfer Capability of Study PK [" + study.getStudyPK() + "] " + e.getLocalizedMessage());
        }
        boolean failed_assoc = false;
        while (association_try_count < association_max_retry) {
            try {
                open();
                failed_assoc = false;
                break;
            } catch (Exception e) {
                log.error("ERROR: Failed to establish association with destination PACS, retrying :" + e.getMessage());
                failed_assoc = true;
                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException ex) {
                    log.error("Exception occured while opening association to the destination PACS "
                            + ex.getLocalizedMessage());
                }
            }
            association_try_count++;
        }
        if (failed_assoc) {
            log.info("Exhausted association connection to destination pacs retry count = "
                    + association_max_retry);
            log.info(
                    "Can not establish an association on destination PACS system, STOPPING MIGRATION FORCEFULLY.");
            System.exit(1); // abnormal exit from the system.
        } else {
            log.info(
                    "--------------------------------Connected to destination PACS----------------------------------------");
        }

        try {
            //failToSend = failedInCleansing.size();
            int remaingToSend = noOfImagesInStudy - failToSend;
            log.info("Sending Study PK <" + study.getStudyPK() + "> with [noOfImagesInStudy = "
                    + noOfImagesInStudy + ", Missing =" + noOfImagesMissingInStudy + "] to destination PACS...");


            send();
            close();
            log.info(
                    "--------------------------------Released connection to destination PACS--------------------------------");
        } finally {
            // stop();
        }

         /*Sleeping for 100 msec post migration of a prioritized study, so that
         the images profile completely and we can perform a cfind. */
        if (Integer.parseInt(route.getMigrationPriority()) > 0) {

            try {
                log.info("Migration done for the Prioritized study. Lets sleep for a 100 mili second!\n");
                Thread.sleep(100);
            } catch (Exception e) {
                log.error("Some problem in sleeping post migration");
                e.printStackTrace();
            }
        }
    }

    /**
     *Adds a DICOM file to the list of migratable images
     * @param file
     * @param instancetID
     */
    public void addFile(File file, final long instancetID) {
        if (file.isDirectory()) {
            File[] fs = file.listFiles();
            for (int i = 0; i < fs.length; i++) {
                addFile(fs[i], instancetID);
            }
            return;
        }

        FileInfo info = null;
        DicomObject dcmObj = null;
        DicomInputStream in = null;
        try {
            info = new FileInfo(file);
            in = new DicomInputStream(file);
            in.setHandler(new StopTagInputHandler(Tag.PixelData));
            //in.readDicomObject(dcmObj, PEEK_LEN);
            dcmObj = in.readDicomObject();
            info.setTSUID(in.getTransferSyntax().uid());
            info.setInstanceID(instancetID);
            info.setFmiEndPos(in.getEndOfFileMetaInfoPosition());
            info.setCleansingFilePath();
        } catch (Exception e) {
            log.info("WARNING: Failed to parse " + file + " - skipped " + 'F');
            e.printStackTrace();
            failToSend++;
            return;
        } finally {
            CloseUtils.safeClose(in);
        }

        info.setCUID(dcmObj.getString(Tag.SOPClassUID));
        if (info.getCUID() == null) {
            log.info("WARNING: Missing SOP Class UID in " + file + " - skipped.");
            failToSend++;
            return;
        }

        info.setIUID(dcmObj.getString(Tag.SOPInstanceUID));
        if (info.getIUID() == null) {
            log.info("WARNING: Missing SOP Instance UID in " + file + " - skipped.");
            failToSend++;
            return;
        }

        addTransferCapability(info.getCUID(), info.getTSUID());
        if (YES.equalsIgnoreCase(BaseUtility.prop.getProperty("migration.destination.check.allPresentationContext"))) {
            log.info("Checking allPresentationContex ");
            for (String sopuid : sopUIDArray) {
                log.debug("adding all SOPUIDs .. " + sopuid);
                addTransferCapability(sopuid, info.getTSUID());
            }
        }

        noOfImagesInStudy++;
        filesToSend.add(info);
    }

    // The core of the migration code
    public void send() {
        for (int i = 0, n = filesToSend.size(); i < n; i++) {
            FileInfo info = filesToSend.get(i);
            boolean cleansingStatus = true;

            //cleansing the file ; if cleansing fails then the file will be skipped migration
            if (isCleansing) {
                cleansing.setCleansingData(cleansingService.getCleansingData(route));
                cleansingStatus = cleansing.cleanStudyFile(info);
                if (!cleansingStatus) {
                    log.info("Skipping migration of the file with instance_id = " + info.getInstanceID()
                            + " and path = " + info.getF().getAbsolutePath() + " due to cleansing errors."
                            + "Please verify the conformance of the image to the DICOM standards");
                    Instance instanceFailedCleansing = new Instance();
                    instanceFailedCleansing.setInstance_id(info.getInstanceID());
                    instanceFailedCleansing.setSenderStatus(MigrationStatus.CLEANSING_FAILED.toString());
                    mdao.updateInstanceStatus(instanceFailedCleansing);
                    log.debug("cleansing complete for file " + info.getF().getName());
                    failToSend++;
                    cleansingFailed++;
                    continue;
                }

            }

            TransferCapability tc = assoc.getTransferCapabilityAsSCU(info.getCUID());
            log.info("transfer capability retreived");
            if (tc == null) {
                log.info(UIDDictionary.getDictionary().prompt(info.getCUID()) + " not supported by " + remoteAE.getAETitle());
                log.info("skip file " + info.getF());
                failToSend++;
                skipped++;
                //addSopSeriesEntries(info, 1);
                continue;
            }
            //String tsuid = selectTransferSyntax(tc.getTransferSyntax(), info.tsuid);
            String tsuid = info.getTSUID();
            if (tsuid == null) {
                log.info(UIDDictionary.getDictionary().prompt(info.getCUID()) + " with "
                        + UIDDictionary.getDictionary().prompt(info.getTSUID()) + " not supported by"
                        + remoteAE.getAETitle());
                log.info("skip file " + info.getF());
                skipped++;
                failToSend++;
                // addSopSeriesEntries(info, 1);
                continue;
            }

            log.info("handling the response from the Pacs and getting ready to migrate");

            DimseRSPHandler rspHandler = null;
            try {
                log.debug("before creating dimse response handler");
                rspHandler = new DimseRSPHandler() {

                    @Override
                    public void onDimseRSP(Association as, DicomObject cmd, DicomObject data) {
                        StudyMigrator.this.onDimseRSP(as, cmd, data);
                    }
                };
                log.info("before C-Store");
                assoc.cstore(info.getCUID(), info.getIUID(), priority, new DataWriter(info), info.getTSUID(), rspHandler);
                log.info("after C-Store");
            } catch (Exception e) {
                log.info("ERROR: Failed to send - " + info.getF() + " File : " + e.getMessage() + " : " + e);
                e.printStackTrace();
                failToSend++;
                // addSopSeriesEntries(info, 1);
            }
            try {
                assoc.waitForDimseRSP();
                boolean hasTheFileBeenDeleted = new File(info.getCleansingFilePath()).delete(); // Delete the temporary cleansed file.
                if(hasTheFileBeenDeleted){
                    log.info("Deleted the temporary file " + info.getCleansingFilePath());
                }else{
                    log.info("Unable to delete the file " + info.getCleansingFilePath());
                }
                
            } catch (InterruptedException e) {
                // should not happen
                log.info("Exception occured " + e.getLocalizedMessage());
            }
        }
//        while (assoc.isReadyForDataTransfer()) {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException ex) {
//            }
//        }
        try {
            log.info("FINISHED SENDING STUDY PK " + study.getStudyPK()
                    + " TO DESTINATION PACS with status : [ TotalToSend= "
                    + noOfImagesInStudy + ", Completed=" + filesSent + ", Failed =" + failToSend + ", Missing =" + noOfImagesMissingInStudy + "].");
            //updateSeriesSop(seriesSopMap, sinfo.getPacs_id());
            if (!dbFinalStatusUpdated) {
                if (!migrationLevel.equalsIgnoreCase("FAILED_INSTANCES_ONLY")) {
                    route.setSenderStatus(MigrationStatus.FAILED_TO_SEND.toString());
                    route.setSenderEndTime("now");
                    route.setNoOfImagesInStudy(noOfImagesInStudy);
                    route.setFailToSend(failToSend);
                    route.setFilesSent(filesSent);

                    String oldHistory = route.getHistory();
                    log.info("Old History :: " + oldHistory);
                    route.setHistory(computeNewHistory(oldHistory));
                    mdao.updateRouteStatus(route);
                } else {

                    route.setSenderStatus(MigrationStatus.FAILED_TO_SEND.toString());
                    log.info("Images to send :: " + prevTotalImgCnt);
                    log.info("Images sent :: " + (filesSent + prevSentImgCnt));
                    log.info("Images failed :: " + (prevFailedImgCnt - filesSent));
                    route.setSenderEndTime("now");
                    route.setNoOfImagesInStudy(prevTotalImgCnt);
                    route.setFailToSend(prevFailedImgCnt - filesSent);
                    route.setFilesSent(filesSent + prevSentImgCnt);

                    String oldHistory = route.getHistory();
                    log.info("Old History :: " + oldHistory);
                    route.setHistory(computeNewHistory(oldHistory));
                    mdao.updateRouteStatus(route);

                }
                route.setSenderEndTime("done");
            }



        } catch (Exception e) {
            log.info("Error :: " + e.getMessage());
        }




    }

    public void close() {
        try {
            assoc.release(false);
        } catch (InterruptedException e) {
            log.info("Exception occured in closing assosiation " + e.getLocalizedMessage());
        }
    }

    /**
     * If we get a successful response from the Pacs, post CStore , then it means the image has gone well. Otherwise...
     * @param as
     * @param cmd
     * @param data
     */
    private void onDimseRSP(Association as, DicomObject cmd, DicomObject data) {
        try {

            int status = cmd.getInt(Tag.Status);
            int msgId = cmd.getInt(Tag.MessageIDBeingRespondedTo); // a count of the number of responses given by the Pacs archive

            //when cleansing fails the image is held from migration or an image is skipped , so there is no response for such an image.
            log.info("MessageIDBeingRespondedTo : " + (msgId + cleansingFailed + skipped) + " Array size : " + filesToSend.size());
            FileInfo info = null;
            info = (FileInfo) filesToSend.get((msgId + cleansingFailed + skipped) - 1);
            log.debug("File Info object retained");

            switch (status) {
                case 0:
                    info.setTransferred(true);
                    totalSize += info.getLength();
                    ++filesSent;
                    // This is to check the duplicate study instance uid in a processing study.
                    //  String duplicateSOPIUid = "";
                    log.info("(Status = " + status + ") : File <" + info.getF().getName() + "> with "
                            + " SOPIUid < " + info.getIUID() + " > sent from directory [" + info.getF().getParent()
                            + "]");

                    log.info("Sending Study PK =" + study.getStudyPK() + " status is : [ TotalToSend= "
                            + noOfImagesInStudy
                            + ", Completed=" + filesSent + ", Failed =" + failToSend + ", Missing =" + noOfImagesMissingInStudy + "].");

                    instance.setInstance_id(info.getInstanceID());
                    instance.setImage_path(info.getF().getAbsolutePath());
                    instance.setSenderStatus(MigrationStatus.SUCCESS.toString());
                    mdao.updateInstanceStatus(instance);

                    if (!migrationLevel.equalsIgnoreCase("FAILED_INSTANCES_ONLY")) {
                        route.setNoOfImagesInStudy(noOfImagesInStudy);
                        route.setFailToSend(failToSend);
                        route.setFilesSent(filesSent);
                    } else {
                        log.info("Images to send :: " + prevTotalImgCnt);
                        log.info("Images sent :: " + (filesSent + prevSentImgCnt));
                        log.info("Images failed :: " + (prevFailedImgCnt - filesSent));
                        route.setSenderEndTime("now");
                        route.setNoOfImagesInStudy(prevTotalImgCnt);
                        route.setFailToSend(prevFailedImgCnt - filesSent);
                        route.setFilesSent(filesSent + prevSentImgCnt);
                    }
                    mdao.updateRouteStatus(route);

                    if (filesSent == noOfImagesInStudy) {

                        log.info("Final Status of Study PK =" + study.getStudyPK()
                                + " sent to destination PACS is : [ TotalToSend= " + noOfImagesInStudy
                                + ", Completed=" + filesSent + ", Failed =" + failToSend + ", Missing =" + noOfImagesMissingInStudy + "].");

                        if (migrationLevel.equalsIgnoreCase("FAILED_INSTANCES_ONLY")) {
                            log.info("Images to send :: " + prevTotalImgCnt);
                            log.info("Images sent :: " + (filesSent + prevSentImgCnt));
                            log.info("Images failed :: " + (prevFailedImgCnt - filesSent));
                            route.setSenderEndTime("now");
                            route.setNoOfImagesInStudy(prevTotalImgCnt);
                            route.setFailToSend(prevFailedImgCnt - filesSent);
                            route.setFilesSent(filesSent + prevSentImgCnt);
                        } else {
                            route.setNoOfImagesInStudy(noOfImagesInStudy);
                            route.setFailToSend(failToSend);
                            route.setFilesSent(filesSent);
                        }

                        if (noOfImagesMissingInStudy <= 0) {
                            route.setSenderStatus(MigrationStatus.SUCCESS.toString());
                        } else {
                            route.setSenderStatus(MigrationStatus.INCOMPLETE_STUDY.toString());
                        }
                        route.setSenderEndTime("now");
                        dbFinalStatusUpdated = true;
                        // maintaining the migration history for the study
                        String oldHistory = route.getHistory();
                        log.info("Old History :: " + oldHistory);

                        route.setHistory(computeNewHistory(oldHistory));
                        mdao.updateRouteStatus(route);

                    } else if ((filesSent + failToSend) == noOfImagesInStudy) {
                        log.info("Final Status of Study PK =" + study.getStudyPK()
                                + " sent to destination PACS is : [ TotalToSend= " + noOfImagesInStudy
                                + ", Completed=" + filesSent + ", Failed =" + failToSend + ", Missing =" + noOfImagesMissingInStudy + "].");

                        if (!migrationLevel.equalsIgnoreCase("FAILED_INSTANCES_ONLY")) {
                            route.setNoOfImagesInStudy(noOfImagesInStudy);
                            route.setFailToSend(failToSend);
                            route.setFilesSent(filesSent);
                            route.setSenderStatus(MigrationStatus.PARTLY_MOVED.toString());
                        } else {
                            log.info("Images to send :: " + prevTotalImgCnt);
                            log.info("Images sent :: " + (filesSent + prevSentImgCnt));
                            log.info("Images failed :: " + (prevFailedImgCnt - filesSent));
                            route.setNoOfImagesInStudy(prevTotalImgCnt);
                            route.setFailToSend(prevFailedImgCnt - filesSent);
                            route.setFilesSent(filesSent + prevSentImgCnt);
                            route.setSenderStatus(MigrationStatus.FAILED_TO_SEND.toString());

                        }

                        route.setSenderEndTime("now");
                        dbFinalStatusUpdated = true;
                        // maintaining the migration history for the study
                        String oldHistory = route.getHistory();
                        log.info("Old History :: " + oldHistory);
                        route.setHistory(computeNewHistory(oldHistory));
                        mdao.updateRouteStatus(route);
                    }

                    break;

                default:
                    promptErrRSP("ERROR: Received RSP with Status ", status, info, cmd);
                    ++failToSend;
                    instance.setInstance_id(info.getInstanceID());
                    instance.setImage_path(info.getF().getAbsolutePath());
                    instance.setSenderStatus(cmd.getString(0x00000902));
                    mdao.updateInstanceStatus(instance);

                    if ((filesSent + failToSend) == noOfImagesInStudy) {
                        log.info("Final Status of Study PK =" + study.getStudyPK()
                                + " sent to destination PACS is : [ TotalToSend= " + noOfImagesInStudy + ", Completed="
                                + filesSent + ", Failed =" + failToSend + ", Missing =" + noOfImagesMissingInStudy + "].");

                        if (failToSend == noOfImagesInStudy) {

                            route.setSenderStatus(MigrationStatus.FAILED_TO_SEND.toString());

                            if (!migrationLevel.equalsIgnoreCase("FAILED_INSTANCES_ONLY")) {
                                route.setNoOfImagesInStudy(noOfImagesInStudy);
                                route.setFailToSend(failToSend);
                                route.setFilesSent(filesSent);
                            } else {
                                log.info("Images to send :: " + prevTotalImgCnt);
                                log.info("Images sent :: " + (filesSent + prevSentImgCnt));
                                log.info("Images failed :: " + (prevFailedImgCnt - filesSent));
                                route.setNoOfImagesInStudy(prevTotalImgCnt);
                                route.setFailToSend(prevFailedImgCnt - filesSent);
                                route.setFilesSent(filesSent + prevSentImgCnt);

                            }

                            route.setSenderEndTime("now");

                            String oldHistory = route.getHistory();
                            log.info("Old History :: " + oldHistory);
                            route.setHistory(computeNewHistory(oldHistory));
                            mdao.updateRouteStatus(route);
                            route.setSenderEndTime("done");

                        } else {

                            if (!migrationLevel.equalsIgnoreCase("FAILED_INSTANCES_ONLY")) {
                                route.setNoOfImagesInStudy(noOfImagesInStudy);
                                route.setFailToSend(failToSend);
                                route.setFilesSent(filesSent);
                                route.setSenderStatus(MigrationStatus.PARTLY_MOVED.toString());
                            } else {
                                log.info("Images to send :: " + prevTotalImgCnt);
                                log.info("Images sent :: " + (filesSent + prevSentImgCnt));
                                log.info("Images failed :: " + (prevFailedImgCnt - filesSent));
                                route.setNoOfImagesInStudy(prevTotalImgCnt);
                                route.setFailToSend(prevFailedImgCnt - filesSent);
                                route.setFilesSent(filesSent + prevSentImgCnt);
                                route.setSenderStatus(MigrationStatus.FAILED_TO_SEND.toString());
                            }
                            route.setSenderEndTime("now");

                            String oldHistory = route.getHistory();
                            log.info("Old History :: " + oldHistory);
                            route.setHistory(computeNewHistory(oldHistory));

                            mdao.updateRouteStatus(route);
                            route.setSenderEndTime("done");
                        }

                        dbFinalStatusUpdated = true;
                    }
            }
        } catch (Exception e) {
            log.info("Error :: " + e.getMessage());
        }
    }

    /**
     * Computes the latest migration history for the study
     * @param oldHistory
     * @return newHistory
     * @author Abdul Hameid Harsoor
     */
    private String computeNewHistory(String oldHistory) {
        if (oldHistory == null) {
            oldHistory = "";
        }
        oldHistory += "###";

        String newHistory = oldHistory + "Migrated " + filesSent + " out of " + noOfImagesInStudy
                + " images and " + failToSend + " failed" + " and missing " + noOfImagesMissingInStudy + " image"
                + " @ " + (new Date()).toString();
        if ("FAILED_INSTANCES_ONLY".equalsIgnoreCase(migrationLevel)) {
            newHistory += "  on Failed-Instances-Only-Mode";
        }
        log.info("Final History :: " + newHistory);

        return newHistory;
    }

    private void promptErrRSP(String prefix, int status, FileInfo info, DicomObject cmd) {
        log.info(prefix + StringUtils.shortToHex(status) + "H for " + info.getF() + ", cuid=" + info.getCUID()
                + ", tsuid=" + info.getTSUID());
        log.info(cmd.toString());
    }

    private void addTransferCapability(String cuid, String tsuid) {
        HashSet ts = (HashSet) as2ts.get(cuid);
        if (ts == null) {
            ts = new HashSet();
            ts.add(tsuid);
            // ts.add(UID.ImplicitVRLittleEndian);
            as2ts.put(cuid, ts);
        } else {
            ts.add(tsuid);
        }
    }

    private String trimString(String s) {
        if (s == null) {
            return "";
        } else {
            return s.trim();
        }
    }

    public void open() throws IOException, ConfigurationException,
            InterruptedException {
        assoc = ae.connect(remoteAE, executor);
    }

    /**
     * initializes network connection.
     */
    public void initialize(String migLevel) {


        try {
            log.info("Initializing Network Entity...");
            timeout = Integer.parseInt(BaseUtility.prop.getProperty("matrix.cstore.waitDimseRsp.timeout"));
            remoteAE.setInstalled(true);
            remoteAE.setAssociationAcceptor(true);
            remoteAE.setNetworkConnection(new NetworkConnection[]{remoteConn});
            device.setNetworkApplicationEntity(ae);
            device.setNetworkConnection(conn);
            ae.setNetworkConnection(conn);
            ae.setAssociationInitiator(true);
            ae.setAssociationAcceptor(true);
            ae.register(this);
            ae.setDimseRspTimeout(timeout);
            ae.setIdleTimeout(timeout);
            ae.setRetrieveRspTimeout(timeout);
            migrationLevel = migLevel;
            conn.setReleaseTimeout(timeout);
            conn.setAcceptTimeout(timeout);
            conn.setConnectTimeout(timeout);

            log.info(" DimseRspTimeout/ReleaseTimeout/IdleTimeout/setRetrieveRspTimeout: " + timeout);

            //ae.setAETitle(ninfo.getLocalAETitle());
            ae.setAETitle(BaseUtility.sourceAET.getAet());
            //conn.setHostname(ninfo.getLocalHost());
            conn.setHostname(BaseUtility.sourceAET.getIp());

            //remoteAE.setAETitle(ninfo.getRemoteAETitle());
            remoteAE.setAETitle(BaseUtility.destAET.getAet());
            //remoteConn.setHostname(ninfo.getRemoteHost());
            remoteConn.setHostname(BaseUtility.destAET.getIp());
            //remoteConn.setPort(ninfo.getRemotePort());
            remoteConn.setPort(BaseUtility.destAET.getPort());

            int rcvpdu = Integer.parseInt(BaseUtility.prop.getProperty(
                    "matrix.dicom.migration.rcvpdulengthinbytes"));
            int sndpdu = Integer.parseInt(BaseUtility.prop.getProperty(
                    "matrix.dicom.migration.sendpdulengthinbytes"));
            ae.setMaxPDULengthSend(sndpdu);
            ae.setMaxPDULengthReceive(rcvpdu);
        } catch (Exception e) {
            log.error("Error in setting up network configuration for ending images : "
                    + e.getLocalizedMessage());
        }
    }

    private void configureTransferCapability() {
        TransferCapability[] tc = new TransferCapability[as2ts.size()];
        Iterator iter = as2ts.entrySet().iterator();
        for (int i = 0; i < tc.length; i++) {
            Map.Entry e = (Map.Entry) iter.next();
            String cuid = (String) e.getKey();
            HashSet ts = (HashSet) e.getValue();
            tc[i] = new TransferCapability(cuid,
                    (String[]) ts.toArray(new String[ts.size()]),
                    TransferCapability.SCU);
            log.debug("ConfigureTCapability : SOP" + tc[i]);
        }

        ae.setTransferCapability(tc);
    }

    private class DataWriter implements org.dcm4che2.net.DataWriter {

        private FileInfo info;

        public DataWriter(FileInfo info) {
            this.info = info;
        }

        //Write the data to the PDVOutputStream which will then be written to(C-Stored into) the Pacs archive
        public void writeTo(PDVOutputStream out, String tsuid) throws IOException {
            if (tsuid.equals(info.getTSUID())) {
                FileInputStream fis = new FileInputStream(info.getCleansingFilePath());
                //FileInputStream fis = new FileInputStream(info.getF());
                try {
                    long skip = info.getFmiEndPos();
                    while (skip > 0) {
                        skip -= fis.skip(skip);
                    }
                    out.copyFrom(fis);
                } catch (Exception excep) {
                    log.info("Error in writeTo function if (tsuid.equals(info.tsuid)) : " + excep.getMessage());
                } finally {
                    fis.close();
                }
            } else {
                log.debug("INSIDE writeTo::ELSE IF");
                DicomInputStream dis = new DicomInputStream(info.getF());
                try {
                    DicomOutputStream dos = new DicomOutputStream(out);
                    dos.setTransferSyntax(tsuid);
                    TranscoderInputHandler h = new TranscoderInputHandler(dos, transcoderBufferSize);
                    dis.setHandler(h);
                    dis.readDicomObject();
                } catch (Exception excep) {
                    log.error("Error in writeTo function else part : " + excep.getMessage());
                } finally {
                    dis.close();
                }
            }
        }
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public void setCleasing(StudyCleasing cleasing) {
        this.cleansing = cleasing;
    }

    public void setIsCleansing(boolean isCleansing) {
        this.isCleansing = isCleansing;
    }

    public boolean isIsSwapByteForUS() {
        return isSwapByteForUS;
    }

    public void setIsSwapByteForUS(boolean isSwapByteForUS) {
        this.isSwapByteForUS = isSwapByteForUS;
    }
}
