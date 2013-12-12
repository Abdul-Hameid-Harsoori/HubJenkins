package com.secant.dcm.dump.object;

import java.io.Serializable;

/**
 * Unique tape name class. corresponds to the database table unq_tape_name
 */
public class UnqTapeName implements Serializable {

    /**
     * Serial version uid
     */
    private static final long serialVersionUID = 1L;
    /**
     * This field corresponds to the database column unq_tape_name.id
     */
    private Long id;
    /**
     * This field corresponds to the database column unq_tape_name.tape_name
     */
    private String tapeName;
    /**
     * This field corresponds to the database column unq_tape_name.status
     */
    private String status;
    /**
     * This field corresponds to the database column unq_tape_name.start_time
     */
    private String startTime;
    /**
     * This field corresponds to the database column unq_tape_name.end_time
     */
    private String endTime;

    /**
     * Default constructor
     */
    public UnqTapeName() {
    }

    /**
     * Gets the id
     * @return the value of unq_tape_name.id
     */
    public final Long getId() {
	return id;
    }

    /**
     * Sets the id
     * @param id the value for unq_tape_name.id
     */
    public final void setId(final Long id) {
	this.id = id;
    }

    /**
     * This method returns the value of the database column unq_tape_name.tape_name
     * @return the value of unq_tape_name.tape_name
     */
    public final String getTapeName() {
	return tapeName;
    }

    /**
     * This method sets the value of the database column unq_tape_name.tape_name
     * @param tapeName the value for unq_tape_name.tape_name
     */
    public final void setTapeName(final String tapeName) {
	this.tapeName = tapeName == null ? null : tapeName.trim();
    }

    /**
     * This method returns the value of the database column unq_tape_name.status
     * @return the value of unq_tape_name.status
     */
    public final String getStatus() {
	return status;
    }

    /**
     * This method sets the value of the database column unq_tape_name.status
     * @param status the value for unq_tape_name.status
     */
    public final void setStatus(final String status) {
	this.status = status == null ? null : status.trim();
    }

    /**
     * This method returns the value of the database column unq_tape_name.start_time
     * @return the value of unq_tape_name.start_time
     */
    public final String getStartTime() {
	return startTime;
    }

    /**
     * This method sets the value of the database column unq_tape_name.start_time
     * @param startTime the value for unq_tape_name.start_time
     */
    public final void setStartTime(final String startTime) {
	this.startTime = startTime;
    }

    /**
     * Gets the end time
     * @return the value of unq_tape_name.end_time
     */
    public final String getEndTime() {
	return endTime;
    }

    /**
     * Sets the end time
     * @param endTime the value for unq_tape_name.end_time
     */
    public final void setEndTime(final String endTime) {
	this.endTime = endTime;
    }
}
