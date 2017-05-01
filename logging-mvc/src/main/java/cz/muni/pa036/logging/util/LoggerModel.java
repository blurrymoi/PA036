package cz.muni.pa036.logging.util;

import cz.muni.pa036.logging.dbsApi.DBLogLevel;
import cz.muni.pa036.logging.dbsApi.LogDestination;

/**
 * @author Kamil Triscik.
 */
public class LoggerModel {

    private LogDestination destination;

    private Boolean loggingCollector;

    private String directory;

    private String fileName;

    private Integer fileMode;

    private Integer rotationAge;

    private Integer rotationSize;

    private DBLogLevel minMessage;

    private DBLogLevel minErrorState;

    private Integer minDuration;

    private Boolean logDuration;

    private String prefix;

    public LoggerModel() {
        setDestination(LogDestination.STDERR);
        setDirectory("/home/kamil/");
        setFileName("pa036");
        setFileMode(0666);
        setRotationAge(100);
        setRotationSize(10000);
        setMinMessage(DBLogLevel.INFO);
        setMinErrorState(DBLogLevel.ERROR);
        setMinDuration(0);
        setLogDuration(true);
        setLoggingCollector(false);
        setPrefix("custom prefix");
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public LogDestination getDestination() {
        return destination;
    }

    public void setDestination(LogDestination destination) {
        this.destination = destination;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileMode() {
        return fileMode;
    }

    public void setFileMode(Integer fileMode) {
        this.fileMode = fileMode;
    }

    public Integer getRotationAge() {
        return rotationAge;
    }

    public void setRotationAge(Integer rotationAge) {
        this.rotationAge = rotationAge;
    }

    public Integer getRotationSize() {
        return rotationSize;
    }

    public void setRotationSize(Integer rotationSize) {
        this.rotationSize = rotationSize;
    }

    public DBLogLevel getMinMessage() {
        return minMessage;
    }

    public void setMinMessage(DBLogLevel minMessage) {
        this.minMessage = minMessage;
    }

    public DBLogLevel getMinErrorState() {
        return minErrorState;
    }

    public void setMinErrorState(DBLogLevel minErrorState) {
        this.minErrorState = minErrorState;
    }

    public Integer getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Integer minDuration) {
        this.minDuration = minDuration;
    }

    public Boolean getLogDuration() {
        return logDuration;
    }

    public void setLogDuration(Boolean logDuration) {
        this.logDuration = logDuration;
    }

    public Boolean getLoggingCollector() {
        return loggingCollector;
    }

    public void setLoggingCollector(Boolean loggingCollector) {
        this.loggingCollector = loggingCollector;
    }



}
