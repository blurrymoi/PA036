package cz.muni.pa036.logging;

import cz.muni.pa036.logging.dao.EventDAO;
import cz.muni.pa036.logging.dao.InvitationDAO;
import cz.muni.pa036.logging.dao.ResultDAO;
import cz.muni.pa036.logging.dao.SportDAO;
import cz.muni.pa036.logging.dao.SportsmanDAO;
import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Invitation;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;
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
    private InvitationService invitationService = new InvitationServiceImpl();
    private ResultService resultService = new ResultServiceImpl();
    private SportService sportService = new SportServiceImpl();
    private SportsmanService sportsmanService = new SportsmanServiceImpl();

    @Mock
    private EventDAO eventDAO;
    @Mock
    private InvitationDAO invitationDAO;
    private ResultDAO resultDAO;
    private SportDAO sportDAO;
    private SportsmanDAO sportsmanDAO;

    private Event event;
    private Invitation invitation;
    private Sportsman sportsman;

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

    @Test(expectedExceptions = FindByException.class)
    public void inviteInvitationExceptionTest() {
        doThrow(new TestException()).when(invitationDAO).create(any());
        doThrow(new TestException()).when(eventDAO).findById(any());

        invitationService.invite(1L, 1L);
        fail("FindByException expected in invite (eventID).");
    }

    @Test(expectedExceptions = FindByException.class)
    public void inviteInvitationExceptionIllegalEventNullTest() {
        doThrow(new TestException()).when(invitationDAO).create(any());
        Mockito.when(eventDAO.findById(1L)).thenReturn(null);

        invitationService.invite(1L, 1L);
        fail("FindByException expected in invite (eventID = null).");
    }

    @Test(expectedExceptions = FindByException.class)
    public void inviteInvitationExceptionIllegalInvitationNullTest() {
        doThrow(new TestException()).when(invitationDAO).findById(any());

        invitationService.invite(1L, 1L);
        fail("FindByException expected in invite (invitationID).");
    }

    @Test(expectedExceptions = FindByException.class)
    public void inviteInvitationExceptionSportsmanNullTest() {
        //fail at sportsman lookup
        invitationService.invite(1L, 1L);
        fail("FindByException expected in invite (invitationID).");
    }

    @Test(expectedExceptions = CreateException.class)
    public void createInvitationExceptionTest() {
        doThrow(new TestException()).when(invitationDAO).create(any());

        invitationService.invite(1L, 1L);
        fail("CreateException expected in invite.");
    }

    /* RESULT */

    /* SPORT */

    /* SPORTSMAN */

}

class TestException extends RuntimeException{}
