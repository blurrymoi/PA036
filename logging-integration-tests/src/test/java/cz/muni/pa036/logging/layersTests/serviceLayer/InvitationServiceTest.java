package cz.muni.pa036.logging.layersTests.serviceLayer;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Invitation;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.log.LogFileDiff;
import cz.muni.pa036.logging.service.EventService;
import cz.muni.pa036.logging.service.InvitationService;
import cz.muni.pa036.logging.service.SportsmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.fail;

/**
 * @author Kamil Triscik.
 */
@Transactional
public class InvitationServiceTest extends ServiceLayerTest {

    private final String className = "InvitationServiceImpl";

    @Autowired
    InvitationService invitationService;

    @Autowired
    EventService eventService;

    @Autowired
    SportsmanService sportsmanService;

    @Test
    public void findByIdTest() throws Exception {
        final String param = "ID";
        final Long value = 1L;
        invitationService.findById(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullIdTest() throws Exception{
        final String param = "ID";
        final String value = null;
        try {
            invitationService.findById(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findAllTest()throws Exception{
        invitationService.findAll();
        logFile.reloadLogFile();
        super.testFindAllMethod(layerName, className);
    }

    @Test
    public void findByEventTest() throws Exception{
        final String param = "event";
        Event value = eventService.findAll().get(0);
        logFile.cleanLogFile();
        invitationService.findByEvent(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullEventTest() throws Exception{
        final String param = "event";
        final String value = null;
        try {
            invitationService.findByEvent(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByInviteeTest() throws Exception {
        final String param = "invitee";
        Sportsman value = sportsmanService.findAll().get(0);
        logFile.cleanLogFile();
        invitationService.findByInvitee(value);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value.toString());
    }

    @Test
    public void findByNullInviteeTest() throws Exception{
        final String param = "invitee";
        final String value = null;
        try {
            invitationService.findByInvitee(null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByEventAndInviteeTest() throws Exception{
        final String eventParam = "event";
        Event eventValue = eventService.findAll().get(0);
        final String inviteeParam = "invitee";
        Sportsman inviteeValue = sportsmanService.findAll().get(0);
        logFile.cleanLogFile();
        invitationService.findByEventAndInvitee(eventValue, inviteeValue);
        logFile.reloadLogFile();
        Map<String, String> values = new HashMap<String, String>(2) {{
            put(eventParam, eventValue.toString());
            put(inviteeParam, inviteeValue.toString());
        }};
        super.testFindByParamsMethod(layerName, className, values);
    }

    @Test
    public void findByNullEventAndInviteeTest() throws Exception{
        final String eventParam = "event";
        final String eventValue = null;
        final String inviteeParam = "invitee";
        Sportsman inviteeValue = sportsmanService.findAll().get(0);
        try {
            logFile.cleanLogFile();
            invitationService.findByEventAndInvitee(null, inviteeValue);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        Map<String, String> values = new HashMap<String, String>(2) {{
            put(eventParam, eventValue);
            put(inviteeParam, inviteeValue.toString());
        }};
        super.testFindByParamsMethod(layerName, className, values);
    }

    @Test
    public void findByEventAndNullInviteeTest() throws Exception{
        final String eventParam = "event";
        Event eventValue = eventService.findAll().get(0);
        final String inviteeParam = "invitee";
        final String inviteeValue = null;
        try {
            logFile.cleanLogFile();
            invitationService.findByEventAndInvitee(eventValue, null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        Map<String, String> values = new HashMap<String, String>(2) {{
            put(eventParam, eventValue.toString());
            put(inviteeParam, inviteeValue);
        }};
        super.testFindByParamsMethod(layerName, className, values);
    }

    @Test
    public void findByNullEventAndNullInviteeTest() throws Exception{
        final String eventParam = "event";
        final String eventValue = null;
        final String inviteeParam = "invitee";
        final String inviteeValue = null;
        try {
            invitationService.findByEventAndInvitee(null, null);
            fail(nullException);
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        Map<String, String> values = new HashMap<String, String>(2) {{
            put(eventParam, eventValue);
            put(inviteeParam, inviteeValue);
        }};
        super.testFindByParamsMethod(layerName, className, values);
    }

    @Test
    public void inviteTest() throws Exception {
        Sportsman invitee = sportsmanService.findAll().get(1);
        Event event = eventService.findAll().get(0);
        try {
            logFile.cleanLogFile();
            invitationService.invite(event, invitee);
        } catch (Exception e) {
            System.out.println();
        }
        LogFileDiff f = logFile.reloadLogFile();
        System.out.println();
    }

    @Test
    public void inviteNullEventTest() throws Exception {
        Sportsman invitee = sportsmanService.findAll().get(0);
        try {
            logFile.cleanLogFile();
            invitationService.invite(null, invitee);
            fail(nullException);
        } catch (FindByException e) {}
        Assert.assertNull(logFile.reloadLogFile());
    }

    @Test
    public void inviteNullEventNullInviteeTest() throws Exception {
        try {
            logFile.cleanLogFile();
            invitationService.invite(null, null);
            fail(nullException);
        } catch (FindByException e) {}
        Assert.assertNull(logFile.reloadLogFile());
    }

    @Test
    public void acceptAlreadyAcceptedTest() throws Exception {
        Invitation invitation = invitationService.findAll().get(0);
        logFile.cleanLogFile();
        try {
            invitationService.accept(invitation);
            fail("Accept already accepted. IllegalStateException should by thrown.");
        } catch (FindByException e) {}
        logFile.reloadLogFile();
        Assert.assertNull(logFile.getLogFileDiff(), "No logs because invitation already confirmed -> nothing to do!");
    }

}
