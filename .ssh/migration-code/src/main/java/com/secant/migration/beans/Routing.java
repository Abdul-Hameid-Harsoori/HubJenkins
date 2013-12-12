package com.secant.migration.beans;

public class Routing {

    private Long routingID;
    private Long studyFK;
    private Long sotFK;
    private long masterImageCount;
    private String totalImgToMig;
    private String sentImages;
    private String failedToSndImg;
    private String senderStartTime;
    private String senderEndTime;
    private int sendRetryCount;
    private String senderStatus;
    private String cleansedFrom;
    private long mboxAET = 0;
    private long noOfImagesInStudy;
    private long filesSent;
    private long failToSend;
    private long sourceAET = 0;
    private long destAET = 0;
    private String isMultipleStudy;
    private String studyInstanceUid;
    private String accessionNumber;
    private String history;
    private String reconcileStatus;
    private String migrationPriority;

    public long getMasterImageCount() {
        return masterImageCount;
    }

    public void setMasterImageCount(long masterImageCount) {
        this.masterImageCount = masterImageCount;
    }

    public String getMigrationPriority() {
        if (migrationPriority == null) {
            migrationPriority = "0";
        }
        return migrationPriority;
    }

    public void setMigrationPriority(String migrationPriority) {
        this.migrationPriority = migrationPriority;
    }

    public String getReconcileStatus() {
        return reconcileStatus;
    }

    public void setReconcileStatus(String reconcileStatus) {
        this.reconcileStatus = reconcileStatus;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getStudyInstanceUid() {
        return studyInstanceUid;
    }

    public void setStudyInstanceUid(String studyInstanceUid) {
        this.studyInstanceUid = studyInstanceUid;
    }

    public String getIsMultipleStudy() {
        return isMultipleStudy;
    }

    public void setIsMultipleStudy(String isMultipleStudy) {
        this.isMultipleStudy = isMultipleStudy;
    }

    public Long getSotFK() {
        return sotFK;
    }

    public void setSotFK(Long risFK) {
        this.sotFK = risFK;
    }

    public Long getStudyFK() {
        return studyFK;
    }

    public void setStudyFK(Long studyFK) {
        this.studyFK = studyFK;
    }

    public String getCleansedFrom() {
        return cleansedFrom;
    }

    public void setCleansedFrom(String cleansedFrom) {
        this.cleansedFrom = cleansedFrom;
    }

    public String getFailedToSndImg() {
        return failedToSndImg;
    }

    public void setFailedToSndImg(String failedToSndImg) {
        this.failedToSndImg = failedToSndImg;
    }

    public long getMboxAET() {
        return mboxAET;
    }

    public void setMboxAET(long mboxAET) {
        this.mboxAET = mboxAET;
    }

    public Long getRoutingID() {
        return routingID;
    }

    public void setRoutingID(Long routingID) {
        this.routingID = routingID;
    }

    public int getSendRetryCount() {
        return sendRetryCount;
    }

    public void setSendRetryCount(int sendRetryCount) {
        this.sendRetryCount = sendRetryCount;
    }

    public String getSenderEndTime() {
        return senderEndTime;
    }

    public void setSenderEndTime(String senderEndTime) {
        this.senderEndTime = senderEndTime;
    }

    public String getSenderStartTime() {
        return senderStartTime;
    }

    public void setSenderStartTime(String senderStartTime) {
        this.senderStartTime = senderStartTime;
    }

    public String getSenderStatus() {
        return senderStatus;
    }

    public void setSenderStatus(String senderStatus) {
        this.senderStatus = senderStatus;
    }

    public String getSentImages() {
        return sentImages;
    }

    public void setSentImages(String sentImages) {
        this.sentImages = sentImages;
    }

    public String getTotalImgToMig() {
        return totalImgToMig;
    }

    public void setTotalImgToMig(String totalImgToMig) {
        this.totalImgToMig = totalImgToMig;
    }

    public long getDestAET() {
        return destAET;
    }

    public void setDestAET(long destAET) {
        this.destAET = destAET;
    }

    public long getFailToSend() {
        return failToSend;
    }

    public void setFailToSend(long failToSend) {
        this.failToSend = failToSend;
    }

    public long getFilesSent() {
        return filesSent;
    }

    public void setFilesSent(long filesSent) {
        this.filesSent = filesSent;
    }

    public long getNoOfImagesInStudy() {
        return noOfImagesInStudy;
    }

    public void setNoOfImagesInStudy(long noOfImagesInStudy) {
        this.noOfImagesInStudy = noOfImagesInStudy;
    }

    public long getSourceAET() {
        return sourceAET;
    }

    public void setSourceAET(long sourceAET) {
        this.sourceAET = sourceAET;
    }
}
