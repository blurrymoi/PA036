package cz.muni.pa036.logging.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CRUDLogger {

    private final Class LOGGER_FOR;
    private final Logger LOGGER;

    public CRUDLogger(Class loggerFor) {
        this.LOGGER = LoggerFactory.getLogger(loggerFor);
        LOGGER_FOR = loggerFor;
    }

    public void logUser(String user) {
        LOGGER.debug(getClassName() + " > ACTION INITIALISED BY [" + user + "]");
    }

    public void logCreate(Object created) {
        LOGGER.debug(getClassName() + " > Trying to create [" + created + "]");
    }

    public void logUpdate(Object updated) {
        LOGGER.debug(getClassName() + " > Trying to update [" + updated + "]");
    }

    public void logDelete(Object deleted) {
        LOGGER.debug(getClassName() + " > Trying to delete [" + deleted + "]");
    }

    public void logFindAll() {
        LOGGER.debug(getClassName() + " > Trying to find all {multiple rows allowed}");
    }

    public void logFindBy(Map<Object, Object> findBy, boolean multipleAllowed) {
        StringBuilder builder = new StringBuilder(getClassName() + " > Trying to find by");
        builder.append(" params=[");
        int mapLength = findBy.keySet().size();
        int currentIndex = 1;
        for (Object by: findBy.keySet()) {
            Object byValue = findBy.get(by);

            builder.append(by + ":\"" + byValue + "\"");
            if (currentIndex < mapLength) {
                builder.append(", ");
            }
            currentIndex++;
        }
        builder.append("]");
        if (multipleAllowed) {
            builder.append(" {multiple rows allowed}");
        }
        LOGGER.debug(builder.toString());
    }

    public void logFindBy(Map<Object, Object> findBy) {
        this.logFindBy(findBy, false);
    }

    public void logFindBy(Object by, Object byValue, boolean multipleAllowed) {
        Map<Object, Object> findBy = new HashMap<>();
        findBy.put(by, byValue);
        this.logFindBy(findBy);
    }

    public void logFindBy(Object by, Object byValue) {
        Map<Object, Object> findBy = new HashMap<>();
        findBy.put(by, byValue);
        this.logFindBy(findBy, false);
    }

    public void logCustom(String toLog) {
        LOGGER.debug(getClassName() + toLog);
    }

    private String getClassName() {
        return LOGGER_FOR.getSimpleName().toUpperCase();
    }
}
