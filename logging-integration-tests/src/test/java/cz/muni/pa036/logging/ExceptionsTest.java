package cz.muni.pa036.logging;

import cz.muni.pa036.logging.dao.EventDAO;
import cz.muni.pa036.logging.dao.InvitationDAO;
import cz.muni.pa036.logging.dao.ResultDAO;
import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Invitation;
import cz.muni.pa036.logging.entity.Result;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.service.*;
import cz.muni.pa036.logging.utils.InvitationState;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.testng.Assert.fail;

/**
 * @author Kamil Triscik et Zuzana Baranova.
 */
@ContextConfiguration(locations = "classpath:integration-tests-context.xml")
public class ExceptionsTest extends AbstractTestNGSpringContextTests {

    @InjectMocks
    private EventService eventService = new EventServiceImpl();
    @InjectMocks
    private InvitationService invitationService = new InvitationServiceImpl();
    @InjectMocks
    private ResultService resultService = new ResultServiceImpl();

    @Mock
    private EventDAO eventDAO;
    @Mock
    private InvitationDAO invitationDAO;
    @Mock
    private ResultDAO resultDAO;

    private Event event;
    private Invitation invitation;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        event = new Event();
        event.setId(1L);
        invitation = new Invitation();
        invitation.setState(InvitationState.DECLINED);
        Mockito.when(eventDAO.findById(1L)).thenReturn(event);
        Mockito.when(invitationDAO.findById(1L)).thenReturn(invitation);
    }

    /***** EVENT *****/

    @Test(expectedExceptions = CreateException.class)
    public void createNullObjectExceptionThrowTest() {
        doThrow(new CreateException(null, null, null))
                .when(eventDAO)
                .create(null);
        eventService.create(null);
        fail("CreateException expected in create(null).");
    }

    @Test(expectedExceptions = CreateException.class)
    public void createEventExceptionTest() {
        doThrow(new TestException()).when(eventDAO).create(any());

        eventService.create(new Event());
        fail("CreateException expected.");
    }

    @Test(expectedExceptions = UpdateException.class)
    public void updateEventExceptionTest() {
        doThrow(new TestException()).when(eventDAO).update(any());

        eventService.update(new Event());
        fail("UpdateException expected.");
    }

    /**
     * Test disabled; notificationService is null because of DAO layer abstraction.
     */
    @Test(enabled=false, expectedExceptions = DeleteException.class)
    public void deleteEventExceptionTest() {
        doThrow(new TestException()).when(eventDAO).delete(any());
        Event event = new Event();
        event.setId(1L);

        eventService.delete(event);
        fail();
    }

    @Test(expectedExceptions = FindByException.class)
    public void findByIDEventExceptionTest() {
        doThrow(new TestException()).when(eventDAO).findById(null);

        eventService.findById(null);
        fail("FindByException expected in findById.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findByNameEventExceptionTest() {
        doThrow(new TestException()).when(eventDAO).findByName(any());

        eventService.findByName(any());
        fail("FindByException expected in findByName.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findByDateEventExceptionTest() {
        doThrow(new TestException()).when(eventDAO).findByDate(any());

        eventService.findByDate(any());
        fail("FindByException expected in findByDate.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findBySportEventExceptionTest() {
        doThrow(new TestException()).when(eventDAO).findBySport(any());

        eventService.findBySport(any());
        fail("FindByException expected in findBySport.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findByCityEventExceptionTest() {
        doThrow(new TestException()).when(eventDAO).findByCity(any());

        eventService.findByCity(any());
        fail("FindByException expected in findByCity.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findByAdminEventExceptionTest() {
        doThrow(new TestException()).when(eventDAO).findByAdmin(any());

        eventService.findByAdmin(any());
        fail("FindByException expected in findByAdmin.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findByParticipantEventExceptionTest() {
        doThrow(new TestException()).when(eventDAO).findByParticipant(any());

        eventService.findByParticipant(any());
        fail("FindByException expected in findByParticipant.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findAllEventExceptionTest() {
        doThrow(new TestException()).when(eventDAO).findAll();

        eventService.findAll();
        fail("FindByException expected in findAll.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void createFindByNullObjectExceptionTest() {
        doThrow(new FindByException(null, null, null, null))
                .when(eventDAO)
                .findByAdmin(null);

        eventService.findByAdmin(null);
        fail("Expected FindByException due to null argument!");
    }

    /***** INVITATION *****/
    /**
     * Invitation has EventDAO abstracted away.
     */

    @Test(expectedExceptions = FindByException.class)
    public void inviteInvitationExceptionTest() {
        doThrow(new TestException()).when(invitationDAO).create(any());
        doThrow(new TestException()).when(eventDAO).findById(any());

        invitationService.invite(1L, 1L);
        fail("FindByException expected in invite (eventID).");
    }

    /***** RESULT *****/

    @Test(expectedExceptions = CreateException.class)
    public void createResultExceptionTest() {
        doThrow(new TestException()).when(resultDAO).create(any());

        resultService.create(new Result());
        fail("CreateException expected.");
    }

    @Test(expectedExceptions = UpdateException.class)
    public void updateResultExceptionTest() {
        doThrow(new TestException()).when(resultDAO).update(any());

        resultService.update(new Result());
        fail("UpdateException expected.");
    }

    @Test(expectedExceptions = DeleteException.class)
    public void deleteResultExceptionTest() {
        doThrow(new TestException()).when(resultDAO).delete(any());

        resultService.delete(new Result());
        fail();
    }


    @Test(expectedExceptions = FindByException.class)
    public void findByIDResultExceptionTest() {
        doThrow(new TestException()).when(resultDAO).findById(any());

        resultService.findById(null);
        fail("FindByException expected in findById.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findAllResultExceptionTest() {
        doThrow(new TestException()).when(resultDAO).findAll();

        resultService.findAll();
        fail("FindByException expected in findAll.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findBySportsmanResultExceptionTest() {
        doThrow(new TestException()).when(resultDAO).findBySportsman(any());

        resultService.findBySportsman(any());
        fail("FindByException expected in findBySportsman.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findByEventResultExceptionTest() {
        doThrow(new TestException()).when(resultDAO).findByEvent(any());

        resultService.findByEvent(any());
        fail("FindByException expected in findByEvent.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findBySportsmanEventResultExceptionTest() {
        doThrow(new TestException()).when(resultDAO).findBySportsmanAndEvent(any(), any());

        resultService.findBySportsmanAndEvent(any(), any());
        fail("FindByException expected in findBySportsmanAndEvent.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findBySportResultExceptionTest() {
        doThrow(new TestException()).when(resultDAO).findBySport(any());

        resultService.findBySport(any());
        fail("FindByException expected in findBySport.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findByPerformanceResultExceptionTest() {
        doThrow(new TestException()).when(resultDAO).findByPerformance(any());

        resultService.findByPerformance(any());
        fail("FindByException expected in findByPerformance.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findByPositionResultExceptionTest() {
        doThrow(new TestException()).when(resultDAO).findByPosition(any());

        resultService.findByPosition(any());
        fail("FindByException expected in findByPosition.");
    }

    @Test(expectedExceptions = FindByException.class)
    public void findByNoteResultExceptionTest() {
        doThrow(new TestException()).when(resultDAO).findByNote(any());

        resultService.findByNote(any());
        fail("FindByException expected in findByNote.");
    }
}

class TestException extends RuntimeException{}
