package cz.muni.pa036.logging.logService;

import cz.muni.pa036.logging.log.Log;
import cz.muni.pa036.logging.log.LogFile;
import cz.muni.pa036.logging.log.LogFileDiff;
import cz.muni.pa036.logging.log.LogLevel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Collection of static method for loading log from log files.
 *
 * @author Kamil Triscik
 * @since 3/28/17
 */
public class LogLoader {

    /**
     * Pattern for detection new log, which always start with time, relative time, log level
     */
    private static final String LOG_START_PATTERN = "\\d\\d:\\d\\d:\\d\\d.\\d\\d\\d \\d+ *(.*)(ALL|DEBUG|ERROR|FATAL|INFO|OFF|TRACE|WARN)(.*)";

    /**
     * Method load logs from log file.
     *
     * @param logFileName absolute path to log file.
     * @return LogFile object.
     */
    public static LogFile loadLogFile(String logFileName) {
        if (logFileName == null || logFileName.isEmpty()) {
            throw new IllegalArgumentException("Illegal \"logFileName\"(" + logFileName + ") value!");
        }
        if (!logFileName.endsWith(".log")) {
            throw new IllegalArgumentException("Log file name should end with \"*.log\"");
        }
        File logFile = new File(logFileName);
        if (!logFile.isFile()) {
            throw new IllegalArgumentException("Expected file not directory!");
        }
        return LogLoader.loadLogFile(logFile);
    }

    /**
     * Method load logs from log file.
     *
     * @param logFile logFile object.
     * @return LogFile object.
     */
    public static LogFile loadLogFile(File logFile) {
        if (logFile == null) {
            throw new IllegalArgumentException("Log file argument is null!");
        }
        if (!logFile.isFile()) {
            throw new IllegalArgumentException("Expected file, not directory!");
        }

        LogFile newLogFile = new LogFile(logFile.getAbsolutePath());
        newLogFile.setFileSize(logFile.length());
        newLogFile.setLogs(LogLoader.loadLogsFromFile(logFile));

        return newLogFile;
    }

    /**
     * Method find all log files in directory load logs from them.
     *
     * @param directory absolute path to directory, where method should look for log files.
     * @return collection of LogFile objects.
     */
    public static List<LogFile> loadLogFiles(String directory) {
        if (directory == null || directory.isEmpty()) {
            throw new IllegalArgumentException("Illegal \"directory\"(" + directory + ") value!");
        }
        File logFile = new File(directory);
        if (!logFile.isDirectory()) {
            throw new IllegalArgumentException("Invalid path to directory!");
        }
        return LogLoader.loadLogFiles(logFile);
    }

    /**
     * Method find all log files in directory load logs from them.
     *
     * @param directory directory File object.
     * @return collection of LogFile objects.
     */
    public static List<LogFile> loadLogFiles(File directory) {
        if (directory == null) {
            throw new IllegalArgumentException("Directory argument is null!");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Expected directory, not file!");
        }

        File[] dirLogFiles = directory.listFiles((dir, filename) -> filename.endsWith(".log"));
        List<LogFile> logFiles = new ArrayList<>();
        if (dirLogFiles != null) {
            for ( File logFile : dirLogFiles) {
                logFiles.add(LogLoader.loadLogFile(logFile));
            }
        }
        return logFiles;
    }

    /**
     * Method load log from og file.
     *
     * @param logFile file of logs we want to load
     * @return collection of logs.
     */
    private static List<Log> loadLogsFromFile(File logFile) {
        List<Log> logs = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat(Log.TIME_FORMAT);
        Pattern p = Pattern.compile(LOG_START_PATTERN);
        Matcher m;

        String line;

        String time = null;
        StringBuilder content = null;
        String logLevel = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("( )+", " ");
                m = p.matcher(line);
                if (m.find()) {
                    if (content != null) {
                        logs.add(new Log(
                                dateFormat.parse(time),
                                LogLevel.getLevel(logLevel),
                                content.toString()
                        ));
                    }
                    content = new StringBuilder();
                    String[] lineParts = line.split(" ");
                    time = lineParts[0];
                    logLevel = lineParts[2];
                    content.append(line.substring(line.indexOf(lineParts[3])));
                } else {
                    content.append(line).append("\n");
                }
            }
            if (content != null) {
                logs.add(new Log(
                        dateFormat.parse(time),
                        LogLevel.getLevel(logLevel),
                        content.toString()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return logs;
    }
}
