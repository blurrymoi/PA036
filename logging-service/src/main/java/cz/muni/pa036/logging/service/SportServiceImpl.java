package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.dao.SportDAO;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.helper.CRUDLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kamil Triscik
 */
@Service
public class SportServiceImpl implements SportService {

    private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

    @Autowired
    private SportDAO sportDAO;

    @Override
    public Sport findById(Long id) {

        CRUD_LOGGER.logFindBy("ID", id);
        return sportDAO.findById(id);
    }

    @Override
    public void create(Sport sport) {

        CRUD_LOGGER.logCreate(sport);
        sportDAO.create(sport);
    }

    @Override
    public void update(Sport sport) {

        CRUD_LOGGER.logUpdate(sport);
        sportDAO.update(sport);
    }

    @Override
    public void delete(Sport sport) {

        CRUD_LOGGER.logDelete(sport);
        sportDAO.delete(sport);
    }

    @Override
    public List<Sport> findAll() {

        CRUD_LOGGER.logFindAll();
        return sportDAO.findAll();
    }

    @Override
    public Sport findByName(String name) {

        CRUD_LOGGER.logFindBy("name", name);
        return sportDAO.findByName(name);
    }
}
