package com.secant.dcm.dump.object;

import java.io.Serializable;

/**
 * This class corresponds to database table tapes_dump
 */
public class TapesDump implements Serializable {

    /**
     * This field corresponds to the database table tapes_dump
     */
    private static final long serialVersionUID = 1L;
    /**
     * This field corresponds to the database column tapes_dump.id
     */
    private Long id;
    /**
     * This field corresponds to the database column tapes_dump.tape_name
     */
    private String tapeName;
    /**
     * This field corresponds to the database column tapes_dump.tape_sec_fld
     */
    private String tapeSecFld;
    /**
     * This field corresponds to the database column tapes_dump.tape_thi_fld
     */
    private String tapeThiFld;
    /**
     * This field corresponds to the database column tapes_dump.file_path
     */
    private String filePath;
    /**
     * This field corresponds to the database column tapes_dump.copied_path
     */
    private String copiedPath;
    /**
     * This field corresponds to the database column tapes_dump.status
     */
    private String status;
    /**
     * This field corresponds to the database column tapes_dump.start_time
     */
    private String startTime;
    /**
     * This field corresponds to the database column tapes_dump.end_time
     */
    private String endTime;
    /**
     * This field corresponds to the database column tapes_dump.file_size
     */
    private String fileSize;
    /**
     * This field corresponds to the database column tapes_dump.untar_start_time
     */
    private String untarStartTime;
    /**
     * This field corresponds to the database column tapes_dump.untar_end_time
     */
    private String untarEndTime;
    /**
     * This field corresponds to the database column tapes_dump.untared_path
     */
    private String untaredPath;
    /**
     * This field corresponds to the database column tapes_dump.untared_size
     */
    private String untaredSize;
    /**
     * This field corresponds to the database column tapes_dump.reconcile
     *
     * @ibatorgenerated Tue Jul 27 23:39:03 IST 2010
     */
    private String reconcile;
    /**
     * This field corresponds to the database column tapes_dump.num_files
     *
     */
    private Long numFiles;

    /**
     * Default constructor
     */
    public TapesDump() {
    }

    /**
     * Argument constructor
     *
     * @param tapeName
     * @param filePath
     * @param copiedPath
     * @param status
     * @param startTime
     * @param endTime
     */
    public TapesDump(final String tapeName, final String filePath, final String copiedPath, final String status,
	    final String startTime,
	    String endTime) {
	this.tapeName = tapeName;
	this.filePath = filePath;
	this.copiedPath = copiedPath;
	this.status = status;
	this.startTime = startTime;
	this.endTime = endTime;
    }

    /**
     * This method returns the value of the database column tapes_dump.id
     *
     * @return the value of tapes_dump.id
     */
    public final Long getId() {
	return id;
    }

    /**
     * This method sets the value of the database column tapes_dump.id
     *
     * @param id the value for tapes_dump.id
     */
    public final void setId(final Long id) {
	this.id = id;
    }

    /**
     * This method returns the value of the database column tapes_dump.tape_name
     *
     * @return the value of tapes_dump.tape_name
     */
    public final String getTapeName() {
	return tapeName;
    }

    /**
     * This method sets the value of the database column tapes_dump.tape_name
     *
     * @param tapeName the value for tapes_dump.tape_name
     */
    public final void setTapeName(final String tapeName) {
	this.tapeName = tapeName == null ? null : tapeName.trim();
    }

    /**
     * This method returns the value of the database column tapes_dump.tape_sec_fld
     *
     * @return the value of tapes_dump.tape_sec_fld
     */
    public final String getTapeSecFld() {
	return tapeSecFld;
    }

    /**
     * This method sets the value of the database column tapes_dump.tape_sec_fld
     *
     * @param tapeSecFld the value for tapes_dump.tape_sec_fld
     */
    public final void setTapeSecFld(final String tapeSecFld) {
	this.tapeSecFld = tapeSecFld == null ? null : tapeSecFld.trim();
    }

    /**
     * This method returns the value of the database column tapes_dump.tape_thi_fld
     *
     * @return the value of tapes_dump.tape_thi_fld
     */
    public final String getTapeThiFld() {
	return tapeThiFld;
    }

    /**
     * This method sets the value of the database column tapes_dump.tape_thi_fld
     *
     * @param tapeThiFld the value for tapes_dump.tape_thi_fld
     */
    public final void setTapeThiFld(final String tapeThiFld) {
	this.tapeThiFld = tapeThiFld == null ? null : tapeThiFld.trim();
    }

    /**
     * This method returns the value of the database column tapes_dump.file_path
     *
     * @return the value of tapes_dump.file_path
     */
    public final String getFilePath() {
	return filePath;
    }

    /**
     * This method sets the value of the database column tapes_dump.file_path
     *
     * @param filePath the value for tapes_dump.file_path
     */
    public final void setFilePath(final String filePath) {
	this.filePath = filePath == null ? null : filePath.trim();
    }

    /**
     * This method returns the value of the database column tapes_dump.copied_path
     *
     * @return the value of tapes_dump.copied_path
     */
    public final String getCopiedPath() {
	return copiedPath;
    }

    /**
     * This method sets the value of the database column tapes_dump.copied_path
     *
     * @param copiedPath the value for tapes_dump.copied_path
     */
    public final void setCopiedPath(final String copiedPath) {
	this.copiedPath = copiedPath == null ? null : copiedPath.trim();
    }

    /**
     * This method returns the value of the database column tapes_dump.status
     *
     * @return the value of tapes_dump.status
     */
    public final String getStatus() {
	return status;
    }

    /**
     * This method sets the value of the database column tapes_dump.status
     *
     * @param status the value for tapes_dump.status
     */
    public final void setStatus(final String status) {
	this.status = status == null ? null : status.trim();
    }

    /**
     * This method returns the value of the database column tapes_dump.start_time
     *
     * @return the value of tapes_dump.start_time
     */
    public final String getStartTime() {
	return startTime;
    }

    /**
     * This method sets the value of the database column tapes_dump.start_time
     *
     * @param startTime the value for tapes_dump.start_time
     */
    public final void setStartTime(final String startTime) {
	this.startTime = startTime;
    }

    /**
     * This method returns the value of the database column tapes_dump.end_time
     *
     * @return the value of tapes_dump.end_time
     */
    public final String getEndTime() {
	return endTime;
    }

    /**
     * This method sets the value of the database column tapes_dump.end_time
     *
     * @param endTime the value for tapes_dump.end_time
     */
    public final void setEndTime(final String endTime) {
	this.endTime = endTime;
    }

    /**
     * This method returns the value of the database column tapes_dump.file_size
     *
     * @return the value of tapes_dump.file_size
     */
    public final String getFileSize() {
	return fileSize;
    }

    /**
     * This method sets the value of the database column tapes_dump.file_size
     *
     * @param fileSize the value for tapes_dump.file_size
     */
    public final void setFileSize(final String fileSize) {
	this.fileSize = fileSize == null ? null : fileSize.trim();
    }

    /**
     * This method returns the value of the database column tapes_dump.untar_start_time
     *
     * @return the value of tapes_dump.untar_start_time
     */
    public final String getUntarStartTime() {
	return untarStartTime;
    }

    /**
     * This method sets the value of the database column tapes_dump.untar_start_time
     *
     * @param untarStartTime the value for tapes_dump.untar_start_time
     */
    public final void setUntarStartTime(final String untarStartTime) {
	this.untarStartTime = untarStartTime;
    }

    /**
     * This method returns the value of the database column tapes_dump.untar_end_time
     *
     * @return the value of tapes_dump.untar_end_time
     */
    public final String getUntarEndTime() {
	return untarEndTime;
    }

    /**
     * This method sets the value of the database column tapes_dump.untar_end_time
     *
     * @param untarEndTime the value for tapes_dump.untar_end_time
     */
    public final void setUntarEndTime(final String untarEndTime) {
	this.untarEndTime = untarEndTime;
    }

    /**
     * This method returns the value of the database column tapes_dump.untared_path
     *
     * @return the value of tapes_dump.untared_path
     */
    public final String getUntaredPath() {
	return untaredPath;
    }

    /**
     * This method sets the value of the database column tapes_dump.untared_path
     *
     * @param untaredPath the value for tapes_dump.untared_path
     */
    public final void setUntaredPath(final String untaredPath) {
	this.untaredPath = untaredPath == null ? null : untaredPath.trim();
    }

    /**
     * This method returns the value of the database column tapes_dump.untared_size
     *
     * @return the value of tapes_dump.untared_size
     */
    public final String getUntaredSize() {
	return untaredSize;
    }

    /**
     * This method sets the value of the database column tapes_dump.untared_size
     *
     * @param untaredSize the value for tapes_dump.untared_size
     */
    public final void setUntaredSize(final String untaredSize) {
	this.untaredSize = untaredSize == null ? null : untaredSize.trim();
    }

    /**
     * This method returns the value of the database column tapes_dump.reconcile
     *
     * @return the value of tapes_dump.reconcile
     */
    public final String getReconcile() {
	return reconcile;
    }

    /**
     * This method sets the value of the database column tapes_dump.reconcile
     *
     * @param reconcile the value for tapes_dump.reconcile
     */
    public final void setReconcile(final String reconcile) {
	this.reconcile = reconcile == null ? null : reconcile.trim();
    }

    /**
     * Gets the number of file.
     * 
     * @return number of files
     */
    public final Long getNumFiles() {
	return numFiles;
    }

    /**
     * Sets the number of files.
     * 
     * @param numFiles
     */
    public final void setNumFiles(final Long numFiles) {
	this.numFiles = numFiles;
    }
}
