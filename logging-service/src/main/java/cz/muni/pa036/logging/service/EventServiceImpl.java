package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.dao.EventDAO;
import cz.muni.pa036.logging.dao.InvitationDAO;
import cz.muni.pa036.logging.dao.ResultDAO;
import cz.muni.pa036.logging.entity.*;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.helper.CRUDLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Vit Hovezak
 */
@Service
public class EventServiceImpl implements EventService {

    private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private ResultDAO resultDAO;

    @Autowired
    private InvitationDAO invitationDAO;

    @Autowired
    private NotificationService notificationService;

    @Override
    public void create(Event event) throws CreateException {
        CRUD_LOGGER.logCreate(event);
        try {
            eventDAO.create(event);
        } catch (Exception ex) {
            throw new CreateException("Failed to create Event", ex, event);
        }
    }

    @Override
    public Event findById(Long id) throws FindByException {
        CRUD_LOGGER.logFindBy("ID", id);
        try {
            return eventDAO.findById(id);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by ID", ex, "ID", id);
        }
    }

    @Override
    public List<Event> findByName(String name) throws FindByException {
        CRUD_LOGGER.logFindBy("name", name);
        try {
            return eventDAO.findByName(name);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by name", ex, "name", name);
        }
    }

    @Override
    public List<Event> findByDate(Calendar date) throws FindByException {
        CRUD_LOGGER.logFindBy("date", date);
        try {
            return eventDAO.findByDate(date);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by date", ex, "date", date);
        }
    }

    @Override
    public List<Event> findBySport(Sport sport) throws FindByException {
        CRUD_LOGGER.logFindBy("sport", sport);
        try {
            return eventDAO.findBySport(sport);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by sport", ex, "sport", sport);
        }
    }

    @Override
    public List<Event> findByCity(String city) throws FindByException {
        CRUD_LOGGER.logFindBy("city", city);
        try {
            return eventDAO.findByCity(city);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by city", ex, "city", city);
        }
    }

    @Override
    public List<Event> findByAdmin(Sportsman admin) throws FindByException {
        CRUD_LOGGER.logFindBy("admin", admin);
        try {
            return eventDAO.findByAdmin(admin);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by admin", ex, "admin", admin);
        }
    }

    @Override
    public List<Event> findByParticipant(Sportsman participant) throws FindByException {
        CRUD_LOGGER.logFindBy("participant", participant);
        try {
            return eventDAO.findByParticipant(participant);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by participant", ex, "participant", participant);
        }
    }

    @Override
    public List<Event> findAll() throws FindByException {
        CRUD_LOGGER.logFindAll();
        try {
            return eventDAO.findAll();
        } catch (Exception ex) {
            throw new FindByException("Failed to find all Events", ex, null);
        }
    }

    @Override
    public void update(Event event) throws UpdateException {
        Set<Sportsman> participants = new HashSet<>();
        participants.addAll(event.getParticipants());
        CRUD_LOGGER.logUpdate(event);
        try {
            eventDAO.update(event);
        } catch (Exception ex) {
            throw new UpdateException("Failed to update Event", ex, event);
        }
        notificationService.notifyEventEdited(participants, event);
    }

    @Override
    public void delete(Event event) throws FindByException, DeleteException {
        if (event == null) {
            throw  new IllegalArgumentException("Trying to delete null object");
        }
        if (event.getId() == null) {
            throw  new IllegalArgumentException("Trying to delete in DB non existing event");
        }
        Set<Sportsman> participants = new HashSet<>();
        if (event.getParticipants() != null) {
            participants.addAll(event.getParticipants());
            //notify
            notificationService.notifyEventCanceled(participants, event);

            //next delete results due to foreign constraints
            CRUD_LOGGER.logFindBy("event", event);
            List<Result> byEvent;
            try {
                byEvent = resultDAO.findByEvent(event);
            } catch (Exception ex) {
                throw new FindByException("Failed to find Result by event", ex, "event", event);
            }
            for (Result result : byEvent) {
                CRUD_LOGGER.logDelete(result);
                try {
                    resultDAO.delete(result);
                } catch (Exception ex) {
                    throw new DeleteException("failed to delete Result", ex, result);
                }
            }
        }
        //next delete all invitations for this event due to foreign key
        CRUD_LOGGER.logFindBy("event", event);
        List<Invitation> byEvent;
        try {
            byEvent = invitationDAO.findByEvent(event);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Invitation by event", ex, "event", event);
        }
        for(Invitation invitation : byEvent) {
            CRUD_LOGGER.logDelete(invitation);
            try {
                invitationDAO.delete(invitation);
            } catch (Exception ex) {
                throw new DeleteException("failed to delete Invitation", ex, invitation);
            }
        }
        CRUD_LOGGER.logDelete(event);
        try {
            eventDAO.delete(event);
        } catch (Exception ex) {
            throw new DeleteException("failed to delete Event", ex, event);
        }
    }
}
