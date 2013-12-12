package com.secant.dcm.dump.object;

import java.io.Serializable;
import java.util.List;

/**
 * This class corresponds to the database table dump_study
 */
public class Study implements Serializable {

    /**
     * This field corresponds to the database table dump_study
     */
    private static final long serialVersionUID = 1L;
    /**
     * This field corresponds to the database column dump_study.study_pk
     */
    private Long studyPk;
    /**
     * This field corresponds to the database column dump_study.patient_fk
     */
    private Long patientFk;
    /**
     * This field corresponds to the database column dump_study.study_instance_uid
     */
    private String studyInstanceUid;
    /**
     * This field corresponds to the database column dump_study.study_date
     */
    private String studyDate;
    /**
     * This field corresponds to the database column dump_study.study_time
     */
    private String studyTime;
    /**
     * This field corresponds to the database column dump_study.accession_number
     */
    private String accessionNumber;
    /**
     * This field corresponds to the database column dump_study.institution_name
     */
    private String institutionName;
    /**
     * This field corresponds to the database column dump_study.referring_physician_name
     */
    private String referringPhysicianName;
    /**
     * This field corresponds to the database column dump_study.req_physician_name
     */
    private String reqPhysicianName;
    /**
     * This field corresponds to the database column dump_study.study_description
     */
    private String studyDescription;
    /**
     * This field corresponds to the database column dump_study.study_id
     */
    private String studyId;
    /**
     * This field corresponds to the database column dump_study.study_directory
     */
    private String studyDirectory;
    /**
     * Series pertaining to this study
     */
    private List<Series> serieses;

    /**
     * This field corresponds to the database column dump_study.department
     */
    private String institutionalDepartmentName;

    public String getInstitutionalDepartmentName() {
        return institutionalDepartmentName;
    }

    public void setInstitutionalDepartmentName(String institutionalDepartmentName) {
        this.institutionalDepartmentName = institutionalDepartmentName;
    }

    public String getReqPhysicianName() {
        return reqPhysicianName;
    }

    public void setReqPhysicianName(String reqPhysicianName) {
        this.reqPhysicianName = reqPhysicianName;
    }
    
    /**
     * Default constructor
     */
    public Study() {
    }

    /**
     * Argument constructor
     *
     * @param studyPk
     * @param patientFk
     * @param studyInstanceUid
     * @param studyDate
     * @param studyTime
     * @param accessionNumber
     * @param institutionName
     * @param referringPhysicianName
     * @param studyDescription
     * @param studyId
     * @param studyDirectory
     * @param serieses
     */
    public Study(final Long studyPk, final Long patientFk, final String studyInstanceUid, final String studyDate,
	    final String studyTime,
	    final String accessionNumber, final String institutionName,final String institutionalDepartmentName , final String referringPhysicianName,
            final String reqPhysicianName,
	    final String studyDescription,
	    final String studyId, final String studyDirectory, final List<Series> serieses) {
	this.studyPk = studyPk;
	this.patientFk = patientFk;
	this.studyInstanceUid = studyInstanceUid;
	this.studyDate = studyDate;
	this.studyTime = studyTime;
	this.accessionNumber = accessionNumber;
	this.institutionName = institutionName;
        this.institutionalDepartmentName = institutionalDepartmentName;
	this.referringPhysicianName = referringPhysicianName;
	this.reqPhysicianName = reqPhysicianName;
	this.studyDescription = studyDescription;
	this.studyId = studyId;
	this.studyDirectory = studyDirectory;
	this.serieses = serieses;
    }

    /**
     * This method returns the value of the database column dump_study.study_pk
     *
     * @return the value of dump_study.study_pk
     */
    public final Long getStudyPk() {
	return studyPk;
    }

    /**
     * This method sets the value of the database column dump_study.study_pk
     *
     * @param studyPk the value for dump_study.study_pk
     */
    public final void setStudyPk(final Long studyPk) {
	this.studyPk = studyPk;
    }

    /**
     * This method returns the value of the database column dump_study.patient_fk
     *
     * @return the value of dump_study.patient_fk
     */
    public final Long getPatientFk() {
	return patientFk;
    }

    /**
     * This method sets the value of the database column dump_study.patient_fk
     *
     * @param patientFk the value for dump_study.patient_fk
     */
    public final void setPatientFk(final Long patientFk) {
	this.patientFk = patientFk;
    }

    /**
     * This method returns the value of the database column dump_study.study_instance_uid
     *
     * @return the value of dump_study.study_instance_uid
     */
    public final String getStudyInstanceUid() {
	return studyInstanceUid;
    }

    /**
     * This method sets the value of the database column dump_study.study_instance_uid
     *
     * @param studyInstanceUid the value for dump_study.study_instance_uid
     */
    public final void setStudyInstanceUid(final String studyInstanceUid) {
	this.studyInstanceUid = studyInstanceUid == null ? null : studyInstanceUid.trim();
    }

    /**
     * This method returns the value of the database column dump_study.study_date
     *
     * @return the value of dump_study.study_date
     */
    public final String getStudyDate() {
	return studyDate;
    }

    /**
     * This method sets the value of the database column dump_study.study_date
     *
     * @param studyDate the value for dump_study.study_date
     */
    public final void setStudyDate(final String studyDate) {
	this.studyDate = studyDate == null ? null : studyDate.trim();
    }

    /**
     * This method returns the value of the database column dump_study.study_time
     *
     * @return the value of dump_study.study_time
     */
    public final String getStudyTime() {
	return studyTime;
    }

    /**
     * This method sets the value of the database column dump_study.study_time
     *
     * @param studyTime the value for dump_study.study_time
     */
    public final void setStudyTime(final String studyTime) {
	this.studyTime = studyTime == null ? null : studyTime.trim();
    }

    /**
     * This method returns the value of the database column dump_study.accession_number
     *
     * @return the value of dump_study.accession_number
     */
    public final String getAccessionNumber() {
	return accessionNumber;
    }

    /**
     * This method sets the value of the database column dump_study.accession_number
     *
     * @param accessionNumber the value for dump_study.accession_number
     */
    public final void setAccessionNumber(final String accessionNumber) {
	this.accessionNumber = accessionNumber == null ? null : accessionNumber.trim();
    }

    /**
     * This method returns the value of the database column dump_study.institution_name
     *
     * @return the value of dump_study.institution_name
     */
    public final String getInstitutionName() {
	return institutionName;
    }

    /**
     * This method sets the value of the database column dump_study.institution_name
     *
     * @param institutionName the value for dump_study.institution_name
     */
    public final void setInstitutionName(final String institutionName) {
	this.institutionName = institutionName == null ? null : institutionName.trim();
    }

    /**
     * This method returns the value of the database column dump_study.referring_physician_name
     *
     * @return the value of dump_study.referring_physician_name
     */
    public final String getReferringPhysicianName() {
	return referringPhysicianName;
    }

    /**
     * This method sets the value of the database column dump_study.referring_physician_name
     *
     * @param referringPhysicianName the value for dump_study.referring_physician_name
     */
    public final void setReferringPhysicianName(final String referringPhysicianName) {
	this.referringPhysicianName = referringPhysicianName == null ? null : referringPhysicianName.trim();
    }

    /**
     * This method returns the value of the database column dump_study.study_description
     *
     * @return the value of dump_study.study_description
     */
    public final String getStudyDescription() {
	return studyDescription;
    }

    /**
     * This method sets the value of the database column dump_study.study_description
     *
     * @param studyDescription the value for dump_study.study_description
     */
    public final void setStudyDescription(final String studyDescription) {
	this.studyDescription = studyDescription == null ? null : studyDescription.trim();
    }

    /**
     * This method returns the value of the database column dump_study.study_id
     *
     * @return the value of dump_study.study_id
     */
    public final String getStudyId() {
	return studyId;
    }

    /**
     * This method sets the value of the database column dump_study.study_id
     *
     * @param studyId the value for dump_study.study_id
     */
    public final void setStudyId(final String studyId) {
	this.studyId = studyId == null ? null : studyId.trim();
    }

    /**
     * This method returns the value of the database column dump_study.study_directory
     *
     * @return the value of dump_study.study_directory
     */
    public final String getStudyDirectory() {
	return studyDirectory;
    }

    /**
     * This method sets the value of the database column dump_study.study_directory
     *
     * @param studyDirectory the value for dump_study.study_directory
     */
    public final void setStudyDirectory(final String studyDirectory) {
	this.studyDirectory = studyDirectory == null ? null : studyDirectory.trim();
    }

    /**
     * Gets the list of series
     *
     * @return List of series
     */
    public final List<Series> getSerieses() {
	return serieses;
    }

    /**
     * Sets the list of series
     *
     * @param serieses list of series
     */
    public final void setSerieses(final List<Series> serieses) {
	this.serieses = serieses;
    }
}
