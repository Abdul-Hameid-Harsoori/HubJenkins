package com.secant.dcm.dump.Service;

import com.secant.dcm.dump.App;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.secant.dcm.dump.object.CorruptFile;
import com.secant.dcm.dump.object.ExceptionFile;
import com.secant.dcm.dump.object.Instance;
import com.secant.dcm.dump.object.Patient;
import com.secant.dcm.dump.object.Series;
import com.secant.dcm.dump.object.Study;
import com.secant.dcm.dump.object.TapesDump;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.StopTagInputHandler;

/**
 * Class which extracts the DICOM header information and puts into DB.
 */
public class DcmExtractor {

    /**
     * Log for logging purpose
     */
    private Log log = LogFactory.getLog(DcmExtractor.class);

    /**
     * Extracts the DICOM header and puts into database
     * 
     * @param tapesDump Tapes info
     * @param dBService Db services
     */
    public final void extractDicom(final TapesDump tapesDump, final DBService dBService) {
	ExceptionFile exceptionFile = null;
	File file = null;
	DicomInputStream dinput = null;
	DicomObject dcm = null;
        long starttime=System.currentTimeMillis();
        long timetoreadfile=System.currentTimeMillis();
        final String considerPatientId = App.properties.getProperty("tag.considerPatientId");

	try {
	    file = new File(tapesDump.getCopiedPath());
	    dinput = new DicomInputStream(file);
	    dinput.setHandler(new StopTagInputHandler(Tag.PixelData));
	    dcm = dinput.readDicomObject();
            timetoreadfile= (System.currentTimeMillis()-starttime);
            log.debug("Time taken to read file " + timetoreadfile);
	    if (dcm != null) {
                boolean condition = false;
                if("YES".equalsIgnoreCase(considerPatientId)){
                    condition = dcm.getString(Tag.PatientID) == null || dcm.getString(Tag.StudyInstanceUID) == null;
                }
                else {
                    condition = dcm.getString(Tag.StudyInstanceUID) == null;

                }
		if (condition) {
		    log.info("ERROR: There is issue with image. Study "
			    + "instance uid not avaialble. " + file.getAbsolutePath());
		    exceptionFile = new ExceptionFile();
		    exceptionFile.setException("NoPatientID || No Study Instance UID");
		    exceptionFile.setPatientId(dcm.getString(Tag.PatientID));
		    exceptionFile.setPatientName(dcm.getString(Tag.PatientName));
		    exceptionFile.setOtherPatientId(dcm.getString(Tag.OtherPatientIDs));
		    exceptionFile.setSex(dcm.getString(Tag.PatientSex));
		    exceptionFile.setDateofbirth(dcm.getString(Tag.PatientBirthDate));
		    exceptionFile.setStudyInstanceUid(dcm.getString(Tag.StudyInstanceUID));
		    exceptionFile.setStudyDate(dcm.getString(Tag.StudyDate));
		    exceptionFile.setStudyTime(dcm.getString(Tag.StudyTime));
		    exceptionFile.setAccessionNumber(dcm.getString(Tag.AccessionNumber));
		    exceptionFile.setInstitutionName(dcm.getString(Tag.InstitutionName));
		    exceptionFile.setReferringPhysicianName(dcm.getString(Tag.ReferringPhysicianName));
		    exceptionFile.setStudyDescription(dcm.getString(Tag.StudyDescription));
		    exceptionFile.setStudyId(dcm.getString(Tag.StudyID));
		    exceptionFile.setModality(dcm.getString(Tag.Modality));
		    exceptionFile.setImageName(file.getName());
		    exceptionFile.setImageLocation(file.getAbsolutePath());
		    dBService.insertExcpetion(exceptionFile);
		    dBService.failureFileStatus(tapesDump);
		} else {
		    final Instance instance = new Instance(null, null, dcm.getString(Tag.SOPInstanceUID),
			    file.getName(), file.getAbsolutePath(), dcm.getString(Tag.SOPClassUID),
			    dcm.getString(Tag.TransferSyntaxUID), dcm.getString(Tag.PhotometricInterpretation),
			    file.length(), dcm.getString(Tag.InstanceNumber),dcm.getString(Tag.PresentationIntentType));


		    final List<Instance> instances = new ArrayList<Instance>();
		    instances.add(instance);
		    final Series series = new Series(null, null, dcm.getString(Tag.SeriesInstanceUID), dcm.getString(
			    Tag.SeriesNumber), dcm.getString(Tag.SeriesDescription), dcm.getString(Tag.Modality),
			    instances);

		    final List<Series> serieses = new ArrayList<Series>();
		    serieses.add(series);

		    final Study study = new Study(null, null, dcm.getString(Tag.StudyInstanceUID),
			    dcm.getString(Tag.StudyDate), dcm.getString(Tag.StudyTime),
			    dcm.getString(Tag.AccessionNumber), dcm.getString(Tag.InstitutionName),
                            dcm.getString(Tag.InstitutionalDepartmentName),
                            dcm.getString(Tag.ReferringPhysicianName),
                            dcm.getString(Tag.RequestingPhysician),dcm.getString(Tag.StudyDescription), dcm.getString(
			    Tag.StudyID), null,
			    serieses);

		    final List<Study> studies = new ArrayList<Study>();
		    studies.add(study);

		    final Patient patient = new Patient(studies, null, dcm.getString(Tag.PatientID), dcm.getString(
			    Tag.PatientName),
			    dcm.getString(Tag.OtherPatientIDs),
			    dcm.getString(Tag.PatientSex), dcm.getString(Tag.PatientBirthDate), tapesDump.getId());
		    dBService.insertDcm(patient);
		    dBService.processFileStatus(tapesDump);
		}
	    }
	} catch (Exception e) {
	    log.info("ERROR: There is issue with image processing.", e);
	    final CorruptFile corruptFile = new CorruptFile(null, tapesDump.getId(), file.getName(),
		    file.getAbsolutePath(), e.getMessage());
	    dBService.insertCurFile(corruptFile);
	    dBService.failureFileStatus(tapesDump);
	} catch (OutOfMemoryError oome) {
	    log.info("ERROR: There is issue with image processing.", oome);
	    final CorruptFile corruptFile = new CorruptFile(null, tapesDump.getId(), file.getName(),
		    file.getAbsolutePath(), oome.getMessage());
	    dBService.insertCurFile(corruptFile);
	    dBService.failureFileStatus(tapesDump);
	} finally {
	    if (dinput != null) {
		try {
                    log.debug("Time taken to insert details in database " +(System.currentTimeMillis()-timetoreadfile));
		    dinput.close();
		} catch (IOException ex) {
		    log.error("There was an issue closing the DICOM input stream.", ex);
		}
	    }
	}
    }
}
