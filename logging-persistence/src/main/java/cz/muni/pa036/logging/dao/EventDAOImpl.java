package cz.muni.pa036.logging.dao;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.pa036.logging.utils.DAOLogger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Vit Hovezak
 */
@Repository
public class EventDAOImpl implements EventDAO {

    private final DAOLogger LOGGER = new DAOLogger(LoggerFactory.getLogger(this.getClass()), "EVENT");

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Event event) {
        LOGGER.logCreate(event);
        if (event == null) {
            throw new IllegalArgumentException("Event is null");
        }
        em.persist(event);
    }

    @Override
    public Event findById(Long id) {
        LOGGER.logFindBy("ID", id);
        if (id == null) {
            throw new IllegalArgumentException("Event ID is null");
        }
        return em.find(Event.class, id);
    }

    @Override
    public List<Event> findByName(String name) {
        LOGGER.logFindBy("name", name, true);
        if (name == null) {
            throw new IllegalArgumentException("Event name is null");
        }
        try {
            Query query = em.createQuery("SELECT e FROM Event e WHERE e.name = :name");
            query.setParameter("name", name);
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Event> findByDate(Calendar date) {
        LOGGER.logFindBy("date", date, true);
        if (date == null) {
            throw new IllegalArgumentException("Event date is null");
        }
        try {
            Calendar startOfDay = (Calendar) date.clone();
            startOfDay.set(Calendar.HOUR_OF_DAY, 0);
            startOfDay.set(Calendar.MINUTE, 0);
            startOfDay.set(Calendar.SECOND, 0);
            startOfDay.set(Calendar.MILLISECOND, 0);
            Calendar endOfDay = (Calendar) startOfDay.clone();
            endOfDay.add(Calendar.DAY_OF_MONTH, 1);

            Query query = em.createQuery("SELECT e FROM Event e WHERE e.date >= :start AND e.date < :end");
            query.setParameter("start", startOfDay);
            query.setParameter("end", endOfDay);
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Event> findBySport(Sport sport) {
        LOGGER.logFindBy("sport", sport, true);
        if (sport == null) {
            throw new IllegalArgumentException("Event sport is null");
        }
        try {
            Query query = em.createQuery("SELECT e FROM Event e WHERE e.sport = :sport");
            query.setParameter("sport", sport);
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Event> findByCity(String city) {
        LOGGER.logFindBy("city", city, true);
        if (city == null) {
            throw new IllegalArgumentException("Event city is null");
        }
        try {
            Query query = em.createQuery("SELECT e FROM Event e WHERE e.city = :city");
            query.setParameter("city", city);
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Event> findByAdmin(Sportsman admin) {
        LOGGER.logFindBy("admin", admin, true);
        if (admin == null) {
            throw new IllegalArgumentException("Event admin is null");
        }
        try {
            Query query = em.createQuery("SELECT e FROM Event e WHERE e.admin = :admin");
            query.setParameter("admin", admin);
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Event> findByParticipant(Sportsman participant) {
        LOGGER.logFindBy("participant", participant, true);
        if (participant == null) {
            throw new IllegalArgumentException("Event participant is null");
        }
        try {
            Query query = em.createQuery("SELECT e FROM Event e JOIN e.participants p WHERE p = :participant");
            query.setParameter("participant", participant);
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Event> findAll() {
        LOGGER.logFindAll();
        try {
            Query query = em.createQuery("SELECT e FROM Event e");
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void update(Event event) {
        LOGGER.logUpdate(event);
        if (event == null) {
            throw new IllegalArgumentException("Event is null");
        }
        em.merge(event);
    }

    @Override
    public void delete(Event event) {
        LOGGER.logDelete(event);
        if (event == null) {
            throw new IllegalArgumentException("Event is null");
        }
        em.remove(event);
    }

}
