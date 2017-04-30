package cz.muni.pa036.logging.layersTests.daoLayer;

import ch.qos.logback.classic.Level;
import cz.muni.pa036.logging.dao.EventDAO;
import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.testng.Assert.fail;

/**
 * @author Kamil Triscik.
 */
@Transactional
public class EventDAOTest extends DAOLayerTests {

    private final String tableName = "event";

    /**
     * Note just for this class.
     * Event entity contains link to 2 objects sportsman and sport:
     */
    private String[] tables = new String[]{"sportsman", "sport"};

    /**
     * Note just for this class.
     * Autowired DAO + "Impl"
     */
    private final String className = "EventDAOImpl";

    @Autowired
    EventDAO eventDAO;

    @Test
    public void createEventTest() throws Exception {
        Event event = this.getEvent();
        logFile.cleanLogFile();
        try {
            eventDAO.create(event);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", event.toString());
        // TODO: 4/26/17 only one row?
    }

    @Test
    public void createNullEventTest() throws Exception {
        try {
            eventDAO.create(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "create", "null");
    }

    @Test
    public void findByIdTest() throws Exception {
        final String param = "ID";
        final Long value = 1L;
        eventDAO.findById(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogs(Level.TRACE), "BIGNIT", String.valueOf(value));
        }
    }

    @Test
    public void findByNullIdTest() throws Exception {
        final String param = "ID";
        final String value = null;
        try {
            eventDAO.findById(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByNameTest() throws Exception {
        final String param = "name";
        final String value = "name";
        eventDAO.findByName(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogs(Level.TRACE), "VARCHAR", value);
        }
    }

    @Test
    public void findByNullNameTest() throws Exception {
        final String param = "name";
        final String value = null;
        try {
            eventDAO.findByName(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByDateTest() throws Exception {
        final String param = "date";
        final Calendar value = Calendar.getInstance();
        eventDAO.findByDate(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogs(Level.TRACE), "DATE", String.valueOf(value));
        }
    }

    @Test
    public void findByNullDateTest() throws Exception {
        final String param = "date";
        final String value = null;
        try {
            eventDAO.findByDate(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findBySportTest() throws Exception {
        final String param = "sport";
        final Sport value = eventDAO.findAll().get(0).getSport();
        logFile.cleanLogFile();
        List<Event> ev = eventDAO.findBySport(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogFileDiff().getLogs(Level.TRACE), "BIGNIT", String.valueOf(value.getId()));
        }
    }

    @Test
    public void findByNullSportTest() throws Exception {
        final String param = "sport";
        final String value = null;
        try {
            eventDAO.findBySport(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByCityTest() throws Exception {
        final String param = "city";
        final String value = "someCity";
        eventDAO.findByCity(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogs(Level.TRACE), "VARCHAR", value);
        }
    }

    @Test
    public void findByNullCityTest() throws Exception {
        final String param = "city";
        final String value = null;
        try {
            eventDAO.findByCity(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByAdminTest() throws Exception {
        final String param = "admin";
        final Sportsman value = eventDAO.findAll().get(0).getAdmin();
        logFile.cleanLogFile();
        eventDAO.findByAdmin(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogFileDiff().getLogs(Level.TRACE), "BIGNIT", String.valueOf(value));
        }
    }

    @Test
    public void findByNullAdminTest() throws Exception {
        final String param = "admin";
        final String value = null;
        try {
            eventDAO.findByAdmin(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByParticipantTest() throws Exception {
        final String param = "participant";
        final Sportsman value = eventDAO.findAll().get(0).getAdmin();
        logFile.cleanLogFile();
        eventDAO.findByParticipant(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
        if (isDebugLevelEnabled) {
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
        }
        if (isTraceLevelEnabled) {
            super.testBindingParameter(logFile.getLogFileDiff().getLogs(Level.TRACE), "BIGNIT", String.valueOf(value));
        }
    }

    @Test
    public void findByNullParticipantTest() throws Exception {
        final String param = "participant";
        final String value = null;
        try {
            eventDAO.findByParticipant(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {
        }
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findAll() throws Exception {
        List<Event> events = eventDAO.findAll();
        logFile.reloadLogFile();
        if (isDebugLevelEnabled) {
            testFindAllMethod(layerName, className);
            testSelectPresent(logFile.getLogs(Level.DEBUG), tableName);
            Arrays.stream(tables).forEach(table -> testSelectPresent(logFile.getLogs(Level.DEBUG), table));
        }
        if (isTraceLevelEnabled) {
            Assert.assertFalse(logFile.getLogFileDiff().getLogs(Level.TRACE).isEmpty());
            Assert.assertEquals(events.size(), logFile.getLogFileDiff().getLogs(Level.TRACE).stream().
                    filter(log -> log.getContent().contains("extracted value ([id1_0_]")).count());
        }
    }

    @Test
    public void updateEventTest() throws Exception {
        Event event = this.getEvent();
        logFile.cleanLogFile();
        try {
            eventDAO.update(event);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", event.toString());
    }

    @Test
    public void updateNullEventTest() throws Exception {
        try {
            eventDAO.update(null);
            fail(nullException);
        } catch (Exception e) {}
        logFile.reloadLogFile();
        super.testCUDObject(layerName, className, "update", "null");
    }

//    @Test
//    public void deleteEventTest() throws Exception {
//        Event event = this.getEvent();
//        logFile.cleanLogFile();
//        try {
//            eventDAO.delete(event);
//            fail(nullException);
//        } catch (Exception e) {}
//        logFile.reloadLogFile();
//        super.testCUDObject(layerName, className, "delete", event.toString());
//    }
//
//    @Test
//    public void deleteNullEventTest() throws Exception {
//        try {
//            eventDAO.delete(null);
//            fail(nullException);
//        } catch (IllegalArgumentException e) {}
//        logFile.reloadLogFile();
//        super.testCUDObject(layerName, className, "delete", "null");
//    }

    private Event getEvent() {
        List<Event> events = eventDAO.findAll();
        if (!events.isEmpty()) {
            return events.get(0);
        }
        Event newEvent = new Event();
        newEvent.setName("new");
        newEvent.setDescription("new");
        newEvent.setDate(Calendar.getInstance());
        newEvent.setSport(null);
        newEvent.setCapacity(2);
        newEvent.setCity("city");
        newEvent.setAddress("address");
        newEvent.setAdmin(null);
        return newEvent;
    }

}
