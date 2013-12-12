/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.dao;

import com.secant.migration.beans.Instance;
import com.secant.migration.beans.Patient;
import com.secant.migration.beans.RIS;
import com.secant.migration.beans.Routing;
import com.secant.migration.beans.Study;
import com.secant.migration.beans.RunMeOn;
import java.util.List;

/**
 *
 * @author Manjut
 */
public interface MigrationDAO {

    public Routing getRoutingToMigrate(RunMeOn runMeOn);

    public Study getStudyToMigrate(Long study_pk);

    public int getMasterImageCount(Long study_fk);

    public int getIndexedImageCount(long study_pk);

//    public int getImageCountByStudyID(String study_instance_uid);
//    public int getImageCountByAccnNumber(String accession_number);
    public Patient getPatient(long patient_pk);

    public List<Instance> getInstances(long study_pk);

    public List<Instance> getFailedInstances(long study_pk);

    public int updateRouteStatus(Routing routing);

    public int updatePostMigrationReconcileStatus(Routing routing);

    public int updateStudyStatus(Study study);

    public int updateInstanceStatus(Instance instance);

    public RIS getRisObject(Long risFK);
}
