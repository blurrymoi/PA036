package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.dao.EventDAO;
import cz.muni.pa036.logging.dao.InvitationDAO;
import cz.muni.pa036.logging.dao.ResultDAO;
import cz.muni.pa036.logging.dao.SportsmanDAO;
import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Invitation;
import cz.muni.pa036.logging.entity.Result;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.helper.ActionLogger;
import cz.muni.pa036.logging.helper.CRUDLogger;
import cz.muni.pa036.logging.utils.InvitationState;
import cz.muni.pa036.logging.utils.PerformanceUnits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Matej Majdis
 */
@Service
public class InvitationServiceImpl implements InvitationService {

    private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

    @Autowired
    private InvitationDAO invitationDAO;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private SportsmanDAO sportsmanDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ResultDAO resultDAO;

    @Override
    public Invitation invite(long eventId, long sportsmanId) {

        CRUD_LOGGER.logFindBy("ID", eventId);
        Event event = eventDAO.findById(eventId);
        if (event == null) {
            throw new IllegalArgumentException("Event not found");
        }

        CRUD_LOGGER.logFindBy("ID", sportsmanId);
        Sportsman sportsman = sportsmanDAO.findById(sportsmanId);
        if (sportsman == null) {
            throw new IllegalArgumentException("Sportsman not found");
        }

        return invite(event, sportsman);
    }

    @Override
    public Invitation invite(Event event, Sportsman invitee) {

        if (event == null) {
            throw new IllegalArgumentException("Event can not be null");
        }
        if (invitee == null) {
            throw new IllegalArgumentException("Invitee can not be null");
        }

        if (event.getAdmin().equals(invitee)) {
            throw new IllegalStateException("Cannot invite yourself");
        }

        if (event.getParticipants().contains(invitee)) {
            return null;
        }

        Map<Object, Object> findBy = new HashMap<>();
        findBy.put("event", event);
        findBy.put("invitee", invitee);

        CRUD_LOGGER.logFindBy(findBy);
        //check and process existing invitations
        Invitation existingInvitation = invitationDAO.findByEventAndInvitee(event, invitee);
        if (existingInvitation != null && !isFinished(existingInvitation)) {
            if (InvitationState.INVITED.equals(existingInvitation.getState())) {
                emailService.sendInvitationMessage(existingInvitation);
                return changeInvitationState(existingInvitation, InvitationState.REINVITED);
            }
            return existingInvitation;
        }

        //if there is no existing invitation || is finished create new
        Invitation newInvitation = new Invitation();
        newInvitation.setState(InvitationState.INVITED);
        newInvitation.setEvent(event);
        newInvitation.setInvitee(invitee);

        CRUD_LOGGER.logCreate(newInvitation);
        invitationDAO.create(newInvitation);

        emailService.sendInvitationMessage(newInvitation);

        return newInvitation;
    }

    @Override
    public Invitation accept(Invitation invitation) {

        if (invitation == null) {
            throw new IllegalArgumentException("Invitation can not be null");
        }

        if (isFinished(invitation)) {
            throw new IllegalStateException("Invitation is already in state: " + invitation.getState());
        }

        Result result = new Result();
        result.setPerformanceUnit(PerformanceUnits.SECOND);
        result.setEvent(invitation.getEvent());
        result.setSportsman(invitation.getInvitee());
        result.setPosition(-1);
        result.setPerformance((double) -1);
        result.setNote("");

        CRUD_LOGGER.logCreate(result);
        resultDAO.create(result);

        Event event = invitation.getEvent();
        Set<Sportsman> participants = event.getParticipants();
        participants.add(invitation.getInvitee());
        event.setParticipants(participants);

        CRUD_LOGGER.logUpdate(event);
        eventDAO.update(event);

        return changeInvitationState(invitation, InvitationState.ACCEPTED);
    }

    @Override
    public Invitation simpleAccept(Invitation invitation) {
        return changeInvitationState(invitation, InvitationState.ACCEPTED);
    }

    @Override
    public Invitation decline(Invitation invitation) {

        if (invitation == null) {
            throw new IllegalArgumentException("Invitation can not be null");
        }

        if (isFinished(invitation)) {
            throw new IllegalStateException("Invitation is already in state: " + invitation.getState());
        }

        return changeInvitationState(invitation, InvitationState.DECLINED);
    }

    @Override
    public Invitation findById(Long id) {
        try {
            CRUD_LOGGER.logFindBy("ID", id);
            return invitationDAO.findById(id);
        } catch (Exception e) {
            ActionLogger actionLogger = new ActionLogger.ActionLoggerBuilder().FIND_BY_ID(id).build();
            CRUD_LOGGER.logOops(actionLogger, e);
            throw new DataRetrievalFailureException("Failed to find invitation by id " + id + ", exception: ", e);
        }
    }

    @Override
    public Invitation findByEventAndInvitee(Event event, Sportsman invitee) {
        Map<Object, Object> findBy = new HashMap<>();
        findBy.put("event", event);
        findBy.put("invitee", invitee);
        try {
            CRUD_LOGGER.logFindBy(findBy);
            return invitationDAO.findByEventAndInvitee(event, invitee);
        } catch (Exception e) {
            ActionLogger actionLogger = new ActionLogger.ActionLoggerBuilder().FIND_BY().byValues(findBy).build();
            CRUD_LOGGER.logOops(actionLogger, e);
            throw new DataRetrievalFailureException("Failed to find invitation", e);
        }
    }

    @Override
    public List<Invitation> findByEvent(Event event) {
        try {
            CRUD_LOGGER.logFindBy("event", event);
            return invitationDAO.findByEvent(event);
        } catch (Exception e) {
            throw new DataRetrievalFailureException("Failed to find invitations, exception: ", e);
        }
    }

    @Override
    public List<Invitation> findByInvitee(Sportsman invitee) {
        try {
            CRUD_LOGGER.logFindBy("invitee", invitee);
            return invitationDAO.findByInvitee(invitee);
        } catch (Exception e) {
            throw new DataRetrievalFailureException("Failed to find invitations, exception: ", e);
        }
    }

    @Override
    public List<Invitation> findAll() {
        try {
            CRUD_LOGGER.logFindAll();
            return invitationDAO.findAll();
        } catch (Exception e) {
            throw new DataRetrievalFailureException("Failed to find all invitations, exception: ", e);
        }
    }

    private boolean isFinished(Invitation invitation) {
        InvitationState state = invitation.getState();
        return state.equals(InvitationState.ACCEPTED) || state.equals(InvitationState.DECLINED);
    }

    private Invitation changeInvitationState(Invitation invitation, InvitationState state) {
        invitation.setState(state);

        CRUD_LOGGER.logUpdate(invitation);
        invitationDAO.update(invitation);

        return invitation;
    }
}
