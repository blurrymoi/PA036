package cz.muni.pa036.logging.controller;

import cz.muni.pa036.logging.controller.advisers.ControllerAdviser;
import cz.muni.pa036.logging.dto.SportsmanDTO;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.facade.EventFacade;
import cz.muni.pa036.logging.facade.InvitationFacade;
import cz.muni.pa036.logging.facade.SportsmanFacade;
import cz.muni.pa036.logging.log.LogFile;
import cz.muni.pa036.logging.log.LogFileDiff;
import cz.muni.pa036.logging.service.BeanMappingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

//TODO: rewrite using real methods from SportsmanController.
//then uncomment this
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:controller-test-context.xml")
public class SportsmanControllerTest {

    private LogFile logFile;

    @InjectMocks
    private SportsmanController sportsmanController = new SportsmanController();

    @Mock
    private EventFacade eventFacade = Mockito.mock(TestUtils.EventFacadeStub.class);

    @Mock
    private SportsmanFacade sportsmanFacade = Mockito.mock(TestUtils.SportsmanFacadeStub.class);

    @Mock
    private InvitationFacade invitationFacade = Mockito.mock(TestUtils.InvitationFacadeStub.class);

    @Mock
    private BeanMappingService beanMappingService = Mockito.mock(TestUtils.BeanMappingServiceStub.class);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        MockMvcBuilders
                .standaloneSetup(sportsmanController)
                .setControllerAdvice(new ControllerAdviser())
                .build();
    }

    @Test
    public void whenCreateMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doThrow(new CreateException("Create failed", null, null)).when(sportsmanFacade).create(any());

        logFile.cleanLogFile();

        // insert SportsmanController method

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenCreateMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doReturn(new SportsmanDTO()).when(sportsmanFacade).create(any());

        logFile.cleanLogFile();

        // insert SportsmanController method

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenUpdateMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doThrow(new UpdateException("Create failed", null, null)).when(sportsmanFacade).update(any());

        logFile.cleanLogFile();

        // insert SportsmanController method

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenUpdateMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doNothing().when(sportsmanFacade).update(any());

        logFile.cleanLogFile();

        // insert SportsmanController method

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenDeleteMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doReturn(new SportsmanDTO()).when(sportsmanFacade).findBySubstring(anyString(), anyLong());
        doThrow(new DeleteException("Delete failed", null, null)).when(sportsmanFacade).delete(any());

        logFile.cleanLogFile();

        // insert SportsmanController method

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenDeleteMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doReturn(new SportsmanDTO()).when(sportsmanFacade).findBySubstring(anyString(), anyLong());
        doNothing().when(sportsmanFacade).delete(any());

        logFile.cleanLogFile();

        // insert SportsmanController method

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenFindByIDMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doThrow(new FindByException("FindBy ID failed", null, null)).when(sportsmanFacade).findBySubstring(anyString(), anyLong());

        logFile.cleanLogFile();

        // insert SportsmanController method

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenFindByIDMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doReturn(new SportsmanDTO()).when(sportsmanFacade).findBySubstring(anyString(), anyLong());

        logFile.cleanLogFile();

        // insert SportsmanController method

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }
}
