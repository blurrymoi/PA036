package cz.muni.pa036.logging.controller;

import cz.muni.pa036.logging.controller.rest.exception.ExistingResourceException;
import cz.muni.pa036.logging.controller.rest.exception.ResourceDeleteException;
import cz.muni.pa036.logging.controller.rest.exception.ResourceNotFoundException;
import cz.muni.pa036.logging.controller.rest.exception.ResourceNotModifiedException;
import cz.muni.pa036.logging.controller.utils.JSONifier;
import cz.muni.pa036.logging.controller.utils.MockConfiguration;
import cz.muni.pa036.logging.controller.utils.TestObjectsProvider;
import cz.muni.pa036.logging.dto.EventDTO;
import cz.muni.pa036.logging.dto.EventUpdateDTO;
import cz.muni.pa036.logging.dto.ResultDTO;
import cz.muni.pa036.logging.dto.SportsmanDTO;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.facade.EventFacade;
import cz.muni.pa036.logging.facade.ResultFacade;
import cz.muni.pa036.logging.facade.SportsmanFacade;
import cz.muni.pa036.logging.log.LogFile;
import cz.muni.pa036.logging.log.LogFileDiff;
import cz.muni.pa036.logging.logService.LogLoader;
import cz.muni.pa036.logging.service.LoggerService;
import cz.muni.pa036.logging.utils.LoggerConfiguration;
import cz.muni.pa036.logging.utils.LoggerModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Import({MockConfiguration.class})
@ContextConfiguration("classpath:controller-test-context.xml")
public class EventsRESTControllerTest {

    private LogFile logFile;

    private EventDTO eventDTO;
    private EventUpdateDTO eventUpdateDTO;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private EventFacade eventFacade;

    @Autowired
    private SportsmanFacade sportsmanFacade;

    @Autowired
    private ResultFacade resultFacade;

    private MockMvc mockMvc;

    private boolean isDebugEnabled;

    @Autowired
    private LoggerService loggerService;

    @Before
    public void setUp() throws Exception {
        LoggerModel model = loggerService.getLoggerModel();
        model.setPa036Level(Level.DEBUG);
        model.setHibernateSQLLevel(Level.DEBUG);
        loggerService.updateLoggingOptions(model);

        isDebugEnabled = LoggerConfiguration.getLoggerModel().getPa036Level() == Level.DEBUG
                || LoggerConfiguration.getLoggerModel().getPa036Level() ==Level.TRACE;

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity())
                .build();

        Mockito.reset(eventFacade);

        eventDTO = TestObjectsProvider.createEventDTO();
        eventUpdateDTO = TestObjectsProvider.createEventUpdateDTO();

        // configure other facades
        doReturn(new SportsmanDTO()).when(sportsmanFacade).getByEmail(anyString());
        doReturn(new ArrayList<ResultDTO>()).when(resultFacade).findByEvent(any());

        // why the fuck is this not needed?
        //logFile = LogLoader.loadLogFile(LoggerConfiguration.getLogFile().replace(".log", "-test.log"), true);
        logFile = LogLoader.loadLogFile(LoggerConfiguration.getLogFile(), true);
        logFile.cleanLogFile();
    }

    @Test
    public void whenCreateMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doThrow(new CreateException("Create failed", null, null)).when(eventFacade).create(any());

        logFile.cleanLogFile();
        try {
            this.mockMvc.perform(post("/rest/events/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSONifier.toJSON(eventDTO)))
                    .andDo(MockMvcResultHandlers.print());
        } catch (ExistingResourceException ex) {}
        LogFileDiff diff = logFile.reloadLogFile();

        if (isDebugEnabled) {
            Assert.assertNotEquals(0.0, logFile.getFileSize());
            Assert.assertNotNull(diff);
            Assert.assertFalse(diff.getLogs().isEmpty());
        } else {
            Assert.assertEquals(new Long(0), logFile.getFileSize());
            Assert.assertNull(diff);
        }
    }

    @Ignore
    @Test
    public void whenCreateMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doReturn(new EventDTO()).when(eventFacade).create(any());

        try {
            this.mockMvc.perform(post("/rest/events/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSONifier.toJSON(eventDTO)))
                    .andDo(MockMvcResultHandlers.print());
        } catch (ExistingResourceException ex) {}

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNull(diff);
    }

    @Test
    public void whenUpdateMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doThrow(new UpdateException("Update failed", null, null)).when(eventFacade).update(any());

        logFile.cleanLogFile();
        try {
            this.mockMvc.perform(put("/rest/events/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSONifier.toJSON(eventUpdateDTO)))
                    .andDo(MockMvcResultHandlers.print());
        } catch (ResourceNotModifiedException | ResourceNotFoundException ex) {}

        LogFileDiff diff = logFile.reloadLogFile();

        if (isDebugEnabled) {
            Assert.assertNotEquals(0.0, logFile.getFileSize());
            Assert.assertNotNull(diff);
            Assert.assertFalse(diff.getLogs().isEmpty());
        } else {
            Assert.assertEquals(new Long(0), logFile.getFileSize());
            Assert.assertNull(diff);
        }
    }

    @Ignore
    @Test
    public void whenUpdateMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doNothing().when(eventFacade).update(any());

        try {
            this.mockMvc.perform(put("/rest/events/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSONifier.toJSON(eventUpdateDTO)))
                    .andDo(MockMvcResultHandlers.print());
        } catch (ResourceNotModifiedException | ResourceNotFoundException ex) {}

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNull(diff);
    }

    @Ignore
    @Test
    public void whenDeleteMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doReturn(new EventDTO()).when(eventFacade).findById(1L);
        doThrow(new DeleteException("Delete failed", null, null)).when(eventFacade).delete(any());

        try {
            this.mockMvc.perform(delete("/1"))
                    .andDo(MockMvcResultHandlers.print());
        } catch (ResourceDeleteException ex) {}

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenDeleteMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doReturn(new EventDTO()).when(eventFacade).findById(1L);
        doNothing().when(eventFacade).delete(any());

        try {
            this.mockMvc.perform(delete("/1"))
                    .andDo(MockMvcResultHandlers.print());
        } catch (ResourceDeleteException ex) {}

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNull(diff);
    }

    @Ignore
    @Test
    public void whenFindByIDMethodOnFacadeThrowsException_thenThisExceptionIsLoggedUsingControllerAdviser() throws Exception {
        doThrow(new FindByException("FindBy ID failed", null, null)).when(eventFacade).findById(any());

        try {
            this.mockMvc.perform(get("/1"))
                    .andDo(MockMvcResultHandlers.print());
        } catch (ResourceNotFoundException ex) {}

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNotNull(diff);
        Assert.assertFalse(diff.getLogs().isEmpty());
    }

    @Test
    public void whenFindByIDMethodOnFacadeDoesNotThrowException_thenNothingInterestingHappens() throws Exception {
        doReturn(new EventDTO()).when(eventFacade).findById(1L);

        try {
            this.mockMvc.perform(get("/1"))
                    .andDo(MockMvcResultHandlers.print());
        } catch (ResourceNotFoundException ex) {}

        LogFileDiff diff = logFile.reloadLogFile();

        Assert.assertNotEquals(0.0, logFile.getFileSize());
        Assert.assertNull(diff);
    }
}
