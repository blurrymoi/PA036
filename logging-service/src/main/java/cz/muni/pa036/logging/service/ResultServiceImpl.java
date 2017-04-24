package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.dao.ResultDAO;
import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Result;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.helper.CRUDLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Veronika Aksamitova
 */
@Service
public class ResultServiceImpl implements ResultService{

    private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

    @Autowired
    private ResultDAO resultDAO;
    
    @Override
    public void create(Result result) {

        try {
            CRUD_LOGGER.logCreate(result);
            resultDAO.create(result);
        } catch (Exception ex) {
            throw new CreateException("Failed to create Result", ex, result);
        }

    }

    @Override
    public void update(Result result) {

        try {
            CRUD_LOGGER.logUpdate(result);
            resultDAO.update(result);
        } catch (Exception ex) {
            throw new UpdateException("Failed to update Result", ex, result);
        }
    }

    @Override
    public void delete(Result result) {

        try {
            CRUD_LOGGER.logDelete(result);
            resultDAO.delete(result);
        } catch (Exception ex) {
            throw new DeleteException("Failed to delete Result", ex, result);
        }
    }

    @Override
    public Result findById(Long id) {

        try {
            CRUD_LOGGER.logFindBy("ID", id);
            return resultDAO.findById(id);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Result by ID", ex, "ID", id);
        }
    }

    @Override
    public List<Result> findAll() {

        try {
            CRUD_LOGGER.logFindAll();
            List<Result> results = resultDAO.findAll();
            return results == null ? new ArrayList<>() : results;
        } catch (Exception ex) {
            throw new FindByException("Failed to find all Results", ex, null);
        }
    }

    @Override
    public List<Result> findBySportsman(Sportsman sportsman) {

        try {
            CRUD_LOGGER.logFindBy("sportsman", sportsman);
            List<Result> results = resultDAO.findBySportsman(sportsman);
            return results == null ? new ArrayList<>() : results;
        } catch (Exception ex) {
            throw new FindByException("Failed to find Result by Sportsman", ex, "sportsman", sportsman);
        }
    }

    @Override
    public List<Result> findByEvent(Event event) {

        try {
            CRUD_LOGGER.logFindBy("event", event);
            List<Result> results = resultDAO.findByEvent(event);
            return results == null ? new ArrayList<>() : results;
        } catch (Exception ex) {
            throw new FindByException("Failed to find Result by Event", ex, "event", event);
        }
    }

    @Override
    public Result findBySportsmanAndEvent(Sportsman sportsman, Event event) {

        Map<Object, Object> findBy = new HashMap<>();
        findBy.put("sportsman", sportsman);
        findBy.put("event", event);

        try {
            CRUD_LOGGER.logFindBy(findBy);
            return resultDAO.findBySportsmanAndEvent(sportsman, event);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Result by Sportsman and Event", ex, "sportsman & event", findBy);
        }
    }

    @Override
    public List<Result> findBySport(Sport sport) {

        try {
            CRUD_LOGGER.logFindBy("sport", sport);
            List<Result> results = resultDAO.findBySport(sport);
            return results == null ? new ArrayList<>() : results;
        } catch (Exception ex) {
            throw new FindByException("Failed to find Result by Sport", ex, "sport", sport);
        }
    }

    @Override
    public List<Result> findByPerformance(Double performance) {

        try {
            CRUD_LOGGER.logFindBy("performance", performance);
            List<Result> results = resultDAO.findByPerformance(performance);
            return results == null ? new ArrayList<>() : results;
        } catch (Exception ex) {
            throw new FindByException("Failed to find Result by Performance", ex, "performance", performance);
        }
    }

    @Override
    public List<Result> findByPosition(Integer position) {

        try {
            CRUD_LOGGER.logFindBy("position", position);
            List<Result> results = resultDAO.findByPosition(position);
            return results == null ? new ArrayList<>() : results;
        } catch (Exception ex) {
            throw new FindByException("Failed to find Result by Position", ex, "position", position);
        }
    }

    @Override
    public List<Result> findByNote(String note) {

        try {
            CRUD_LOGGER.logFindBy("note", note);
            List<Result> results = resultDAO.findByNote(note);
            return results == null ? new ArrayList<>() : results;
        } catch (Exception ex) {
            throw new FindByException("Failed to find Result by Note", ex, "note", note);
        }
    }
}
