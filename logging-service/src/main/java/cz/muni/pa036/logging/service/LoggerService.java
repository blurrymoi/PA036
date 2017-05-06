package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.utils.LoggerModel;

/**
 * @author Kamil Triscik.
 */
public interface LoggerService {

    LoggerModel getLoggerModel() throws Exception;

    void updateLoggingOptions(LoggerModel loggerModel) throws Exception;

}
