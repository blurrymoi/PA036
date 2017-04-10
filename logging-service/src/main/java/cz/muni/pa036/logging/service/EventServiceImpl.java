package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.dao.EventDAO;
import cz.muni.pa036.logging.dao.InvitationDAO;
import cz.muni.pa036.logging.dao.ResultDAO;
import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Invitation;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.helper.CRUDLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        CRUD_LOGGER.logCreate(event);
        eventDAO.create(event);
    }

    @Override
    public Event findById(Long id) {

        CRUD_LOGGER.logFindBy("ID", id);
        return eventDAO.findById(id);
    }

    @Override
    public List<Event> findByName(String name) {

        CRUD_LOGGER.logFindBy("name", name);
        return eventDAO.findByName(name);
    }

    @Override
    public List<Event> findByDate(Calendar date) {

        CRUD_LOGGER.logFindBy("date", date);
        return eventDAO.findByDate(date);
    }

    @Override
    public List<Event> findBySport(Sport sport) {

        CRUD_LOGGER.logFindBy("sport", sport);
        return eventDAO.findBySport(sport);
    }

    @Override
    public List<Event> findByCity(String city) {

        CRUD_LOGGER.logFindBy("city", city);
        return eventDAO.findByCity(city);
    }

    @Override
    public List<Event> findByAdmin(Sportsman admin) {

        CRUD_LOGGER.logFindBy("admin", admin);
        return eventDAO.findByAdmin(admin);
    }

    @Override
    public List<Event> findByParticipant(Sportsman participant) {

        CRUD_LOGGER.logFindBy("participant", participant);
        return eventDAO.findByParticipant(participant);
    }

    @Override
    public List<Event> findAll() {

        CRUD_LOGGER.logFindAll();
        return eventDAO.findAll();
    }

    @Override
    public void update(Event event) {
        Set<Sportsman> participants = new HashSet<>();
        participants.addAll(event.getParticipants());
        CRUD_LOGGER.logUpdate(event);
        eventDAO.update(event);
        notificationService.notifyEventEdited(participants, event);
    }

    @Override
    public void delete(Event event) {
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
            resultDAO.findByEvent(event).forEach(result -> {
                CRUD_LOGGER.logDelete(result);
                resultDAO.delete(result);
            });
        }

        //next delete all invitations for this event due to foreign key
        CRUD_LOGGER.logFindBy("event", event);
        List<Invitation> invitationList = invitationDAO.findByEvent(event);
        if (invitationList != null) {
            for(Invitation invitation : invitationList) {
                CRUD_LOGGER.logDelete(invitation);
                invitationDAO.delete(invitation);
            }
        }
        CRUD_LOGGER.logDelete(event);
        eventDAO.delete(event);
    }

    @Override
    public void edit(Event event) {
        Set<Sportsman> participants = new HashSet<>();
        participants.addAll(event.getParticipants());
        CRUD_LOGGER.logUpdate(event);
        eventDAO.update(event);
        notificationService.notifyEventEdited(participants, event);
    }
}
