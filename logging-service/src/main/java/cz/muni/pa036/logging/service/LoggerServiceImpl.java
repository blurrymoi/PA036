package cz.muni.pa036.logging.service;

import ch.qos.logback.classic.Level;
import cz.muni.pa036.logging.dbsApi.DBSApi;
import cz.muni.pa036.logging.utils.LoggerConfiguration;
import cz.muni.pa036.logging.utils.LoggerModel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @author Kamil Triscik.
 */
@Service
public class LoggerServiceImpl implements LoggerService{

    Logger logger = org.slf4j.LoggerFactory.getLogger(LoggerServiceImpl.class);


    @Autowired
    DBSApi dbsApi;

    @Override
    public LoggerModel getLoggerModel() throws Exception {
        return LoggerConfiguration.getLoggerModel();
    }

    @Override
    public void updateLoggingOptions(LoggerModel loggerModel) throws Exception {
        LoggerModel old = LoggerConfiguration.getLoggerModel();
        Map<String, Level> levels = loggerModel.getAllLevels();

        for (String logger : levels.keySet()) {
            Level level = levels.get(logger);

            LoggerConfiguration.setLoggingLevel(logger, level);
        }

        boolean changed = false;

        if (old.getDestination() != loggerModel.getDestination()) {
            dbsApi.setLogDestination(loggerModel.getDestination(), true);
            changed = true;
        }

        if (!Objects.equals(old.getDirectory(), loggerModel.getDirectory())) {
            dbsApi.setLoggingDirectory(loggerModel.getDirectory(), true);
            changed = true;
        }

        if (!Objects.equals(old.getFileName(), loggerModel.getFileName())) {
            dbsApi.setLogFilename(loggerModel.getFileName(), true);
            changed = true;
        }

        if (!Objects.equals(old.getFileMode(), loggerModel.getFileMode())) {
            dbsApi.setLogFileMode(loggerModel.getFileMode(), true);
            changed = true;
        }

        if (!Objects.equals(old.getRotationAge(), loggerModel.getRotationAge())) {
            dbsApi.setRotationAge(loggerModel.getRotationAge(), true);
            changed = true;
        }

        if (!Objects.equals(old.getRotationSize(), loggerModel.getRotationSize())) {
            dbsApi.setRotationSize(loggerModel.getRotationSize(), true);
            changed = true;
        }

        if (old.getMinMessage() != loggerModel.getMinMessage()) {
            dbsApi.setLogMinMessage(loggerModel.getMinMessage(), true);
            changed = true;
        }

        if (old.getMinErrorState() != loggerModel.getMinErrorState()) {
            dbsApi.setLogMinErrorState(loggerModel.getMinErrorState(), true);
            changed = true;
        }

        if (!Objects.equals(old.getMinDuration(), loggerModel.getMinDuration())) {
            dbsApi.setLogMinDurationStatement(loggerModel.getMinDuration(), true);
            changed = true;
        }

        if (old.getLogDuration() != loggerModel.getLogDuration()) {
            dbsApi.setLogDuration(loggerModel.getLogDuration(), true);
            changed = true;
        }

        if (!Objects.equals(old.getPrefix(), loggerModel.getPrefix())) {
            dbsApi.setLogLinePrefix(loggerModel.getPrefix(), true);
            changed = true;
        }

        if (changed) {
            dbsApi.reload();
            logger.info("DBS log settings changes reloaded");
            //should be good to check if reload successful
            //not part of project
        } else {
            logger.info("DBS log settings changes not reloaded. No changes!");

        }

        LoggerConfiguration.update(loggerModel);

    }
}
