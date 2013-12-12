/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secant.dcm.dump.dao;

import com.secant.dcm.dump.object.TapesDump;
import com.secant.dcm.dump.object.UnqTapeName;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * DAO for getting the next tape
 */
public class NextTapeDAO extends SqlMapClientDaoSupport {

    /**
     * Date format for DB date fields.
     */
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Gets the next tape from the database.
     * @return UnqTapeName next tape object
     * @throws DataAccessException If there is an issue accessing DB.
     */
    public final synchronized UnqTapeName getNextTape() throws DataAccessException {
        final UnqTapeName tapeName = (UnqTapeName) getSqlMapClientTemplate().queryForObject("getNextTapesName");
        if (tapeName != null) {
            tapeName.setStatus("INPROGRESS");
            if (tapeName.getStartTime() == null || "".equals(tapeName.getStartTime())) {
                tapeName.setStartTime(df.format(Calendar.getInstance().getTime()));
            }
            updateTapeStatus(tapeName);
        }
        return tapeName;
    }

    public final synchronized TapesDump getTapesDump() throws DataAccessException {
        final TapesDump tapesDump = (TapesDump) getSqlMapClientTemplate().queryForObject("getTapesDump");
        if (tapesDump != null){
            tapesDump.setStatus("INPROGRESS");
            updateTapeDump(tapesDump);
        }
        return tapesDump;
    }

    /**
     * Updates the status of tape.
     * @param unqTapeName Unique tape name object
     * @throws DataAccessException If there is an issue accessing DB.
     */
    public final void updateTapeStatus(final UnqTapeName unqTapeName) throws DataAccessException {
        getSqlMapClientTemplate().update("updateUnqTapeById", unqTapeName);
    }

    private void updateTapeDump(TapesDump tapesDump) {
        getSqlMapClientTemplate().update("updateTapesDumpById", tapesDump);
    }
}
