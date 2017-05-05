package cz.muni.pa036.logging.layersTests.daoLayer;

import ch.qos.logback.classic.Level;
import cz.muni.pa036.logging.dao.InvitationDAO;
import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Invitation;
import cz.muni.pa036.logging.entity.Result;
import cz.muni.pa036.logging.entity.Sportsman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.fail;

/**
 * @author Kamil Triscik.
 */
@Transactional
public class InvitationDAOTest extends DAOLayerTests {

    private final String tableName = "invitation";

    private final String className = "InvitationDAOImpl";

    private String[] tables = new String[]{"sportsman", "sport"};

    @Autowired
    InvitationDAO invitationDAO;

    @Test
    public void findByIdTest() throws Exception{
        final String param = "ID";
        final Long value = 1L;
        invitationDAO.findById(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogs(Level.TRACE), "BIGNIT", String.valueOf(value));
        }
    }

    @Test
    public void findByNullIdTest() throws Exception{
        final String param = "ID";
        final String value = null;
        try {
            invitationDAO.findById(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findAllTest() throws Exception{
        List<Invitation> invitations = invitationDAO.findAll();
        logFile.reloadLogFile();
        if (isDebugLevelEnabled) {
            testFindAllMethod(layerName, className);
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
            Arrays.stream(tables).forEach(table -> testSelectPresent(logFile.getLogs(Level.DEBUG), table));
        }
        if (isTraceLevelEnabled) {
            Assert.assertFalse(logFile.getLogFileDiff().getLogs(Level.TRACE).isEmpty());
            Assert.assertTrue(invitations.size() <= logFile.getLogFileDiff().getLogs(Level.TRACE).stream().
                    filter(log -> log.getContent().contains("extracted value ([id")).count());
        }
    }

    @Test
    public void findByEventTest() throws Exception{
        final String param = "event";
        final Event value = invitationDAO.findAll().get(0).getEvent();
        logFile.cleanLogFile();
        invitationDAO.findByEvent(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogFileDiff().getLogs(Level.TRACE), "BIGNIT", String.valueOf(value.getId()));
        }
    }

    @Test
    public void findByNullEventTest() throws Exception{
        final String param = "event";
        final String value = null;
        try {
            invitationDAO.findByEvent(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByInviteeTest() throws Exception{
        final String param = "invitee";
        final Sportsman value = invitationDAO.findAll().get(0).getInvitee();
        logFile.cleanLogFile();
        invitationDAO.findByInvitee(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogFileDiff().getLogs(Level.TRACE), "BIGNIT", String.valueOf(value.getId()));
        }
    }

    @Test
    public void findByNullInviteeTest() throws Exception{
        final String param = "invitee";
        final String value = null;
        try {
            invitationDAO.findByInvitee(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByEventAndInviteeTest() throws Exception{
        final String param = "event";
        final Event value = invitationDAO.findAll().get(0).getEvent();
        final String param2 = "invitee";
        final Sportsman value2 = invitationDAO.findAll().get(0).getInvitee();
        logFile.cleanLogFile();
        Invitation res = null;
        try {
            res = invitationDAO.findByEventAndInvitee(value, value2);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        Map<String, String> values = new HashMap<String, String>(2){{
            put(param, value.toString());
            put(param2, value2.toString());
        }};

        if (isDebugLevelEnabled) {
            super.testFindByParamsMethod(layerName, className, values);
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            Assert.assertFalse(logFile.getLogFileDiff().getLogs(Level.TRACE).isEmpty());
        }
    }

    @Test
    public void findByNullEventAndInviteeTest() throws Exception{
        final String param = "event";
        final Event value = null;
        final String param2 = "invitee";
        final Sportsman value2 = invitationDAO.findAll().get(0).getInvitee();
        logFile.cleanLogFile();
        Invitation res = null;
        try {
            res = invitationDAO.findByEventAndInvitee(value, value2);
            fail(nullException);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        Map<String, String> values = new HashMap<String, String>(2){{
            put(param, null);
            put(param2, value2.toString());
        }};

        if (isDebugLevelEnabled) {
            super.testFindByParamsMethod(layerName, className, values);
        }
        if (isTraceLevelEnabled) {
            Assert.assertTrue(logFile.getLogFileDiff().getLogs(Level.TRACE).isEmpty());
        }
    }

    @Test
    public void findByEventAndNullInviteeTest() throws Exception{
        final String param = "event";
        final Event value = invitationDAO.findAll().get(0).getEvent();
        final String param2 = "invitee";
        final Sportsman value2 = null;
        logFile.cleanLogFile();
        Invitation res = null;
        try {
            res = invitationDAO.findByEventAndInvitee(value, value2);
            fail(nullException);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        Map<String, String> values = new HashMap<String, String>(2){{
            put(param, value.toString());
            put(param2, null);
        }};

        if (isDebugLevelEnabled) {
            super.testFindByParamsMethod(layerName, className, values);
        }
        if (isTraceLevelEnabled) {
            Assert.assertTrue(logFile.getLogFileDiff().getLogs(Level.TRACE).isEmpty());
        }
    }

    @Test
    public void findByNullEventAndNullInviteeTest() throws Exception{
        final String param = "event";
        final Event value = null;
        final String param2 = "invitee";
        final Sportsman value2 = null;
        logFile.cleanLogFile();
        try {
            invitationDAO.findByEventAndInvitee(value, value2);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        Map<String, String> values = new HashMap<String, String>(2){{
            put(param, null);
            put(param2, null);
        }};

        if (isDebugLevelEnabled) {
            super.testFindByParamsMethod(layerName, className, values);
        }
        if (isTraceLevelEnabled) {
            Assert.assertTrue(logFile.getLogFileDiff().getLogs(Level.TRACE).isEmpty());
        }
    }
}
