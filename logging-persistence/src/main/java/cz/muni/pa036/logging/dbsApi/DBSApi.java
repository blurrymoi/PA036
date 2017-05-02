package cz.muni.pa036.logging.dbsApi;

/**
 * @author Kamil Triscik.
 */
public interface DBSApi {

    void turnOnLogging(boolean enabled) throws Exception;

    /**
     * PostgreSQL supports several methods for logging server messages,
     * including stderr, csvlog and syslog.
     * On Windows, eventlog is also supported.
     * Set this parameter to a list of desired log destinations separated by commas.
     * The default is to log to stderr only. This parameter can only be set in the postgresql.conf
     * file or on the server command line.
     *
     * @param destination
     * @throws Exception
     */
    void setLogDestination(LogDestination destination, boolean enabled) throws Exception;

    /**
     * When logging_collector is enabled, this parameter determines the directory
     * n which log files will be created. It can be specified as an absolute path,
     * or relative to the cluster data directory. This parameter can only be set
     * in the postgresql.conf file or on the server command line. The default is pg_log.
     *
     * @param directory
     * @throws Exception
     */
    void setLoggingDirectory(String directory, boolean enabled) throws Exception;

    /**
     * When logging_collector is enabled,this parameter sets
     * the file names of the created log files.
     * The value is treated as a strftime pattern,
     * so %-escapes can be used to specify time-varying file names.
     * (Note that if there are any time-zone-dependent %-escapes,
     * the computation is done in the zone specified by log_timezone.)
     * The supported %-escapes are similar to those listed in the Open Group's strftime specification.
     * Note that the system's strftime is not used directly, so platform-specific
     * (nonstandard) extensions do not work. The default is postgresql-%Y-%m-%d_%H%M%S.log.
     *
     * @param logFilename
     * @throws Exception
     */
    void setLogFilename(String logFilename, boolean enabled) throws Exception;

    /**
     * On Unix systems this parameter sets the permissions for log files when logging_collector is enabled.
     * (On Microsoft Windows this parameter is ignored.) The parameter value is expected to be a numeric mode
     * specified in the format accepted by the chmod and umask system calls.
     * (To use the customary octal format the number must start with a 0 (zero).)
     * The default permissions are 0600, meaning only the server owner can read or write the log files.
     * The other commonly useful setting is 0640, allowing members of the owner's group to read the file
     *
     * @param mode
     * @throws Exception
     */
    void setLogFileMode(int mode, boolean enabled) throws Exception;


    /**
     * When logging_collector is enabled, this parameter determines the maximum
     * lifetime of an individual log file. After this many minutes have elapsed,
     * a new log file will be created. Set to zero to disable time-based creation of new log files.
     * This parameter can only be set in the postgresql.conf file or on the server command line.
     *
     * @param age
     * @throws Exception
     */
    void setRotationAge(int age, boolean enabled) throws Exception;

    /**
     * When logging_collector is enabled, this parameter determines the maximum
     * size of an individual log file. After this many kilobytes have been emitted into a log file,
     * a new log file will be created. Set to zero to disable size-based creation of new log files.
     * This parameter can only be set in the postgresql.conf file or on the server command line.
     *
     * @param size
     * @throws Exception
     */
    void setRotationSize(int size, boolean enabled) throws Exception;

    /**
     * Controls which message levels are written to the server log.
     * Valid values are DEBUG5, DEBUG4, DEBUG3, DEBUG2, DEBUG1, INFO, NOTICE, WARNING, ERROR, LOG, FATAL, and PANIC.
     * Each level includes all the levels that follow it. The later the level, the fewer messages are sent to the log.
     * The default is WARNING. Note that LOG has a different rank here than in client_min_messages.
     * Only superusers can change this setting.
     *
     * @param level
     * @throws Exception
     */
    void setLogMinMessage(DBLogLevel level, boolean enabled) throws Exception;

    /**
     * Controls which SQL statements that cause an error condition are recorded in the server log.
     * The current SQL statement is included in the log entry for any message of the specified severity or higher.
     * Valid values are DEBUG5, DEBUG4, DEBUG3, DEBUG2, DEBUG1, INFO, NOTICE, WARNING, ERROR, LOG, FATAL, and PANIC.
     * The default is ERROR, which means statements causing errors, log messages, fatal errors, or panics will be logged.
     * To effectively turn off logging of failing statements, set this parameter to PANIC.
     * Only superusers can change this setting.
     *
     * @param level
     * @throws Exception
     */
    void setLogMinErrorState(DBLogLevel level, boolean enabled) throws Exception;

    /**
     * Causes the duration of each completed statement to be logged if the statement ran for at least
     * the specified number of milliseconds. Setting this to zero prints all statement durations.
     * Minus-one (the default) disables logging statement durations. For example, if you set it to 250ms
     * then all SQL statements that run 250ms or longer will be logged. Enabling this parameter can be helpful
     * in tracking down unoptimized queries in your applications. Only superusers can change this setting
     *
     * @param duration
     * @throws Exception
     */
    void setLogMinDurationStatement(int duration, boolean enabled) throws Exception;

    /**
     * Causes the duration of every completed statement to be logged. The default is off.
     * Only superusers can change this setting. For clients using extended query protocol, durations of the Parse,
     * Bind, and Execute steps are logged independently.
     *
     * @param enabledLogDuration
     * @throws Exception
     */
    void setLogDuration(boolean enabledLogDuration, boolean enabled) throws Exception;

    /**
     * This is a printf-style string that is output at the beginning of each log line.
     * % characters begin "escape sequences" that are replaced with status information as outlined below.
     * Unrecognized escapes are ignored. Other characters are copied straight to the log line.
     * Some escapes are only recognized by session processes, and will be treated as empty by background processes such as the main server process.
     * Status information may be aligned either left or right by specifying a numeric literal after the % and before the option.
     * A negative value will cause the status information to be padded on the right with spaces to give it a minimum width,
     * whereas a positive value will pad on the left. Padding can be useful to aid human readability in log files.
     * This parameter can only be set in the postgresql.conf file or on the server command line. The default is an empty string.
     *
     * @param prefix
     * @throws Exception
     */
    void setLogLinePrefix(String prefix, boolean enabled) throws Exception;

    /**
     * Method run random query.
     *
     * @param query
     * @throws Exception
     */
    void runQuery(String query) throws Exception;

    /**
     * Method execute query for reloading system log configuration.
     * @throws Exception
     */
    void reload() throws Exception;
}
