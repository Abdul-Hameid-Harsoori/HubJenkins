/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.beans;

/**
 *
 * @author Manjut
 */
public class Series {

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getSeriesDescription() {
        return seriesDescription;
    }

    public void setSeriesDescription(String seriesDescription) {
        this.seriesDescription = seriesDescription;
    }

    public String getSeriesInstanceUID() {
        return seriesInstanceUID;
    }

    public void setSeriesInstanceUID(String seriesInstanceUID) {
        this.seriesInstanceUID = seriesInstanceUID;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public void setSeriesNumber(String seriesNumber) {
        this.seriesNumber = seriesNumber;
    }

    public int getSeriesPK() {
        return seriesPK;
    }

    public void setSeriesPK(int seriesPK) {
        this.seriesPK = seriesPK;
    }

    public int getStudyFK() {
        return studyFK;
    }

    public void setStudyFK(int studyFK) {
        this.studyFK = studyFK;
    }

    public String getMigrationStatus() {
        return migrationStatus;
    }

    public void setMigrationStatus(String migrationStatus) {
        this.migrationStatus = migrationStatus;
    }

    
    private int seriesPK;
    private int studyFK;
    private String seriesInstanceUID;
    private String seriesNumber;
    private String seriesDescription;
    private String modality;
    private String migrationStatus;
}
