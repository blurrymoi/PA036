package cz.muni.pa036.logging.log;

/**
 * Available log levels
 * <li>{@link #ALL} - All levels including custom levels.</li>
 * <li>{@link #DEBUG} - Designates fine-grained informational events that are most useful to debug an application.</li>
 * <li>{@link #ERROR} - Designates error events that might still allow the application to continue running.</li>
 * <li>{@link #FATAL} - Designates very severe error events that will presumably lead the application to abort.</li>
 * <li>{@link #INFO} - Designates informational messages that highlight the progress of the application at coarse-grained level.</li>
 * <li>{@link #OFF} - The highest possible rank and is intended to turn off logging.</li>
 * <li>{@link #TRACE} - Designates finer-grained informational events than the DEBUG.</li>
 * <li>{@link #WARN} - Designates potentially harmful situations.</li>
 *
 * @see <a href="https://www.tutorialspoint.com/log4j/log4j_logging_levels.htm">log4j - Logging Levels</a>
 *
 * @author Kamil Triscik
 * @since 3/28/17
 */
public enum LogLevel {
    ALL,
    DEBUG,
    ERROR,
    FATAL,
    INFO,
    OFF,
    TRACE,
    WARN;

    /**
     * Method transform string level object to LogLevel object.
     * @param level string representation of level.
     * @return LogLevel representation.
     */
    public static LogLevel getLevel(String level) {
        switch (level.toLowerCase()) {
            case "all":
                return LogLevel.ALL;
            case "debug":
                return LogLevel.DEBUG;
            case "error":
                return LogLevel.ERROR;
            case "fatal":
                return LogLevel.FATAL;
            case "info":
                return LogLevel.INFO;
            case "off":
                return LogLevel.OFF;
            case "trace":
                return LogLevel.TRACE;
            case "warn":
                return LogLevel.WARN;
            default:
                throw new IllegalArgumentException("Unsupported log level: " + level + " !");
        }
    }
}
