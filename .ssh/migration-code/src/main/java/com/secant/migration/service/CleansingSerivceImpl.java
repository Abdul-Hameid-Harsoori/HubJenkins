/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.service;

import com.secant.migration.beans.RIS;
import com.secant.migration.beans.Routing;
import com.secant.migration.dao.MigrationDAO;
import java.util.HashMap;
import java.util.Map;
import org.dcm4che2.data.Tag;

/**
 * Default cleansing service implementation.
 * The tags that are to be cleansed must be specified in this class
 * @author Seyfert
 */
public class CleansingSerivceImpl implements CleansingService {

    private MigrationDAO mdao;

    public CleansingSerivceImpl(MigrationDAO mdao) {
        this.mdao = mdao;
    }

    @Override
    public Map<Integer, String> getCleansingData(final Routing routing) {
        RIS ris = mdao.getRisObject(routing.getSotFK());
        Map cleansingData = new HashMap();

        cleansingData.put(Tag.PatientName, ris.getPatient_name());
        if (ris.getPatient_id() != null || (!ris.getPatient_id().equalsIgnoreCase(""))) {
        cleansingData.put(Tag.PatientID, ris.getPatient_id());
        }
        if (ris.getDateofbirth() != null || (!ris.getDateofbirth().equalsIgnoreCase(""))) {
            cleansingData.put(Tag.PatientBirthDate, ris.getDateofbirth());
        }
        if (ris.getSex() != null || (!ris.getSex().equalsIgnoreCase(""))) {
            cleansingData.put(Tag.PatientSex, ris.getSex());
        }
        // cleansingData.put(Tag.StudyInstanceUID,ris.getStudy_uid());
        // cleansingData.put(Tag.InstitutionName,ris.getInstitution_name());
        // cleansingData.put(Tag.InstitutionalDepartmentName,ris.getInstitutional_department_name());
        // cleansingData.put(Tag.Study,ris.getInstitutional_department_name());
        // cleansingData.put(, ris)
        //cleansingData.put(Tag.StationName, "SECANT_GC");
        //cleansingData.put(Tag.StudyStatusID, "remove");

        return cleansingData;
    }
}
