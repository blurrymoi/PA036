package cz.muni.pa036.logging.layersTests.daoLayer;

import ch.qos.logback.classic.Level;
import cz.muni.pa036.logging.dao.ResultDAO;
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


import java.util.List;
import java.util.Arrays;


/**
 * @author Kamil Triscik.
 */
@Transactional
public class ResultDAOTest extends DAOLayerTests {

    private final String tableName = "result";

    private final String className = "ResultDAOImpl";

    private String[] tables = new String[]{"sportsman", "sport"};

    @Autowired
    ResultDAO resultDAO;

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
        List<Result> results = resultDAO.findAll();
        logFile.reloadLogFile();
        if (isDebugLevelEnabled) {
            testFindAllMethod(layerName, className);
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
            Arrays.stream(tables).forEach(table -> testSelectPresent(logFile.getLogs(Level.DEBUG), table));
        }
        if (isTraceLevelEnabled) {
            Assert.assertFalse(logFile.getLogFileDiff().getLogs(Level.TRACE).isEmpty());
            Assert.assertEquals(results.size(), logFile.getLogFileDiff().getLogs(Level.TRACE).stream().
                    filter(log -> log.getContent().contains("extracted value ([id1_0_]")).count());
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
    public void findBySportsmanAndEventTest()  {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByNullSportsmanAndEventTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findBySportsmanAndNullSportsmanTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByNullSportsmanAndNullEventTest() {
        Assert.fail("Not implemented");
    }

//    @Test
//    public void findBySportTest() throws Exception{
//        final String param = "sport";
//        final Sport value = resultDAO.findAll().get(0).getSport();
//        logFile.cleanLogFile();
//        List<Result> ev = resultDAO.findBySport(value);
//        logFile.reloadLogFile();
//        super.testFindByParamsMethod(layerName, className, param, value.toString());
//        if (isDebugLevelEnabled) {
//            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
//        }
//        if (isTraceLevelEnabled) {
//            super.testBindingParameter(logFile.getLogFileDiff().getLogs(Level.TRACE), "BIGNIT", String.valueOf(value.getId()));
//        }
//    }

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
        final String param = "performence";
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
        final String param = "performence";
        final String value = null;
        try {
            resultDAO.findByPerformance(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {
        }
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
            fail(nullException);
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
