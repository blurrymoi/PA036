package cz.muni.pa036.logging.layersTests.serviceLayer;

import cz.muni.pa036.logging.layersTests.BasicLayerTest;
import cz.muni.pa036.logging.logService.LogLoader;
import cz.muni.pa036.logging.utils.LoggerConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

/**
 * @author Kamil Triscik
 */
@ContextConfiguration(locations = "classpath:service-context.xml")
public class ServiceLayerTest extends BasicLayerTest {

    protected final String layerName = "service";

    @Override
    @BeforeClass
    public void initTest() throws Exception {
        //todo finish path to log file
        logFile = LogLoader.loadLogFile(LoggerConfiguration.getLogFile().replace(".log", "-test.log"), true);
        logFile.cleanLogFile();

        isDebugLevelEnabled = true;//todo load from config
    }

}
