package cz.muni.pa036.logging.utils;

import org.slf4j.event.Level;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kamil Triscik.
 */
public class LoggerModel {

    private String logDir;
    private String logFile;

    private Level rootLevel;
    private Level pa036Level;
    private Level springLevel;
    private Level hibernateLevel;
    private Level hibernateTypeLevel;
    private Level hibernateSQLLevel;


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

    public Level getRootLevel() {
        return rootLevel;
    }

    public void setRootLevel(String rootLevel) {
        this.rootLevel = Level.valueOf(rootLevel);
    }

    public void setRootLevel(Level rootLevel) {
        this.rootLevel = rootLevel;
    }

    public Level getPa036Level() {
        return pa036Level;
    }

    public void setPa036Level(String pa036Level) {
        this.pa036Level = Level.valueOf(pa036Level);
    }

    public void setPa036Level(Level pa036Level) {
        this.pa036Level = pa036Level;
    }


    public Level getSpringLevel() {
        return springLevel;
    }

    public void setSpringLevel(String springLevel) {
        this.springLevel = Level.valueOf(springLevel);
    }

    public void setSpringLevel(Level springLevel) {
        this.springLevel = springLevel;
    }


    public Level getHibernateLevel() {
        return hibernateLevel;
    }

    public void setHibernateLevel(String hibernateLevel) {
        this.hibernateLevel = Level.valueOf(hibernateLevel);
    }

    public void setHibernateLevel(Level hibernateLevel) {
        this.hibernateLevel = hibernateLevel;
    }


    public Level getHibernateTypeLevel() {
        return hibernateTypeLevel;
    }

    public void setHibernateTypeLevel(String hibernateTypeLevel) {
        this.hibernateTypeLevel = Level.valueOf(hibernateTypeLevel);
    }

    public void setHibernateTypeLevel(Level hibernateTypeLevel) {
        this.hibernateTypeLevel = hibernateTypeLevel;
    }

    public Level getHibernateSQLLevel() {
        return hibernateSQLLevel;
    }

    public void setHibernateSQLLevel(String hibernateSQLLevel) {
        this.hibernateSQLLevel = Level.valueOf(hibernateSQLLevel);
    }

    public void setHibernateSQLLevel(Level hibernateSQLLevel) {
        this.hibernateSQLLevel = hibernateSQLLevel;
    }

    public Map<String,  ch.qos.logback.classic.Level> getAllLevels() {
        Map<String, ch.qos.logback.classic.Level> levels = new HashMap<>();

        levels.put("ROOT",                  LoggerConfiguration.resolveLevel(getRootLevel().toString()));
        levels.put("org.springframework",   LoggerConfiguration.resolveLevel(getSpringLevel().toString()));
        levels.put("org.hibernate",         LoggerConfiguration.resolveLevel(getHibernateLevel().toString()));
        levels.put("org.hibernate.SQL",     LoggerConfiguration.resolveLevel(getHibernateSQLLevel().toString()));
        levels.put("org.hibernate.type",    LoggerConfiguration.resolveLevel(getHibernateTypeLevel().toString()));
        levels.put("cz.muni.pa036.logging", LoggerConfiguration.resolveLevel(getPa036Level().toString()));

        return levels;
    }
}
