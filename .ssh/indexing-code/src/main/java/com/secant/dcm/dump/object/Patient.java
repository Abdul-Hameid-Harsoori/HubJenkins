package com.secant.dcm.dump.object;

import java.io.Serializable;
import java.util.List;

/**
 * This class corresponds to the database table dump_patient
 * @author Anantj
 */
public class Patient implements Serializable {

    /**
     * This field corresponds to the database table dump_patient
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * This field corresponds to the database column dump_patient.patient_pk
     *
     */
    private Long patientPk;
    /**
     * This field corresponds to the database column dump_patient.patient_id
     *
     */
    private String patientId;
    /**
     * This field corresponds to the database column dump_patient.patient_name
     *
     */
    private String patientName;
    /**
     * This field corresponds to the database column dump_patient.other_patient_id
     *
     */
    private String otherPatientId;
    /**
     * This field corresponds to the database column dump_patient.sex
     *
     */
    private String sex;
    /**
     * This field corresponds to the database column dump_patient.dateofbirth
     *
     */
    private String dateofbirth;
    /**
     * This field corresponds to the database column dump_patient.tapes_dump_id
     *
     */
    private Long tapesDumpId;
    /**
     * List of studies pertaining to this patient
     */
    private List<Study> studies;

    /**
     * Default constructor
     */
    public Patient() {
    }

    /**
     * Argument constructor
     *
     * @param studies
     * @param patientPk
     * @param patientId
     * @param patientName
     * @param otherPatientId
     * @param sex
     * @param dateofbirth
     * @param tapesDumpId
     */
    public Patient(final List<Study> studies, final Long patientPk, final String patientId, final String patientName,
	    final String otherPatientId,
	    final String sex, final String dateofbirth, final Long tapesDumpId) {
	this.studies = studies;
	this.patientPk = patientPk;
	this.patientId = patientId;
	this.patientName = patientName;
	this.otherPatientId = otherPatientId;
	this.sex = sex;
	this.dateofbirth = dateofbirth;
	this.tapesDumpId = tapesDumpId;
    }

    /**
     * This method returns the value of the database column dump_patient.patient_pk
     *
     * @return the value of dump_patient.patient_pk
     *
     */
    public final Long getPatientPk() {
	return patientPk;
    }

    /**
     * This method sets the value of the database column dump_patient.patient_pk
     *
     * @param patientPk the value for dump_patient.patient_pk
     *
     */
    public final void setPatientPk(final Long patientPk) {
	this.patientPk = patientPk;
    }

    /**
     * This method returns the value of the database column dump_patient.patient_id
     *
     * @return the value of dump_patient.patient_id
     *
     */
    public final String getPatientId() {
	return patientId;
    }

    /**
     * This method sets the value of the database column dump_patient.patient_id
     *
     * @param patientId the value for dump_patient.patient_id
     *
     */
    public final void setPatientId(final String patientId) {
	this.patientId = patientId == null ? null : patientId.trim();
    }

    /**
     * This method returns the value of the database column dump_patient.patient_name
     *
     * @return the value of dump_patient.patient_name
     *
     */
    public final String getPatientName() {
	return patientName;
    }

    /**
     * This method sets the value of the database column dump_patient.patient_name
     *
     * @param patientName the value for dump_patient.patient_name
     *
     */
    public final void setPatientName(final String patientName) {
	this.patientName = patientName == null ? null : patientName.trim();
    }

    /**
     * This method returns the value of the database column dump_patient.other_patient_id
     *
     * @return the value of dump_patient.other_patient_id
     *
     */
    public final String getOtherPatientId() {
	return otherPatientId;
    }

    /**
     * This method sets the value of the database column dump_patient.other_patient_id
     *
     * @param otherPatientId the value for dump_patient.other_patient_id
     *
     */
    public final void setOtherPatientId(final String otherPatientId) {
	this.otherPatientId = otherPatientId == null ? null : otherPatientId.trim();
    }

    /**
     * This method returns the value of the database column dump_patient.sex
     *
     * @return the value of dump_patient.sex
     *
     */
    public final String getSex() {
	return sex;
    }

    /**
     * This method sets the value of the database column dump_patient.sex
     *
     * @param sex the value for dump_patient.sex
     *
     */
    public final void setSex(final String sex) {
	this.sex = sex == null ? null : sex.trim();
    }

    /**
     * This method returns the value of the database column dump_patient.dateofbirth
     *
     * @return the value of dump_patient.dateofbirth
     *
     */
    public final String getDateofbirth() {
	return dateofbirth;
    }

    /**
     * This method sets the value of the database column dump_patient.dateofbirth
     *
     * @param dateofbirth the value for dump_patient.dateofbirth
     *
     */
    public final void setDateofbirth(final String dateofbirth) {
	this.dateofbirth = dateofbirth == null ? null : dateofbirth.trim();
    }

    /**
     * This method returns the value of the database column dump_patient.tapes_dump_id
     *
     * @return the value of dump_patient.tapes_dump_id
     *
     */
    public final Long getTapesDumpId() {
	return tapesDumpId;
    }

    /**
     * This method sets the value of the database column dump_patient.tapes_dump_id
     *
     * @param tapesDumpId the value for dump_patient.tapes_dump_id
     *
     */
    public final void setTapesDumpId(final Long tapesDumpId) {
	this.tapesDumpId = tapesDumpId;
    }

    /**
     * Gets the list of studies
     * @return list of studies
     */
    public final List<Study> getStudies() {
	return studies;
    }

    /**
     * Sets the list of studies
     * @param studies list of studies
     */
    public final void setStudies(List<Study> studies) {
	this.studies = studies;
    }
}
