package cz.muni.pa036.logging.log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Object represent one log from log file.
 *
 * @author Kamil Triscik
 * @since 3/28/17
 */
public class Log implements Comparable {

    /**
     * Log time format.
     */
    public static final String TIME_FORMAT = "HH:mm:ss.SSS";

    /*Log time*/
    private Date time;

    /*Log level*/
    private LogLevel logLevel;

    /*Log content*/
    private String content;

    public Log(Date time, LogLevel logLevel, String content) {
        this.time = time;
        this.logLevel = logLevel;
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Log log = (Log) o;

        if (time != null ? !time.equals(log.time) : log.time != null) return false;
        if (logLevel != log.logLevel) return false;
        return content != null ? content.equals(log.content) : log.content == null;
    }

    @Override
    public int hashCode() {
        int result = time != null ? time.hashCode() : 0;
        result = 31 * result + (logLevel != null ? logLevel.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat(TIME_FORMAT).format(getTime()) + " " + logLevel + " " + content;
    }

    @Override
    public int compareTo(Object o) {
        return this.getTime().compareTo(((Log)o).getTime());
    }
}
