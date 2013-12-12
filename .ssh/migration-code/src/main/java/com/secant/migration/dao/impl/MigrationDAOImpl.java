/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.dao.impl;

import com.secant.migration.Enums.MigrationStatus;
import com.secant.migration.beans.Instance;
import com.secant.migration.beans.Patient;
import com.secant.migration.beans.RIS;
import com.secant.migration.beans.Routing;
import com.secant.migration.beans.Study;
import com.secant.migration.beans.RunMeOn;
import com.secant.migration.dao.MigrationDAO;
import com.secant.migration.utility.BaseUtility;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 *
 * @author Manjut
 */
public class MigrationDAOImpl extends SqlMapClientDaoSupport implements MigrationDAO {

    public Routing getRoutingToMigrate(RunMeOn runMeOn) {
        Routing routing = (Routing) getSqlMapClientTemplate().queryForObject("RoutingToMigrate",runMeOn);
        if (routing != null) {
            //once you get a study for processing , marking it as "INPROGRESS"
            routing.setSenderStatus(MigrationStatus.INPROGRESS.toString());
            routing.setSenderStartTime("now");
            routing.setSourceAET(BaseUtility.sourceAET.getId());
            routing.setDestAET(BaseUtility.destAET.getId());
            routing.setMboxAET(BaseUtility.mboxAET.getId());
            getSqlMapClientTemplate().update("updateRouteStatus", routing);
            routing.setSenderStartTime("done");
            routing.setSourceAET(0);
            routing.setDestAET(0);
        }
        return routing;
    }

    public Study getStudyToMigrate(Long study_pk) {
        return (Study) getSqlMapClientTemplate().queryForObject("StudyToMigrate", study_pk);
    }

    public int getIndexedImageCount(long study_pk) {
        return (Integer) getSqlMapClientTemplate().queryForObject("getIndexedImageCount", study_pk);
    }

    public Patient getPatient(long patient_pk) {
        return (Patient) getSqlMapClientTemplate().queryForObject("getPatient", patient_pk);
    }

    public List<Instance> getInstances(long study_pk) {
        return getSqlMapClientTemplate().queryForList("getInstances", study_pk);
    }

    public List<Instance> getFailedInstances(long study_pk) {
        return getSqlMapClientTemplate().queryForList("getFailedInstances", study_pk);
    }

    public int updateInstanceStatus(Instance instance) {
        return getSqlMapClientTemplate().update("UpdateInstanceStatus", instance);
    }

    public int updateRouteStatus(Routing routing) {
        return getSqlMapClientTemplate().update("updateRouteStatus", routing);
    }

    public int updatePostMigrationReconcileStatus(Routing routing){
        return getSqlMapClientTemplate().update("updatePostMigrationReconcileStatus" , routing);
    }

    public int updateStudyStatus(Study study) {
        return getSqlMapClientTemplate().update("UpdateStudyStatus", study);
    }

    public int getMasterImageCount(Long study_fk){
        return (Integer) getSqlMapClientTemplate().queryForObject("getMasterImageCountFromRouting", study_fk);
    }
    
//    public int getImageCountByStudyID(String study_instance_uid) {
//        return (Integer) getSqlMapClientTemplate().queryForObject("getImageCountByStudyID", study_instance_uid);
//    }
//
//    public int getImageCountByAccnNumber(String accession_number) {
//        return (Integer) getSqlMapClientTemplate().queryForObject("getImageCountByAccnNumber", accession_number);
//    }

    public RIS getRisObject(Long risFK) {
        return (RIS) getSqlMapClientTemplate().queryForObject("getRis", risFK);
    }
}
