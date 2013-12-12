/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.secant.migration.utility;

import com.secant.migration.beans.AET;
import java.util.Properties;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;

/**
 * Utility class to open,read and fetch the settings from the migration.properties file
 * @author Seyfert
 */
public class BaseUtility {
    public static Properties prop;
    public static AET sourceAET;
    public static AET destAET;
    public static AET mboxAET;
    public static AET prefetchAET;
    public static AET cfindAET;
    private static boolean prefetchAvailable;
    /** below property is to enable only pre-fetch migration and disable regular migration. **/
    private static boolean regularMigrationDisable;

    public static final String[] IVRLE_TS = {
        UID.ImplicitVRLittleEndian };

    public static final int[] DCMMWL_RETURN_KEYS = {
        Tag.AccessionNumber,
        Tag.ReferringPhysicianName,
        Tag.PatientName,
        Tag.PatientID,
        Tag.PatientBirthDate,
        Tag.PatientSex,
        Tag.PatientWeight,
        Tag.MedicalAlerts,
        Tag.Allergies,
        Tag.PregnancyStatus,
        Tag.StudyInstanceUID,
        Tag.RequestingPhysician,
        Tag.RequestingService,
        Tag.RequestedProcedureDescription,
        Tag.AdmissionID,
        Tag.SpecialNeeds,
        Tag.CurrentPatientLocation,
        Tag.PatientState,
        Tag.RequestedProcedureID,
        Tag.RequestedProcedurePriority,
        Tag.PatientTransportArrangements,
        Tag.PlacerOrderNumberImagingServiceRequest,
        Tag.FillerOrderNumberImagingServiceRequest,
        Tag.ConfidentialityConstraintOnPatientDataDescription,
    };

    public static final int[] DCMMWL_SPS_RETURN_KEYS = {
        Tag.Modality,
        Tag.RequestedContrastAgent,
        Tag.ScheduledStationAETitle,
        Tag.ScheduledProcedureStepStartDate,
        Tag.ScheduledProcedureStepStartTime,
        Tag.ScheduledPerformingPhysicianName,
        Tag.ScheduledProcedureStepDescription,
        Tag.ScheduledProcedureStepID,
        Tag.ScheduledStationName,
        Tag.ScheduledProcedureStepLocation,
        Tag.PreMedication,
        Tag.ScheduledProcedureStepStatus
    };

    private static int qrlevel = 1;
    private static final String[] PATIENT_LEVEL_CUID = {
        UID.PatientRootQueryRetrieveInformationModelFIND,
        UID.PatientStudyOnlyQueryRetrieveInformationModelFINDRetired};
    private static final String[] STUDY_LEVEL_CUID = {
        UID.StudyRootQueryRetrieveInformationModelFIND,
        UID.PatientRootQueryRetrieveInformationModelFIND,
        UID.PatientStudyOnlyQueryRetrieveInformationModelFINDRetired};
    private static final String[] SERIES_LEVEL_CUID = {
        UID.StudyRootQueryRetrieveInformationModelFIND,
        UID.PatientRootQueryRetrieveInformationModelFIND,};

    private static final String[][] CUID = {
        PATIENT_LEVEL_CUID,
        STUDY_LEVEL_CUID,
        SERIES_LEVEL_CUID,
        SERIES_LEVEL_CUID};
    public static String[] getSTUDY_CUID() {
        return SERIES_LEVEL_CUID;
    }

    public static String[] getCUID() {
        return CUID[qrlevel];
    }

    public static final String[] LE_TS = {
        UID.ExplicitVRLittleEndian,
        UID.ImplicitVRLittleEndian };

    public static boolean isPrefetchAvailable() {
        return prefetchAvailable;
    }

    public static void setPrefetchAvailable(boolean prefetchAvailable) {
        BaseUtility.prefetchAvailable = prefetchAvailable;
    }

    public static int getThreadCount() {
        return Integer.parseInt(prop.getProperty("matrix.prefetch.dcmmwl.thread.corepoolsize"));
    }

    public static int getFetchSleepTime() {
        return (60000 * (Integer.parseInt((prop.getProperty("matrix.prefetch.dcmmwl.fetch.sleep.time") == null || prop.
                getProperty("matrix.prefetch.dcmmwl.fetch.sleep.time").equals("")) ? "1" : prop.getProperty(
                "matrix.prefetch.dcmmwl.fetch.sleep.time"))));
    }

    public static int getConnectionTimeOut() {
        return Integer.parseInt((prop.getProperty("matrix.prefetch.dcmmwl.connection.timeout") == null || prop.
                getProperty("matrix.prefetch.dcmmwl.connection.timeout").equals("")) ? "1000" : prop.getProperty(
                "matrix.prefetch.dcmmwl.connection.timeout"));
    }

    public static int getSenderPDULength() {
        return Integer.parseInt((prop.getProperty("matrix.dicom.migration.sendpdulengthinbytes")));
    }

    public static int getReceiverPDULength() {
        return Integer.parseInt((prop.getProperty("matrix.dicom.migration.rcvpdulengthinbytes")));
    }

    public static Integer[] getCFindKeys() {
        return new Integer[]{
                    Tag.PatientID,
                    Tag.PatientName,
                    Tag.PatientBirthDate,
                    Tag.PatientSex,
                    Tag.StudyInstanceUID,
                    Tag.AccessionNumber,
                    Tag.StudyDescription,
                    Tag.ModalitiesInStudy,
                    Tag.StudyDate,
                    Tag.StudyID,
                    Tag.StudyTime,
                    Tag.SeriesInstanceUID,
                    Tag.InstanceNumber,
                    Tag.NameOfPhysiciansReadingStudy,
                    Tag.NumberOfPatientRelatedInstances,
                    Tag.NumberOfPatientRelatedSeries,
                    Tag.NumberOfPatientRelatedStudies,
                    Tag.NumberOfSeriesRelatedInstances,
                    Tag.NumberOfStudyRelatedInstances,
                    Tag.NumberOfStudyRelatedSeries,
                    Tag.ReferringPhysicianName,
                    Tag.SeriesNumber,
                    Tag.SOPClassUID,
                    Tag.SOPInstanceUID
                };
    }

    public static boolean isRegularMigrationDisable() {
        return regularMigrationDisable;
    }

    public static void setRegularMigrationDisable(boolean regularMigrationDisable) {
        BaseUtility.regularMigrationDisable = regularMigrationDisable;
    }

    public static int getQueryLevel() {
        return Integer.parseInt((prop.getProperty("matrix.cfind.query.level") == null || prop.getProperty(
                "matrix.cfind.query.level").equals("")) ? "1" : prop.getProperty(
                "matrix.cfind.query.level"));
    }

    public static String getQueryLevelString() {
        return new String[]{"PATIENT", "STUDY", "SERIES", "IMAGE"}[Integer.parseInt((prop.getProperty(
                "matrix.cfind.query.level") == null || prop.getProperty(
                "matrix.cfind.query.level").equals("")) ? "1" : prop.getProperty(
                "matrix.cfind.query.level"))];
    }

    public static String getMigrationLevel(){
        return   prop.getProperty("migration.level");
    }

    public static String getMigrationMode(){
        return   prop.getProperty("migration.mode");
    }
    public static String getRunMeOnValue(){
        return prop.getProperty("migration.runmeon","PICKME");
    }
    public static String getTemporaryCleansingDirectory(){
        return prop.getProperty("matrix.cleanse.temp.dir");
    }
}
