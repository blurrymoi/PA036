package cz.muni.pa036.logging.log;

import cz.muni.pa036.logging.logService.LogLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Object represent one log file.
 * Contain all logs and file size.
 *
 * @author Kamil Triscik
 * @since 3/28/17
 */
public class LogFile extends LogFileDiff {

    /*Absolute path*/
    private String filePath;

    private LogFileDiff logFileDiff;

    public LogFile(String logFilePath) {
        this.filePath = logFilePath;
        this.logFileDiff = null;
    }

    /**
     * @return log file absolute path.
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * @return return LogFileDiff object, if exist, otherwise return null.
     */
    public LogFileDiff getLogFileDiff() {
        return logFileDiff;
    }

    /**
     * Method reload log file and generate LogFileDiff object,
     * which contain information about changes from previous (re)load.
     *
     * @return log file diff.
     */
    public LogFileDiff reloadLogFile() throws Exception {
        LogFile logFile = LogLoader.loadLogFile(this.getFilePath());

        this.logFileDiff = new LogFileDiff();
        this.logFileDiff.setFileSize(logFile.getFileSize() - this.getFileSize());
        List<Log> logsDiff = new ArrayList<>(logFile.getLogs());
        logsDiff.removeAll(this.getLogs());
        this.logFileDiff.setLogs(logsDiff);

        this.setLogs(logFile.getLogs());
        this.setFileSize(logFile.getFileSize());
        return this.logFileDiff;
    }

}
