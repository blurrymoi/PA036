package cz.muni.pa036.logging.dao;

import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.helper.CRUDLogger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Triscik
 */
@Repository
public class SportDAOImpl implements SportDAO {

    private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Sport sport) {
        CRUD_LOGGER.logCreate(sport);
        if (sport == null) {
            throw new IllegalArgumentException("Sport is null");
        }
        em.persist(sport);
    }

    @Override
    public List<Sport> findAll() {
        CRUD_LOGGER.logFindAll();
        try {
            Query query = em.createQuery("SELECT s FROM Sport s");
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Sport findById(Long id) {
        CRUD_LOGGER.logFindBy("ID", id);
        if (id == null) {
            throw new IllegalArgumentException("Sport ID is null");
        }
        return em.find(Sport.class, id);
    }

    @Override
    public Sport findByName(String name) {
        CRUD_LOGGER.logFindBy("name", name);
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Sport name to find results for is null!");
        }
        try {
            return em.createQuery("select s from Sport s where name = :name",
                    Sport.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public void update(Sport sport) {
        CRUD_LOGGER.logUpdate(sport);
        if (sport == null) {
            throw new IllegalArgumentException("Sport is null");
        }
        em.merge(sport);
    }

    @Override
    public void delete(Sport sport) {
        CRUD_LOGGER.logDelete(sport);
        if (sport == null) {
            throw new IllegalArgumentException("Sport is null");
        }
        em.remove(sport);
    }
}
