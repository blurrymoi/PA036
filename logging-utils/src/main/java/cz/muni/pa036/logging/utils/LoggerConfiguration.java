package cz.muni.pa036.logging.utils;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

public class LoggerConfiguration extends ContextAwareBase implements LoggerContextListener, LifeCycle {

    private static final String DEFAULT_LOG_FILE = "PA036";

    private boolean started = false;

    @Override
    public void start() {
        if (started) return;

        /*String logDir = System.getProperty("user.home");
        String logFile = System.getProperty("log.file");*/
        String logDir = "D:\\MUNI\\PA036\\PA036";
        String logFile = "PA036";

        logFile = (logFile != null && logFile.length() > 0) ? logFile : DEFAULT_LOG_FILE;

        Context context = getContext();

        context.putProperty("LOG_DIR", logDir);
        context.putProperty("LOG_FILE", logFile);

        started = true;
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
