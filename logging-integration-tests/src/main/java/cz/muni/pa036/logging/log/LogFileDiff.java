package cz.muni.pa036.logging.log;

import ch.qos.logback.classic.Level;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kamil Triscik
 * @since 3/31/17
 */
public class LogFileDiff {

    private Long fileSize;

    private List<Log> logs;

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public List<Log> getLogs(Level logLevel) {
        return this.getLogs().stream().filter(log -> log.getLogLevel() == logLevel).collect(Collectors.toList());
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogFileDiff that = (LogFileDiff) o;

        if (fileSize != null ? !fileSize.equals(that.fileSize) : that.fileSize != null) return false;
        return logs != null ? logs.equals(that.logs) : that.logs == null;
    }

    @Override
    public int hashCode() {
        int result = fileSize != null ? fileSize.hashCode() : 0;
        result = 31 * result + (logs != null ? logs.hashCode() : 0);
        return result;
    }
}
