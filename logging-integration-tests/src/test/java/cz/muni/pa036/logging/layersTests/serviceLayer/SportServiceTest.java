package cz.muni.pa036.logging.layersTests.serviceLayer;

import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.fail;

/**
 * @author Kamil Triscik.
 */
@Transactional
public class SportServiceTest extends ServiceLayerTest {

    private final String className = "SportServiceImpl";

    @Autowired
    SportService sportService;

    @Test
    public void findByIdTest() throws Exception{
        final String param = "ID";
        final Long value = 1L;
        sportService.findById(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullIdTest() throws Exception{
        final String param = "ID";
        final String value = null;
        try {
            sportService.findById(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findAllTest() throws Exception{
        sportService.findAll();
        logFile.reloadLogFile();
        super.testFindAllMethod(layerName, className);
    }

    @Test
    public void findByNameTest() throws Exception{
        final String param = "name";
        final String value = "name";
        sportService.findByName(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByEmptyNameTest() throws Exception{
        final String param = "name";
        final String value = "";
        try {
            sportService.findByName("");
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
            sportService.findByName(null);
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
            sportService.create(sport);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", sport.toString());
    }

    @Test
    public void createNullTest() throws Exception{
        try {
            sportService.create(null);
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
            sportService.delete(sport);
            fail(nullException);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", sport.toString());
    }

    @Test
    public void deleteNullTest() throws Exception{
        try {
            sportService.delete(null);
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
            sportService.update(sport);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", sport.toString());
    }

    @Test
    public void updateNullTest() throws Exception{
        try {
            sportService.update(null);
            fail(nullException);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", "null");
    }

    private Sport getSport() {
        List<Sport> sports = sportService.findAll();
        if (!sports.isEmpty()) {
            return sports.get(0);
        }
        Sport newSport = new Sport();
        newSport.setDescription("new");
        newSport.setName("new");
        return newSport;
    }
}
