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
    public void create(Event event) {
        try {
            CRUD_LOGGER.logCreate(event);
            eventDAO.create(event);
        } catch (Exception ex) {
            throw new CreateException("Failed to create Event", ex, event);
        }
    }

    @Override
    public Event findById(Long id) {
        try {
            CRUD_LOGGER.logFindBy("ID", id);
            return eventDAO.findById(id);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by ID", ex, "ID", id);
        }
    }

    @Override
    public List<Event> findByName(String name) {
        try {
            CRUD_LOGGER.logFindBy("name", name);
            return eventDAO.findByName(name);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by name", ex, "name", name);
        }
    }

    @Override
    public List<Event> findByDate(Calendar date) {
        try {
            CRUD_LOGGER.logFindBy("date", date);
            return eventDAO.findByDate(date);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by date", ex, "date", date);
        }
    }

    @Override
    public List<Event> findBySport(Sport sport) {
        try {
            CRUD_LOGGER.logFindBy("sport", sport);
            return eventDAO.findBySport(sport);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by sport", ex, "sport", sport);
        }
    }

    @Override
    public List<Event> findByCity(String city) {
        try {
            CRUD_LOGGER.logFindBy("city", city);
            return eventDAO.findByCity(city);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by city", ex, "city", city);
        }
    }

    @Override
    public List<Event> findByAdmin(Sportsman admin) {
        try {
            CRUD_LOGGER.logFindBy("admin", admin);
            return eventDAO.findByAdmin(admin);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by admin", ex, "admin", admin);
        }
    }

    @Override
    public List<Event> findByParticipant(Sportsman participant) {
        try {
            CRUD_LOGGER.logFindBy("participant", participant);
            return eventDAO.findByParticipant(participant);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Event by participant", ex, "participant", participant);
        }
    }

    @Override
    public List<Event> findAll() {
        try {
            CRUD_LOGGER.logFindAll();
            return eventDAO.findAll();
        } catch (Exception ex) {
            throw new FindByException("Failed to find all Events", ex, null);
        }
    }

    @Override
    public void update(Event event) {
        Set<Sportsman> participants = new HashSet<>();
        participants.addAll(event.getParticipants());
        try {
            CRUD_LOGGER.logUpdate(event);
            eventDAO.update(event);
        } catch (Exception ex) {
            throw new UpdateException("Failed to update Event", ex, event);
        }
        notificationService.notifyEventEdited(participants, event);
    }

    @Override
    public void delete(Event event) {
        if (event == null) {
            throw new DeleteException("Trying to delete null object", new Exception(), event);
        }
        if (event.getId() == null) {
            throw new DeleteException("Trying to delete in DB non existing event", new Exception(), event);
        }
        Set<Sportsman> participants = new HashSet<>();
        if (event.getParticipants() != null) {
            participants.addAll(event.getParticipants());
            //notify
            notificationService.notifyEventCanceled(participants, event);

            //next delete results due to foreign constraints
            List<Result> byEvent;
            try {
                CRUD_LOGGER.logFindBy("event", event);
                byEvent = resultDAO.findByEvent(event);
            } catch (Exception ex) {
                throw new FindByException("Failed to find Result by event", ex, "event", event);
            }
            for (Result result : byEvent) {
                try {
                    CRUD_LOGGER.logDelete(result);
                    resultDAO.delete(result);
                } catch (Exception ex) {
                    throw new DeleteException("failed to delete Result", ex, result);
                }
            }
        }
        //next delete all invitations for this event due to foreign key
        List<Invitation> byEvent;
        try {
            CRUD_LOGGER.logFindBy("event", event);
            byEvent = invitationDAO.findByEvent(event);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Invitation by event", ex, "event", event);
        }
        for(Invitation invitation : byEvent) {
            try {
                CRUD_LOGGER.logDelete(invitation);
                invitationDAO.delete(invitation);
            } catch (Exception ex) {
                throw new DeleteException("failed to delete Invitation", ex, invitation);
            }
        }
        try {
            CRUD_LOGGER.logDelete(event);
            eventDAO.delete(event);
        } catch (Exception ex) {
            throw new DeleteException("failed to delete Event", ex, event);
        }
    }
}
