package cz.muni.pa036.logging.dbsApi;

import cz.muni.pa036.logging.utils.DBLogLevel;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author Kamil Triscik.
 */
public class DBSApiImpl implements DBSApi {
    final String query = "ALTER SYSTEM SET <option> = <value>;";

    private JdbcTemplate jdbc;

    public DBSApiImpl(DataSource dataSource) {
        if (dataSource == null) {

        }
        jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void reload() throws Exception {
        jdbc.execute("SELECT pg_reload_conf();");
    }

    @Override
    public void runQuery(String query) throws Exception {
        jdbc.execute(query);
    }

    @Override
    public void setLogStatement(String statement, boolean enabled) throws Exception {
        final String option = "log_statement";
        jdbc.execute(getQuery(option, "'" + statement + "'", String.valueOf(enabled)));
    }

    @Override
    public void setLogDestination(String destination, boolean enabled) throws Exception {
        final String option = "log_destination";
        jdbc.execute(getQuery(option, destination, String.valueOf(enabled)));
    }

    @Override
    public void setLoggingDirectory(String directory, boolean enabled) throws Exception {
        final String option = "log_directory";
        jdbc.execute(getQuery(option, directory, String.valueOf(enabled)));
    }

    @Override
    public void setLogFilename(String logFilename, boolean enabled) throws Exception {
        final String option = "log_filename";
        jdbc.execute(getQuery(option, logFilename, String.valueOf(enabled)));
    }

    @Override
    public void setLogFileMode(int mode, boolean enabled) throws Exception {
        final String option = "log_file_mode";
        jdbc.execute(getQuery(option, String.valueOf(mode), String.valueOf(enabled)));
    }

    @Override
    public void setRotationAge(int age, boolean enabled) throws Exception {
        final String option = "log_rotation_age";
        jdbc.execute(getQuery(option, String.valueOf(age), String.valueOf(enabled)));
    }

    @Override
    public void setRotationSize(int size, boolean enabled) throws Exception {
        final String option = "log_rotation_size";
        jdbc.execute(getQuery(option, String.valueOf(size), String.valueOf(enabled)));
    }

    @Override
    public void setLogMinMessage(DBLogLevel level, boolean enabled) throws Exception {
        final String option = "log_min_messages";
        jdbc.execute(getQuery(option, level.toString().toLowerCase(), String.valueOf(enabled)));
    }

    @Override
    public void setLogMinErrorState(DBLogLevel level, boolean enabled) throws Exception {
        final String option = "log_min_error_statement";
        jdbc.execute(getQuery(option, level.toString().toLowerCase(), String.valueOf(enabled)));
    }

    @Override
    public void setLogMinDurationStatement(int duration, boolean enabled) throws Exception {
        final String option = "log_min_duration_statement";
        jdbc.execute(getQuery(option, String.valueOf(duration), String.valueOf(enabled)));
    }

    @Override
    public void setLogDuration(boolean enabledLogDuration, boolean enabled) throws Exception {
        final String option = "log_duration";
        jdbc.execute(getQuery(option, String.valueOf(enabledLogDuration), String.valueOf(enabled)));
    }

    @Override
    public void setLogLinePrefix(String prefix, boolean enabled) throws Exception {
        final String option = "log_line_prefix";
        jdbc.execute(getQuery(option, prefix, String.valueOf(enabled)));
    }

    private String getQuery(String option, String value, String enabled) {
        return query
                .replace("<option>", option)
                .replace("<value>", value);
//                .replace("<enabled>", enabled);
    }
}
