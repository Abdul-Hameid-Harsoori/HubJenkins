/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration;

import com.secant.migration.beans.RunMeOn;
import com.secant.migration.dao.AetDAO;
import com.secant.migration.dao.ResetDAO;
import com.secant.migration.utility.BaseUtility;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 *
 * @author Seyfert Software Solutions LLP
 */
public class App {

    private static Log log = LogFactory.getLog(App.class);

    public static void main(String[] args) throws IOException {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

            MigrationExecutor migrationExecutor = (MigrationExecutor) context.getBean("migrationExecutor");

            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("classpath:migration.properties");
            Properties properties = new Properties();

            //Fetch the settings from the properties file
            log.info("Load properties.......");
            properties.load(resource.getInputStream());

            BaseUtility.prop = properties;
            AetDAO aetDAO = (AetDAO) context.getBean("aetDAO");
            //Set the AETs
            BaseUtility.sourceAET = aetDAO.getAETByModule(BaseUtility.prop.getProperty("matrix.source.aet.module"));
            BaseUtility.mboxAET = aetDAO.getAETByModule(BaseUtility.prop.getProperty("matrix.mbox.aet.module"));
            BaseUtility.destAET = aetDAO.getAETByModule(BaseUtility.prop.getProperty("matrix.dest.aet.module"));
            BaseUtility.cfindAET = aetDAO.getAETByModule(BaseUtility.prop.getProperty("matrix.cfind.aet.module"));

            //in order to reset values , we need to know the current "runMeOn" value
            String runMeOn = BaseUtility.getRunMeOnValue();
            if (runMeOn == null && runMeOn.length() <= 0) {
                runMeOn = "PICKME";
            }
            RunMeOn runMe = new RunMeOn();
            runMe.setRunMeOn(runMeOn);
            log.info("The current run will be on : " + runMe.getRunMeOn() + " status value");

            log.info("Resetting the database...");
            ResetDAO reset = (ResetDAO) context.getBean("resetDAO");
            reset.resetRouting(runMe);

            //no of threds the app will run on
            int numThreads = Integer.parseInt(properties.getProperty("migrator.threads.corepoolsize"));

            // dictates whether all the images of a study are to be moved or only those that failed migration prior to current run

            String migrationLevel = BaseUtility.getMigrationLevel();

            if (migrationLevel == null) {
                migrationLevel = "ALL_INSTANCES";
            }

            log.info("Starting Migration with thread count " + numThreads + " and the migration level is " + migrationLevel);

            migrationExecutor.executeMThreads(numThreads, context, migrationLevel , runMe);

        } catch (Exception e) {
            log.info("Error :: " + e.getMessage());
        }
    }
}
