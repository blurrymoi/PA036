package cz.muni.pa036.logging.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CRUDLogger {

    private final Logger LOGGER;

    public CRUDLogger(Class loggerFor) {
        this.LOGGER = LoggerFactory.getLogger(loggerFor);
    }

    public void logUser(String user) {
        LOGGER.debug(" > ACTION INITIALISED BY [" + user + "]");
    }

    public void logURLRedirect(String URL, String user) {
        LOGGER.debug(" > ACTION [" + URL + "] INITIALISED BY [" + user + "]");
    }

    public void logCreate(Object created) {
        LOGGER.debug(" > Trying to create [" + created + "]");
    }

    public void logUpdate(Object updated) {
        LOGGER.debug(" > Trying to update [" + updated + "]");
    }

    public void logDelete(Object deleted) {
        LOGGER.debug(" > Trying to delete [" + deleted + "]");
    }

    public void logFindAll() {
        LOGGER.debug(" > Trying to find all {multiple rows allowed}");
    }

    public void logFindBy(Map<Object, Object> findBy, boolean multipleAllowed) {
        StringBuilder builder = new StringBuilder(" > Trying to find by");
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
        LOGGER.debug(toLog);
    }
}
