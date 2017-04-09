package cz.muni.pa036.logging.dao;

import cz.muni.pa036.logging.helper.CRUDLogger;
import org.springframework.stereotype.Repository;

import cz.muni.pa036.logging.entity.*;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * @author xaksamit
 */
@Repository
public class ResultDAOImpl implements ResultDAO {

    private final CRUDLogger LOGGER = new CRUDLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Result result) {
        LOGGER.logCreate(result);
        if (result == null) {
            throw new IllegalArgumentException("Result is null");
        }
        entityManager.persist(result);
    }

    @Override
    public Result findById(Long id) {
        LOGGER.logFindBy("ID", id);
        if (id == null) {
            throw new IllegalArgumentException("Result ID is null");
        }
        return entityManager.find(Result.class, id);
    }

    @Override
    public List<Result> findBySportsman(Sportsman sportsman) {
        LOGGER.logFindBy("sportsman", sportsman, true);
        if (sportsman == null) {
            throw new IllegalArgumentException("Sportsman to find results for is null");
        }

        try {
            Query quer = entityManager.createQuery("SELECT r FROM Result r WHERE sportsman = :sportsman");
            quer.setParameter("sportsman", sportsman);
            return quer.getResultList();
        } catch (NoResultException excp) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Result> findByEvent(Event event) {
        LOGGER.logFindBy("event", event, true);
        if (event == null) {
            throw new IllegalArgumentException("Event to find results for is null");
        }

        try {
            Query quer = entityManager.createQuery("SELECT r FROM Result r WHERE r.event = :event");
            quer.setParameter("event", event);
            return quer.getResultList();
        } catch (NoResultException excp) {
            return new ArrayList<>();
        }
    }

    @Override
    public Result findBySportsmanAndEvent(Sportsman sportsman, Event event) {
        Map<Object, Object> findBy = new HashMap<>();
        findBy.put("sportsman", sportsman);
        findBy.put("event", event);

        LOGGER.logFindBy(findBy);
        if (event == null) {
            throw new IllegalArgumentException("Event to find results for is null");
        }
        if (sportsman == null) {
            throw new IllegalArgumentException("Sportsman to find results for is null");
        }


        try {
            Query quer = entityManager.createQuery("SELECT r FROM Result r WHERE r.event = :event AND r.sportsman = :sportsman");
            quer.setParameter("event", event);
            quer.setParameter("sportsman", sportsman);
            List<Result> results = quer.getResultList();
            if(results.isEmpty()) {
                return null;
            }
            return results.get(0);
        } catch (NoResultException excp) {
            return null;
        }
    }

    @Override
    public List<Result> findBySport(Sport sport) {
        LOGGER.logFindBy("sport", sport, true);
        if (sport == null) {
            throw new IllegalArgumentException("Sport to find results for is null");
        }
        try {
            Query quer = entityManager.createQuery("SELECT r FROM Result r WHERE r.event.sport = :sport");
            quer.setParameter("sport", sport);
            return quer.getResultList();
        } catch (NoResultException excp) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Result> findByPerformance(Double performance) {
        LOGGER.logFindBy("performance", performance, true);
        if (performance == null) {
            throw new IllegalArgumentException("Performance you want to find in results is null");
        }

        try {
            Query quer = entityManager.createQuery("SELECT r FROM Result r WHERE r.performance = :performance");
            quer.setParameter("performance", performance);
            return quer.getResultList();
        } catch (NoResultException excp) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Result> findByPosition(Integer position) {
        LOGGER.logFindBy("position", position, true);
        if (position == null) {
            throw new IllegalArgumentException("Position you want to find in results is null");
        }
        try {
            Query quer = entityManager.createQuery("SELECT r FROM Result r WHERE r.position = :position");
            quer.setParameter("position", position);
            return quer.getResultList();
        } catch (NoResultException excp) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Result> findByNote(String note) {
        LOGGER.logFindBy("note", note, true);
        if (note == null) {
            throw new IllegalArgumentException("Note you want to find in results is null");
        }
        try {
            Query quer = entityManager.createQuery("SELECT r FROM Result r WHERE upper(r.note) LIKE :note");
            quer.setParameter("note", "%" + note.toUpperCase() + "%");
            return quer.getResultList();
        } catch (NoResultException excp) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Result> findAll() {
        LOGGER.logFindAll();
        try {
            Query query = entityManager.createQuery("SELECT r FROM Result r");
            return query.getResultList();
        } catch (NoResultException excp) {
            return new ArrayList<>();
        }
    }

    @Override
    public void update(Result result) {
        LOGGER.logUpdate(result);
        if (result == null) {
            throw new IllegalArgumentException("Result you want to update is null");
        }
        entityManager.merge(result);
    }

    @Override
    public void delete(Result result) {
        LOGGER.logDelete(result);
        if (result == null) {
            throw new IllegalArgumentException("Result you want to delete is null");
        }
        entityManager.remove(result);
    }

}
