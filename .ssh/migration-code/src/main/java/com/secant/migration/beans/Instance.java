/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.secant.migration.beans;

/**
 *
 * @author Seyfert
 */
public class Instance {

    private long instance_id;
    private String image_path;
    private String senderStatus;

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public long getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(long instance_id) {
        this.instance_id = instance_id;
    }

    public String getSenderStatus() {
        return senderStatus;
    }

    public void setSenderStatus(String senderStatus) {
        this.senderStatus = senderStatus;
    }

    

}
