package cz.muni.pa036.logging.utils;

import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class DAOLogger {

    private final Logger LOGGER;
    private final String CLASS_NAME;

    public DAOLogger(Logger LOGGER, String className) {
        this.LOGGER = LOGGER;
        CLASS_NAME = className;
    }

    public void logCreate(Object created) {
        LOGGER.debug("Trying to create " + CLASS_NAME +
                " [" + created + "]");
    }

    public void logUpdate(Object updated) {
        LOGGER.debug("Trying to update " + CLASS_NAME +
                " [" + updated + "]");
    }

    public void logDelete(Object deleted) {
        LOGGER.debug("Trying to delete " + CLASS_NAME +
                " [" + deleted + "]");
    }

    public void logFindAll() {
        LOGGER.debug("Trying to find all " + CLASS_NAME +
                "{multiple rows allowed}");
    }

    public void logFindBy(Map<Object, Object> findBy, boolean multipleAllowed) {
        StringBuilder builder = new StringBuilder("Trying to find " + CLASS_NAME);
        if (multipleAllowed) {
            builder.append(" {multiple rows allowed}");
        }
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
}
