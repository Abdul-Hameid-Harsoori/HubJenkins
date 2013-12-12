/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.dao;

import com.secant.migration.beans.Routing;
import com.secant.migration.beans.RunMeOn;

public class StudyFetcher {

    private MigrationDAO migrationDAO;

    public StudyFetcher(MigrationDAO migrationDAO) {
        this.migrationDAO = migrationDAO;
    }

    public synchronized Routing nxtStdToMigrate(RunMeOn runMeOn) {
        return migrationDAO.getRoutingToMigrate(runMeOn);
    }
}
