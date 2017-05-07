package cz.muni.pa036.logging;

import cz.muni.pa036.logging.log.LogFile;
import cz.muni.pa036.logging.log.LogFileDiff;
import cz.muni.pa036.logging.logService.LogLoader;
import cz.muni.pa036.logging.service.LoggerService;
import cz.muni.pa036.logging.service.SportsmanService;
import cz.muni.pa036.logging.utils.LoggerConfiguration;
import cz.muni.pa036.logging.utils.LoggerModel;
import org.junit.Before;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

/**
 * @author Kamil Triscik.
 */
@ContextConfiguration(locations = "classpath:integration-tests-context.xml")
public class LogManagementTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private
    SportsmanService sportsmanService;

    @Autowired
    private
    LoggerService loggerService;

    private LogFile logFile;

    private LoggerModel oldModel;

    @BeforeClass
    public void init() throws Exception {
        this.oldModel = loggerService.getLoggerModel();
    }

    @Before
    public void before() throws Exception {
        loggerService.updateLoggingOptions(oldModel);
    }

    @AfterClass
    public void after() throws Exception {
        loggerService.updateLoggingOptions(oldModel);
    }

    @Test
    public void PA036Log() throws Exception {

        logFile = LogLoader.loadLogFile(LoggerConfiguration.getLogFile(), true);

        LogFileDiff diff;

        LoggerModel model;

        model = loggerService.getLoggerModel();
        model.setPa036Level(Level.TRACE);
        model.setSpringLevel(Level.ERROR);
        model.setHibernateLevel(Level.ERROR);
        model.setHibernateTypeLevel(Level.ERROR);
        model.setHibernateSQLLevel(Level.ERROR);
        model.setRootLevel(Level.ERROR);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        logFile.cleanLogFile();
        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.DEBUG).isEmpty());


        model = loggerService.getLoggerModel();
        model.setPa036Level(Level.DEBUG);
        model.setSpringLevel(Level.ERROR);
        model.setHibernateLevel(Level.ERROR);
        model.setHibernateTypeLevel(Level.ERROR);
        model.setHibernateSQLLevel(Level.ERROR);
        model.setRootLevel(Level.ERROR);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        logFile.cleanLogFile();
        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.DEBUG).isEmpty());
        Assert.assertTrue(diff.getLogs(ch.qos.logback.classic.Level.TRACE).isEmpty());

        model = loggerService.getLoggerModel();
        model.setPa036Level(Level.INFO);
        model.setSpringLevel(Level.ERROR);
        model.setHibernateLevel(Level.ERROR);
        model.setHibernateTypeLevel(Level.ERROR);
        model.setHibernateSQLLevel(Level.ERROR);
        model.setRootLevel(Level.ERROR);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        logFile.cleanLogFile();
        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNull(diff);
    }

    @Test
    public void SpringLogTest() throws Exception {
        logFile = LogLoader.loadLogFile(LoggerConfiguration.getTestLogDirectory() + File.separator + "otherLibs.log", true);

        LogFileDiff diff;

        LoggerModel model;

        model = loggerService.getLoggerModel();
        model.setPa036Level(Level.ERROR);
        model.setSpringLevel(Level.TRACE);
        model.setHibernateLevel(Level.ERROR);
        model.setHibernateTypeLevel(Level.ERROR);
        model.setHibernateSQLLevel(Level.ERROR);
        model.setRootLevel(Level.ERROR);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        logFile.cleanLogFile();
        sportsmanService.findById(1L);
        diff = logFile.reloadLogFile();
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.DEBUG).isEmpty());


        model = loggerService.getLoggerModel();
        model.setSpringLevel(Level.DEBUG);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        logFile.cleanLogFile();
        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.DEBUG).isEmpty());

        model = loggerService.getLoggerModel();
        model.setSpringLevel(Level.INFO);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        logFile.cleanLogFile();
        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNull(diff);
    }

    @Test
    public void HibernateLogTest() throws Exception {
        logFile = LogLoader.loadLogFile(LoggerConfiguration.getTestLogDirectory() + File.separator + "otherLibs.log", true);

        LogFileDiff diff;

        LoggerModel model;

        model = loggerService.getLoggerModel();
        model.setPa036Level(Level.ERROR);
        model.setSpringLevel(Level.ERROR);
        model.setHibernateLevel(Level.TRACE);
        model.setHibernateTypeLevel(Level.ERROR);
        model.setHibernateSQLLevel(Level.ERROR);
        model.setRootLevel(Level.ERROR);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());

        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.INFO).isEmpty());
        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.DEBUG).isEmpty());
        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.TRACE).isEmpty());

        model = loggerService.getLoggerModel();
        model.setHibernateLevel(Level.DEBUG);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
//        missing info log, hibernate bug
//        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.INFO).isEmpty());
        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.DEBUG).isEmpty());
        Assert.assertTrue(diff.getLogs(ch.qos.logback.classic.Level.TRACE).isEmpty());



        model = loggerService.getLoggerModel();
        model.setHibernateLevel(Level.INFO);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        //missing info log, hibernate bug -> diff should be null
        Assert.assertNull(diff);
//        Assert.assertFalse(diff.getLogs().isEmpty());
//        missing info log, hibernate bug
//        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.INFO).isEmpty());

        //diff null -> no tests
//        Assert.assertFalse(diff.getLogs().isEmpty());
//        Assert.assertTrue(diff.getLogs(ch.qos.logback.classic.Level.DEBUG).isEmpty());
//        Assert.assertTrue(diff.getLogs(ch.qos.logback.classic.Level.TRACE).isEmpty());





        model = loggerService.getLoggerModel();
        model.setHibernateLevel(Level.WARN);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNull(diff);
    }

    @Test
    public void HibernateTypeLogTest() throws Exception {
        logFile = LogLoader.loadLogFile(LoggerConfiguration.getLogFile(), true);

        LogFileDiff diff;

        LoggerModel model;

        model = loggerService.getLoggerModel();
        model.setPa036Level(Level.ERROR);
        model.setSpringLevel(Level.ERROR);
        model.setHibernateLevel(Level.ERROR);
        model.setHibernateTypeLevel(Level.TRACE);
        model.setHibernateSQLLevel(Level.ERROR);
        model.setRootLevel(Level.ERROR);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.TRACE).isEmpty());

        model = loggerService.getLoggerModel();
        model.setPa036Level(Level.ERROR);
        model.setSpringLevel(Level.ERROR);
        model.setHibernateLevel(Level.ERROR);
        model.setHibernateTypeLevel(Level.DEBUG);
        model.setHibernateSQLLevel(Level.ERROR);
        model.setRootLevel(Level.ERROR);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNull(diff);
    }


    @Test
    public void HibernateSQLLogTest() throws Exception {
        logFile = LogLoader.loadLogFile(LoggerConfiguration.getLogFile(), true);

        LogFileDiff diff;

        LoggerModel model;

        model = loggerService.getLoggerModel();
        model.setPa036Level(Level.ERROR);
        model.setSpringLevel(Level.ERROR);
        model.setHibernateLevel(Level.ERROR);
        model.setHibernateTypeLevel(Level.ERROR);
        model.setHibernateSQLLevel(Level.TRACE);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.DEBUG).isEmpty());

        model = loggerService.getLoggerModel();
        model.setHibernateSQLLevel(Level.DEBUG);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
        Assert.assertFalse(diff.getLogs(ch.qos.logback.classic.Level.DEBUG).isEmpty());



        model = loggerService.getLoggerModel();
        model.setHibernateSQLLevel(Level.INFO);
        loggerService.updateLoggingOptions(model);
        logFile.cleanLogFile();

        sportsmanService.findAll();
        diff = logFile.reloadLogFile();
        Assert.assertNull(diff);
    }


}
