/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.beans;

/**
 *
 * @author Manjut
 */
public class RIS {

    private long ris_id;
    private String patient_id;
    private String patient_name;
    private String patient_lname;
    private String patient_fname;
    private String dateofbirth;
    private String sex;
    private String study_uid;
    private String study_description;
    private String study_date;
    private String study_time;
    private String carrier;
    private String accession_number;
    private String study_id;
    private String modality;
    private String institution_name;
    private String institutional_department_name;
    private String facility_location;

    public String getFacility_location() {
        return facility_location;
    }

    public void setFacility_location(String facility_location) {
        this.facility_location = facility_location;
    }
    
    public String getInstitutional_department_name() {
        return institutional_department_name;
    }

    public void setInstitutional_department_name(String institutional_department_name) {
        this.institutional_department_name = institutional_department_name;
    }

    public String getInstitution_name() {
        return institution_name;
    }

    public void setInstitution_name(String institution_name) {
        this.institution_name = institution_name;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getStudy_time() {
        return study_time;
    }

    public void setStudy_time(String study_time) {
        this.study_time = study_time;
    }

    public String getAccession_number() {
        return accession_number;
    }

    public void setAccession_number(String accession_number) {
        this.accession_number = accession_number;
    }

    public String getStudy_id() {
        return study_id;
    }

    public void setStudy_id(String study_id) {
        this.study_id = study_id;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPatient_fname() {
        return patient_fname;
    }

    public void setPatient_fname(String patient_fname) {
        this.patient_fname = patient_fname;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }
    
    public String getPatient_lname() {
        return patient_lname;
    }

    public void setPatient_lname(String patient_lname) {
        this.patient_lname = patient_lname;
    }

    public long getRis_id() {
        return ris_id;
    }

    public void setRis_id(long ris_id) {
        this.ris_id = ris_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStudy_uid() {
        return study_uid;
    }

    public void setStudy_uid(String study_uid) {
        this.study_uid = study_uid;
    }

    public String getStudy_description() {
        return study_description;
    }

    public void setStudy_description(String study_description) {
        this.study_description = study_description;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getStudy_date() {
        return study_date;
    }

    public void setStudy_date(String study_date) {
        this.study_date = study_date;
    }

    
    
}
