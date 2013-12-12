/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.beans;

/**
 *
 * @author Manjut
 */
public class Study {

    public Long getPatientFK() {
        return patient_fk;
    }

    public void setPatientFK(Long patientFK) {
        this.patient_fk = patientFK;
    }

    public Long getStudyPK() {
        return study_pk;
    }

    public void setStudyPK(Long studyPK) {
        this.study_pk = studyPK;
    }

    public Long getPatient_fk() {
	return patient_fk;
    }

    public void setPatient_fk(Long patient_fk) {
	this.patient_fk = patient_fk;
    }

    public String getStudy_id() {
	return study_id;
    }

    public void setStudy_id(String study_id) {
	this.study_id = study_id;
    }

    

    private Long study_pk;
    
    private Long patient_fk;
    private String study_id;
   

}
