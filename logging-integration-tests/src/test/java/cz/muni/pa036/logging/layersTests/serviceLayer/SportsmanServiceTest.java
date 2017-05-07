package cz.muni.pa036.logging.layersTests.serviceLayer;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.log.LogFileDiff;
import cz.muni.pa036.logging.service.EventService;
import cz.muni.pa036.logging.service.SportsmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.List;

import static org.testng.Assert.fail;

/**
 * @author Kamil Triscik.
 */
@Transactional
public class SportsmanServiceTest extends ServiceLayerTest {

    private final String className = "SportsmanServiceImpl";

    @Autowired
    SportsmanService sportsmanService;

    @Test
    public void findByIdTest() throws Exception{
        final String param = "ID";
        final Long value = 1L;
        sportsmanService.findById(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullIdTest() throws Exception{
        final String param = "ID";
        final String value = null;
        try {
            sportsmanService.findById(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findAllTest() throws Exception{
        sportsmanService.findAll();
        logFile.reloadLogFile();
        if (isDebugLevelEnabled) {
            super.testFindAllMethod(layerName, className);
        }
    }

    @Test
    public void findByNameTest() throws Exception{
        final String param = "name";
        final String value = "name";
        sportsmanService.findByName(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByEmptyNameTest() throws Exception{
        final String param = "name";
        final String value = "";
        try {
            sportsmanService.findByName("");
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNullNameTest() throws Exception{
        final String param = "name";
        final String value = null;
        try {
            sportsmanService.findByName(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        if (isDebugLevelEnabled) {
            super.testFindByParamsMethod(layerName, className, param, value);
        }
    }

    @Test
    public void findBySurNameTest() throws Exception{
        final String param = "surname";
        final String value = "surname";
        sportsmanService.findBySurname(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByEmptySurNameTest() throws Exception{
        final String param = "surname";
        final String value = "";
        try {
            sportsmanService.findBySurname("");
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNullSurNameTest() throws Exception{
        final String param = "surname";
        final String value = null;
        try {
            sportsmanService.findBySurname(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByEmailTest() throws Exception{
        final String param = "email";
        final String value = "email";
        sportsmanService.findByEmail(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByEmptyEmailTest() throws Exception{
        final String param = "email";
        final String value = "";
        try {
            sportsmanService.findByEmail("");
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNullEmailTest() throws Exception{
        final String param = "email";
        final String value = null;
        try {
            sportsmanService.findByEmail(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findBySubstringTest() throws Exception {
        final String param = "substring";
        final String value = "random";
        sportsmanService.findBySubstring(value, 1L);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByEmptySubstringTest() throws Exception {
        final String param = "substring";
        final String value = "";
        sportsmanService.findBySubstring(value, 1L);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNullSubstringTest() throws Exception {
        final String param = "substring";
        final String value = null;
        try {
            sportsmanService.findBySubstring(value, 1L);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void createTest() throws Exception{
        Sportsman sportsman = this.getSportsman();
        logFile.cleanLogFile();
        try {
            sportsmanService.create(sportsman);
        } catch (CreateException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", sportsman.toString());
    }

    @Test
    public void createNullTest() throws Exception{
        try {
            sportsmanService.create(null);
            fail(nullException);
        } catch (CreateException e) {}
        LogFileDiff diff = logFile.reloadLogFile();
        Assert.assertNull(diff, "Expected null diff due to. Special create method(generating pss hash, ...). If null, nothing to happened!");
    }

    @Test
    public void deleteTest() throws Exception{
        Sportsman sportsman = this.getSportsman();
        logFile.cleanLogFile();
        try {
            sportsmanService.delete(sportsman);
            fail(nullException);
        } catch (DeleteException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", sportsman.toString());
    }

    @Test
    public void deleteNullTest() throws Exception{
        try {
            sportsmanService.delete(null);
            fail(nullException);
        } catch (DeleteException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", "null");
    }

    @Test
    public void updateTest() throws Exception{
        Sportsman sportsman = this.getSportsman();
        logFile.cleanLogFile();
        try {
            sportsmanService.update(sportsman);
        } catch (UpdateException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", sportsman.toString());
    }

    @Test
    public void updateNullTest() throws Exception{
        try {
            sportsmanService.update(null);
            fail(nullException);
        } catch (UpdateException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", "null");
    }

    private Sportsman getSportsman() {
        List<Sportsman> sportsmans = sportsmanService.findAll();
        if (!sportsmans.isEmpty()) {
            return sportsmans.get(0);
        }
        Sportsman newSportman = new Sportsman();
        newSportman.setBirthDate(Calendar.getInstance());
        newSportman.setEmail("new");
        newSportman.setEvents(null);
        newSportman.setInvitations(null);
        newSportman.setIsManager(false);
        newSportman.setName("new");
        newSportman.setPassword("new");
        newSportman.setSurname("new");
        return newSportman;
    }
}
