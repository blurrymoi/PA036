package cz.muni.pa036.logging.layersTests.daoLayer;

import ch.qos.logback.classic.Level;
import cz.muni.pa036.logging.dao.SportsmanDAO;
import cz.muni.pa036.logging.entity.Sportsman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.List;
import java.util.Arrays;

import static org.testng.Assert.fail;

/**
 * @author Kamil Triscik.
 */
@Transactional
public class SportsmanDAOTest extends DAOLayerTests {

    private final String tableName = "sportsman";

    private final String className = "SportsmanDAOImpl";

    private String[] tables = new String[]{"sportsman", "sport"};

    @Autowired
    SportsmanDAO sportsmanDAO;

    @Test
    public void findByIdTest() throws Exception{
        final String param = "ID";
        final Long value = 1L;
        sportsmanDAO.findById(value);
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
            sportsmanDAO.findById(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findAllTest() throws Exception{
        List<Sportsman> sports = sportsmanDAO.findAll();
        logFile.reloadLogFile();
        if (isDebugLevelEnabled) {
            testFindAllMethod(layerName, className);
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
            Arrays.stream(tables).forEach(table -> testSelectPresent(logFile.getLogs(Level.DEBUG), table));
        }
        if (isTraceLevelEnabled) {
            Assert.assertFalse(logFile.getLogFileDiff().getLogs(Level.TRACE).isEmpty());
            Assert.assertEquals(sports.size(), logFile.getLogFileDiff().getLogs(Level.TRACE).stream().
                    filter(log -> log.getContent().contains("extracted value ([id1_0_]")).count());
        }
    }

    @Test
    public void findByNameTest() throws Exception{
        final String param = "name";
        final String value = "name";
        sportsmanDAO.findByName(value);
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
            sportsmanDAO.findByName("");
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
            sportsmanDAO.findByName(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findBySurNameTest() throws Exception{
        final String param = "surname";
        final String value = "surname";
        sportsmanDAO.findBySurname(value);
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
    public void findByEmptySurNameTest() throws Exception{
        final String param = "surname";
        final String value = "";
        try {
            sportsmanDAO.findBySurname("");
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNullSurNameTest() throws Exception{
        final String param = "surname";
        final String value = null;
        try {
            sportsmanDAO.findBySurname(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByEmailTest() throws Exception{
        final String param = "email";
        final String value = "email";
        sportsmanDAO.findByEmail(value);
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
    public void findByEmptyEmailTest() throws Exception{
        final String param = "email";
        final String value = "";
        try {
            sportsmanDAO.findByEmail("");
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNullEmailTest() throws Exception{
        final String param = "email";
        final String value = null;
        try {
            sportsmanDAO.findByEmail(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findBySubstringTest() { Assert.fail("Not implemented"); }

    @Test
    public void findByEmptySubstringTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByNullSubstringTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void createTest() throws Exception{
        Sportsman sportsman = this.getSportsman();
        logFile.cleanLogFile();
        try {
            sportsmanDAO.create(sportsman);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", sportsman.toString());
    }

    @Test
    public void createNullTest() throws Exception{
        try {
            sportsmanDAO.create(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", "null");
    }

    @Test
    public void deleteTest() throws Exception{
        Sportsman sportsman = this.getSportsman();
        logFile.cleanLogFile();
        try {
            sportsmanDAO.delete(sportsman);
            fail(nullException);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", sportsman.toString());
    }

    @Test
    public void deleteNullTest() throws Exception{
        try {
            sportsmanDAO.delete(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", "null");
    }

    @Test
    public void updateTest() throws Exception{
        Sportsman sportsman = this.getSportsman();
        logFile.cleanLogFile();
        try {
            sportsmanDAO.update(sportsman);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", sportsman.toString());
    }

    @Test
    public void updateNullTest() throws Exception{
        try {
            sportsmanDAO.update(null);
            fail(nullException);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", "null");
    }

    private Sportsman getSportsman() {
        List<Sportsman> sportsmens = sportsmanDAO.findAll();
        if (!sportsmens.isEmpty()) {
            return sportsmens.get(0);
        }
        Sportsman newSportsman = new Sportsman();
        newSportsman.setSurname("new");
        newSportsman.setPassword("new");
        newSportsman.setName("new");
        newSportsman.setIsManager(false);
        newSportsman.setInvitations(null);
        newSportsman.setEvents(null);
        newSportsman.setEmail("new");
        newSportsman.setBirthDate(Calendar.getInstance());
        return newSportsman;
    }

}
