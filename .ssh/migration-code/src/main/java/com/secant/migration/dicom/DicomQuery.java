/*
 *
 * @author Bhupat Parmar
 * Created on Feb 15, 2011;
 *
 * Secant Healthcare. - Copyright 2011
 *
 * Secant Healthcare. (hereafter, the Company) software [both binary and source (if released)](hereafter,
 * the Software) is intellectual property owned by the Company and is the copyright of the Company in all countries
 * in the world and ownership remains with the Company.You are not allowed to distribute the binary and source code
 * (if released) to third parties.  You are not allowed to reverse engineer, disassemble or decompile code, or make
 * any modifications to the binary or source code, remove or alter any trademark, logo, copyright or other proprietary
 * notices, legends, symbols, or labels in the Software. You are not allowed to sub-license the Software or any
 * derivative work based on or derived from the Software.
 *
 * YOU ACKNOWLEDGE AND AGREE THAT THE SOFTWARE IS DELIVERED 'AS IS' WITHOUT WARRANTY AND WITHOUT ANY SUPPORT SERVICES
 * UNLESS AGREED TO OTHERWISE BY THE COMPANY).THE COMPANY MAKES NO WARRANTIES, EITHER EXPRESSED OR IMPLIED, AS TO THE
 * SOFTWARE AND OR ITS DERIVATIVES.
 *
 * IT IS UNDERSTOOD BY YOU THAT THE COMPANY SHALL NOT BE LIABLE FOR ANY LOSS OR DAMAGES THAT MAY ARISE, INCLUDING ANY
 * INDIRECT, SPECIAL, OR CONSEQUENTIAL LOSS OR DAMAGE IN CONNECTION WITH OR ARISING FROM THE PERFORMANCE OR USE OF
 * THE SOFTWARE, INCLUDING FITNESS FOR ANY PARTICULAR PURPOSE.
 *
 * By using or copying this Software, you agree to abide by the copyright laws and all other applicable laws of the
 * United States and the State of Illinois including, but not limited to, export control laws, and the getting rms
 * of this license.You may be held legally responsible for any copyright infringement that is caused or encouraged
 * by your failure to abide by the terms of this notice.
 */
package com.secant.migration.dicom;

import com.secant.migration.Enums.MigrationStatus;
import com.secant.migration.utility.BaseUtility;
import static com.secant.migration.Enums.Constants.*;
import static com.secant.migration.Enums.MigrationStatus.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;
import org.dcm4che2.data.UIDDictionary;
import org.dcm4che2.net.Association;
import org.dcm4che2.net.CommandUtils;
import org.dcm4che2.net.ConfigurationException;
import org.dcm4che2.net.Device;
import org.dcm4che2.net.DimseRSP;
import org.dcm4che2.net.NetworkApplicationEntity;
import org.dcm4che2.net.NetworkConnection;
import org.dcm4che2.net.NewThreadExecutor;
import org.dcm4che2.net.NoPresentationContextException;
import org.dcm4che2.net.TransferCapability;

/**
 *
 * @author Seyfert
 *
 * Configures the Transfer Capability to the AET
 * Does a CFind to the Pacs,in Prefetch enabled mode
 */

public class DicomQuery {

    private Log log = LogFactory.getLog(DicomQuery.class);
    private NetworkApplicationEntity ae = new NetworkApplicationEntity();
    private NetworkApplicationEntity remoteAET = new NetworkApplicationEntity();
    private NetworkConnection remoteConn = new NetworkConnection();
    private NetworkConnection conn = new NetworkConnection();
    private Device device = new Device("DCMQR_DEVICE");
    private Executor executor = new NewThreadExecutor("DCMQR");
    private Association assoc;
    private int priority = 0;
    private DicomObject keys = new BasicDicomObject();
    private int qrLevel;
    private int cancelAfter = Integer.MAX_VALUE;
    private ArrayList<String> privateFind = new ArrayList<String>();

    public DicomQuery() {
    }

    public void initialize() {
        ae.setAETitle(BaseUtility.mboxAET.getAet());
        ae.setNetworkConnection(conn);
        ae.setMaxPDULengthSend(BaseUtility.getSenderPDULength());
        ae.setMaxPDULengthReceive(BaseUtility.getReceiverPDULength());
        remoteAET.setDescription(BaseUtility.cfindAET.getAetURL());
        remoteAET.setAETitle(BaseUtility.cfindAET.getAet());
        remoteAET.setInstalled(true);
        remoteAET.setAssociationAcceptor(true);
        remoteAET.setNetworkConnection(new NetworkConnection[]{remoteConn});

        remoteConn.setHostname(BaseUtility.cfindAET.getIp());
        remoteConn.setPort(BaseUtility.cfindAET.getPort());
        configureTransferCapability();
        ae.setRetrieveRspTimeout(BaseUtility.getConnectionTimeOut());
        ae.setIdleTimeout(BaseUtility.getConnectionTimeOut());
        ae.setDimseRspTimeout(BaseUtility.getConnectionTimeOut());
        conn.setConnectTimeout(BaseUtility.getConnectionTimeOut());
        device.setNetworkApplicationEntity(ae);
        device.setNetworkConnection(conn);
        ae.setAssociationInitiator(true);
        qrLevel = BaseUtility.getQueryLevel();
    }

    public boolean open() {
        boolean connected = false;
        try {
            assoc = ae.connect(remoteAET, executor);
            log.info("Connected to REMOTE AET :: " + remoteAET.getDescription());
            connected = true;
        } catch (ConfigurationException e) {
            log.info("Could not connect to " + remoteAET.getDescription() + "v:: " + e.getMessage());
        } catch (IOException e) {
            log.info("Could not connect to " + remoteAET.getDescription() + " :: " + e.getMessage());
        } catch (InterruptedException e) {
            log.info("Could not connect to " + remoteAET.getDescription() + " :: " + e.getMessage());
        }
        return connected;
    }

    public String query(String siuidAcnNum, Long imgCnt, String preFetchChoice) throws IOException, InterruptedException {
        String studyAvailabilityStatus = MigrationStatus.NOT_FOUND_AT_DESTINATION.toString();
        TransferCapability tc = getFindTransferCapability();
        String cuid = tc.getSopClass();
        String tsuid = selectTransferSyntax(tc);
        DimseRSP rsp = assoc.cfind(cuid, priority, keys, tsuid, cancelAfter);
        String destsiuid = null;
        int destimgCnt = 0;
        while (rsp.next()) {
            DicomObject cmd = rsp.getCommand();
            if (CommandUtils.isPending(cmd)) {
                DicomObject data = rsp.getDataset();
                if (AccNum.equalsIgnoreCase(preFetchChoice)) {
                    destsiuid = data.getString(Tag.AccessionNumber);

                } else if (SIUID.equalsIgnoreCase(preFetchChoice)) {
                    destsiuid = data.getString(Tag.StudyInstanceUID);

                }
                 destimgCnt += data.getInt(Tag.NumberOfStudyRelatedInstances);
                log.debug("c-find result ::" + data.toString());
            }
        }
        if (siuidAcnNum.equalsIgnoreCase(destsiuid)) {
            log.info("Our image count :: " +imgCnt );
            log.info("The destination image count :: " + destimgCnt);
            
            if (imgCnt <= destimgCnt) {
                studyAvailabilityStatus = MigrationStatus.RECONCILE_SUCCESS.toString();
            }else{
                studyAvailabilityStatus = MigrationStatus.RECONCILED_WITH_LOWER_IMAGE_COUNT.toString();
            }
        }
        if (studyAvailabilityStatus.equalsIgnoreCase("NOT_FOUND_AT_DESTINATION")) {
            log.info("Study with " + siuidAcnNum + " is NOT present on destination PACS.");
        }
        return studyAvailabilityStatus;
    }

    public void addKey(int tag, String value) {
        keys.putString(tag, null, value);
    }

    private TransferCapability getFindTransferCapability()
            throws NoPresentationContextException {
        TransferCapability tc;
        if ((tc = selectTransferCapability(privateFind)) != null) {
            return tc;
        }
        if ((tc = getTransferCapability(BaseUtility.getCUID())) != null) {
            return tc;
        }
        throw new NoPresentationContextException(UIDDictionary.getDictionary().prompt(BaseUtility.getCUID()[0])
                + " not supported by " + remoteAET.getAETitle());
    }

    private TransferCapability getTransferCapability(String[] cuid) {
        TransferCapability tc;
        for (int i = 0; i < cuid.length; i++) {
            tc = assoc.getTransferCapabilityAsSCU(cuid[i]);
            if (tc != null) {
                return tc;
            }
        }
        return null;
    }

    private TransferCapability selectTransferCapability(List cuid) {
        TransferCapability tc;
        for (int i = 0, n = cuid.size(); i < n; i++) {
            tc = assoc.getTransferCapabilityAsSCU((String) cuid.get(i));
            if (tc != null) {
                return tc;
            }
        }

        return null;
    }

    private String selectTransferSyntax(TransferCapability tc) {
        String[] tcuids = tc.getTransferSyntax();
        if (Arrays.asList(tcuids).indexOf(UID.DeflatedExplicitVRLittleEndian) != -1) {
            return UID.DeflatedExplicitVRLittleEndian;
        }
        return tcuids[0];
    }

    public void close() {
        try {
            assoc.release(true);
            log.info("Connection released from REMOTE AET :: " + remoteAET.getDescription());
        } catch (InterruptedException e) {
            log.info("Exception releasing connection :: " + e.getMessage());
        }
    }
    private static final String[] NATIVE_LE_TS = {
        UID.ImplicitVRLittleEndian,
        UID.ExplicitVRLittleEndian};

    private void configureTransferCapability() {
        log.debug("::CfindQRThread::configureTransferCapability()::");
        final String[] findCuids = BaseUtility.getSTUDY_CUID();
        final TransferCapability[] tc = new TransferCapability[findCuids.length];
        int i = 0;
        for (int j = 0; j
                < findCuids.length; j++) {
            tc[i++] = new TransferCapability(findCuids[j], NATIVE_LE_TS, TransferCapability.SCU);
        }
        ae.setTransferCapability(tc);
    }

    public void setCfindKeys(Integer[] tagsList) {
        for (Integer tag : tagsList) {
            keys.putString(tag, null, null);
        }
        keys.putString(Tag.QueryRetrieveLevel, null, BaseUtility.getQueryLevelString());
    }
}
