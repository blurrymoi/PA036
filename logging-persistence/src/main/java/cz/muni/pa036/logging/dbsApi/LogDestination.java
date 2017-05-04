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
    private final String destination;

    private LogDestination(String fullName, String desc) {
        this.type = fullName;
        this.destination = desc;
    }

    public String getType() {
        return type;
    }

    public String getDestination() {
        return destination;
    }

}
