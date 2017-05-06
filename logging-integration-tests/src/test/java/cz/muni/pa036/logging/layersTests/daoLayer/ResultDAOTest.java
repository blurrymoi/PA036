package cz.muni.pa036.logging.layersTests.daoLayer;

import ch.qos.logback.classic.Level;
import cz.muni.pa036.logging.dao.EventDAO;
import cz.muni.pa036.logging.dao.ResultDAO;
import cz.muni.pa036.logging.dao.SportDAO;
import cz.muni.pa036.logging.dao.SportsmanDAO;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Result;
import cz.muni.pa036.logging.utils.PerformanceUnits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;


import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;


/**
 * @author Kamil Triscik.
 */
@Transactional
public class ResultDAOTest extends DAOLayerTests {

    private final String tableName = "result";

    private final String className = "ResultDAOImpl";

    private String[] tables = new String[]{"sportsman", "sport"};
    private String[] values = new String[]{"id", "event", "note", "position", "sportsma"};


    @Autowired
    ResultDAO resultDAO;

    @Autowired
    SportDAO sportDAO;

    @Autowired
    EventDAO eventDAO;

    @Autowired
    SportsmanDAO sportsmanDAO;

    @Test
    public void findByIdTest() throws Exception {
        final String param = "ID";
        final Long value = 1L;
        resultDAO.findById(value);
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
            resultDAO.findById(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findAllTest() throws Exception{
        logFile.cleanLogFile();
        List<Result> results = resultDAO.findAll();
        logFile.reloadLogFile();
        if (isDebugLevelEnabled) {
            testFindAllMethod(layerName, className);
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
            Arrays.stream(tables).forEach(table -> testSelectPresent(logFile.getLogs(Level.DEBUG), table));
        }
        if (isTraceLevelEnabled) {
            Assert.assertFalse(logFile.getLogFileDiff().getLogs(Level.TRACE).isEmpty());
            for (String value : values) {
                String expected = "extracted value ([" + value;
                int f = (int) logFile.getLogFileDiff().getLogs(Level.TRACE).stream().
                        filter(log -> log.getContent().contains(expected)).count();
                Assert.assertTrue(results.size() <= logFile.getLogFileDiff().getLogs(Level.TRACE).stream().
                        filter(log -> log.getContent().contains(expected)).count(), "Bad count of logs with string \"" + expected + "\"");
            }
        }
    }

    @Test
    public void findBySportsmanTest() throws Exception{
        final String param = "sportsman";
        final Sportsman value = resultDAO.findAll().get(0).getSportsman();
        logFile.cleanLogFile();
        List<Result> ev = resultDAO.findBySportsman(value);
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
    public void findByNullSportsmanTest() throws Exception{
        final String param = "sportsman";
        final String value = null;
        try {
            resultDAO.findBySportsman(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByEventTest() throws Exception{
        final String param = "event";
        final Event value = resultDAO.findAll().get(0).getEvent();
        logFile.cleanLogFile();
        List<Result> ev = resultDAO.findByEvent(value);
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
            resultDAO.findByEvent(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findBySportsmanAndEventTest() throws Exception  {
        final String param = "sportsman";
        final Sportsman value = sportsmanDAO.findAll().get(0);
        final String param2 = "event";
        final Event value2 = eventDAO.findAll().get(0);
        logFile.cleanLogFile();
        Result res = null;
        try {
            res = resultDAO.findBySportsmanAndEvent(value, value2);
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
            for (String value_ : this.values) {
                String expected = "extracted value ([" + value_;
                Assert.assertEquals(logFile.getLogFileDiff().getLogs(Level.TRACE).stream().
                        filter(log -> log.getContent().contains(expected)).count(), (res == null ? 0 : 1) , "Bad count of logs with string \"" + expected + "\"");
            }
        }
    }

    @Test
    public void findByNullSportsmanAndEventTest() throws Exception {
        final String param = "sportsman";
        final Sportsman value = null;
        final String param2 = "event";
        final Event value2 = eventDAO.findAll().get(0);
        logFile.cleanLogFile();
        Result res = null;
        try {
            res = resultDAO.findBySportsmanAndEvent(value, value2);
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
            for (String value_ : this.values) {
                String expected = "extracted value ([" + value_;
                Assert.assertEquals(logFile.getLogFileDiff().getLogs(Level.TRACE).stream().
                        filter(log -> log.getContent().contains(expected)).count(), (res == null ? 0 : 1) , "Bad count of logs with string \"" + expected + "\"");
            }
        }
    }

    @Test
    public void findBySportsmanAndNullEventTest() throws Exception {
        final String param = "sportsman";
        final Sportsman value = sportsmanDAO.findAll().get(0);
        final String param2 = "event";
        final Event value2 = null;
        logFile.cleanLogFile();
        Result res = null;
        try {
            res = resultDAO.findBySportsmanAndEvent(value, value2);
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
            for (String value_ : this.values) {
                String expected = "extracted value ([" + value_;
                Assert.assertEquals(logFile.getLogFileDiff().getLogs(Level.TRACE).stream().
                        filter(log -> log.getContent().contains(expected)).count(), (res == null ? 0 : 1) , "Bad count of logs with string \"" + expected + "\"");
            }
        }
    }

    @Test
    public void findByNullSportsmanAndNullEventTest() throws Exception {
        final String param = "sportsman";
        final Sportsman value = null;
        final String param2 = "event";
        final Event value2 = null;
        logFile.cleanLogFile();
        Result res = null;
        try {
            res = resultDAO.findBySportsmanAndEvent(value, value2);
            fail(nullException);
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
            for (String value_ : this.values) {
                String expected = "extracted value ([" + value_;
                Assert.assertEquals(logFile.getLogFileDiff().getLogs(Level.TRACE).stream().
                        filter(log -> log.getContent().contains(expected)).count(), (res == null ? 0 : 1) , "Bad count of logs with string \"" + expected + "\"");
            }
        }
    }

    @Test
    public void findBySportTest() throws Exception{
        final String param = "sport";
        final Sport value = sportDAO.findAll().get(0);
        logFile.cleanLogFile();
        List<Result> ev = resultDAO.findBySport(value);
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
    public void findByNullSportTest() throws Exception{
        final String param = "sport";
        final String value = null;
        try {
            resultDAO.findBySport(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByPerformanceTest() throws Exception{
        final String param = "performance";
        final Double value = resultDAO.findAll().get(0).getPerformance();
        logFile.cleanLogFile();
        resultDAO.findByPerformance(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogFileDiff().getLogs(Level.TRACE), "BIGNIT", String.valueOf(value));
        }
    }

    @Test
    public void findByNullPerformanceTest() throws Exception{
        final String param = "performance";
        final String value = null;
        try {
            resultDAO.findByPerformance(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByPositionTest() throws Exception{
        final String param = "position";
        final Integer value = resultDAO.findAll().get(0).getPosition();
        logFile.cleanLogFile();
        resultDAO.findByPosition(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogFileDiff().getLogs(Level.TRACE), "BIGNIT", String.valueOf(value));
        }
    }

    @Test
    public void findByNullPositionTest() throws Exception{
        final String param = "position";
        final String value = null;
        try {
            resultDAO.findByPosition(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNoteTest() throws Exception{
        final String param = "note";
        final String value = resultDAO.findAll().get(0).getNote();
        logFile.cleanLogFile();
        resultDAO.findByNote(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogFileDiff().getLogs(Level.TRACE), "BIGNIT", String.valueOf(value));
        }
    }

    @Test
    public void findByNullNoteTest() throws Exception{
        final String param = "note";
        final String value = null;
        try {
            resultDAO.findByNote(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void createTest() throws Exception{
        Result result = this.getResult();
        logFile.cleanLogFile();
        try {
            resultDAO.create(result);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", result.toString());
    }

    @Test
    public void createNullTest() throws Exception{
        try {
            resultDAO.create(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", "null");
    }

    @Test
    public void deleteTest() throws Exception{
        Result result = this.getResult();
        logFile.cleanLogFile();
        try {
            resultDAO.delete(result);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", result.toString());
    }

    @Test
    public void deleteNullTest() throws Exception{
        try {
            resultDAO.delete(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", "null");
    }

    @Test
    public void updateTest() throws Exception{
        Result result = this.getResult();
        logFile.cleanLogFile();
        try {
            resultDAO.update(result);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", result.toString());
    }

    @Test
    public void updateNullTest() throws Exception{
        try {
            resultDAO.update(null);
            fail(nullException);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", "null");
    }

    private Result getResult() {
        List<Result> results = resultDAO.findAll();
        if (!results.isEmpty()) {
            return results.get(0);
        }
        Result newResult = new Result();
        newResult.setSportsman(null);
        newResult.setPosition(1);
        newResult.setPerformanceUnit(PerformanceUnits.valueOf(""));
        newResult.setPerformance(1.1);
        newResult.setNote("new");
        newResult.setEvent(null);
        return newResult;
    }
}
