package cz.muni.pa036.logging.layersTests.serviceLayer;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.List;

import static org.testng.Assert.fail;

/**
 * @author Kamil Triscik.
 */
@Transactional
public class EventServiceTest extends ServiceLayerTest {

    /**
     * Note just for this class.
     * Autowired Service + "Impl"
     */
    private final String className = "EventServiceImpl";

    @Autowired
    EventService eventService;

    @Test
    public void createEventTest() throws Exception {
        Event event = this.getEvent();
        logFile.cleanLogFile();
        try {
            eventService.create(event);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", event.toString());
    }

    @Test
    public void createNullEventTest() throws Exception {
        try {
            eventService.create(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", "null");
    }

    @Test
    public void findByIdTest() throws Exception {
        final String param = "ID";
        final Long value = 1L;
        eventService.findById(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullIdTest() throws Exception {
        final String param = "ID";
        final String value = null;
        try {
            eventService.findById(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNameTest() throws Exception {
        final String param = "name";
        final String value = "name";
        eventService.findByName(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNullNameTest() throws Exception {
        final String param = "name";
        final String value = null;
        try {
            eventService.findByName(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByDateTest() throws Exception {
        final String param = "date";
        final Calendar value = Calendar.getInstance();
        eventService.findByDate(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullDateTest() throws Exception {
        final String param = "date";
        final String value = null;
        try {
            eventService.findByDate(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findBySportTest() throws Exception {
        final String param = "sport";
        final Sport value = eventService.findAll().get(0).getSport();
        logFile.cleanLogFile();
        eventService.findBySport(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullSportTest() throws Exception {
        final String param = "sport";
        final String value = null;
        try {
            eventService.findBySport(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByCityTest() throws Exception {
        final String param = "city";
        final String value = "someCity";
        eventService.findByCity(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNullCityTest() throws Exception {
        final String param = "city";
        final String value = null;
        try {
            eventService.findByCity(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByAdminTest() throws Exception {
        final String param = "admin";
        final Sportsman value = eventService.findAll().get(0).getAdmin();
        logFile.cleanLogFile();
        eventService.findByAdmin(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullAdminTest() throws Exception {
        final String param = "admin";
        final String value = null;
        try {
            eventService.findByAdmin(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByParticipantTest() throws Exception {
        final String param = "participant";
        final Sportsman value = eventService.findAll().get(0).getAdmin();
        logFile.cleanLogFile();
        eventService.findByParticipant(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullParticipantTest() throws Exception {
        final String param = "participant";
        final String value = null;
        try {
            eventService.findByParticipant(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findAllEventsTest() throws Exception {
        eventService.findAll();
        logFile.reloadLogFile();
        super.testFindAllMethod(layerName, className);
    }


    @Test
    public void updateEventTest() throws Exception {
        Event event = this.getEvent();
        logFile.cleanLogFile();
        try {
            eventService.update(event);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", event.toString());
    }

    @Test
    public void updateNullEventTest() throws Exception {
        try {
            eventService.update(null);
            fail(nullException);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", "null");
    }

    @Test
    public void deleteEventTest() throws Exception {
        Event event = this.getEvent();
        logFile.cleanLogFile();
        try {
            eventService.delete(event);
            fail(nullException);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", event.toString());
    }

    @Test
    public void deleteNullEventTest() throws Exception {
        try {
            eventService.delete(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "delete", "null");
    }

    private Event getEvent() {
        List<Event> events = eventService.findAll();
        if (!events.isEmpty()) {
            return events.get(0);
        }
        Event newEvent = new Event();
        newEvent.setName("new");
        newEvent.setDescription("new");
        newEvent.setDate(Calendar.getInstance());
        newEvent.setSport(null);
        newEvent.setCapacity(1);
        newEvent.setCity("city");
        newEvent.setAddress("address");
        newEvent.setAdmin(null);
        return newEvent;
    }

}
