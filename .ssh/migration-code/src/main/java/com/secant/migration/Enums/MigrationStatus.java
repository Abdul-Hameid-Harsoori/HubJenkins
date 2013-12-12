/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.Enums;

/**
 *
 * @author Seyfert
 *
 * The various possible migration status values
 */
public enum MigrationStatus {

    INPROGRESS,
    NO_IMAGES,
    SUCCESS, //both study and instance level
    INCOMPLETE_STUDY,
    PARTLY_MOVED,
    FAILED_TO_SEND,
    NO_STUDY,
    
    PREFETCHED,
    
    RECONCILE_SUCCESS,
    RECONCILED_WITH_LOWER_IMAGE_COUNT,
    NOT_FOUND_AT_DESTINATION,

    SKIPPED_IN_STRICT_MODE,
    
    MISSING_MASTER_COUNT,
    
    MISSING_IMAGE,//instance level
    CLEANSING_FAILED //instance level
}
