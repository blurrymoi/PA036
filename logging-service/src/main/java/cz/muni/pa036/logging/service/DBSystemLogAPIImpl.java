package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.utils.DBLogLevel;
import cz.muni.pa036.logging.dbsApi.DBSApi;
import cz.muni.pa036.logging.utils.LogDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kamil Triscik.
 */
@Service
public class DBSystemLogAPIImpl implements DBSystemLogAPI {

    @Autowired
    private DBSApi dbsApi;

    @Override
    public void turnOnLogging(boolean enabled) throws Exception {
        dbsApi.turnOnLogging(enabled);
    }

    @Override
    public void setLogDestination(LogDestination destination, boolean enabled) throws Exception {
        dbsApi.setLogDestination(destination, enabled);
    }

    @Override
    public void setLoggingDirectory(String directory, boolean enabled) throws Exception {
        dbsApi.setLoggingDirectory(directory, enabled);
    }

    @Override
    public void setLogFilename(String logFilename, boolean enabled) throws Exception {
        dbsApi.setLogFilename(logFilename, enabled);
    }

    @Override
    public void setLogFileMode(int mode, boolean enabled) throws Exception {
        dbsApi.setLogFileMode(mode, enabled);
    }

    @Override
    public void setRotationAge(int age, boolean enabled) throws Exception {
        dbsApi.setRotationAge(age, enabled);
    }

    @Override
    public void setRotationSize(int size, boolean enabled) throws Exception {
        dbsApi.setRotationSize(size, enabled);
    }

    @Override
    public void setLogMinMessage(DBLogLevel level, boolean enabled) throws Exception {
        dbsApi.setLogMinMessage(level, enabled);
    }

    @Override
    public void setLogMinErrorState(DBLogLevel level, boolean enabled) throws Exception {
        dbsApi.setLogMinErrorState(level, enabled);
    }

    @Override
    public void setLogMinDurationStatement(int duration, boolean enabled) throws Exception {
        dbsApi.setLogMinDurationStatement(duration, enabled);
    }

    @Override
    public void setLogDuration(boolean enabledLogDuration, boolean enabled) throws Exception {
        dbsApi.setLogDuration(enabledLogDuration, enabled);
    }

    @Override
    public void setLogLinePrefix(String prefix, boolean enabled) throws Exception {
        dbsApi.setLogLinePrefix(prefix, enabled);
    }
}
