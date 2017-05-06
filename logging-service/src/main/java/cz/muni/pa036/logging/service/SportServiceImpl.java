package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.dao.SportDAO;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;
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

        try {
            CRUD_LOGGER.logFindBy("ID", id);
            return sportDAO.findById(id);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Sport by ID", ex, "ID", id);
        }
    }

    @Override
    public void create(Sport sport) {

        try {
            CRUD_LOGGER.logCreate(sport);
            sportDAO.create(sport);
        } catch (Exception ex) {
            throw new CreateException("Failed to create Sport", ex, sport);
        }
    }

    @Override
    public void update(Sport sport) {

        try {
            CRUD_LOGGER.logUpdate(sport);
            sportDAO.update(sport);
        } catch (Exception ex) {
            throw new UpdateException("Failed to update Sport", ex, sport);
        }
    }

    @Override
    public void delete(Sport sport) {

        try {
            CRUD_LOGGER.logDelete(sport);
            sportDAO.delete(sport);
        } catch (Exception ex) {
            throw new DeleteException("Failed to delete Sport", ex, sport);
        }
    }

    @Override
    public List<Sport> findAll() {

        try {
            CRUD_LOGGER.logFindAll();
            return sportDAO.findAll();
        } catch (Exception ex) {
            throw new FindByException("Failed to find all Sports", ex, null);
        }
    }

    @Override
    public Sport findByName(String name) {

        try {
            CRUD_LOGGER.logFindBy("name", name);
            return sportDAO.findByName(name);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Sport by name", ex, "name", name);
        }
    }
}
