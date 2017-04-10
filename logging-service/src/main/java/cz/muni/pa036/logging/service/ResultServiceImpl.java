package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.dao.ResultDAO;
import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Result;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;
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

        CRUD_LOGGER.logCreate(result);
        resultDAO.create(result);
    }

    @Override
    public void update(Result result) {

        CRUD_LOGGER.logUpdate(result);
        resultDAO.update(result);
    }

    @Override
    public void delete(Result result) {

        CRUD_LOGGER.logDelete(result);
        resultDAO.delete(result);
    }

    @Override
    public Result findById(Long id) {

        CRUD_LOGGER.logFindBy("ID", id);
        return resultDAO.findById(id);
    }

    @Override
    public List<Result> findAll() {

        CRUD_LOGGER.logFindAll();
        List<Result> results = resultDAO.findAll();
        return results == null ? new ArrayList<>() : results;
        
    }

    @Override
    public List<Result> findBySportsman(Sportsman sportsman) {

        CRUD_LOGGER.logFindBy("sportsman", sportsman);
        List<Result> results = resultDAO.findBySportsman(sportsman);
        return results == null ? new ArrayList<>() : results;
    }

    @Override
    public List<Result> findByEvent(Event event) {

        CRUD_LOGGER.logFindBy("event", event);
        List<Result> results = resultDAO.findByEvent(event);
        return results == null ? new ArrayList<>() : results;
    }

    @Override
    public Result findBySportsmanAndEvent(Sportsman sportsman, Event event) {

        Map<Object, Object> findBy = new HashMap<>();
        findBy.put("sportsman", sportsman);
        findBy.put("event", event);

        CRUD_LOGGER.logFindBy(findBy);
        return resultDAO.findBySportsmanAndEvent(sportsman, event);
    }

    @Override
    public List<Result> findBySport(Sport sport) {

        CRUD_LOGGER.logFindBy("sport", sport);
        List<Result> results = resultDAO.findBySport(sport);
        return results == null ? new ArrayList<>() : results;
    }

    @Override
    public List<Result> findByPerformance(Double performance) {

        CRUD_LOGGER.logFindBy("performance", performance);
        List<Result> results = resultDAO.findByPerformance(performance);
        return results == null ? new ArrayList<>() : results;
    }

    @Override
    public List<Result> findByPosition(Integer position) {

        CRUD_LOGGER.logFindBy("position", position);
        List<Result> results = resultDAO.findByPosition(position);
        return results == null ? new ArrayList<>() : results;
    }

    @Override
    public List<Result> findByNote(String note) {

        CRUD_LOGGER.logFindBy("note", note);
        List<Result> results = resultDAO.findByNote(note);
        return results == null ? new ArrayList<>() : results;
    }
    
}
