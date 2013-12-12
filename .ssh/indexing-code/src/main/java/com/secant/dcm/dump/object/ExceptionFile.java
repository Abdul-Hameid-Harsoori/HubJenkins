package com.secant.dcm.dump.object;

import java.io.Serializable;

/**
 * This class corresponds to the database table dump_exceptions
 */
public class ExceptionFile implements Serializable {

    /**
     * This field corresponds to the database table dump_exceptions
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * This field corresponds to the database column dump_exceptions.exceptions_id
     *
     */
    private Long exceptionsId;
    /**
     * This field corresponds to the database column dump_exceptions.image_name
     *
     */
    private String imageName;
    /**
     * This field corresponds to the database column dump_exceptions.image_path
     *
     */
    private String imageLocation;
    /**
     * This field corresponds to the database column dump_exceptions.exception
     *
     */
    private String exception;
    /**
     * This field corresponds to the database column dump_exceptions.patient_id
     *
     */
    private String patientId;
    /**
     * This field corresponds to the database column dump_exceptions.patient_name
     *
     */
    private String patientName;
    /**
     * This field corresponds to the database column dump_exceptions.other_patient_id
     *
     */
    private String otherPatientId;
    /**
     * This field corresponds to the database column dump_exceptions.sex
     *
     */
    private String sex;
    /**
     * This field corresponds to the database column dump_exceptions.dateofbirth
     *
     */
    private String dateofbirth;
    /**
     * This field corresponds to the database column dump_exceptions.study_instance_uid
     *
     */
    private String studyInstanceUid;
    /**
     * This field corresponds to the database column dump_exceptions.study_date
     *
     */
    private String studyDate;
    /**
     * This field corresponds to the database column dump_exceptions.study_time
     *
     */
    private String studyTime;
    /**
     * This field corresponds to the database column dump_exceptions.accession_number
     *
     */
    private String accessionNumber;
    /**
     * This field corresponds to the database column dump_exceptions.institution_name
     *
     */
    private String institutionName;
    /**
     * This field corresponds to the database column dump_exceptions.referring_physician_name
     *
     */
    private String referringPhysicianName;
    /**
     * This field corresponds to the database column dump_exceptions.study_description
     *
     */
    private String studyDescription;
    /**
     * This field corresponds to the database column dump_exceptions.study_id
     *
     */
    private String studyId;
    /**
     * This field corresponds to the database column dump_exceptions.study_directory
     *
     */
    private String studyDirectory;
    /**
     * This field corresponds to the database column dump_exceptions.modality
     *
     */
    private String modality;

    /**
     * Default constructor
     */
    public ExceptionFile() {
    }

    /**
     * This method returns the value of the database column dump_exceptions.exceptions_id
     *
     * @return the value of dump_exceptions.exceptions_id
     *
     */
    public final Long getExceptionsId() {
	return exceptionsId;
    }

    /**
     * This method sets the value of the database column dump_exceptions.exceptions_id
     *
     * @param exceptionsId the value for dump_exceptions.exceptions_id
     *
     */
    public final void setExceptionsId(final Long exceptionsId) {
	this.exceptionsId = exceptionsId;
    }

    /**
     * This method returns the value of the database column dump_exceptions.image_name
     *
     * @return the value of dump_exceptions.image_name
     *
     */
    public final String getImageName() {
	return imageName;
    }

    /**
     * This method sets the value of the database column dump_exceptions.image_name
     *
     * @param imageName the value for dump_exceptions.image_name
     *
     */
    public final void setImageName(final String imageName) {
	this.imageName = imageName == null ? null : imageName.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.image_location
     *
     * @return the value of dump_exceptions.image_location
     *
     */
    public final String getImageLocation() {
	return imageLocation;
    }

    /**
     * This method sets the value of the database column dump_exceptions.image_location
     *
     * @param imageLocation the value for dump_exceptions.image_location
     *
     */
    public final void setImageLocation(final String imageLocation) {
	this.imageLocation = imageLocation == null ? null : imageLocation.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.exception
     *
     * @return the value of dump_exceptions.exception
     *
     */
    public final String getException() {
	return exception;
    }

    /**
     * This method sets the value of the database column dump_exceptions.exception
     *
     * @param exception the value for dump_exceptions.exception
     *
     */
    public final void setException(final String exception) {
	this.exception = exception == null ? null : exception.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.patient_id
     *
     * @return the value of dump_exceptions.patient_id
     *
     */
    public final String getPatientId() {
	return patientId;
    }

    /**
     * This method sets the value of the database column dump_exceptions.patient_id
     *
     * @param patientId the value for dump_exceptions.patient_id
     *
     */
    public final void setPatientId(final String patientId) {
	this.patientId = patientId == null ? null : patientId.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.patient_name
     *
     * @return the value of dump_exceptions.patient_name
     *
     */
    public final String getPatientName() {
	return patientName;
    }

    /**
     * This method sets the value of the database column dump_exceptions.patient_name
     *
     * @param patientName the value for dump_exceptions.patient_name
     *
     */
    public final void setPatientName(final String patientName) {
	this.patientName = patientName == null ? null : patientName.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.other_patient_id
     *
     * @return the value of dump_exceptions.other_patient_id
     *
     */
    public final String getOtherPatientId() {
	return otherPatientId;
    }

    /**
     * This method sets the value of the database column dump_exceptions.other_patient_id
     *
     * @param otherPatientId the value for dump_exceptions.other_patient_id
     *
     */
    public final void setOtherPatientId(final String otherPatientId) {
	this.otherPatientId = otherPatientId == null ? null : otherPatientId.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.sex
     *
     * @return the value of dump_exceptions.sex
     *
     */
    public final String getSex() {
	return sex;
    }

    /**
     * This method sets the value of the database column dump_exceptions.sex
     *
     * @param sex the value for dump_exceptions.sex
     *
     */
    public final void setSex(final String sex) {
	this.sex = sex == null ? null : sex.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.dateofbirth
     *
     * @return the value of dump_exceptions.dateofbirth
     *
     */
    public final String getDateofbirth() {
	return dateofbirth;
    }

    /**
     * This method sets the value of the database column dump_exceptions.dateofbirth
     *
     * @param dateofbirth the value for dump_exceptions.dateofbirth
     *
     */
    public final void setDateofbirth(final String dateofbirth) {
	this.dateofbirth = dateofbirth == null ? null : dateofbirth.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.study_instance_uid
     *
     * @return the value of dump_exceptions.study_instance_uid
     *
     */
    public final String getStudyInstanceUid() {
	return studyInstanceUid;
    }

    /**
     * This method sets the value of the database column dump_exceptions.study_instance_uid
     *
     * @param studyInstanceUid the value for dump_exceptions.study_instance_uid
     *
     */
    public final void setStudyInstanceUid(final String studyInstanceUid) {
	this.studyInstanceUid = studyInstanceUid == null ? null : studyInstanceUid.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.study_date
     *
     * @return the value of dump_exceptions.study_date
     *
     */
    public final String getStudyDate() {
	return studyDate;
    }

    /**
     * This method sets the value of the database column dump_exceptions.study_date
     *
     * @param studyDate the value for dump_exceptions.study_date
     *
     */
    public final void setStudyDate(final String studyDate) {
	this.studyDate = studyDate == null ? null : studyDate.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.study_time
     *
     * @return the value of dump_exceptions.study_time
     *
     */
    public final String getStudyTime() {
	return studyTime;
    }

    /**
     * This method sets the value of the database column dump_exceptions.study_time
     *
     * @param studyTime the value for dump_exceptions.study_time
     *
     */
    public final void setStudyTime(final String studyTime) {
	this.studyTime = studyTime == null ? null : studyTime.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.accession_number
     *
     * @return the value of dump_exceptions.accession_number
     *
     */
    public final String getAccessionNumber() {
	return accessionNumber;
    }

    /**
     * This method sets the value of the database column dump_exceptions.accession_number
     *
     * @param accessionNumber the value for dump_exceptions.accession_number
     *
     */
    public final void setAccessionNumber(final String accessionNumber) {
	this.accessionNumber = accessionNumber == null ? null : accessionNumber.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.institution_name
     *
     * @return the value of dump_exceptions.institution_name
     *
     */
    public final String getInstitutionName() {
	return institutionName;
    }

    /**
     * This method sets the value of the database column dump_exceptions.institution_name
     *
     * @param institutionName the value for dump_exceptions.institution_name
     *
     */
    public final void setInstitutionName(final String institutionName) {
	this.institutionName = institutionName == null ? null : institutionName.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.referring_physician_name
     *
     * @return the value of dump_exceptions.referring_physician_name
     *
     */
    public final String getReferringPhysicianName() {
	return referringPhysicianName;
    }

    /**
     * This method sets the value of the database column dump_exceptions.referring_physician_name
     *
     * @param referringPhysicianName the value for dump_exceptions.referring_physician_name
     *
     */
    public final void setReferringPhysicianName(final String referringPhysicianName) {
	this.referringPhysicianName = referringPhysicianName == null ? null : referringPhysicianName.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.study_description
     *
     * @return the value of dump_exceptions.study_description
     *
     */
    public final String getStudyDescription() {
	return studyDescription;
    }

    /**
     * This method sets the value of the database column dump_exceptions.study_description
     *
     * @param studyDescription the value for dump_exceptions.study_description
     *
     */
    public final void setStudyDescription(final String studyDescription) {
	this.studyDescription = studyDescription == null ? null : studyDescription.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.study_id
     *
     * @return the value of dump_exceptions.study_id
     *
     */
    public final String getStudyId() {
	return studyId;
    }

    /**
     * This method sets the value of the database column dump_exceptions.study_id
     *
     * @param studyId the value for dump_exceptions.study_id
     *
     */
    public final void setStudyId(final String studyId) {
	this.studyId = studyId == null ? null : studyId.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.study_directory
     *
     * @return the value of dump_exceptions.study_directory
     *
     */
    public final String getStudyDirectory() {
	return studyDirectory;
    }

    /**
     * This method sets the value of the database column dump_exceptions.study_directory
     *
     * @param studyDirectory the value for dump_exceptions.study_directory
     *
     */
    public final void setStudyDirectory(final String studyDirectory) {
	this.studyDirectory = studyDirectory == null ? null : studyDirectory.trim();
    }

    /**
     * This method returns the value of the database column dump_exceptions.modality
     *
     * @return the value of dump_exceptions.modality
     *
     */
    public final String getModality() {
	return modality;
    }

    /**
     * This method sets the value of the database column dump_exceptions.modality
     *
     * @param modality the value for dump_exceptions.modality
     *
     */
    public final void setModality(final String modality) {
	this.modality = modality == null ? null : modality.trim();
    }
}
