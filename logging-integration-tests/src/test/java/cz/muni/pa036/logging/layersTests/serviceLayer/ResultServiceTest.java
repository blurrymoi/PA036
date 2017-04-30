package cz.muni.pa036.logging.layersTests.serviceLayer;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Result;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.service.EventService;
import cz.muni.pa036.logging.service.InvitationService;
import cz.muni.pa036.logging.service.ResultService;
import cz.muni.pa036.logging.service.SportsmanService;
import cz.muni.pa036.logging.utils.PerformanceUnits;
import org.apache.derby.iapi.services.diag.Performance;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.List;

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
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findAllTest() throws Exception{
        resultService.findAll();
        logFile.reloadLogFile();
        super.testFindAllMethod(layerName, className);
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
        } catch (IllegalArgumentException e) {}
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
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findBySportsmanAndEventTest() throws Exception{
        final String sportsmanparam = "sportsman";
        final Sportsman sportsmanvalue = sportsmanService.findAll().get(0);
        final String eventparam = "event";
        Event eventvalue = eventService.findAll().get(0);
        logFile.cleanLogFile();
        resultService.findBySportsman(sportsmanvalue);
        resultService.findByEvent(eventvalue);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, sportsmanparam, sportsmanvalue.toString());
        super.testFindByParamsMethod(layerName, className, eventparam, eventvalue.toString());
    }

    @Test
    public void findByNullSportsmanAndEventTest() throws Exception{
        final String sportsmanparam = "sportsman";
        final String sportsmanvalue = null;
        final String eventparam = "event";
        Event eventvalue = eventService.findAll().get(0);
        try {
            resultService.findBySportsman(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        logFile.cleanLogFile();
        resultService.findByEvent(eventvalue);
        super.testFindByParamsMethod(layerName, className, sportsmanparam, sportsmanvalue);
        super.testFindByParamsMethod(layerName, className, eventparam, eventvalue.toString());
    }

    @Test
    public void findBySportsmanAndNullSportsmanTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByNullSportsmanAndNullEventTest() throws Exception{
        final String sportsmanparam = "sportsman";
        final String sportsmanvalue = null;
        final String eventparam = "event";
        final String eventvalue = null;
        try {
            resultService.findBySportsman(null);
            resultService.findByEvent(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, sportsmanparam, sportsmanvalue);
        super.testFindByParamsMethod(layerName, className, eventparam, eventvalue);
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
        } catch (IllegalArgumentException e) {}
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
        } catch (IllegalArgumentException e) {}
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
        } catch (IllegalArgumentException e) {}
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
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void createTest() throws Exception{
        Result result = this.getResult();
        logFile.cleanLogFile();
        try {
            resultService.create(result);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", result.toString());
    }

    @Test
    public void createNullTest() throws Exception{
        try {
            resultService.create(null);
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
            resultService.delete(result);
            fail(nullException);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", result.toString());
    }

    @Test
    public void deleteNullTest() throws Exception{
        try {
            resultService.delete(null);
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
            resultService.update(result);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", result.toString());
    }

    @Test
    public void updateNullTest() throws Exception{
        try {
            resultService.update(null);
            fail(nullException);
        } catch (Exception e) {}
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
