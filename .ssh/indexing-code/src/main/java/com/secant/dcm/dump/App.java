package com.secant.dcm.dump;

import java.io.IOException;
import java.util.Properties;

import com.secant.dcm.dump.dao.ResetDBDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Application main class to execute the program.
 */
public class App {

    public static final Properties properties = new Properties();
    /**
     * Main method to execute the program.
     * @param args String arguments
     * @throws IOException This exception is thrown when we don't find properties file.
     */
    public static void main(final String[] args) throws IOException {
        final Log log = LogFactory.getLog(App.class);
        log.info("Starting ......");

        //Get application context of spring.
        final ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        final ResourceLoader resourceLoader = new DefaultResourceLoader();
        final Resource resource = resourceLoader.getResource("classpath:indexing.properties");

        final ResetDBDAO resetDBDAO = (ResetDBDAO) ctx.getBean("resetDBDAO");
        
        //Set properties
        log.info("Load properties.......");
        properties.load(resource.getInputStream());

        final int noThreads = Integer.parseInt(properties.getProperty("number.threads"));
        final String excteWithTapes = properties.getProperty("execute.new.tapes");
        if ("NO".equalsIgnoreCase(excteWithTapes)) {
            resetDBDAO.resetForFailure();
        }else{
           resetDBDAO.resetDB();   
        }        
        final DumpExecutor dumpExecutor = (DumpExecutor) ctx.getBean("dumpExecutor");
        dumpExecutor.dumpFiles(noThreads, ctx, excteWithTapes);
    }
}
