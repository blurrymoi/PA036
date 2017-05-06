package cz.muni.pa036.logging.utils;


import ch.qos.logback.classic.Level;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kamil Triscik.
 */
public class LoggerModel {

    private String logDir;
    private String logFile;

    private String rootLevel;
    private String pa036Level;
    private String springLevel;
    private String hibernateLevel;
    private String hibernateTypeLevel;
    private String hibernateSQLLevel;


    private LogDestination destination;
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

    public void setDestination(String destination) {
        this.destination = LogDestination.valueOf(destination);
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

    public String getLogDir() {
        return logDir;
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public String getRootLevel() {
        return rootLevel;
    }

    public void setRootLevel(String rootLevel) {
        this.rootLevel = rootLevel;
    }

    public String getPa036Level() {
        return pa036Level;
    }

    public void setPa036Level(String pa036Level) {
        this.pa036Level = pa036Level;
    }

    public String getSpringLevel() {
        return springLevel;
    }

    public void setSpringLevel(String springLevel) {
        this.springLevel = springLevel;
    }

    public String getHibernateLevel() {
        return hibernateLevel;
    }

    public void setHibernateLevel(String hibernateLevel) {
        this.hibernateLevel = hibernateLevel;
    }

    public String getHibernateTypeLevel() {
        return hibernateTypeLevel;
    }

    public void setHibernateTypeLevel(String hibernateTypeLevel) {
        this.hibernateTypeLevel = hibernateTypeLevel;
    }

    public String getHibernateSQLLevel() {
        return hibernateSQLLevel;
    }

    public void setHibernateSQLLevel(String hibernateSQLLevel) {
        this.hibernateSQLLevel = hibernateSQLLevel;
    }

    public Map<String, Level> getAllLevels() {
        Map<String, Level> levels = new HashMap<>();

        levels.put("ROOT",                  LoggerConfiguration.resolveLevel(getRootLevel()));
        levels.put("org.springframework",   LoggerConfiguration.resolveLevel(getSpringLevel()));
        levels.put("org.hibernate",         LoggerConfiguration.resolveLevel(getHibernateLevel()));
        levels.put("org.hibernate.SQL",     LoggerConfiguration.resolveLevel(getHibernateSQLLevel()));
        levels.put("org.hibernate.type",    LoggerConfiguration.resolveLevel(getHibernateTypeLevel()));
        levels.put("cz.muni.pa036.logging", LoggerConfiguration.resolveLevel(getPa036Level()));

        return levels;
    }
}
