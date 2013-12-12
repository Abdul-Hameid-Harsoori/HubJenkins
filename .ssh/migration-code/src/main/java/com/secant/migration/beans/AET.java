/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.beans;

/**
 *
 * @author Seyfert
 *
 * The AET details for migration
 */
public class AET {

    public String getAet() {
        return aet;
    }

    public void setAet(String aet) {
        this.aet = aet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAetURL(){
        return  aet + "@" + ip + ":" +port;
    }
    private long id;
    private String module;
    private String aet;
    private String ip;
    private int port;
}
