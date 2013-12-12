/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.threads;

import com.secant.migration.beans.FileInfo;
import static com.secant.migration.Enums.Constants.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.SimpleDcmElement;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.VR;
import org.dcm4che2.io.*;
import org.dcm4che2.util.TagUtils;

/**
 *
 * @author Seyfert
 *
 * Takes care of the whole image cleansing process
 * Compute the  cleansing history
 * Ask the StudyMigrator thread to hold back an image from migration,if it fails cleansing
 * 
 */
public class StudyCleasing {

    private Log log = LogFactory.getLog(StudyCleasing.class);
    private boolean headerModified = false;
    StringBuffer privateTagData;
    private int privateTagForOldData = 0x78610010;
    private int privateTagPvtCreator = 0x78611010;
    private ArrayList<String> failedInCleansing = new ArrayList<String>();
    private String errorMessage = null;
    private Map<Integer, String> cleansingData;

    public boolean cleanStudyFile(FileInfo file) {
        return cleanStudy(file);
    }

    private boolean cleanStudy(FileInfo fileInfo) {

        File imageFile = fileInfo.getF();
        String orignalFileLocation = imageFile.getAbsolutePath();
        
        String tempCleansingLocation = fileInfo.getCleansingFilePath();
        
        
        log.info("The temporary cleansing location :: " + tempCleansingLocation);
        boolean cleansed = false;
     
        try {
            log.info("Started cleansing of image : [" + orignalFileLocation + "]");

                DicomInputStream inStream = null;
                DicomOutputStream out = null;
                headerModified = false;
                privateTagData = null;
                try {
                    privateTagData = new StringBuffer("Data prior to modified image header was : ");
                    //processingFilePath = imageFile.getAbsolutePath();

                    inStream = new DicomInputStream(imageFile);
                    DicomObject dcmObj = inStream.readDicomObject();
                    Iterator<Integer> dcmTagItr = cleansingData.keySet().iterator();
                    Integer dcmTag;
                    while (dcmTagItr.hasNext()) {
                        dcmTag = dcmTagItr.next();
                        if (REMOVE.equals(cleansingData.get(dcmTag))) {
                            dcmObj.remove(dcmTag);
                            privateTagData.append("Removed tag ").append(TagUtils.toString(dcmTag)).append(
                                    "...");
                            log.info("Removed Tag : [" + TagUtils.toString(dcmTag) + "]");
                            headerModified = true;
                        } else if (Tag.PatientName == dcmTag && "^".equals(cleansingData.get(dcmTag))) {
                            modifyDcmHeaderField(dcmObj, dcmTag, TagUtils.toString(dcmTag), dcmObj.getString(dcmTag).
                                    replaceAll(" ", "^"),
                                    imageFile, true);
                        } else {
                            modifyDcmHeaderField(dcmObj, dcmTag, TagUtils.toString(dcmTag), cleansingData.get(dcmTag),
                                    imageFile, true);
                        }
                    }

                    if (headerModified) {
                        log.info(privateTagData.toString());


                        /* Case 1 : No private tag present: Add PrivateTag inside the image header to store data prior to modified image header
                         * Case 2 : Private tag present: Append the old contents to the new tag contents , to create a history of how the
                         * headers changed through different runs of migration
                         */

                        DicomElement dcmElementPvtTag = dcmObj.get(privateTagPvtCreator);
                        if (dcmElementPvtTag != null) {
                            log.info("Private tag already present in the image.");
                            String historyPvtTag = new String(dcmElementPvtTag.getBytes());
                            historyPvtTag.replaceAll("Data prior to modified image header was :", " ");
                            log.info("Pvt creator tag :: " + historyPvtTag);
                            Date date = new Date();
                            historyPvtTag = " ,Timestamp: " + date.toString() + " ### " + historyPvtTag;
                            log.info("The current contents of the private tag :: " + historyPvtTag);
                            privateTagData.append(historyPvtTag);
                            log.info("Final pvt tag data :: " + privateTagData.toString());
                        }

                        dcmObj.putString(dcmObj.resolveTag(privateTagForOldData, "Secant Cleansing", true), VR.LT,
                                privateTagData.toString());
                        log.info("private tag added");
                       
                    }
                        
                        out = new DicomOutputStream(new FileOutputStream(new File(fileInfo.getCleansingFilePath())));
                        out.writeDicomFile(dcmObj);
                        log.info("dicomobject output written to file: "+ fileInfo.getCleansingFilePath());
                } 
                catch (OutOfMemoryError oom) {
                    log.info("Error while cleansing file " + orignalFileLocation + " : " + oom.getMessage()
                            + " ::" + oom);
                    failedInCleansing.add(orignalFileLocation);
                }
                catch (Exception e) {
                    log.info("Error while cleansing file " + orignalFileLocation + " : " + e.getMessage()
                            + " ::" + e);
                    failedInCleansing.add(orignalFileLocation);
                }  finally {

                    try {
                        if (inStream != null) {
                            inStream.close();
                            log.info("input file closed");
                        }
                        if (out != null) {
                            out.close();
                            log.info("output file closed");
                        }
                        log.info("file closed");
                    } catch (IOException ioE) {
                        log.info("Error in closing Stream while processing " + orignalFileLocation + " : "
                                + ioE.getMessage() + " ::" + ioE);
                    }
                }
            
            log.info("Image failed on cleansing ::  " + (failedInCleansing.size() > 0 ? "YES" : "NO"));
            if (failedInCleansing.size() > 0) {
                cleansed = false;
            } else {
                cleansed = true;
            }
            failedInCleansing.clear();
            log.info("Completed cleansing of images file [" + orignalFileLocation + "]");
        } catch (Exception exception) {
            errorMessage = "Error while cleansing file " + orignalFileLocation + " : " + exception.getMessage()
                    + " ::" + exception;
            log.info(errorMessage + " : " + exception);
            cleansed = false;
        }
        return cleansed;
    }

    // Cleanse a field iff it's current value is different from the one obtained from the SOT(RIS) data
    private boolean modifyDcmHeaderField(DicomObject dicomObj, int tagNumber, String fldName, String newContent, File f,
            boolean addTagIfNotFound) {
        log.info("Modifying header for file :: " + f.getAbsolutePath());
        boolean modifiedHeader = false;
        String oldContent = null;
        if (dicomObj == null) {
            return modifiedHeader;
        }

        DicomElement dcmElmt = dicomObj.get(tagNumber);
        if (dcmElmt != null) {
            oldContent = new String(dcmElmt.getBytes());
            if (!trimString(oldContent).equalsIgnoreCase(trimString(newContent))) {
                log.info("Replacing '" + fldName + "' from [" + oldContent + "] to new content [" + newContent
                        + "]");

                dicomObj.remove(tagNumber);
                log.info("Removed " + fldName + " from dcmObj.");

                dicomObj.add(new SimpleDcmElement(dcmElmt.tag(), dcmElmt.vr(), dcmElmt.bigEndian(),
                        trimString(newContent).getBytes(), null));

                log.info("Added " + fldName + " for dcmObj.");

                modifiedHeader = true;
                headerModified = true;
                privateTagData.append(fldName + "=" + oldContent + "  ");

            } else {
                log.info("Not required cleansing since Image header and database have same " + fldName + "="
                        + oldContent);
            }
        } else if (addTagIfNotFound) {
            dicomObj.putString(tagNumber, null, trimString(newContent));
            modifiedHeader = true;
            headerModified = true;
            privateTagData.append(" ");
            privateTagData.append(fldName);
            privateTagData.append(" Not available ");
            log.info("Dicom Element added for " + fldName + " with value <" + newContent + "> [" + TagUtils.toString(
                    tagNumber)
                    + "] since it was not found in image file " + f.getAbsolutePath());
        } else {
            log.info("Dicom Element for " + fldName + " [" + TagUtils.toString(tagNumber)
                    + "] not found in image file "
                    + f.getAbsolutePath());
        }
        return modifiedHeader;
    }

    private String trimString(String s) {
        if (s == null) {
            return "";
        } else {
            return s.trim();
        }
    }

    public Map<Integer, String> getCleansingData() {
        return cleansingData;
    }

    public void setCleansingData(Map<Integer, String> cleansingData) {
        this.cleansingData = cleansingData;
    }
}
