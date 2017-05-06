package cz.muni.pa036.logging.logTests;

import cz.muni.pa036.logging.log.LogFile;
import cz.muni.pa036.logging.log.LogFileDiff;
import cz.muni.pa036.logging.logService.LogLoader;
import cz.muni.pa036.logging.service.SportService;
import cz.muni.pa036.logging.utils.LoggerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.security.Principal;
import java.util.*;

/**
 * @author Kamil Triscik
 * @since 4/1/17
 */
@Transactional
@ContextConfiguration(locations = "classpath:service-context.xml")
public class BasicLogFileTest extends AbstractTestNGSpringContextTests {

    private final Logger LOGGER = LoggerFactory.getLogger(BasicLogFileTest.class);

    private static final String TEST_LOG_FILE_NAME = "persistence-test.log";

    @Autowired
    SportService sportService;

    @BeforeClass
    public void beforeClass() {
        List<Path> logFiles = getLogFilesInUserDir();

        for (Path logFile : logFiles) {
            if (Files.exists(logFile)) {
                deleteOfFailQuietly(logFile);

                // if it still exists, try to set permissions and delete again
                if (Files.exists(logFile)) {
                    setPermissions(logFile);
                    deleteOfFailQuietly(logFile);

                    // if it still exists, fuck it
                    if (Files.exists(logFile)) {
                        LOGGER.warn("Failed to delete \"" + logFile + "\" before test, continuing nevertheless...");
                    }
                }
            }
        }
    }

    @Test
    public void CreateLogFileTest() throws IOException {
        sportService.findAll();

        List<Path> logFile = getFilesInDir(LoggerConfiguration.getLogFile().substring(0, LoggerConfiguration.getLogFile().lastIndexOf(File.separator)),
                LoggerConfiguration.getLogFile().substring(LoggerConfiguration.getLogFile().lastIndexOf(File.separator) + 1));

        Assert.assertNotNull(logFile,
                "{user.dir} should contain the specified *.log file!");

        Assert.assertEquals(1, logFile.size(),
                "{user.dir} should contain ONLY the specified *.log file!");
    }

    @Test
    public void LogFileChangedTest() {
        sportService.findAll();
        LogFile logFile;
        try {
            String testLogfileName = System.getProperty("user.dir") + File.separator + TEST_LOG_FILE_NAME;
                    logFile = LogLoader.loadLogFile(testLogfileName);
            sportService.findAll();
            LogFileDiff logFileDiff = logFile.reloadLogFile();
            Assert.assertTrue(logFileDiff.getFileSize() > 0, "Log file diff size should be greater than 0!");
            Assert.assertFalse(logFileDiff.getLogs().isEmpty(), "Log file diff logs should not be empty");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Path> getLogFilesInUserDir() {
        return getFilesInDir(System.getProperty("user.dir"), ".log");
    }

    private List<Path> getFilesInDir(String directory, String filename) {
        Path dir = Paths.get(directory);
        Assert.assertNotNull(dir);

        DirectoryStream<Path> directoryStream;
        try {
            directoryStream = Files.newDirectoryStream(dir);
        } catch (IOException e) {
            LOGGER.debug("Exception occurred, ignoring it: " + e.toString());
            return new ArrayList<>();
        }

        List<Path> files = new ArrayList<>();

        for (Path file : directoryStream) {
            if (file.toString().toLowerCase().endsWith(filename)) {
                files.add(file);
            }
        }
        LOGGER.debug("Found following files matching \"" + filename
                + "\" in directory \"" + directory + "\": " + files);
        return files;
    }

    // not useful here, but I am keeping this in case it will be needed
    private void setPermissions(Path path) {
        AclFileAttributeView view = Files.getFileAttributeView(path, AclFileAttributeView.class);
        AclEntry.Builder builder = AclEntry.newBuilder();

        List<AclEntry> aclEntries;
        try {
            Principal fileOwner = Files.getOwner(path);

            List<AclEntry> acls = view.getAcl();
            for (int i = 0; i < acls.size(); i++) {
                UserPrincipal principal = acls.get(i).principal();
                if (principal.equals(fileOwner)) {
                    // get the current permissions
                    Set<AclEntryPermission> permissions = acls.get(i).permissions();
                    // add DELETE permission
                    permissions.addAll(EnumSet.allOf(AclEntryPermission.class));

                    // create a new ACL entry
                    AclEntry entry = AclEntry.newBuilder()
                            .setType(AclEntryType.ALLOW)
                            .setPrincipal(principal)
                            .setPermissions(permissions)
                            .build();

                    // replace the ACL entry for authenticated users
                    acls.set(i, entry);
                }
            }
            // set the updated list of ACLs
            view.setAcl(acls);
        } catch (IOException e) {
            LOGGER.error("Error setting file permissions, test output might be affected.");
        }
    }

    private void deleteOfFailQuietly(Path logFile) {
        try {
            Files.delete(logFile);
        } catch (IOException e) {
            // do nothing
        }
    }
}
