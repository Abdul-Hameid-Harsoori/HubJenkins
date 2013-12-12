/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.beans;

import com.secant.migration.utility.BaseUtility;
import java.io.File;

/**
 *
 * @author Seyfert
 * 
 */
public class FileInfo {

    public String getCUID() {
        return cuid;
    }

    public void setCUID(String cuid) {
        this.cuid = cuid;
    }

    public File getF() {
        return f;
    }

    public void setF(File f) {
        this.f = f;
    }

    public long getFmiEndPos() {
        return fmiEndPos;
    }

    public void setFmiEndPos(long fmiEndPos) {
        this.fmiEndPos = fmiEndPos;
    }

    public String getIUID() {
        return iuid;
    }

    public void setIUID(String iuid) {
        this.iuid = iuid;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getSeriesuid() {
        return seriesuid;
    }

    public void setSeriesuid(String seriesuid) {
        this.seriesuid = seriesuid;
    }

    public boolean isTransferred() {
        return transferred;
    }

    public void setTransferred(boolean transferred) {
        this.transferred = transferred;
    }

    public String getTSUID() {
        return tsuid;
    }

    public void setTSUID(String tsuid) {
        this.tsuid = tsuid;
    }

    public long getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(long instanceID) {
        this.instanceID = instanceID;
    }

    
    
      
    private File f;
    
    private String cuid;
    private String iuid;
    private String tsuid;
    private long fmiEndPos;
    private String seriesuid;
    private String modality;
    private long length;
    private boolean transferred;
    private long instanceID;
    private String cleansingFilePath;

    public String getCleansingFilePath() {
        return cleansingFilePath;
    }

    public void setCleansingFilePath() {
        //the cleansing path will we the (directory obtained in the constructor + instance_id of the image)
        this.cleansingFilePath = cleansingDirPath + this.instanceID;;
    }
    private String cleansingDirPath;
    
    public FileInfo(File f) throws Exception {
        boolean isDirCreated = false;
        this.f = f;
        this.length = f.length();

        // Get the temporary cleansing directory & add the trailing slash if necessary

        cleansingDirPath = BaseUtility.getTemporaryCleansingDirectory();
     

        if (cleansingDirPath.charAt(cleansingDirPath.length() - 1) != File.separatorChar) {
            cleansingDirPath += File.separator;
        }
        File cleansingDir = new File(cleansingDirPath);
        
        if (!cleansingDir.exists()) {
            isDirCreated = cleansingDir.mkdirs();
        }
        if (!isDirCreated && !cleansingDir.exists()) {
            throw new Exception("Unabe to create matrix.cleanse.temp.dir");
        }
         //        this.cleanseFileLocation = cleansingDirPath + f.getName();

    }
}
