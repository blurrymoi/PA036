package cz.muni.pa036.logging.layersTests.daoLayer;

import ch.qos.logback.classic.Level;
import cz.muni.pa036.logging.dao.SportDAO;
import cz.muni.pa036.logging.entity.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.fail;

/**
 * @author Kamil Triscik.
 */
@Transactional
public class SportDAOTest extends DAOLayerTests {

    private final String tableName = "sport";

    private final String className = "SportDAOImpl";

    private String[] tables = new String[]{"sportsman", "sport"};
    private String[] values = new String[]{"id", "descript", "name"};

    @Autowired
    SportDAO sportDAO;

    @Test
    public void findByIdTest() throws Exception{
        final String param = "ID";
        final Long value = 1L;
        sportDAO.findById(value);
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
            sportDAO.findById(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findAllTest() throws Exception{
        List<Sport> sports = sportDAO.findAll();
        logFile.reloadLogFile();
        if (isDebugLevelEnabled) {
            testFindAllMethod(layerName, className);
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            Assert.assertFalse(logFile.getLogFileDiff().getLogs(Level.TRACE).isEmpty());
            for (String value : values) {
                String expected = "extracted value ([" + value;
                Assert.assertEquals(sports.size(), logFile.getLogFileDiff().getLogs(Level.TRACE).stream().
                        filter(log -> log.getContent().contains(expected)).count());
            }
        }
    }

    @Test
    public void findByNameTest() throws Exception{
        final String param = "name";
        final String value = sportDAO.findAll().get(0).getName();
        logFile.cleanLogFile();
        sportDAO.findByName(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogs(Level.TRACE), "VARCHAR", value);
        }
    }

    @Test
    public void findByEmptyNameTest() throws Exception{
        final String param = "name";
        final String value = "";
        try {
            sportDAO.findByName("");
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNullNameTest() throws Exception{
        final String param = "name";
        final String value = null;
        try {
            sportDAO.findByName(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void createTest() throws Exception{
        Sport sport = this.getSport();
        logFile.cleanLogFile();
        try {
            sportDAO.create(sport);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", sport.toString());
    }

    @Test
    public void createNullTest() throws Exception{
        try {
            sportDAO.create(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", "null");
    }

    @Test
    public void deleteTest() throws Exception{
        Sport sport = this.getSport();
        logFile.cleanLogFile();
        try {
            sportDAO.delete(sport);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", sport.toString());
    }

    @Test
    public void deleteNullTest() throws Exception{
        try {
            sportDAO.delete(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", "null");
    }

    @Test
    public void updateTest() throws Exception{
        Sport sport = this.getSport();
        logFile.cleanLogFile();
        try {
            sportDAO.update(sport);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", sport.toString());
    }

    @Test
    public void updateNullTest() throws Exception{
        try {
            sportDAO.update(null);
            fail(nullException);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", "null");
    }

    private Sport getSport() {
        List<Sport> sports = sportDAO.findAll();
        if (!sports.isEmpty()) {
            return sports.get(0);
        }
        Sport newSport = new Sport();
        newSport.setName("new");
        newSport.setDescription("new");
        return newSport;
    }
}
