package cz.muni.pa036.logging.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:loggingConfig.properties")
public class LoggerConfiguration extends ContextAwareBase implements LoggerContextListener, LifeCycle {

    private static final String DEFAULT_LOG_DIR = System.getProperty("user.home");
    private static final String DEFAULT_LOG_FILE = "PA036";

    @Value("${LOG_DIR}")
    private static String logDir;

    @Value("${LOG_FILE}")
    private static String logFile;

    @Value("${ROOT_LEVEL}")
    private static String rootLevel;

    @Value("${PA036_LEVEL}")
    private static String pa036Level;

    @Value("${SPRING_LEVEL}")
    private static String springLevel;

    @Value("${HIBERNATE_LEVEL}")
    private static String hibernateLevel;

    @Value("${HIBERNATE_TYPE_LEVEL}")
    private static String hibernateTypeLevel;

    @Value("${HIBERNATE_SQL_LEVEL}")
    private static String hibernateSQLLevel;

    private boolean started = false;

    @Override
    public void start() {
        if (started) return;

        String actualLogDir = (logDir.length() > 0) ? resolveSystemProperty(logDir) : DEFAULT_LOG_DIR;
        String actualLogFile = (logFile.length() > 0) ? cutTheDot(resolveSystemProperty(logFile)) : DEFAULT_LOG_FILE;

        Context context = getContext();

        context.putProperty("LOG_DIR", actualLogDir);
        context.putProperty("LOG_FILE", actualLogFile);

        context.putProperty("ROOT_LEVEL", resolveOfFallbackToDefault(rootLevel));
        context.putProperty("PA036_LEVEL", resolveOfFallbackToDefault(pa036Level));
        context.putProperty("SPRING_LEVEL", resolveOfFallbackToDefault(springLevel));
        context.putProperty("HIBERNATE_LEVEL", resolveOfFallbackToDefault(hibernateLevel));
        context.putProperty("HIBERNATE_TYPE_LEVEL", resolveOfFallbackToDefault(hibernateTypeLevel));
        context.putProperty("HIBERNATE_SQL_LEVEL", resolveOfFallbackToDefault(hibernateSQLLevel));

        started = true;
    }

    private String cutTheDot(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex >= 0) {
            fileName = fileName.substring(0, dotIndex);
        }
        if (StringUtils.isBlank(fileName)) {
            fileName = DEFAULT_LOG_FILE;
        }
        return fileName;
    }

    String resolveProperties(String value) {
        int index = 0;

        int tokenStartIndex;
        int tokenEndIndex;

        while (index < value.length()) {
            tokenStartIndex = value.indexOf("${", index);
            if (tokenStartIndex >= 0) {
                tokenEndIndex = value.indexOf("}", tokenStartIndex);
            } else {
                break;
            }
            if (tokenEndIndex >= 0) {
                String token = value.substring(tokenStartIndex, tokenEndIndex + 1);
                String replacement = resolveSystemProperty(token);
                value = value.replace(token, replacement);
            } else {
                break;
            }
            index = tokenEndIndex;
        }
        return value;
    }

    private String resolveSystemProperty(String value) {
        if (value.startsWith("${") && value.endsWith("}")) {
            value = value.substring(2, value.length() - 1);
        }
        return System.getProperty(value, "NO_SUCH_PROPERTY");
    }

    private String resolveOfFallbackToDefault(String level) {
        return Level.toLevel(level, Level.DEBUG).toString();
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public boolean isResetResistant() {
        return true;
    }

    @Override
    public void onStart(LoggerContext context) {
    }

    @Override
    public void onReset(LoggerContext context) {
    }

    @Override
    public void onStop(LoggerContext context) {
    }

    @Override
    public void onLevelChange(Logger logger, Level level) {
    }
}
