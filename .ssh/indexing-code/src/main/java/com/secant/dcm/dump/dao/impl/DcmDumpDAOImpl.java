package com.secant.dcm.dump.dao.impl;

import java.util.List;

import com.secant.dcm.dump.dao.DcmDumpDAO;
import com.secant.dcm.dump.object.CorruptFile;
import com.secant.dcm.dump.object.ExceptionFile;
import com.secant.dcm.dump.object.Instance;
import com.secant.dcm.dump.object.Patient;
import com.secant.dcm.dump.object.Series;
import com.secant.dcm.dump.object.Study;
import com.secant.dcm.dump.object.TapesDump;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * DAO implementation class. Doing actual DB operations and calling queries from 
 * iBatis xml queries.
 */
public class DcmDumpDAOImpl extends SqlMapClientDaoSupport implements DcmDumpDAO {

    @Override
    public final boolean insertTapeDump(final TapesDump tapesDump) throws DataAccessException {
	boolean exists = true;
	if (getSqlMapClientTemplate().queryForObject("getFileByPath", tapesDump) == null) {
	    getSqlMapClientTemplate().insert("insertTapeSelective", tapesDump);
	    exists = false;
	}
	return exists;
    }

    @Override
    public final void updateFileStatus(final TapesDump tapesDump) throws DataAccessException {
	getSqlMapClientTemplate().update("updateTapesDumpSelective", tapesDump);
    }

    @Override
    public final void insertExcpetion(final ExceptionFile exceptionFile) throws DataAccessException {
	getSqlMapClientTemplate().insert("insertExcpetionSelective", exceptionFile);
    }

    @Override
    public final void insertCurropted(final CorruptFile corruptFile) throws DataAccessException {
	getSqlMapClientTemplate().insert("insertCorFileSelective", corruptFile);
    }

    @Override
    public final void insertDcm(final Patient patient) throws DataAccessException {
	final Patient ptn = (Patient) getSqlMapClientTemplate().queryForObject("selectPatientByInfo", patient);
	List<Study> studies;
	List<Series> serieses;
	List<Instance> instances;

	if (ptn != null) {
	    studies = patient.getStudies();
	    Study std;
	    Series sers;
	    for (Study study : studies) {
		study.setPatientFk(ptn.getPatientPk());
		std = (Study) getSqlMapClientTemplate().queryForObject("selectStudiesByInfo", study);
		if (std != null) {
		    serieses = study.getSerieses();
		    for (Series series : serieses) {
			series.setStudyFk(std.getStudyPk());
			sers = (Series) getSqlMapClientTemplate().queryForObject("selectSeriesByInfo", series);
			if (sers != null) {
			    instances = series.getInstances();
			    for (Instance instance : instances) {
				instance.setSeriesFk(sers.getSeriesPk());
				insertInstance(instance);
			    }
			} else {
			    insertSeries(series);
			}
		    }
		} else {
		    insertStudy(study);
		}
	    }
	} else {
	    insertPatient(patient);
	}
    }

    /**
     * Inserts patient information in database
     * @param patient Patient info
     */
    private void insertPatient(final Patient patient) {
	getSqlMapClientTemplate().insert("insertPatientSelective", patient);
	final List<Study> studies = patient.getStudies();
	for (Study study : studies) {
	    study.setPatientFk(patient.getPatientPk());
	    insertStudy(study);
	}

    }

    /**
     * Insert Study info in DB.
     * @param study Study info
     */
    private void insertStudy(final Study study) {
	getSqlMapClientTemplate().insert("insertStudySelective", study);
	final List<Series> serieses = study.getSerieses();
	for (Series series : serieses) {
	    series.setStudyFk(study.getStudyPk());
	    insertSeries(series);
	}
    }

    /**
     * Insert series info in DB.
     * @param series Series info
     */
    private void insertSeries(final Series series) {
	getSqlMapClientTemplate().insert("insertSeriesSelective", series);
	final List<Instance> instances = series.getInstances();
	for (Instance instance : instances) {
	    instance.setSeriesFk(series.getSeriesPk());
	    insertInstance(instance);
	}
    }

    /**
     * Insert instance info in DB
     * @param instance Instance info
     */
    private void insertInstance(final Instance instance) {
	getSqlMapClientTemplate().insert("insertInstanceSelective", instance);
    }
}
