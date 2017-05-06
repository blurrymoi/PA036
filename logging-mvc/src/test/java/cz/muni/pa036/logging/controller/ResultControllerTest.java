package cz.muni.pa036.logging.controller;

import cz.muni.pa036.logging.controller.advisers.ControllerAdviser;
import cz.muni.pa036.logging.dto.ResultDTO;
import cz.muni.pa036.logging.dto.ResultUpdateDTO;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.facade.EventFacade;
import cz.muni.pa036.logging.facade.ResultFacade;
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
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

//TODO: rewrite using real methods from SportsmanController.
//then uncomment this
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:controller-test-context.xml")
public class ResultControllerTest {

    private LogFile logFile;

    @InjectMocks
    private ResultController resultController = new ResultController();

    @Mock
    private EventFacade eventFacade = Mockito.mock(TestUtils.EventFacadeStub.class);

    @Mock
    private ResultFacade resultFacade = Mockito.mock(TestUtils.ResultFacadeStub.class);

    @Mock
    private SportsmanFacade sportsmanFacade = Mockito.mock(TestUtils.SportsmanFacadeStub.class);

    @Mock
    private BeanMappingService beanMappingService = Mockito.mock(TestUtils.BeanMappingServiceStub.class);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        MockMvcBuilders
                .standaloneSetup(resultController)
                .setControllerAdvice(new ControllerAdviser())
                .build();
    }

    @Test
    public void whenUpdateMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doThrow(new UpdateException("Create failed", null, null)).when(resultFacade).update(any());

        logFile.cleanLogFile();

        resultController.processUpdate(
                new ResultUpdateDTO(),
                new DirectFieldBindingResult(null, null),
                new BindingAwareModelMap()
        );

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenUpdateMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doNothing().when(resultFacade).update(any());

        logFile.cleanLogFile();

        resultController.processUpdate(
                new ResultUpdateDTO(),
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
        doReturn(new ResultDTO()).when(resultFacade).findById(1L);
        doThrow(new DeleteException("Delete failed", null, null)).when(resultFacade).delete(any());

        logFile.cleanLogFile();

        resultController.renderDelete(1L);

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenDeleteMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doReturn(new ResultDTO()).when(resultFacade).findById(1L);
        doNothing().when(resultFacade).delete(any());

        logFile.cleanLogFile();

        resultController.renderDelete(1L);

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenFindByIDMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doThrow(new FindByException("FindBy ID failed", null, null)).when(resultFacade).findById(any());

        logFile.cleanLogFile();

        resultController.renderDelete(1L);

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenFindByIDMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doReturn(new ResultDTO()).when(resultFacade).findById(1L);

        logFile.cleanLogFile();

        resultController.renderDelete(1L);

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }
}
