package cz.muni.pa036.logging.utils;

/**
 * @author Kamil Triscik.
 */
public enum DBLogLevel {
    DEBUG5("DEBUG5","DEBUG5"),
    DEBUG4("DEBUG4","DEBUG4"),
    DEBUG3("DEBUG3","DEBUG3"),
    DEBUG2("DEBUG2","DEBUG2"),
    DEBUG1("DEBUG1","DEBUG1"),
    INFO("INFO","INFO"),
    NOTICE("NOTICE","NOTICE"),
    WARNING("WARNING","WARNING"), //default for log_min_messages
    ERROR("ERROR","ERROR"), //default for log_min_error_statement
    LOG("LOG","LOG"),
    FATAL("FATAL","FATAL"),
    PANIC("PANIC","PANIC");

    private final String type;
    private final String description;

    private DBLogLevel(String fullName, String desc) {
        this.type = fullName;
        this.description = desc;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
