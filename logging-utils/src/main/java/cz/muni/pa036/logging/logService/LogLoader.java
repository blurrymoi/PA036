package cz.muni.pa036.logging.logService;

import cz.muni.pa036.logging.log.Log;
import cz.muni.pa036.logging.log.LogFile;

import ch.qos.logback.classic.Level;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public static LogFile loadLogFile(String logFileName, boolean create) throws Exception {
        if (logFileName == null || logFileName.isEmpty()) {
            throw new IllegalArgumentException("Illegal \"logFileName\"(" + logFileName + ") value!");
        }
        if (!logFileName.endsWith(".log")) {
            throw new IllegalArgumentException("Log file name should end with \"*.log\"");
        }
        File logFile = new File(logFileName);
        if (!logFile.exists()) {
            Files.createFile(Paths.get(logFileName));
        }
        return LogLoader.loadLogFile(logFile);
    }

    /**
     * Method load logs from log file.
     *
     * @param logFileName absolute path to log file.
     * @return LogFile object.
     */
    public static LogFile loadLogFile(String logFileName) throws Exception {
        if (logFileName == null || logFileName.isEmpty()) {
            throw new IllegalArgumentException("Illegal \"logFileName\"(" + logFileName + ") value!");
        }
        if (!logFileName.endsWith(".log")) {
            throw new IllegalArgumentException("Log file(" + logFileName + ") name should end with \"*.log\"");
        }
        File logFile = new File(logFileName);
        if (!logFile.exists()) {
            throw new IllegalArgumentException("File(" + logFileName + ") does not exist!");
        }
        if (!logFile.isFile()) {
            throw new IllegalArgumentException("Expected a file(" + logFileName + "), not a directory!");
        }
        return LogLoader.loadLogFile(logFile);
    }

    /**
     * Method load logs from log file.
     *
     * @param logFile logFile object.
     * @return LogFile object.
     */
    public static LogFile loadLogFile(File logFile) throws Exception {
        if (logFile == null) {
            throw new IllegalArgumentException("Log file argument is null!");
        }
        if (!logFile.isFile()) {
            throw new IllegalArgumentException("Expected a file, not a directory!");
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
    public static List<LogFile> loadLogFiles(String directory) throws Exception {
        if (directory == null || directory.isEmpty()) {
            throw new IllegalArgumentException("Illegal \"directory\"(" + directory + ") value!");
        }
        File logDirectory = new File(directory);
        if (!logDirectory.exists()) {
            throw new IllegalArgumentException("Directory does not exist!");
        }
        if (!logDirectory.isDirectory()) {
            throw new IllegalArgumentException("Invalid path to directory!");
        }
        return LogLoader.loadLogFiles(logDirectory);
    }

    /**
     * Method find all log files in directory load logs from them.
     *
     * @param directory directory File object.
     * @return collection of LogFile objects.
     */
    public static List<LogFile> loadLogFiles(File directory) throws Exception {
        if (directory == null) {
            throw new IllegalArgumentException("Directory argument is null!");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Expected a directory, not a file!");
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
    private static List<Log> loadLogsFromFile(File logFile) throws Exception {
        List<Log> logs = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat(Log.TIME_FORMAT);
        Pattern p = Pattern.compile(LOG_START_PATTERN);
        Matcher m;

        String line;

        String time = null;
        StringBuilder content = null;
        Level logLevel = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("( )+", " ");
                m = p.matcher(line);
                if (m.find()) {
                    if (content != null) {
                        logs.add(new Log(
                                dateFormat.parse(time),
                                logLevel,
                                content.toString()
                        ));
                    }
                    content = new StringBuilder();
                    String[] lineParts = line.split(" ");
                    time = lineParts[0];
                    logLevel = Level.toLevel(lineParts[2]);
                    content.append(line.substring(line.indexOf(lineParts[3])));
                } else {
                    content.append(line).append("\n");
                }
            }
            if (content != null) {
                logs.add(new Log(
                        dateFormat.parse(time),
                        logLevel,
                        content.toString()
                ));
            }
        } catch (Exception e) {
            throw new Exception("Problem during loading logs", e);
        }

        return logs;
    }
}
