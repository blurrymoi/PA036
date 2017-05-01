package cz.muni.pa036.logging.dbsApi;

/**
 * @author Kamil Triscik.
 */
public enum LogDestination {

    STDERR("STDERR", "Standard error"),
    CSVLOG("CSVLOG", "CSV log file"),
    SYSLOG("SYSLOG", "System log file"),
    EVENTLOG("EVENTLOG", "Event log file");


    private final String type;
    private final String description;

    private LogDestination(String fullName, String desc) {
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
