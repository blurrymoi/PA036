package cz.muni.pa036.logging.layersTests;

import ch.qos.logback.classic.Level;
import cz.muni.pa036.logging.log.LogFile;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;

import java.util.Map;

/**
 * @author Kamil Triscik.
 */
public abstract class BasicLayerTest extends AbstractTestNGSpringContextTests {

    protected final String missingLogs = "LogFileDiff should not be null. Probably missing logs.";

    protected final String nullException = "Expected exception due to null param!";

    protected LogFile logFile;

    protected Boolean isDebugLevelEnabled;

    protected abstract void initTest() throws Exception;

    protected void testCUDObject(String layerName, String className, String type, String value) {
        final String expectedPattern = "." + className + " - > Trying to " + type + " [" + value + "]";
        if (isDebugLevelEnabled) {
            containPattern(expectedPattern);
        }
    }

    protected void testFindAllMethod(String layerName, String className) {
        final String expectedPattern = "." + className + " - > Trying to find all {multiple rows allowed}";
            containPattern(expectedPattern);
    }

    protected void testFindByParamsMethod(String layerName, String className, Map<String, String> values) {
        StringBuilder builder = new StringBuilder();
        builder.append(".").append(className).append(" - > Trying to find by params=[");
        int pos = 1;
        for (String name : values.keySet()) {
            builder.append(name).append(":\"").append(values.get(name)).append("\"");
            if (pos != values.size()) {
                builder.append(", ");
            }
            pos++;
        }
        final String expectedPattern = builder.append("]").toString();

        if (isDebugLevelEnabled) {
            containPattern(expectedPattern);
        }
    }

    protected void testFindByParamsMethod(String layerName, String className, final String name, final String value) {
        final String expectedPattern = "." + className + " - > Trying to find by params=[" + name + ":\"" + value + "\"]";
        if (isDebugLevelEnabled) {
            containPattern(expectedPattern);
        }
    }

    private void containPattern(String expectedPattern) {
        Assert.assertNotNull(logFile.getLogFileDiff(), missingLogs);
        Assert.assertEquals(logFile.getLogFileDiff().getLogs(Level.DEBUG)
                .stream().filter(log -> log.getContent().contains(expectedPattern)).count(), 1,
                "Checking pattern \"" + expectedPattern + "\"");
    }

}
