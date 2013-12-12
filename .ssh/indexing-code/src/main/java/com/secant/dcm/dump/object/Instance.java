package com.secant.dcm.dump.object;

import java.io.Serializable;

/**
 * This class corresponds to the database table dump_instance
 */
public class Instance implements Serializable {

    /**
     * This field corresponds to the database table dump_instance
     */
    private static final long serialVersionUID = 1L;
    /**
     * This field corresponds to the database column dump_instance.instance_id
     */
    private Long instanceId;
    /**
     * This field corresponds to the database column dump_instance.series_fk
     */
    private Long seriesFk;
    /**
     * This field corresponds to the database column dump_instance.sop_instance_uid
     */
    private String sopInstanceUid;
    /**
     * This field corresponds to the database column dump_instance.image_name
     */
    private String imageName;
    /**
     * This field corresponds to the database column dump_instance.image_path
     *
     */
    private String imagePath;
    /**
     * This field corresponds to the database column dump_instance.sop_class_uid
     *
     */
    private String sopClassUid;
    /**
     * This field corresponds to the database column dump_instance.transer_syntax_uid
     *
     */
    private String transerSyntaxUid;
    /**
     * This field corresponds to the database column dump_instance.photometric_interpretation
     *
     */
    private String photometricInterpretation;
    /**
     * This field corresponds to the database column dump_instance.image_size
     *
     */
    private Long imageSize;
    /**
     * This field corresponds to the database column dump_instance.instance_number
     *
     */
    private String instanceNumber;
    
    private String presentationIntentType;

    /**
     * Default constructor
     */
    public Instance() {
    }

    /**
     * Argument constructor
     * 
     * @param instanceId
     * @param seriesFk
     * @param sopInstanceUid
     * @param imageName
     * @param imagePath
     * @param sopClassUid
     * @param transerSyntaxUid
     * @param photometricInterpretation
     * @param imageSize
     * @param instanceNumber
     */
    public Instance(final Long instanceId, final Long seriesFk, final String sopInstanceUid, final String imageName,
	    final String imagePath,
	    final String sopClassUid, final String transerSyntaxUid, final String photometricInterpretation,
	    final Long imageSize,
	    final String instanceNumber , String presentationIntentType) {
	this.instanceId = instanceId;
	this.seriesFk = seriesFk;
	this.sopInstanceUid = sopInstanceUid;
	this.imageName = imageName;
	this.imagePath = imagePath;
	this.sopClassUid = sopClassUid;
	this.transerSyntaxUid = transerSyntaxUid;
	this.photometricInterpretation = photometricInterpretation;
	this.imageSize = imageSize;
	this.instanceNumber = instanceNumber;
        this.presentationIntentType = presentationIntentType;
    }

    public String getPresentationIntentType() {
        return presentationIntentType;
    }

    public void setPresentationIntentType(String presentationIntentType) {
        this.presentationIntentType = presentationIntentType;
    }    

    /**
     * This method returns the value of the database column dump_instance.instance_id
     *
     * @return the value of dump_instance.instance_id
     *
     */
    public final Long getInstanceId() {
	return instanceId;
    }

    /**
     * This method sets the value of the database column dump_instance.instance_id
     *
     * @param instanceId the value for dump_instance.instance_id
     *
     */
    public final void setInstanceId(final Long instanceId) {
	this.instanceId = instanceId;
    }

    /**
     * This method returns the value of the database column dump_instance.series_fk
     *
     * @return the value of dump_instance.series_fk
     *
     */
    public final Long getSeriesFk() {
	return seriesFk;
    }

    /**
     * This method sets the value of the database column dump_instance.series_fk
     *
     * @param seriesFk the value for dump_instance.series_fk
     *
     */
    public final void setSeriesFk(final Long seriesFk) {
	this.seriesFk = seriesFk;
    }

    /**
     * This method returns the value of the database column dump_instance.sop_instance_uid
     *
     * @return the value of dump_instance.sop_instance_uid
     *
     */
    public final String getSopInstanceUid() {
	return sopInstanceUid;
    }

    /**
     * This method sets the value of the database column dump_instance.sop_instance_uid
     *
     * @param sopInstanceUid the value for dump_instance.sop_instance_uid
     *
     */
    public final void setSopInstanceUid(final String sopInstanceUid) {
	this.sopInstanceUid = sopInstanceUid == null ? null : sopInstanceUid.trim();
    }

    /**
     * This method returns the value of the database column dump_instance.image_name
     *
     * @return the value of dump_instance.image_name
     *
     */
    public final String getImageName() {
	return imageName;
    }

    /**
     * This method sets the value of the database column dump_instance.image_name
     *
     * @param imageName the value for dump_instance.image_name
     *
     */
    public final void setImageName(final String imageName) {
	this.imageName = imageName == null ? null : imageName.trim();
    }

    /**
     * This method returns the value of the database column dump_instance.image_path
     *
     * @return the value of dump_instance.image_path
     *
     */
    public final String getImagePath() {
	return imagePath;
    }

    /**
     * This method sets the value of the database column dump_instance.image_path
     *
     * @param imagePath the value for dump_instance.image_path
     *
     */
    public final void setImagePath(final String imagePath) {
	this.imagePath = imagePath == null ? null : imagePath.trim();
    }

    /**
     * This method returns the value of the database column dump_instance.sop_class_uid
     *
     * @return the value of dump_instance.sop_class_uid
     *
     */
    public final String getSopClassUid() {
	return sopClassUid;
    }

    /**
     * This method sets the value of the database column dump_instance.sop_class_uid
     *
     * @param sopClassUid the value for dump_instance.sop_class_uid
     *
     */
    public final void setSopClassUid(final String sopClassUid) {
	this.sopClassUid = sopClassUid == null ? null : sopClassUid.trim();
    }

    /**
     * This method returns the value of the database column dump_instance.transer_syntax_uid
     *
     * @return the value of dump_instance.transer_syntax_uid
     *
     */
    public final String getTranserSyntaxUid() {
	return transerSyntaxUid;
    }

    /**
     * This method sets the value of the database column dump_instance.transer_syntax_uid
     *
     * @param transerSyntaxUid the value for dump_instance.transer_syntax_uid
     *
     */
    public final void setTranserSyntaxUid(final String transerSyntaxUid) {
	this.transerSyntaxUid = transerSyntaxUid == null ? null : transerSyntaxUid.trim();
    }

    /**
     * This method returns the value of the database column dump_instance.photometric_interpretation
     *
     * @return the value of dump_instance.photometric_interpretation
     *
     */
    public final String getPhotometricInterpretation() {
	return photometricInterpretation;
    }

    /**
     * This method sets the value of the database column dump_instance.photometric_interpretation
     *
     * @param photometricInterpretation the value for dump_instance.photometric_interpretation
     *
     */
    public final void setPhotometricInterpretation(final String photometricInterpretation) {
	this.photometricInterpretation = photometricInterpretation == null ? null : photometricInterpretation.trim();
    }

    /**
     * This method returns the value of the database column dump_instance.image_size
     *
     * @return the value of dump_instance.image_size
     *
     */
    public final Long getImageSize() {
	return imageSize;
    }

    /**
     * This method sets the value of the database column dump_instance.image_size
     *
     * @param imageSize the value for dump_instance.image_size
     */
    public final void setImageSize(final Long imageSize) {
	this.imageSize = imageSize;
    }

    /**
     * This method returns the value of the database column dump_instance.instance_number
     *
     * @return the value of dump_instance.instance_number
     */
    public final String getInstanceNumber() {
	return instanceNumber;
    }

    /**
     * This method sets the value of the database column dump_instance.instance_number
     *
     * @param instanceNumber the value for dump_instance.instance_number
     */
    public final void setInstanceNumber(final String instanceNumber) {
	this.instanceNumber = instanceNumber == null ? null : instanceNumber.trim();
    }
}
