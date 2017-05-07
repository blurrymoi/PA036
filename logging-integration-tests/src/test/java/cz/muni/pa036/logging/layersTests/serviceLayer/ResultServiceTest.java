package cz.muni.pa036.logging.layersTests.serviceLayer;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Result;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.service.EventService;
import cz.muni.pa036.logging.service.ResultService;
import cz.muni.pa036.logging.service.SportsmanService;
import cz.muni.pa036.logging.utils.PerformanceUnits;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.fail;

/**
 * @author Kamil Triscik.
 */
public class ResultServiceTest extends ServiceLayerTest {

    private final String className = "ResultServiceImpl";

    @Autowired
    ResultService resultService;

    @Autowired
    EventService eventService;

    @Autowired
    SportsmanService sportsmanService;

    @Test
    public void findByIdTest() throws Exception{
        final String param = "ID";
        final Long value = 1L;
        resultService.findById(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullIdTest() throws Exception{
        final String param = "ID";
        final String value = null;
        try {
            resultService.findById(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findAllTest() throws Exception{
        resultService.findAll();
        logFile.reloadLogFile();
        if (isDebugLevelEnabled) {
            super.testFindAllMethod(layerName, className);
        }
    }

    @Test
    public void findBySportsmanTest() throws Exception{
        final String param = "sportsman";
        final Sportsman value = sportsmanService.findAll().get(0);
        logFile.cleanLogFile();
        resultService.findBySportsman(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullSportsmanTest() throws Exception{
        final String param = "sportsman";
        final String value = null;
        try {
            resultService.findBySportsman(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByEventTest() throws Exception{
        final String param = "event";
        Event value = eventService.findAll().get(0);
        logFile.cleanLogFile();
        resultService.findByEvent(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullEventTest() throws Exception{
        final String param = "event";
        final String value = null;
        try {
            resultService.findByEvent(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findBySportsmanAndEventTest() throws Exception{
        final String sportsmanParam = "sportsman";
        final Sportsman sportsmanValue = sportsmanService.findAll().get(0);
        final String eventParam = "event";
        Event eventValue = eventService.findAll().get(0);
        logFile.cleanLogFile();
        resultService.findBySportsmanAndEvent(sportsmanValue, eventValue);
        logFile.reloadLogFile();
        Map<String, String> values = new HashMap<String, String>(2) {{
                put(sportsmanParam, sportsmanValue.toString());
                put(eventParam, eventValue.toString());
        }};
        super.testFindByParamsMethod(layerName, className, values);
    }

    @Test
    public void findByNullSportsmanAndEventTest() throws Exception{
        final String sportsmanParam = "sportsman";
        final String sportsmanValue = null;
        final String eventParam = "event";
        Event eventValue = eventService.findAll().get(0);
        try {
            logFile.cleanLogFile();
            resultService.findBySportsmanAndEvent(null, eventValue);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        Map<String, String> values = new HashMap<String, String>(2) {{
            put(sportsmanParam, sportsmanValue);
            put(eventParam, eventValue.toString());
        }};
        super.testFindByParamsMethod(layerName, className, values);
    }

    @Test
    public void findBySportsmanAndNullEventTest() throws Exception{
        final String sportsmanParam = "sportsman";
        final Sportsman sportsmanValue = sportsmanService.findAll().get(0);
        final String eventParam = "event";
        final String eventValue = null;
        logFile.cleanLogFile();
        try {
            resultService.findBySportsmanAndEvent(sportsmanValue, null);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        Map<String, String> values = new HashMap<String, String>(2) {{
            put(sportsmanParam, sportsmanValue.toString());
            put(eventParam, eventValue);
        }};
        super.testFindByParamsMethod(layerName, className, values);
    }

    @Test
    public void findByNullSportsmanAndNullEventTest() throws Exception{
        final String sportsmanParam = "sportsman";
        final String sportsmanValue = null;
        final String eventParam = "event";
        final String eventValue = null;
        logFile.cleanLogFile();
        try {
            resultService.findBySportsmanAndEvent(null, null);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        Map<String, String> values = new HashMap<String, String>(2) {{
            put(sportsmanParam, sportsmanValue);
            put(eventParam, eventValue);
        }};
        super.testFindByParamsMethod(layerName, className, values);
    }

    @Test
    public void findBySportTest() throws Exception{
        final String param = "sport";
        final Sport value = eventService.findAll().get(0).getSport();
        logFile.cleanLogFile();
        resultService.findBySport(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullSportTest() throws Exception{
        final String param = "sport";
        final String value = null;
        try {
            resultService.findBySport(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByPerformanceTest() throws Exception{
        final String param = "performance";
        final Double value = resultService.findAll().get(0).getPerformance();
        logFile.cleanLogFile();
        resultService.findByPerformance(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullPerformanceTest() throws Exception{
        final String param = "performance";
        final String value = null;
        try {
            resultService.findByPerformance(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByPositionTest() throws Exception{
        final String param = "position";
        final Integer value = resultService.findAll().get(0).getPosition();
        logFile.cleanLogFile();
        resultService.findByPosition(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullPositionTest() throws Exception{
        final String param = "position";
        final String value = null;
        try {
            resultService.findByPosition(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNoteTest() throws Exception{
        final String param = "note";
        final String value = resultService.findAll().get(0).getNote();
        logFile.cleanLogFile();
        resultService.findByNote(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNullNoteTest() throws Exception{
        final String param = "note";
        final String value = null;
        try {
            resultService.findByNote(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void createTest() throws Exception{
        Result result = this.getResult();
        logFile.cleanLogFile();
        try {
            resultService.create(result);
        } catch (CreateException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", result.toString());
    }

    @Test
    public void createNullTest() throws Exception{
        try {
            resultService.create(null);
            fail(nullException);
        } catch (CreateException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", "null");
    }

    @Test
    public void deleteTest() throws Exception{
        Result result = this.getResult();
        logFile.cleanLogFile();
        try {
            resultService.delete(result);
            fail(nullException);
        } catch (DeleteException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", result.toString());
    }

    @Test
    public void deleteNullTest() throws Exception{
        try {
            resultService.delete(null);
            fail(nullException);
        } catch (DeleteException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", null);
    }

    @Test
    public void updateTest() throws Exception{
        Result result = this.getResult();
        logFile.cleanLogFile();
        try {
            resultService.update(result);
        } catch (UpdateException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", result.toString());
    }

    @Test
    public void updateNullTest() throws Exception{
        try {
            resultService.update(null);
            fail(nullException);
        } catch (UpdateException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", "null");
    }

    private Result getResult() {
        List<Result> results = resultService.findAll();
        if (!results.isEmpty()) {
            return results.get(0);
        }
        Result newResult = new Result();
        newResult.setNote("new");
        newResult.setPerformance(1.1);
        newResult.setPerformanceUnit(PerformanceUnits.valueOf(""));
        newResult.setPosition(1);
        newResult.setSportsman(sportsmanService.findAll().get(0));
        return newResult;
    }
}
