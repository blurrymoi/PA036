package cz.muni.pa036.logging.layersTests.serviceLayer;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Invitation;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.service.EventService;
import cz.muni.pa036.logging.service.InvitationService;
import cz.muni.pa036.logging.service.SportsmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        } catch (IllegalArgumentException e) {}
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
        } catch (IllegalArgumentException e) {}
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
            eventService.findBySport(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, param, value);
    }

    @Test
    public void findByEventAndInviteeTest() throws Exception{
        final String eventparam = "event";
        Event eventvalue = eventService.findAll().get(0);
        final String inviteeparam = "invitee";
        Sportsman inviteevalue = sportsmanService.findAll().get(0);
        logFile.cleanLogFile();
        invitationService.findByEvent(eventvalue);
        invitationService.findByInvitee(inviteevalue);
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, eventparam, eventvalue.toString());
        super.testFindByParamsMethod(layerName, className, inviteeparam, inviteevalue.toString());
    }

    @Test
    public void findByNullEventAndInviteeTest() throws Exception{
        final String eventparam = "event";
        final String eventvalue = null;
        final String inviteeparam = "invitee";
        Sportsman inviteevalue = sportsmanService.findAll().get(0);
        try {
            invitationService.findByEvent(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        logFile.cleanLogFile();
        invitationService.findByInvitee(inviteevalue);
        super.testFindByParamsMethod(layerName, className, eventparam, eventvalue);
        super.testFindByParamsMethod(layerName, className, inviteeparam, inviteevalue.toString());
    }

    @Test
    public void findByEventAndNullInviteeTest() throws Exception{
        final String eventparam = "event";
        Event eventvalue = eventService.findAll().get(0);
        final String inviteeparam = "invitee";
        final String inviteevalue = null;
        try {
            eventService.findBySport(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        logFile.cleanLogFile();
        invitationService.findByEvent(eventvalue);
        super.testFindByParamsMethod(layerName, className, eventparam, eventvalue.toString());
        super.testFindByParamsMethod(layerName, className, inviteeparam, inviteevalue);
    }

    @Test
    public void findByNullEventAndNullInviteeTest() throws Exception{
        final String eventparam = "event";
        final String eventvalue = null;
        final String inviteeparam = "invitee";
        final String inviteevalue = null;
        try {
            invitationService.findByEvent(null);
            eventService.findBySport(null);
            fail(nullException);
        } catch (IllegalArgumentException e) {}
        logFile.reloadLogFile();
        super.testFindByParamsMethod(layerName, className, eventparam, eventvalue);
        super.testFindByParamsMethod(layerName, className, inviteeparam, inviteevalue);
    }

    @Test
    public void inviteTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void inviteNullValuesTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void acceptTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void acceptNullTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void declineTest() {
        Assert.fail("Not implemented");
    }

    @Test
    public void declineNullTest() {
        Assert.fail("Not implemented");
    }

}
