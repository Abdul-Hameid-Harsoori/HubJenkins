package com.secant.dcm.dump.object;

import java.io.Serializable;

public class SourceFile implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String sourceFileName;
    private String filePath;
    private String copiedPath;
    private String status;
    private String startTime;
    private String endTime;
    private String fileSize;
    
    public SourceFile() {
    }

    public SourceFile(final String sourceFileName, final String filePath, final String copiedPath, final String status,
	    final String startTime,String endTime) {
	this.sourceFileName = sourceFileName;
	this.filePath = filePath;
	this.copiedPath = copiedPath;
	this.status = status;
	this.startTime = startTime;
	this.endTime = endTime;        
    }

    public final Long getId() {
	return id;
    }
    public final void setId(final Long id) {
	this.id = id;
    }

    public final String getSourceFileName() {
	return sourceFileName;
    }

    public final void setSourceFileName(final String sourceFileName) {
	this.sourceFileName = sourceFileName == null ? null : sourceFileName.trim();
    }

    public final String getFilePath() {
	return filePath;
    }
    public final void setFilePath(final String filePath) {
	this.filePath = filePath == null ? null : filePath.trim();
    }

    public final String getCopiedPath() {
	return copiedPath;
    }
    public final void setCopiedPath(final String copiedPath) {
	this.copiedPath = copiedPath == null ? null : copiedPath.trim();
    }

    public final String getStatus() {
	return status;
    }
    public final void setStatus(final String status) {
	this.status = status == null ? null : status.trim();
    }

    public final String getStartTime() {
	return startTime;
    }
    public final void setStartTime(final String startTime) {
	this.startTime = startTime;
    }

    public final String getEndTime() {
	return endTime;
    }

    public final void setEndTime(final String endTime) {
	this.endTime = endTime;
    }

    public final String getFileSize() {
	return fileSize;
    }
    public final void setFileSize(final String fileSize) {
	this.fileSize = fileSize == null ? null : fileSize.trim();
    }


}
