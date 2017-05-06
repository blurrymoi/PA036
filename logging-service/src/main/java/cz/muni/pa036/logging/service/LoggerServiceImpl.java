package cz.muni.pa036.logging.service;

import ch.qos.logback.classic.Level;
import cz.muni.pa036.logging.utils.LoggerConfiguration;
import cz.muni.pa036.logging.utils.LoggerModel;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Kamil Triscik.
 */
@Service
public class LoggerServiceImpl implements LoggerService{

    @Override
    public LoggerModel getLoggerModel() throws Exception {
        return LoggerConfiguration.getLoggerModel();
    }

    @Override
    public void updateLoggingOptions(LoggerModel loggerModel) throws Exception {
        Map<String, Level> levels = loggerModel.getAllLevels();

        for (String logger : levels.keySet()) {
            Level level = levels.get(logger);

            LoggerConfiguration.setLoggingLevel(logger, level);
        }
    }
}
