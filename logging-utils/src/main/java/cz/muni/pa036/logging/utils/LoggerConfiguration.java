package cz.muni.pa036.logging.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.io.File;

public class LoggerConfiguration extends ContextAwareBase implements LoggerContextListener, LifeCycle {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LoggerConfiguration.class);

    private static final String DEFAULT_LOG_DIR = System.getProperty("user.home");
    private static final String DEFAULT_LOG_FILE = "PA036";

    private static String logDir;
    private static String logFile;

    private static String actualLogDir;
    private static String actualLogFile;

    private static String rootLevel;
    private static String pa036Level;
    private static String springLevel;
    private static String hibernateLevel;
    private static String hibernateTypeLevel;
    private static String hibernateSQLLevel;

    private static String dbsLogStatement;
    private static String dbsLogDestination;
    private static String dbsLoggingDirectory;
    private static String dbsLogFilename;
    private static String dbsLogFilemode;
    private static String dbsRotationAge;
    private static String dbsRotationSize;
    private static String dbsMinMessage;
    private static String dbsMinErrorState;
    private static String dbsLogDuration;
    private static String dbsLogMinDurationStatement;
    private static String dbsLogLinePrefix;

    private boolean started = false;

    @Override
    public void start() {
        if (started) return;

        Properties properties = new Properties();
        try {
            properties.load(LoggerConfiguration.class.getClassLoader().getResourceAsStream("loggingConfig.properties"));

            logDir = properties.getProperty("LOG_DIR");
            logFile = properties.getProperty("LOG_FILE");

            rootLevel = properties.getProperty("ROOT_LEVEL");
            pa036Level = properties.getProperty("PA036_LEVEL");
            springLevel = properties.getProperty("SPRING_LEVEL");
            hibernateLevel = properties.getProperty("HIBERNATE_LEVEL");
            hibernateTypeLevel = properties.getProperty("HIBERNATE_TYPE_LEVEL");
            hibernateSQLLevel = properties.getProperty("HIBERNATE_SQL_LEVEL");

            dbsLogStatement = properties.getProperty("DBS_LOG_STATEMENT");
            dbsLogDestination = properties.getProperty("DBS_LOG_DESTINATION");
            dbsLoggingDirectory = properties.getProperty("DBS_LOGGING_DIRECTORY");
            dbsLogFilename = properties.getProperty("DBS_LOG_FILENAME");
            dbsLogFilemode = properties.getProperty("DBS_LOGFILE_MODE");
            dbsRotationAge = properties.getProperty("DBS_ROTATION_AGE");
            dbsRotationSize = properties.getProperty("DBS_ROTATION_SIZE");
            dbsMinMessage = properties.getProperty("DBS_MIN_MESSAGE");
            dbsMinErrorState = properties.getProperty("DBS_MIN_ERROR_STATE");
            dbsLogDuration = properties.getProperty("DBS_LOG_DURATION");
            dbsLogMinDurationStatement = properties.getProperty("DBS_LOG_MIN_DURATION_STATEMENT");
            dbsLogLinePrefix = properties.getProperty("DBS_LOG_LINE_PREFIX");

        } catch (IOException e) {
            e.printStackTrace();
        }

        actualLogDir = (logDir.length() > 0) ? resolveProperties(logDir) : DEFAULT_LOG_DIR;
        actualLogFile = (logFile.length() > 0) ? cutTheDot(resolveProperties(logFile)) : DEFAULT_LOG_FILE;

        Context context = getContext();

        context.putProperty("LOG_DIR", actualLogDir);
        context.putProperty("LOG_FILE", actualLogFile);

        context.putProperty("ROOT_LEVEL", validateOfFallbackToDefault(rootLevel));
        context.putProperty("PA036_LEVEL", validateOfFallbackToDefault(pa036Level));
        context.putProperty("SPRING_LEVEL", validateOfFallbackToDefault(springLevel));
        context.putProperty("HIBERNATE_LEVEL", validateOfFallbackToDefault(hibernateLevel));
        context.putProperty("HIBERNATE_TYPE_LEVEL", validateOfFallbackToDefault(hibernateTypeLevel));
        context.putProperty("HIBERNATE_SQL_LEVEL", validateOfFallbackToDefault(hibernateSQLLevel));

        started = true;
    }

    public static void setLoggingLevel(String loggerName, Level level) {
        Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
        logger.setLevel(level);
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

    static String validateOfFallbackToDefault(String level) {
        return resolveLevel(level).toString();
    }

    static Level resolveLevel(String level) {
        return Level.toLevel(level, Level.DEBUG);
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
        LOGGER.debug(" << Logging level changed to " + level + " for Logger " + logger.getName() + " >> ");
    }

    public static String getLogFile() {
        return actualLogDir + File.separator + actualLogFile + ".log";
    }

    public static String getTestLogFile() {
        return actualLogDir + File.separator + actualLogFile + "-test.log";
    }

    public static String getTestLogDirectory() {
        return actualLogDir;
    }


    public static void setLoggerModel(LoggerModel model) {

    }

    public static LoggerModel getLoggerModel() {
        LoggerModel model = new LoggerModel();

        model.setLogDir(logDir);
        model.setLogFile(logFile);
        model.setRootLevel(rootLevel);
        model.setPa036Level(pa036Level);
        model.setSpringLevel(springLevel);
        model.setHibernateLevel(hibernateLevel);
        model.setHibernateTypeLevel(hibernateTypeLevel);
        model.setHibernateSQLLevel(hibernateSQLLevel);

        model.setLogStatement(LogStatement.valueOf(dbsLogStatement.toUpperCase()));
        model.setDestination(dbsLogDestination);
        model.setDirectory(dbsLoggingDirectory);
        model.setFileName(dbsLogFilename);
        model.setFileMode(Integer.valueOf(dbsLogFilemode));
        model.setRotationAge(Integer.valueOf(dbsRotationAge));
        model.setRotationSize(Integer.valueOf(dbsRotationSize));
        model.setMinMessage(DBLogLevel.valueOf(dbsMinMessage));
        model.setMinErrorState(DBLogLevel.valueOf(dbsMinErrorState));
        model.setMinDuration(Integer.valueOf(dbsLogMinDurationStatement));
        model.setLogDuration(Boolean.valueOf(dbsLogDuration));
        model.setPrefix(dbsLogLinePrefix);

        return model;
    }

    public static void update(LoggerModel model) {
        logDir = model.getLogDir();
        logFile = model.getLogFile();
        rootLevel = model.getRootLevel().toString().toUpperCase();
        pa036Level = model.getPa036Level().toString().toUpperCase();
        springLevel = model.getSpringLevel().toString().toUpperCase();
        hibernateLevel = model.getHibernateLevel().toString().toUpperCase();
        hibernateTypeLevel = model.getHibernateTypeLevel().toString().toUpperCase();
        hibernateSQLLevel = model.getHibernateSQLLevel().toString().toUpperCase();

        dbsLogStatement = model.getLogStatement().toString().toLowerCase();
        dbsLogDestination = model.getDestination().toUpperCase();
        dbsLoggingDirectory = model.getDirectory();
        dbsLogFilename = model.getFileName();
        dbsLogFilemode = String.valueOf(model.getFileMode());
        dbsRotationAge = String.valueOf(model.getRotationAge());
        dbsRotationSize = String.valueOf(model.getRotationSize());
        dbsMinMessage = model.getMinMessage().toString().toUpperCase();
        dbsMinErrorState = model.getMinErrorState().toString().toUpperCase();
        dbsLogMinDurationStatement = String.valueOf(model.getMinDuration());
        dbsLogDuration = model.getLogDuration().toString().toUpperCase();
        dbsLogLinePrefix = model.getPrefix();
    }
}
