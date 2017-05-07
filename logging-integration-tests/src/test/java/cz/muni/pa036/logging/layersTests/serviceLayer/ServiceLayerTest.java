package cz.muni.pa036.logging.layersTests.serviceLayer;

import cz.muni.pa036.logging.layersTests.BasicLayerTest;
import cz.muni.pa036.logging.logService.LogLoader;
import cz.muni.pa036.logging.utils.LoggerConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.slf4j.event.Level;
/**
 * @author Kamil Triscik
 */
@ContextConfiguration(locations = "classpath:service-context.xml")
public class ServiceLayerTest extends BasicLayerTest {

    protected final String layerName = "service";

    @Override
    @BeforeClass
    public void initTest() throws Exception {
        logFile = LogLoader.loadLogFile(LoggerConfiguration.getLogFile(), true);
        logFile.cleanLogFile();

        isDebugLevelEnabled = LoggerConfiguration.getLoggerModel().getPa036Level() == Level.DEBUG
                || LoggerConfiguration.getLoggerModel().getPa036Level() == Level.TRACE;
    }

}
