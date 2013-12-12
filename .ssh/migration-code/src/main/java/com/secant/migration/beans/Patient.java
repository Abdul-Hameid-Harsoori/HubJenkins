/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.beans;

/**
 *
 * @author Manjut
 */
public class Patient {

    private long patient_pk;
    private String patient_id;
    private String patient_name;
    private String other_patient_id;
    private String sex;
    private String dateofbirth;

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getOther_patient_id() {
        return other_patient_id;
    }

    public void setOther_patient_id(String other_patient_id) {
        this.other_patient_id = other_patient_id;
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

    public long getPatient_pk() {
        return patient_pk;
    }

    public void setPatient_pk(long patient_pk) {
        this.patient_pk = patient_pk;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
