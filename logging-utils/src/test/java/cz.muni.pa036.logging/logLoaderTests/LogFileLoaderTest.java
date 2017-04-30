package cz.muni.pa036.logging.logLoaderTests;

import ch.qos.logback.classic.Level;
import cz.muni.pa036.logging.log.Log;
import cz.muni.pa036.logging.log.LogFile;
import cz.muni.pa036.logging.log.LogFileDiff;
import cz.muni.pa036.logging.logService.LogLoader;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.fail;
import static java.nio.file.StandardOpenOption.APPEND;


/**
 * @author Kamil Triscik
 * @since 4/2/17
 */
@Transactional
@ContextConfiguration(locations = "service-context.xml")
public class LogFileLoaderTest {

    private final String TEST_FILE = System.getProperty("user.dir") + "/testLog.log";

    private File logRealFile = null;

    @BeforeClass
    public void beforeClass() {
        removeLogFile(logRealFile);
    }

    @AfterTest
    public final void tearDown() {
        removeLogFile(logRealFile);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void loadLogFileNullArgumentTest() throws Exception {
        LogLoader.loadLogFile((String)null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void loadLogFileEmptyArgumentTest() throws Exception {
        LogLoader.loadLogFile("");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void loadLogFileBadSuffixTest() throws Exception {
        LogLoader.loadLogFile("file.txt");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void loadLogFileDoesNotExistTest() throws Exception {
        LogLoader.loadLogFile("notExistedFile.log");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void loadLogFileDirectoryInsteadFilePathTest() throws Exception {
        LogLoader.loadLogFile(System.getProperty("user.dir"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void nullLoadLogFileTest() throws Exception {
        LogLoader.loadLogFile((File)null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void loadLogFileDirectoryInsteadFileTest() throws Exception {
        LogLoader.loadLogFile(new File(System.getProperty("user.dir")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void loadLogFilesNullArgumentTest() throws Exception {
        LogLoader.loadLogFiles((String)null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void loadLogFilesEmptyArgumentTest() throws Exception {
        LogLoader.loadLogFiles("");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void loadLogDirectoryDoesNotExistTest() throws Exception {
        LogLoader.loadLogFiles("notExistedDirectory");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void nullLoadLogFilesTest() throws Exception {
        LogLoader.loadLogFiles((File)null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void loadLogFilesFileInsteadDirectoryTest() throws Exception {
        File newFile = null;
        try {
            newFile = generateLogFile();
        } catch (IOException e) {
            fail("Problem with test preparation", e);
        }
        LogLoader.loadLogFiles(newFile);
    }

    @Test
    public void loadInvalidLogFileTest() {
        File logRealFile = null;
        try {
            logRealFile = generateLogFile();
            //add invalid log
            String newLogLine = new SimpleDateFormat(Log.TIME_FORMAT).format(new Date().getTime()) +
                    "4541 INFO  o.h.tool.hbm2ddl.SchemaUpdate - HHH000228: Running hbm2ddl schema update";
            this.writeFile(logRealFile, new ArrayList<String>(){{ add(newLogLine);}});
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            LogLoader.loadLogFile(logRealFile);
            fail("Expected exception due to invalid log");
        } catch (Exception e) {}

    }

    @Test
    public void loadEmptyLogFileTest() {
        try {
            File logRealFile = generateLogFile();
            LogFile logFile = LogLoader.loadLogFile(logRealFile);
            Assert.assertTrue(logFile.getLogs().isEmpty());
            Assert.assertEquals(logFile.getFileSize(), new Long(0));
        } catch (Exception e) {
            fail("Problem with test preparation", e);
        }
    }

    @Test
    public void reloadEmptyLogFileWithoutChangesTest() {
        try {
            logRealFile = generateLogFile();
            LogFile logFile = LogLoader.loadLogFile(logRealFile);
            LogFileDiff logFileDiff = logFile.reloadLogFile();
            Assert.assertNull(logFileDiff);
            Assert.assertEquals(logFile.getFileSize(), new Long(0));
        } catch (Exception e) {
            fail("Problem with test preparation", e);
        }
    }

    @Test
    public void reloadEmptyLogFileWithChangesTest() {
        try {
            logRealFile = generateLogFile();
            LogFile logFile = LogLoader.loadLogFile(logRealFile);

            //add new log
            String newLogLine = new SimpleDateFormat(Log.TIME_FORMAT).format(new Date().getTime()) +
                    " 4541 INFO  o.h.tool.hbm2ddl.SchemaUpdate - HHH000228: Running hbm2ddl schema update";
            writeFile(logRealFile, new ArrayList<String>(){{ add(newLogLine);}});

            LogFileDiff logFileDiff = logFile.reloadLogFile();
            Assert.assertFalse(logFile.getLogs().isEmpty());
            Assert.assertEquals(logFile.getLogs().size(), 1);
            Assert.assertNotEquals(logFile.getFileSize(), 0L);

            Assert.assertFalse(logFileDiff.getLogs().isEmpty());
            Assert.assertEquals(logFileDiff.getLogs().size(), 1);
            Assert.assertNotEquals(logFileDiff.getFileSize(), 0L);

            Assert.assertEquals(logFile.getLogs().size(), logFileDiff.getLogs().size());
            Assert.assertEquals(logFile.getLogs().size(), logFileDiff.getLogs().size());

        } catch (Exception e) {
            fail("Problem with test preparation", e);
        }
    }

    @Test
    public void loadLogFileTest() {
        try {
            List logs = getStringLogs();
            logRealFile = generateLogFile(logs);
            LogFile logFile = LogLoader.loadLogFile(logRealFile);
            Assert.assertFalse(logFile.getLogs().isEmpty());
            Assert.assertEquals(logFile.getLogs().size(), logs.size());
            Assert.assertNotEquals(logFile.getFileSize(), 0);
        } catch (Exception e) {
            fail("Problem with test preparation", e);
        }
    }

    @Test
    public void reloadLogFileWithoutChangesTest() {
        try {
            List logs = getStringLogs();
            logRealFile = generateLogFile(logs);
            LogFile logFile = LogLoader.loadLogFile(logRealFile);
            LogFileDiff logFileDiff = logFile.reloadLogFile();

            Assert.assertTrue(logFileDiff.getLogs().isEmpty());
            Assert.assertEquals(logFileDiff.getFileSize(), new Long(0));
            Assert.assertFalse(logFile.getLogs().isEmpty());
            Assert.assertEquals(logFile.getLogs().size(), logs.size());
            Assert.assertNotEquals(logFile.getFileSize(), 0);
        } catch (Exception e) {
            fail("Problem with test preparation", e);
        }
    }

    @Test
    public void reloadLogFileWithChangesTest() {
        try {
            List logs = getStringLogs();
            logRealFile = generateLogFile(logs);
            LogFile logFile = LogLoader.loadLogFile(logRealFile);

            //add new log
            String newLogLine = new SimpleDateFormat(Log.TIME_FORMAT).format(new Date().getTime()) +
                    " 4541 INFO  o.h.tool.hbm2ddl.SchemaUpdate - HHH000228: Running hbm2ddl schema update";
            writeFile(logRealFile, new ArrayList<String>(){{ add(newLogLine);}});
            LogFileDiff logFileDiff = logFile.reloadLogFile();

            Assert.assertFalse(logFileDiff.getLogs().isEmpty());
            Assert.assertEquals(logFileDiff.getLogs().size(), 1);
            Assert.assertNotEquals(logFileDiff.getFileSize(), 0);

            Assert.assertFalse(logFile.getLogs().isEmpty());
            Assert.assertNotEquals(logFile.getLogs().size(), logs.size());
            Assert.assertEquals(logFile.getLogs().size(), logs.size() + 1);
            Assert.assertNotEquals(logFile.getFileSize(), 0);
        } catch (Exception e) {
            fail("Problem with test preparation", e);
        }
    }

    @Test
    public void loadLogFilesFromEmptyDirectoryTest() {
        try {
            new File(TEST_FILE).delete();
            List<LogFile> logFiles = LogLoader.loadLogFiles(System.getProperty("user.dir"))
                    .stream().filter(file -> file.getFilePath().equals(TEST_FILE)).collect(Collectors.toList());
            Assert.assertTrue(logFiles.isEmpty());
        } catch (Exception e) {
            fail("Problem with test preparation", e);
        }
    }

    //TODO: fix this, I am done with this shit around testing logs
    @Test(enabled = false)
    public void loadLogFilesFromNonEmptyDirectoryTest() {
        try {
            logRealFile = generateLogFile(getStringLogs());
            List<LogFile> logFiles = LogLoader.loadLogFiles(System.getProperty("user.dir"));

            Assert.assertFalse(logFiles.isEmpty());
            Assert.assertEquals(logFiles.size(), 1);
            LogFile logFile = LogLoader.loadLogFile(logRealFile);
            Assert.assertEquals(logFile, logFiles.get(0));
        } catch (Exception e) {
            fail("Problem with test preparation", e);
        }
    }






    private File generateLogFile() throws IOException {
        File newFile = new File(TEST_FILE);
        if (newFile.exists()) {
            newFile.delete();
        }
        if(!newFile.createNewFile()) {
            throw new IllegalStateException("Problem with creating test log file!");
        }
        return newFile;
    }

    private File generateLogFile(List<String> lines) throws IOException {
        File newFile = generateLogFile();
        this.writeFile(newFile, lines);
        return newFile;
    }

    private void writeFile(File file, List<String> lines) throws IOException {
        if(lines != null) {
            Files.write(Paths.get(file.getAbsolutePath()), lines, APPEND);
        }
    }

    private void removeLogFile(File file) {
        if (file != null) {
            file.delete();
        }
    }

    private List<String> getStringLogs() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Log.TIME_FORMAT);
        return new ArrayList<String>() {{
            add("23:30:02.853 2401  INFO  org.hibernate.Version - HHH000412: Hibernate Core {5.0.11.Final}");
            add("23:30:02.856 2404  INFO  org.hibernate.cfg.Environment - HHH000206: hibernate.properties not found");
            add("23:30:02.859 2407  INFO  org.hibernate.cfg.Environment - HHH000021: Bytecode provider name : javassist");
            add("23:30:02.926 2474  INFO  o.h.annotations.common.Version - HCANN000001: Hibernate Commons Annotations {5.0.1.Final}");
            add("23:30:03.347 2895  INFO  org.hibernate.dialect.Dialect - HHH000400: Using dialect: org.hibernate.dialect.PostgreSQLDialect");
            add("23:30:03.637 3185  INFO  o.h.e.j.e.i.LobCreatorBuilderImpl - HHH000424: Disabling contextual LOB creation as createClob() method threw error : java.lang.reflect.InvocationTargetException");
            add("23:30:03.642 3190  INFO  o.hibernate.type.BasicTypeRegistry - HHH000270: Type registration [java.util.UUID] overrides previous : org.hibernate.type.UUIDBinaryType@16fe9c29");
            add("23:30:04.390 3938  INFO  o.h.validator.internal.util.Version - HV000001: Hibernate Validator 5.2.4.Final");
            add("23:30:05.029 4577  INFO  o.h.tool.hbm2ddl.SchemaUpdate - HHH000228: Running hbm2ddl schema update");

            add("23:30:05.477 5025  INFO  org.hibernate.dialect.Dialect - HHH000400: Using dialect: org.hibernate.dialect.PostgreSQLDialect\n");
            add("23:30:05.483 5031  WARN  o.h.j.i.s.JpaSchemaGenerator - Hibernate hbm2ddl-auto setting was specified [update] in combination with JPA schema-generation; combination will likely cause trouble\n");
            add("23:30:05.658 5206  INFO  o.s.o.j.LocalContainerEntityManagerFactoryBean - Initialized JPA EntityManagerFactory for persistence unit 'default'\n");
            add("23:30:06.130 5678  ERROR  o.s.t.c.t.TransactionContext - Began transaction (1) for test context [DefaultTestContext@591a4f8e testClass = EventDAOTest, testInstance = cz.muni.pa036.logging.dao.EventDAOTest@2aa5fe93, testMethod = createUninitializedEvent@EventDAOTest, testException = [null], mergedContextConfiguration = [MergedContextConfiguration@2f0ed952 testClass = EventDAOTest, locations = '{classpath:dao-test-context.xml}', classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextCustomizers = set[[empty]], contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]]]; transaction manager [org.springframework.orm.jpa.JpaTransactionManager@380e1909]; rollback [true]");
        }};
    }

    private List<Log> getLogs() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Log.TIME_FORMAT);
        return new ArrayList<Log>() {{
            add(new Log(dateFormat.parse("23:30:03.642"),
                    Level.toLevel("INFO"),
                    "o.hibernate.type.BasicTypeRegistry - HHH000270: Type registration [java.util.UUID] overrides previous : org.hibernate.type.UUIDBinaryType@16fe9c29\n")
            );

            add(new Log(dateFormat.parse("23:30:04.390"),
                    Level.toLevel("INFO"),
                    "o.h.validator.internal.util.Version - HV000001: Hibernate Validator 5.2.4.Final\n" + "23:30:05.029 4577  INFO  o.h.tool.hbm2ddl.SchemaUpdate - HHH000228: Running hbm2ddl schema update\n")
            );

            add(new Log(dateFormat.parse("23:30:05.477"),
                    Level.toLevel("INFO"),
                    "org.hibernate.dialect.Dialect - HHH000400: Using dialect: org.hibernate.dialect.PostgreSQLDialect\n")
            );
            add(new Log(dateFormat.parse("23:30:05.483"),
                    Level.toLevel("WARN"),
                    "o.h.j.i.s.JpaSchemaGenerator - Hibernate hbm2ddl-auto setting was specified [update] in combination with JPA schema-generation; combination will likely cause trouble\n")
            );

            add(new Log(dateFormat.parse("23:30:05.658"),
                    Level.toLevel("ERROR"),
                    "o.s.o.j.LocalContainerEntityManagerFactoryBean - Initialized JPA EntityManagerFactory for persistence unit 'default'\n")
            );
            add(new Log(dateFormat.parse("23:30:06.130"),
                    Level.toLevel("INFO"),
                    "o.s.t.c.t.TransactionContext - Began transaction (1) for test context [DefaultTestContext@591a4f8e testClass = EventDAOTest,\n" +
                            "testInstance = cz.muni.pa036.logging.dao.EventDAOTest@2aa5fe93, testMethod = createUninitializedEvent@EventDAOTest, testException = [null],\n" +
                            "mergedContextConfiguration = [MergedContextConfiguration@2f0ed952 testClass = EventDAOTest,\n locations = '{classpath:dao-test-context.xml}',\n" +
                            "classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}',\n" +
                            "contextCustomizers = set[[empty]], contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]]];\n" +
                            "transaction manager [org.springframework.orm.jpa.JpaTransactionManager@380e1909]; rollback [true]")
            );
        }};
    }

}
