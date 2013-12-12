package com.secant.dcm.dump.dao.impl;

import com.secant.dcm.dump.dao.ResetDBDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * DAO implementation for reset DB
 */
public class ResetDBDAOImpl extends SqlMapClientDaoSupport implements ResetDBDAO {

    @Override
    public final void resetDB() throws DataAccessException {
	//getSqlMapClientTemplate().update("resetInprogress");
	//getSqlMapClientTemplate().delete("deleteInprogress");
    }

    public void resetForFailure() throws DataAccessException {
       // getSqlMapClientTemplate().update("resetForFailure");
    }
}
