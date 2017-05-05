package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.utils.LoggerConfiguration;
import cz.muni.pa036.logging.utils.LoggerModel;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author Kamil Triscik.
 */
@Service
public class LoggerServiceImpl implements LoggerService{

    Logger logger = org.slf4j.LoggerFactory.getLogger(LoggerServiceImpl.class);


    @Override
    public LoggerModel getLoggerModel() throws Exception {
        return LoggerConfiguration.getLoggerModel();
    }

    @Override
    public void updateLoggingOptions(LoggerModel loggerModel) throws Exception {
        logger.info("updateLoggingOptions() method called!");
        System.out.println();
    }
}
