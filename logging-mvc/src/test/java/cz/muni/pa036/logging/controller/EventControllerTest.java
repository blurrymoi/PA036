package cz.muni.pa036.logging.controller;

import cz.muni.pa036.logging.controller.advisers.ControllerAdviser;
import cz.muni.pa036.logging.dto.*;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.facade.*;
import cz.muni.pa036.logging.log.LogFile;
import cz.muni.pa036.logging.log.LogFileDiff;
import cz.muni.pa036.logging.logService.LogLoader;
import cz.muni.pa036.logging.service.BeanMappingService;
import cz.muni.pa036.logging.utils.LoggerConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.ArrayList;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:controller-test-context.xml")
public class EventControllerTest {

    private LogFile logFile;

    @InjectMocks
    private EventController eventController = new EventController();

    @Mock
    private EventFacade eventFacade = Mockito.mock(TestUtils.EventFacadeStub.class);

    @Mock
    private SportFacade sportFacade = Mockito.mock(TestUtils.SportFacadeStub.class);

    @Mock
    private SportsmanFacade sportsmanFacade = Mockito.mock(TestUtils.SportsmanFacadeStub.class);

    @Mock
    private ResultFacade resultFacade = Mockito.mock(TestUtils.ResultFacadeStub.class);

    @Mock
    private InvitationFacade invitationFacade = Mockito.mock(TestUtils.InvitationFacadeStub.class);

    @Mock
    private BeanMappingService beanMappingService = Mockito.mock(TestUtils.BeanMappingServiceStub.class);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        MockMvcBuilders
                .standaloneSetup(eventController)
                .setControllerAdvice(new ControllerAdviser())
                .build();

        // configure other facades
        doReturn(new SportsmanDTO()).when(sportsmanFacade).getByEmail(anyString());
        doReturn(new ArrayList<ResultDTO>()).when(resultFacade).findByEvent(any());

        logFile = LogLoader.loadLogFile(LoggerConfiguration.getLogFile().replace(".log", "-test.log"), true);
        logFile.cleanLogFile();
    }

    @Test
    public void whenCreateMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doThrow(new CreateException("Create failed", null, null)).when(eventFacade).create(any());

        try {
            eventController.processCreate(
                    new EventCreateDTO(),
                    new DirectFieldBindingResult(null, null),
                    new BindingAwareModelMap()
            );
            fail();
        } catch (CreateException e) {}

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenCreateMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doReturn(new EventDTO()).when(eventFacade).create(any());

        eventController.processCreate(
                new EventCreateDTO(),
                new DirectFieldBindingResult(null, null),
                new BindingAwareModelMap()
        );

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenUpdateMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doThrow(new UpdateException("Update failed", null, null)).when(eventFacade).update(any());

        try {
            eventController.processUpdate(
                    new EventUpdateDTO(),
                    new DirectFieldBindingResult(null, null),
                    new BindingAwareModelMap()
            );
            fail();
        } catch (UpdateException e) {}

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenUpdateMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doNothing().when(eventFacade).update(any());

        eventController.processUpdate(
                new EventUpdateDTO(),
                new DirectFieldBindingResult(null, null),
                new BindingAwareModelMap()
        );

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenDeleteMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doReturn(new EventDTO()).when(eventFacade).findById(1L);
        doThrow(new DeleteException("Delete failed", null, null)).when(eventFacade).delete(any());

        try {
            eventController.renderDelete(1L);
            fail();
        } catch (DeleteException e) {}

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenDeleteMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doReturn(new EventDTO()).when(eventFacade).findById(1L);
        doNothing().when(eventFacade).delete(any());

        eventController.renderDelete(1L);

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenFindByIDMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doThrow(new FindByException("FindBy ID failed", null, null)).when(eventFacade).findById(any());

        try {
            eventController.renderDetail(1L, new BindingAwareModelMap(), new UsernamePasswordAuthenticationToken(null, null));
            fail();
        } catch (FindByException e) {}

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenFindByIDMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doReturn(new EventDTO()).when(eventFacade).findById(1L);

        eventController.renderDetail(1L, new BindingAwareModelMap(), new UsernamePasswordAuthenticationToken(null, null));

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }
}
