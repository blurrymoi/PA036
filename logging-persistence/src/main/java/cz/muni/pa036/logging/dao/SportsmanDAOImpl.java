package cz.muni.pa036.logging.dao;

import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.helper.CRUDLogger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Matej Majdis
 */
@Repository
public class SportsmanDAOImpl implements SportsmanDAO {

    private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Sportsman sportsman) {
        CRUD_LOGGER.logCreate(sportsman);
        if (sportsman == null) {
            throw new IllegalArgumentException("Sportsman is null");
        }
        entityManager.persist(sportsman);
    }

    @Override
    public Sportsman findById(Long id) {
        CRUD_LOGGER.logFindBy("ID", id);
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return entityManager.find(Sportsman.class, id);
    }

    @Override
    public List<Sportsman> findByName(String name) {
        CRUD_LOGGER.logFindBy("name", name, true);
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }
        try {
            List<Sportsman> result = entityManager.createQuery("select s from Sportsman s where name = :name",
                    Sportsman.class).setParameter("name", name).getResultList();
            if (result.isEmpty()) {
                return null;
            }
            return result;
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public List<Sportsman> findBySurname(String surname) {
        CRUD_LOGGER.logFindBy("surname", surname, true);
        if (surname == null) {
            throw new IllegalArgumentException("surname is null");
        }
        try {
            List<Sportsman> result = entityManager.createQuery("select s from Sportsman s where surname = :surname",
                    Sportsman.class).setParameter("surname", surname).getResultList();
            if (result.isEmpty()) {
                return null;
            }
            return result;
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    //TODO : optimize
    public Sportsman findByEmail(String email) {
        CRUD_LOGGER.logFindBy("email", email);
        if (email == null) {
            throw new IllegalArgumentException("email is null");
        }
        try {
            List<Sportsman> result = entityManager.createQuery("select s from Sportsman s where email = :email",
                    Sportsman.class).setParameter("email", email).getResultList();
            if (result.isEmpty()) {
                return null;
            }
            if (result.size() != 1) {
                throw new IllegalStateException("email is not unique");
            }
            return result.get(0);
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public void update(Sportsman sportsman) {
        CRUD_LOGGER.logUpdate(sportsman);
        if (sportsman == null) {
            throw new IllegalArgumentException("Sportsman is null");
        }
        entityManager.merge(sportsman);
    }

    @Override
    public void delete(Sportsman sportsman) {
        CRUD_LOGGER.logDelete(sportsman);
        if (sportsman == null) {
            throw new IllegalArgumentException("Sportsman is null");
        }
        entityManager.remove(sportsman);
    }

    @Override
    public List<Sportsman> findAll() {
        CRUD_LOGGER.logFindAll();
        Query query = entityManager.createQuery("SELECT s FROM Sportsman s");
        return query.getResultList();
    }

    @Override
    public List<Sportsman> findBySubstring(String substring, Long event_id) {
        CRUD_LOGGER.logFindBy("substring", substring, true);
        if (substring == null) {
            throw new IllegalArgumentException("substring is null");
        }
        if (substring.isEmpty()) {
            return this.findAll();
        }
        Long event = event_id;
        if (event_id == null) {
            event = -1L;
        }
        try {
            Query quer = entityManager.createQuery("select s from Sportsman s where " +
                    "(LOWER(s.name) LIKE :substring OR LOWER(s.surname) LIKE :substring OR LOWER(s.email) LIKE :substring) " +
                    "AND s.id NOT IN (select r.sportsman.id from Result r where r.event.id=:event_id) " + //not already enrolled
                    "AND s.id NOT IN (select i.invitee.id from Invitation i where i.event.id=:event_id)"); //not already invited
            quer.setParameter("substring", "%" + substring + "%");
            quer.setParameter("event_id", event);
            return quer.getResultList();
        } catch (NoResultException nrf) {
            return null;
        }
    }
}
