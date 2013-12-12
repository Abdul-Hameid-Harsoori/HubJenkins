package com.secant.dcm.dump.object;

import java.io.Serializable;

/**
 * This class corresponds to the database table dump_corrupt
 */
public class CorruptFile implements Serializable {

    /**
     * This field corresponds to the database table dump_corrupt
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * This field corresponds to the database column dump_corrupt.id
     *
     */
    private Long id;
    /**
     * This field corresponds to the database column dump_corrupt.tapes_dump_id
     *
     */
    private Long tapesDumpId;
    /**
     * This field corresponds to the database column dump_corrupt.image_name
     *
     */
    private String imageName;
    /**
     * This field corresponds to the database column dump_corrupt.image_path
     *
     */
    private String imagePath;
    /**
     * This field corresponds to the database column dump_corrupt.cause
     *
     */
    private String cause;

    /**
     * Default constructor
     */
    public CorruptFile() {
    }

    /**
     * Argument constructor
     * 
     * @param id
     * @param tapesDumpId
     * @param imageName
     * @param imagePath
     * @param cause
     */
    public CorruptFile(final Long id, final Long tapesDumpId, final String imageName, final String imagePath,
	    final String cause) {
	this.id = id;
	this.tapesDumpId = tapesDumpId;
	this.imageName = imageName;
	this.imagePath = imagePath;
	this.cause = cause;
    }

    /**
     * This method returns the value of the database column dump_corrupt.id
     *
     * @return the value of dump_corrupt.id
     *
     */
    public final Long getId() {
	return id;
    }

    /**
     * This method sets the value of the database column dump_corrupt.id
     *
     * @param id the value for dump_corrupt.id
     *
     */
    public final void setId(final Long id) {
	this.id = id;
    }

    /**
     * This method returns the value of the database column dump_corrupt.tapes_dump_id
     *
     * @return the value of dump_corrupt.tapes_dump_id
     *
     */
    public final Long getTapesDumpId() {
	return tapesDumpId;
    }

    /**
     * This method sets the value of the database column dump_corrupt.tapes_dump_id
     *
     * @param tapesDumpId the value for dump_corrupt.tapes_dump_id
     *
     */
    public final void setTapesDumpId(final Long tapesDumpId) {
	this.tapesDumpId = tapesDumpId;
    }

    /**
     * This method returns the value of the database column dump_corrupt.image_name
     *
     * @return the value of dump_corrupt.image_name
     *
     */
    public final String getImageName() {
	return imageName;
    }

    /**
     * This method sets the value of the database column dump_corrupt.image_name
     *
     * @param imageName the value for dump_corrupt.image_name
     *
     */
    public final void setImageName(final String imageName) {
	this.imageName = imageName == null ? null : imageName.trim();
    }

    /**
     * This method returns the value of the database column dump_corrupt.image_path
     *
     * @return the value of dump_corrupt.image_path
     *
     */
    public final String getImagePath() {
	return imagePath;
    }

    /**
     * This method sets the value of the database column dump_corrupt.image_path
     *
     * @param imagePath the value for dump_corrupt.image_path
     *
     */
    public final void setImagePath(final String imagePath) {
	this.imagePath = imagePath == null ? null : imagePath.trim();
    }

    /**
     * This method returns the value of the database column dump_corrupt.cause
     *
     * @return the value of dump_corrupt.cause
     *
     */
    public final String getCause() {
	return cause;
    }

    /**
     * This method sets the value of the database column dump_corrupt.cause
     *
     * @param cause the value for dump_corrupt.cause
     *
     */
    public final void setCause(final String cause) {
	this.cause = cause == null ? null : cause.trim();
    }
}
