package com.secant.migration.threads;

import static com.secant.migration.Enums.Constants.*;
import com.secant.migration.Enums.MigrationStatus;
import com.secant.migration.beans.Routing;
import com.secant.migration.beans.RunMeOn;
import com.secant.migration.beans.Study;
import com.secant.migration.dao.MigrationDAO;
import com.secant.migration.dicom.DicomQuery;
import com.secant.migration.dao.StudyFetcher;
import com.secant.migration.utility.BaseUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.Tag;
import org.springframework.context.ApplicationContext;

/**
 * @author Seyfert
 * Controls the invocation of cleansing and migration services
 * Performs the Prefetch check against the Pacs archive besides the CFind against the Pacs post-migration to confirm if the study
 * has been migrated and received perfectly at the Pacs ends
 * Performs the check in Strict mode migration
 */
public class MigratorThread implements Runnable {

    private MigrationDAO mdaoImpl;
    private StudyFetcher studyFetcher;
    private Routing routing;
    private StudyMigrator sender;
    private ApplicationContext context;
    private DicomQuery dcmQry;
    private Log log = LogFactory.getLog(MigratorThread.class);
    //private int pacsImageCountOfTheStudy = 0;
    private long masterImageCountOfTheStudy = 0;
    private int threadNumber = 0;
    private String migrationLevel;
    private RunMeOn runMeOn;

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }
    

    public void setInstanceDetails(String migrationLevel) {
        this.migrationLevel = migrationLevel;
    }
    public void setRunMeOn(RunMeOn runMeOn){
        this.runMeOn = runMeOn;
    }

    public MigratorThread(final MigrationDAO mdaoImpl, final StudyFetcher studyFetcher, final StudyMigrator sender,
            final DicomQuery dicomQuery) {
        this.mdaoImpl = mdaoImpl;
        this.studyFetcher = studyFetcher;
        this.sender = sender;
        this.dcmQry = dicomQuery;
    }

    public void run() {

        log.info("Migration in Progress");
                
        log.info("The current run will be on : " + runMeOn.getRunMeOn() + " status value");

        //as long as there are studies to process ... keep moving 
        while ((routing = studyFetcher.nxtStdToMigrate(runMeOn)) != null) {
            
            // if the master_image_count for a study is null or 0(in the query , null translates to '0' ) , then skip the study
            if(routing.getMasterImageCount() == 0){
                log.info("Skipping the study with routing_id " + routing.getRoutingID()  +" and SIUID = " + routing.getStudyInstanceUid() + 
                        " as the master image count has neither been provided nor the check explicitly bypassed");
                routing.setSenderStatus(MigrationStatus.MISSING_MASTER_COUNT.toString());
                mdaoImpl.updateRouteStatus(routing);
                continue;
            }
            
             String studyAvailabilityStatus = MigrationStatus.NOT_FOUND_AT_DESTINATION.toString();
            try {
                String preFetchChoice = BaseUtility.prop.getProperty("migration.destination.checkstudypresent");
                if (preFetchChoice != null && !preFetchChoice.equalsIgnoreCase("NO")) {

                         studyAvailabilityStatus =  performCFind();
                    //if the study is already present on the Pacs with the full image count , then do not migrate it
                    if (studyAvailabilityStatus.equalsIgnoreCase(MigrationStatus.RECONCILE_SUCCESS.toString())) {

                        routing.setSenderStatus(MigrationStatus.PREFETCHED.toString());
                        routing.setNoOfImagesInStudy(masterImageCountOfTheStudy);
                        routing.setFailToSend(0);
                        routing.setFilesSent(0);
                        mdaoImpl.updateRouteStatus(routing);
                        log.info("Study with routing_id=" + routing.getRoutingID() + " and SIUID = " + routing.getStudyInstanceUid() + " is prefetched on destination PACS.");
                        continue;
                    }
                         studyAvailabilityStatus = MigrationStatus.NOT_FOUND_AT_DESTINATION.toString();
                } else {//get the pacs image count ; shall be used in Strict mode
                    //pacsImageCountOfTheStudy = mdaoImpl.getImageCountByStudyID(routing.getStudyInstanceUid());
                    masterImageCountOfTheStudy = mdaoImpl.getMasterImageCount(routing.getStudyFK());;
                }

                log.info("Status Updated to " + routing.getSenderStatus() + " for route id " + routing.getRoutingID()
                        + " and Study Dump FK " + routing.getStudyFK());

                Long studyID = routing.getStudyFK();
                Study study = getStudy(studyID);
                if (study != null) {

                    //default mode : strict mode
                    //"strict" migration mode : study will be skipped if the indexed img count is lesser than the img count in Pacs.
                    //"strict" mode will work only when the migration.level is "ALL_INSTANCES"
                    if ((BaseUtility.prop.getProperty("migration.mode") == null
                            || BaseUtility.prop.getProperty("migration.mode").equalsIgnoreCase("STRICT"))
                            && !(BaseUtility.prop.getProperty("migration.level").equalsIgnoreCase("FAILED_INSTANCES_ONLY"))) {

                        log.info("Master Image Count of the Study :: " + masterImageCountOfTheStudy);
                        int indexedImageCountOfTheStudy = mdaoImpl.getIndexedImageCount(study.getStudyPK());
                        log.info("Indexed Image Count of the Study :: " + indexedImageCountOfTheStudy);

                        if (indexedImageCountOfTheStudy < masterImageCountOfTheStudy) {
                            log.info("STRICT mode in place. Cannot migrate the study as it contains "
                                    + (masterImageCountOfTheStudy - indexedImageCountOfTheStudy) + " images lesser than in the Pacs!");
                            routing.setSenderStatus(MigrationStatus.SKIPPED_IN_STRICT_MODE.toString());
                            routing.setMasterImageCount(masterImageCountOfTheStudy);
                            routing.setNoOfImagesInStudy(indexedImageCountOfTheStudy);
                            routing.setSenderEndTime("now");
                            mdaoImpl.updateRouteStatus(routing);
                            continue;//lets skip this study and move to the next one
                        }

                    }


                    log.info("Migration Process Started for Study ID : " + study.getStudyPK());
                    sender = (StudyMigrator) context.getBean("studyMigrator");

                    //## FOR CLEANSING ##
                    if (BaseUtility.prop.getProperty("migration.cleansing.flag").equals("YES")) {
                        log.info("Cleansing is enabled");
                        sender.setIsCleansing(true);
                        StudyCleasing cleansing = (StudyCleasing) context.getBean("getCleasing");
                        sender.setCleasing(cleansing);
                    } else {
                        log.info("Cleansing is disabled");
                        sender.setIsCleansing(false);
                    }
                    //## CLEANSING END ##
                    sender.initialize(migrationLevel);
                    sender.sendStudy(routing, study);
                } else {// this never happens
                    routing.setSenderStatus(MigrationStatus.NO_STUDY.toString());
                    routing.setNoOfImagesInStudy(0);
                    routing.setFailToSend(0);
                    routing.setFilesSent(0);
                    mdaoImpl.updateRouteStatus(routing);
                    log.info("No Study Record found for Study Dump pk " + routing.getStudyFK());
                }
            } catch (Exception e) {
                log.error("Error while migrating study :: ", e);
                routing.setSenderStatus(MigrationStatus.FAILED_TO_SEND.toString());
                mdaoImpl.updateRouteStatus(routing);
            }

            if (Integer.parseInt(routing.getMigrationPriority()) > 0) {


                //Performing post-migration CFind
                studyAvailabilityStatus = performCFind();
                log.info("The study has been reconciled with status :: " + studyAvailabilityStatus);

                routing.setReconcileStatus(studyAvailabilityStatus + "_MIG-APP");
                mdaoImpl.updatePostMigrationReconcileStatus(routing);
                log.info("-------------------------------- Done with Post-Migration CFind --------------------------------");
            }
             
        }

        log.info("No more Studies to Migrate!");
    }

    public void startMigration() {
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public Study getStudy(Long study_pk) {
        return mdaoImpl.getStudyToMigrate(study_pk);
    }

    /**
     * Perform CFind against the Destination Pacs
     */
    public String performCFind(){
         String preFetchChoice = BaseUtility.prop.getProperty("migration.destination.checkstudypresent");
          String studyAvailabilityStatus = MigrationStatus.NOT_FOUND_AT_DESTINATION.toString();
         try{
               dcmQry.initialize();
                    if (!dcmQry.open()) {
                        log.info("Could not connect to " + BaseUtility.cfindAET.getAetURL()
                                + "  for cfind to check study is already there or not,"
                                + " will try to reconnect after 1 minute!");
                        Thread.sleep(60 * 1000);
                    }

                    dcmQry.setCfindKeys(BaseUtility.getCFindKeys());
                   
                    masterImageCountOfTheStudy = routing.getMasterImageCount();
                    
                    if (AccNum.equalsIgnoreCase(preFetchChoice)) {
                        String accnNum = routing.getAccessionNumber();
                        log.info("Checking status of the study on destination PACS ... Accession number= " + accnNum);
                        dcmQry.addKey(Tag.AccessionNumber, accnNum);
                        //pacsImageCountOfTheStudy = mdaoImpl.getImageCountByAccnNumber(accnNum);
                        studyAvailabilityStatus = dcmQry.query(accnNum, masterImageCountOfTheStudy, preFetchChoice);

                    } else if (SIUID.equalsIgnoreCase(preFetchChoice)) {
                        String siuid = routing.getStudyInstanceUid();
                        log.info("Checking status of the study on destination PACS ... SIUID = " + siuid);
                        dcmQry.addKey(Tag.StudyInstanceUID, siuid);
                        //pacsImageCountOfTheStudy = mdaoImpl.getImageCountByStudyID(siuid);
                        studyAvailabilityStatus = dcmQry.query(siuid, masterImageCountOfTheStudy , preFetchChoice);
                    }
        
        }
         catch(Exception e){
        log.info("Some problem encountered in performing CFind");
        e.printStackTrace();
    }
         return studyAvailabilityStatus;
    }
}
