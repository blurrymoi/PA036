package cz.muni.pa036.logging;

import cz.muni.pa036.logging.dao.EventDAO;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.log.LogFile;
import cz.muni.pa036.logging.log.LogFileDiff;
import cz.muni.pa036.logging.logService.LogLoader;
import cz.muni.pa036.logging.service.EventService;
import cz.muni.pa036.logging.service.EventServiceImpl;
import cz.muni.pa036.logging.utils.LoggerConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doThrow;
import static org.testng.Assert.fail;

/**
 * @author Kamil Triscik.
 */
@ContextConfiguration(locations = "classpath:integration-tests-context.xml")
public class ExceptionsTest extends AbstractTestNGSpringContextTests {

    private LogFile logFile;

    @InjectMocks
    private EventService eventService = new EventServiceImpl();

    @Mock
    private EventDAO eventDAO;

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        //ZUZANA
        //pls edit lines 7-8 in loggingConfig.properties

        //load log file a and initialize(clean) it
        logFile = LogLoader.loadLogFile(LoggerConfiguration.getLogFile().replace(".log", "-test.log"), true);
        logFile.cleanLogFile();
    }

    @Test(expectedExceptions = CreateException.class)
    public void createNullObjectExceptionThrowTest() {
        //initialize mockito for this method
        //in case of calling create method with null argument
        //on eventDAO via eventService throw CreateException
        doThrow(new CreateException(null, null, null))
                .when(eventDAO)
                .create(null);
        eventService.create(null);
    }

    @Test
    public void createFindByNullObjectExceptionThrowLogTest() throws Exception {
        //initialize mockito for this method
        //in case of calling findByAdmin method with null argument
        //on eventDAO via EventService throw FindByException
        doThrow(new FindByException(null, null, null, null))
                .when(eventDAO)
                .findByAdmin(null);

        //we have initialized log file.
        //before test we should clean our log file, log file is empty
        logFile.cleanLogFile();
        try {
            //call method
            eventService.findByAdmin(null);
            fail("Expected FindByException due to null argument!");
        } catch (FindByException e) {}
        //now we reload logFile object and get new logs
        //diff contain new logs
        LogFileDiff diff = logFile.reloadLogFile();

        //check that there are new logs, file is not empty anymore
        Assert.assertNotEquals(0, logFile.getFileSize());
        Assert.assertNotNull(diff); //another check that there are new logs
        Assert.assertFalse(diff.getLogs().isEmpty()); //another check that there are new logs
        //here should be some check for exception log
        //but currently this exception log is missing -> bug
    }

}
