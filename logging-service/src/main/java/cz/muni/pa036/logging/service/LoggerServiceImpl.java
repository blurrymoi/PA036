package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.utils.LoggerModel;
import org.springframework.stereotype.Service;

/**
 * @author Kamil Triscik.
 */
@Service
public class LoggerServiceImpl implements LoggerService{


    @Override
    public LoggerModel getLoggerModel() throws Exception {
        return new LoggerModel();
    }

    @Override
    public void updateLoggingOptions(LoggerModel loggerModel) throws Exception {

    }
}
