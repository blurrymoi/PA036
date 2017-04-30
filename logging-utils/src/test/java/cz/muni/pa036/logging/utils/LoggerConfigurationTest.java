package cz.muni.pa036.logging.utils;

import org.junit.Assert;
import org.junit.Test;

public class LoggerConfigurationTest {

    private LoggerConfiguration loggerConfiguration = new LoggerConfiguration();

    @Test
    public void whenNoPlaceholders_thenNothingBadHappens() {
        String value = "D://PA036//PA036";
        String result = loggerConfiguration.resolveProperties(value);
        Assert.assertEquals("Nothing got translated", result, value);
    }

    @Test
    public void whenOneValidPlaceholder_thenItGetsTranslated() {
        String value = "${user.dir}//PA036";
        String propValue = System.getProperty("user.dir");
        String result = loggerConfiguration.resolveProperties(value);

        String expectedResult = value.replace("${user.dir}", propValue);
        Assert.assertEquals("${user.dir} got translated", expectedResult, result);
    }

    @Test
    public void whenMultipleValidPlaceholders_thenAllGetsTranslated() {
        String value = "D:${file.separator}PA036${file.separator}PA036";
        String separator = System.getProperty("file.separator");
        String expectedResult = "D:" + separator + "PA036" + separator + "PA036";
        String result = loggerConfiguration.resolveProperties(value);

        Assert.assertEquals("Multiple ${file.separator} got translated", expectedResult, result);
    }

    @Test
    public void whenOneInvalidPlaceholder_thenItGetsTranslatedToDefValue() {
        String value = "${non.existing.property}//PA036";
        String propValue = "NO_SUCH_PROPERTY";
        String result = loggerConfiguration.resolveProperties(value);

        String expectedResult = value.replace("${non.existing.property}", propValue);
        Assert.assertEquals("${non.existing.property} got translated to default value", expectedResult, result);
    }
}