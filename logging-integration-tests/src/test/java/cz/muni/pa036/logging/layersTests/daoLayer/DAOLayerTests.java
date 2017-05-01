package cz.muni.pa036.logging.layersTests.daoLayer;

import cz.muni.pa036.logging.layersTests.BasicLayerTest;
import cz.muni.pa036.logging.log.Log;
import cz.muni.pa036.logging.logService.LogLoader;
import cz.muni.pa036.logging.utils.LoggerConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Kamil Triscik.
 */
@ContextConfiguration(locations = "classpath:dao-context.xml")
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public abstract class DAOLayerTests extends BasicLayerTest {

    protected final String layerName = "dao";

    protected Boolean isTraceLevelEnabled;

    @Override
    @BeforeClass
    public void initTest() throws Exception {
        //todo finish path to log file
        logFile = LogLoader.loadLogFile(LoggerConfiguration.getLogFile() + "-test.log", true);
        logFile.cleanLogFile();

        isDebugLevelEnabled = true; //todo load from config
        isTraceLevelEnabled = true; //todo load from config
    }

    protected void testSelectPresent(List<Log> logs, String tableName) {
        final String stringPattern = "hibernate[.]SQL\\s+-\\s+select[A-Za-z0-9_\\s\\S\\n]+from\\s+";
        final Pattern pattern = Pattern.compile(stringPattern + tableName);
        Assert.assertTrue(logs.stream().
                filter(log -> pattern.matcher(log.getContent()).find()).findFirst().isPresent());
    }

    protected void testBindingParameter(List<Log> logs, String type, String value) {
        String stringPattern = "o.h.type.descriptor.sql.BasicBinder - binding parameter [1] as [" + type + "] - [" + value + "]";
        Assert.assertNotEquals(logs.stream().
                filter(log -> log.getContent().contains(stringPattern)).count(), 0);
    }

    protected void testExtractingValue(List<Log> logs, List<Long> values_) {
        List<String> values = new ArrayList<>(values_.size());
        for (Long value : values_) {
//            02:02:40.270 9040  TRACE o.h.t.descriptor.sql.BasicExtractor - extracted value ([id1_0_] : [BIGINT]) - [2]
            values.add("o.h.type.descriptor.sql.BasicBinder - binding parameter [1] as [BIGNIT] - [" + value + "]");
        }
        Assert.assertTrue(logs.stream().map(Log::getContent).collect(Collectors.toCollection(ArrayList::new)).containsAll(values));
    }

    protected void testWrapperValue(List<Log> logs, List<Long> values_) {
        List<String> values = new ArrayList<>(values_.size());
        for (Long value : values_) {
//            02:33:04.828 9055  TRACE org.hibernate.type.CollectionType - Created collection wrapper: [cz.muni.pa036.logging.entity.Sportsman.events#1]
            values.add("o.h.type.descriptor.sql.BasicBinder - binding parameter [1] as [BIGNIT] - [" + value + "]");
        }
        Assert.assertTrue(logs.stream().map(Log::getContent).collect(Collectors.toCollection(ArrayList::new)).containsAll(values));
    }
}
