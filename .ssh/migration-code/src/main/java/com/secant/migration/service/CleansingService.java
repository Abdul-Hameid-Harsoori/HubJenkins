package com.secant.migration.service;

import com.secant.migration.beans.Routing;
import java.util.Map;

/**
 * Cleansing service to get the data required to cleanse.
 * @author Seyfert
 */
public interface CleansingService {

    /**
     * Gets the cleansing data required to be cleansed.
     * 
     * @param Routing routing. Object holding the study and sot information.
     * @return Map<Integer, String> Tag hex value as key and value to be changed as String.
     */
    Map<Integer, String> getCleansingData(final Routing routing);
}
