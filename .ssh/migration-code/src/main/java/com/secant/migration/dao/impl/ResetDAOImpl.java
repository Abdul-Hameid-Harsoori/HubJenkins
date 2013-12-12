/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.migration.dao.impl;

import com.secant.migration.beans.RunMeOn;
import com.secant.migration.dao.ResetDAO;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 *
 * @author Manjut
 */
public class ResetDAOImpl extends SqlMapClientDaoSupport implements ResetDAO {

    public void resetRouting(RunMeOn runMeOn) throws DataAccessException {
        getSqlMapClientTemplate().update("resetRouting" , runMeOn);
    }
}
