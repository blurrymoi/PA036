package cz.muni.pa036.logging.logTests;

import cz.muni.pa036.logging.log.LogFile;
import cz.muni.pa036.logging.log.LogFileDiff;
import cz.muni.pa036.logging.logService.LogLoader;
import cz.muni.pa036.logging.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;

/**
 * @author Kamil Triscik
 * @since 4/1/17
 */
@Transactional
@ContextConfiguration(locations = "classpath:service-context.xml")
public class BasicLogFileTest extends AbstractTestNGSpringContextTests {

    private final String testLogFile = System.getProperty("user.dir") + "/persistence-test.log";

    @Autowired
    SportService sportService;

    @BeforeClass
    public void beforeClass() {
        File[] logFiles = new File(System.getProperty("user.dir")).listFiles((dir, filename) -> filename.endsWith(".log"));
        if (logFiles != null) Arrays.stream(logFiles).forEach(File::delete);
    }

    @Test
    public void CreateLogFileTest() {
        Assert.assertTrue( new File(System.getProperty("user.dir")).listFiles((dir, filename) -> filename.endsWith(".log")).length == 0,
                "{user.dir} should not contain any *.log files!");
        sportService.findAll();
        Assert.assertTrue( new File(System.getProperty("user.dir")).listFiles((dir, filename) -> filename.endsWith(".log")).length != 0,
                "{user.dir} should contain new *.log files!");
    }

    @Test
    public void LogFileChangedTest() {
        sportService.findAll();
        LogFile logFile = null;
        try {
            logFile = LogLoader.loadLogFile(testLogFile);
            sportService.findAll();
            LogFileDiff logFileDiff = logFile.reloadLogFile();
            Assert.assertTrue( logFileDiff.getFileSize() > 0, "Log file diff size should be greater than 0!");
            Assert.assertFalse(logFileDiff.getLogs().isEmpty(), "Log file diff logs should not be empty");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
