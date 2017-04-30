package cz.muni.pa036.logging.layersTests.daoLayer;

import ch.qos.logback.classic.Level;
import cz.muni.pa036.logging.dao.ResultDAO;
import cz.muni.pa036.logging.entity.Sportsman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

import javax.xml.transform.Result;

/**
 * @author Kamil Triscik.
 */
@Transactional
public class ResultDAOTest extends DAOLayerTests {

    private final String tableName = "result";

    private final String className = "ResultDAOImpl";

    @Autowired
    ResultDAO resultDAO;

    @Test
    public void findByIdTest() throws Exception { Assert.fail("Not implemented"); }

    @Test
    public void findByNullIdTest() { Assert.fail("Not implemented");}

    @Test
    public void findAllTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findBySportsmanTest() {Assert.fail("Not implemented"); }

    @Test
    public void findByNullSportsmanTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByEventTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByNullEventTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findBySportsmanAndEventTest() {
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

    @Test
    public void findBySportTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByNullSportTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByPerformanceTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByNullPerformanceTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByPositionTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByNullPositionTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByNoteTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void findByNullNoteTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void createTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void createNullTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void deleteTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void deleteNullTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void updateTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void updateNullTest() {
        Assert.fail("Not implemented");
    }

}
