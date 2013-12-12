package com.secant.dcm.dump.object;

import java.io.Serializable;
import java.util.Date;

/**
 * This class corresponds to the database table routing
 */
public class Routing implements Serializable {

    /**
     * This field corresponds to the database table routing
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * This field corresponds to the database column routing.routing_id
     *
     */
    private Long routingId;
    /**
     * This field corresponds to the database column routing.pacs_fk
     *
     */
    private Long pacsFk;
    /**
     * This field corresponds to the database column routing.ris_fk
     *
     */
    private Long risFk;
    /**
     * This field corresponds to the database column routing.total_img_to_mig
     *
     */
    private Long totalImgToMig;
    /**
     * This field corresponds to the database column routing.sent_images
     *
     */
    private Long sentImages;
    /**
     * This field corresponds to the database column routing.failed_to_snd_img
     *
     */
    private Long failedToSndImg;
    /**
     * This field corresponds to the database column routing.sender_start_time
     *
     */
    private Date senderStartTime;
    /**
     * This field corresponds to the database column routing.sender_end_time
     *
     */
    private Date senderEndTime;
    /**
     * This field corresponds to the database column routing.send_retry_count
     *
     */
    private Long sendRetryCount;
    /**
     * This field corresponds to the database column routing.sender_status
     *
     */
    private String senderStatus;
    /**
     * This field corresponds to the database column routing.cleansed_from
     *
     */
    private String cleansedFrom;
    /**
     * This field corresponds to the database column routing.source_aet
     *
     */
    private String sourceAet;
    /**
     * This field corresponds to the database column routing.mbox_aet
     *
     */
    private String mboxAet;
    /**
     * This field corresponds to the database column routing.dest_aet
     *
     */
    private String destAet;

    /**
     * Default constructor
     */
    public Routing() {
    }

    /**
     * This method returns the value of the database column routing.routing_id
     *
     * @return the value of routing.routing_id
     *
     */
    public final Long getRoutingId() {
	return routingId;
    }

    /**
     * This method sets the value of the database column routing.routing_id
     *
     * @param routingId the value for routing.routing_id
     *
     */
    public final void setRoutingId(final Long routingId) {
	this.routingId = routingId;
    }

    /**
     * This method returns the value of the database column routing.pacs_fk
     *
     * @return the value of routing.pacs_fk
     *
     */
    public final Long getPacsFk() {
	return pacsFk;
    }

    /**
     * This method sets the value of the database column routing.pacs_fk
     *
     * @param pacsFk the value for routing.pacs_fk
     *
     */
    public final void setPacsFk(final Long pacsFk) {
	this.pacsFk = pacsFk;
    }

    /**
     * This method returns the value of the database column routing.ris_fk
     *
     * @return the value of routing.ris_fk
     *
     */
    public final Long getRisFk() {
	return risFk;
    }

    /**
     * This method sets the value of the database column routing.ris_fk
     *
     * @param risFk the value for routing.ris_fk
     *
     */
    public final void setRisFk(final Long risFk) {
	this.risFk = risFk;
    }

    /**
     * This method returns the value of the database column routing.total_img_to_mig
     *
     * @return the value of routing.total_img_to_mig
     *
     */
    public final Long getTotalImgToMig() {
	return totalImgToMig;
    }

    /**
     * This method sets the value of the database column routing.total_img_to_mig
     *
     * @param totalImgToMig the value for routing.total_img_to_mig
     *
     */
    public final void setTotalImgToMig(final Long totalImgToMig) {
	this.totalImgToMig = totalImgToMig;
    }

    /**
     * This method returns the value of the database column routing.sent_images
     *
     * @return the value of routing.sent_images
     *
     */
    public final Long getSentImages() {
	return sentImages;
    }

    /**
     * This method sets the value of the database column routing.sent_images
     *
     * @param sentImages the value for routing.sent_images
     *
     */
    public final void setSentImages(final Long sentImages) {
	this.sentImages = sentImages;
    }

    /**
     * This method returns the value of the database column routing.failed_to_snd_img
     *
     * @return the value of routing.failed_to_snd_img
     *
     */
    public final Long getFailedToSndImg() {
	return failedToSndImg;
    }

    /**
     * This method sets the value of the database column routing.failed_to_snd_img
     *
     * @param failedToSndImg the value for routing.failed_to_snd_img
     *
     */
    public final void setFailedToSndImg(final Long failedToSndImg) {
	this.failedToSndImg = failedToSndImg;
    }

    /**
     * This method returns the value of the database column routing.sender_start_time
     *
     * @return the value of routing.sender_start_time
     *
     */
    public final Date getSenderStartTime() {
	return senderStartTime;
    }

    /**
     * This method sets the value of the database column routing.sender_start_time
     *
     * @param senderStartTime the value for routing.sender_start_time
     *
     */
    public final void setSenderStartTime(final Date senderStartTime) {
	this.senderStartTime = senderStartTime;
    }

    /**
     * This method returns the value of the database column routing.sender_end_time
     *
     * @return the value of routing.sender_end_time
     *
     */
    public final Date getSenderEndTime() {
	return senderEndTime;
    }

    /**
     * This method sets the value of the database column routing.sender_end_time
     *
     * @param senderEndTime the value for routing.sender_end_time
     *
     */
    public final void setSenderEndTime(final Date senderEndTime) {
	this.senderEndTime = senderEndTime;
    }

    /**
     * This method returns the value of the database column routing.send_retry_count
     *
     * @return the value of routing.send_retry_count
     *
     */
    public final Long getSendRetryCount() {
	return sendRetryCount;
    }

    /**
     * This method sets the value of the database column routing.send_retry_count
     *
     * @param sendRetryCount the value for routing.send_retry_count
     *
     */
    public final void setSendRetryCount(final Long sendRetryCount) {
	this.sendRetryCount = sendRetryCount;
    }

    /**
     * This method returns the value of the database column routing.sender_status
     *
     * @return the value of routing.sender_status
     *
     */
    public final String getSenderStatus() {
	return senderStatus;
    }

    /**
     * This method sets the value of the database column routing.sender_status
     *
     * @param senderStatus the value for routing.sender_status
     *
     */
    public final void setSenderStatus(final String senderStatus) {
	this.senderStatus = senderStatus == null ? null : senderStatus.trim();
    }

    /**
     * This method returns the value of the database column routing.cleansed_from
     *
     * @return the value of routing.cleansed_from
     *
     */
    public final String getCleansedFrom() {
	return cleansedFrom;
    }

    /**
     * This method sets the value of the database column routing.cleansed_from
     *
     * @param cleansedFrom the value for routing.cleansed_from
     *
     */
    public final void setCleansedFrom(final String cleansedFrom) {
	this.cleansedFrom = cleansedFrom == null ? null : cleansedFrom.trim();
    }

    /**
     * This method returns the value of the database column routing.source_aet
     *
     * @return the value of routing.source_aet
     *
     */
    public final String getSourceAet() {
	return sourceAet;
    }

    /**
     * This method sets the value of the database column routing.source_aet
     *
     * @param sourceAet the value for routing.source_aet
     *
     */
    public final void setSourceAet(final String sourceAet) {
	this.sourceAet = sourceAet == null ? null : sourceAet.trim();
    }

    /**
     * This method returns the value of the database column routing.mbox_aet
     *
     * @return the value of routing.mbox_aet
     *
     */
    public final String getMboxAet() {
	return mboxAet;
    }

    /**
     * This method sets the value of the database column routing.mbox_aet
     *
     * @param mboxAet the value for routing.mbox_aet
     *
     */
    public final void setMboxAet(final String mboxAet) {
	this.mboxAet = mboxAet == null ? null : mboxAet.trim();
    }

    /**
     * This method returns the value of the database column routing.dest_aet
     *
     * @return the value of routing.dest_aet
     *
     */
    public final String getDestAet() {
	return destAet;
    }

    /**
     * This method sets the value of the database column routing.dest_aet
     *
     * @param destAet the value for routing.dest_aet
     *
     */
    public final void setDestAet(final String destAet) {
	this.destAet = destAet == null ? null : destAet.trim();
    }
}
