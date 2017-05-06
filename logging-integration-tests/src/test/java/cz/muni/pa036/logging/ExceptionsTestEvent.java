package cz.muni.pa036.logging;

import cz.muni.pa036.logging.dao.EventDAO;
import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.service.EventService;
import cz.muni.pa036.logging.service.EventServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.testng.Assert.fail;

/**
 * @author Kamil Triscik et Zuzana Baranova.
 */
@ContextConfiguration(locations = "classpath:integration-tests-context.xml")
public class ExceptionsTestEvent extends AbstractTestNGSpringContextTests {

    @InjectMocks
    private EventService eventService = new EventServiceImpl();

    @Mock
    private EventDAO eventDAO;

    private Event event;
    private Sport sport;
    private Sportsman sportsman;
    private Calendar date;

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        sport = new Sport();
        sport.setName("sport");
        sport.setDescription("desc");

        event = new Event();
        String eventName = "ev";
        event.setName(eventName);
        String description = "New event= name";
        event.setDescription(description);
        date = new GregorianCalendar(2016,1,31);
        event.setDate(date);
        event.setAdmin(sportsman);
        Integer capacity = 5;
        event.setCapacity(capacity);
        event.setSport(sport);
        String address = "Address";
        event.setAddress(address);
        String city = "city";
        event.setCity(city);
        event.addParticipant(sportsman);

        Mockito.when(eventDAO.findById(1L)).thenReturn(event);
        Mockito.when(eventDAO.findById(argThat(not(1L)))).thenReturn(null);
        Mockito.when(eventDAO.findByName("ev")).thenReturn(Collections.singletonList(event));
        Mockito.when(eventDAO.findByName(argThat(not("ev")))).thenReturn(new ArrayList<>());
        Mockito.when(eventDAO.findBySport(sport)).thenReturn(Collections.singletonList(event));
        Mockito.when(eventDAO.findBySport(argThat(not(sport)))).thenReturn(new ArrayList<>());
        Mockito.when(eventDAO.findByCity("ev")).thenReturn(Collections.singletonList(event));
        Mockito.when(eventDAO.findByCity(argThat(not("ev")))).thenReturn(new ArrayList<>());
        Mockito.when(eventDAO.findByAdmin(sportsman)).thenReturn(Collections.singletonList(event));
        Mockito.when(eventDAO.findByAdmin(argThat(not(sportsman)))).thenReturn(new ArrayList<>());
        Mockito.when(eventDAO.findByParticipant(sportsman)).thenReturn(Collections.singletonList(event));
        Mockito.when(eventDAO.findByParticipant(argThat(not(sportsman)))).thenReturn(new ArrayList<>());
        Mockito.when(eventDAO.findByDate(date)).thenReturn(Collections.singletonList(event));
        Mockito.when(eventDAO.findByDate(argThat(not(date)))).thenReturn(new ArrayList<>());
        Mockito.when(eventDAO.findAll()).thenReturn(Collections.singletonList(event));

    }

    @Test(expectedExceptions = CreateException.class)
    public void createNullObjectExceptionThrowTest() {
        //initialize mockito for this method
        //in case of calling create method with null argument
        //on eventDAO via eventService throw CreateException
        doThrow(new CreateException(null, null, null))
                .when(eventDAO)
                .create(null);
        eventService.create(null);
    }

    @Test(expectedExceptions = CreateException.class)
    public void testExceptionCreate() throws Exception {
        doThrow(new TestException()).when(eventDAO).create(any());

        eventService.create(new Event());
        fail("Expected FindByException due to null argument!");
    }

    @Test(expectedExceptions = UpdateException.class)
    public void testExceptionUpdate() {
        doThrow(new TestException()).when(eventDAO).update(any());

        eventService.update(new Event());
        fail();
    }

    //no workies
    @Test(expectedExceptions = DeleteException.class)
    public void testExceptionDelete() {
        doThrow(new TestException()).when(eventDAO).delete(any());
        event.setId(1L);

        eventService.delete(event);
        fail();
    }

    @Test(expectedExceptions = FindByException.class)
    public void testExceptionFindByID() {
        doThrow(new TestException()).when(eventDAO).findById(null);

        eventService.findById(null);
        fail();
    }

    @Test(expectedExceptions = FindByException.class)
    public void testExceptionFindByName() {
        doThrow(new TestException()).when(eventDAO).findByName(any());

        eventService.findByName(any());
        fail();
    }

    @Test(expectedExceptions = FindByException.class)
    public void testExceptionFindByDate() {
        doThrow(new TestException()).when(eventDAO).findByDate(any());

        eventService.findByDate(any());
        fail();
    }

    @Test(expectedExceptions = FindByException.class)
    public void testExceptionFindBySport() {
        doThrow(new TestException()).when(eventDAO).findBySport(any());

        eventService.findBySport(any());
        fail();
    }

    @Test(expectedExceptions = FindByException.class)
    public void testExceptionFindByCity() {
        doThrow(new TestException()).when(eventDAO).findByCity(any());

        eventService.findByCity(any());
        fail();
    }

    @Test(expectedExceptions = FindByException.class)
    public void testExceptionFindByAdmin() {
        doThrow(new TestException()).when(eventDAO).findByAdmin(any());

        eventService.findByAdmin(any());
        fail();
    }

    @Test(expectedExceptions = FindByException.class)
    public void testExceptionFindByParticipant() {
        doThrow(new TestException()).when(eventDAO).findByParticipant(any());

        eventService.findByParticipant(any());
        fail();
    }

    @Test(expectedExceptions = FindByException.class)
    public void testExceptionFindAll() {
        doThrow(new TestException()).when(eventDAO).findAll();

        eventService.findAll();
        fail();
    }

    @Test(expectedExceptions = FindByException.class)
    public void createFindByNullObjectExceptionThrowLogTest() throws Exception {
        doThrow(new FindByException(null, null, null, null))
                .when(eventDAO)
                .findByAdmin(null);

        eventService.findByAdmin(null);
        fail("Expected FindByException due to null argument!");
    }

}

class TestException extends RuntimeException{}
