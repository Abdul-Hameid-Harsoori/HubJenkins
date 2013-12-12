package com.secant.dcm.dump.object;

import java.io.Serializable;
import java.util.List;

/**
 * This class corresponds to the database table dump_series
 */
public class Series implements Serializable {

    /**
     * This field corresponds to the database table dump_series
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * This field corresponds to the database column dump_series.series_pk
     *
     */
    private Long seriesPk;
    /**
     * This field corresponds to the database column dump_series.study_fk
     *
     */
    private Long studyFk;
    /**
     * This field corresponds to the database column dump_series.series_instance_uid
     *
     */
    private String seriesInstanceUid;
    /**
     * This field corresponds to the database column dump_series.series_number
     *
     */
    private String seriesNumber;
    /**
     * This field corresponds to the database column dump_series.series_description
     *
     */
    private String seriesDescription;
    /**
     * This field corresponds to the database column dump_series.modality
     *
     */
    private String modality;
    /**
     * List of instances pertaining to this series
     */
    private List<Instance> instances;

    /**
     * Default constructor
     */
    public Series() {
    }

    /**
     * Argument constructor
     *
     * @param seriesPk
     * @param studyFk
     * @param seriesInstanceUid
     * @param seriesNumber
     * @param seriesDescription
     * @param modality
     * @param instances
     */
    public Series(final Long seriesPk, final Long studyFk, final String seriesInstanceUid, final String seriesNumber,
	    final String seriesDescription, final String modality, final List<Instance> instances) {
	this.seriesPk = seriesPk;
	this.studyFk = studyFk;
	this.seriesInstanceUid = seriesInstanceUid;
	this.seriesNumber = seriesNumber;
	this.seriesDescription = seriesDescription;
	this.modality = modality;
	this.instances = instances;
    }

    /**
     * This method returns the value of the database column dump_series.series_pk
     *
     * @return the value of dump_series.series_pk
     *
     */
    public final Long getSeriesPk() {
	return seriesPk;
    }

    /**
     * This method sets the value of the database column dump_series.series_pk
     *
     * @param seriesPk the value for dump_series.series_pk
     *
     */
    public final void setSeriesPk(final Long seriesPk) {
	this.seriesPk = seriesPk;
    }

    /**
     * This method returns the value of the database column dump_series.study_fk
     *
     * @return the value of dump_series.study_fk
     *
     */
    public final Long getStudyFk() {
	return studyFk;
    }

    /**
     * This method sets the value of the database column dump_series.study_fk
     *
     * @param studyFk the value for dump_series.study_fk
     *
     */
    public final void setStudyFk(final Long studyFk) {
	this.studyFk = studyFk;
    }

    /**
     * This method returns the value of the database column dump_series.series_instance_uid
     *
     * @return the value of dump_series.series_instance_uid
     *
     */
    public final String getSeriesInstanceUid() {
	return seriesInstanceUid;
    }

    /**
     * This method sets the value of the database column dump_series.series_instance_uid
     *
     * @param seriesInstanceUid the value for dump_series.series_instance_uid
     *
     */
    public final void setSeriesInstanceUid(final String seriesInstanceUid) {
	this.seriesInstanceUid = seriesInstanceUid == null ? null : seriesInstanceUid.trim();
    }

    /**
     * This method returns the value of the database column dump_series.series_number
     *
     * @return the value of dump_series.series_number
     *
     */
    public final String getSeriesNumber() {
	return seriesNumber;
    }

    /**
     * This method sets the value of the database column dump_series.series_number
     *
     * @param seriesNumber the value for dump_series.series_number
     *
     */
    public final void setSeriesNumber(final String seriesNumber) {
	this.seriesNumber = seriesNumber == null ? null : seriesNumber.trim();
    }

    /**
     * This method returns the value of the database column dump_series.series_description
     *
     * @return the value of dump_series.series_description
     *
     */
    public final String getSeriesDescription() {
	return seriesDescription;
    }

    /**
     * This method sets the value of the database column dump_series.series_description
     *
     * @param seriesDescription the value for dump_series.series_description
     *
     */
    public final void setSeriesDescription(final String seriesDescription) {
	this.seriesDescription = seriesDescription == null ? null : seriesDescription.trim();
    }

    /**
     * This method returns the value of the database column dump_series.modality
     *
     * @return the value of dump_series.modality
     *
     */
    public final String getModality() {
	return modality;
    }

    /**
     * This method sets the value of the database column dump_series.modality
     *
     * @param modality the value for dump_series.modality
     *
     */
    public final void setModality(final String modality) {
	this.modality = modality == null ? null : modality.trim();
    }

    /**
     * Gets the instance list
     *
     * @return instance list
     */
    public final List<Instance> getInstances() {
	return instances;
    }

    /**
     * Sets the instance list
     * 
     * @param instances instance list
     */
    public final void setInstances(List<Instance> instances) {
	this.instances = instances;
    }
}
