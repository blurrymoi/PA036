package cz.muni.pa036.logging.dbsApi;

/**
 * @author Kamil Triscik.
 */
public enum DBLogLevel {
    DEBUG5,
    DEBUG4,
    DEBUG3,
    DEBUG2,
    DEBUG1,
    INFO,
    NOTICE,
    WARNING, //default for log_min_messages
    ERROR, //default for log_min_error_statement
    LOG,
    FATAL,
    PANIC
}
