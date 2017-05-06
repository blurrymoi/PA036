package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.dao.SportsmanDAO;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.utils.Constants;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.helper.CRUDLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Matej Majdis
 */
@Service
public class SportsmanServiceImpl implements SportsmanService {

	private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

	@Autowired
	private SportsmanDAO sportsmanDAO;

	@Override
	public void create(Sportsman sportsman) {

		if(sportsman == null || sportsman.getPassword() == null || sportsman.getPassword().isEmpty()) {
            throw new CreateException("Failed to create Sportsman", new Exception(), sportsman);
		}

		sportsman.setPassword(hashPassword(sportsman.getPassword()));
		try {
            CRUD_LOGGER.logCreate(sportsman);
            sportsmanDAO.create(sportsman);
		} catch (Exception ex) {
			throw new CreateException("Failed to create Sportsman", ex, sportsman);
		}
	}

	@Override
	public Sportsman findById(Long id) {

        try {
            CRUD_LOGGER.logFindBy("ID", id);
            return sportsmanDAO.findById(id);
        } catch (Exception ex) {
            throw new FindByException ("Failed to find Sportsman by ID", ex, "ID", id);
        }
	}

	@Override
	public List<Sportsman> findByName(String name) {

        try {
            CRUD_LOGGER.logFindBy("name", name);
            return sportsmanDAO.findByName(name);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Sportsman by name", ex, "name", name);
        }
	}

	@Override
	public List<Sportsman> findBySurname(String surname) {

        try {
            CRUD_LOGGER.logFindBy("surname", surname);
            return sportsmanDAO.findBySurname(surname);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Sportsman by surname", ex, "surname", surname);
        }
	}

	@Override
	public Sportsman findByEmail(String email) {

        try {
            CRUD_LOGGER.logFindBy("email", email);
            return sportsmanDAO.findByEmail(email);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Sportsman by email", ex, "email", email);
        }
	}

	@Override
	public List<Sportsman> findAll() {

        try {
            CRUD_LOGGER.logFindAll();
            return sportsmanDAO.findAll();
        } catch (Exception ex) {
            throw new FindByException("Failed to find all Sportsmen", ex, null);
        }
	}

	@Override
	public void update(Sportsman sportsman) {

        try {
            CRUD_LOGGER.logUpdate(sportsman);
            sportsmanDAO.update(sportsman);
        } catch (Exception ex) {
            throw new UpdateException("Failed to update Sportsman", ex, sportsman);
        }
	}

	@Override
	public List<Sportsman> findBySubstring(String substring, Long event_id) {

        try {
            CRUD_LOGGER.logFindBy("substring", substring);
            return sportsmanDAO.findBySubstring(substring, event_id);
        } catch (Exception ex) {
            throw new FindByException("Failed to find Sportsman by substring", ex, "substring", substring);
        }
	}

	@Override
	public void delete(Sportsman sportsman) {

        try {
            CRUD_LOGGER.logDelete(sportsman);
            sportsmanDAO.delete(sportsman);
        } catch (Exception ex) {
            throw new DeleteException("Failed to delete Sportsman", ex, sportsman);
        }
	}

	private String hashPassword(String password) {

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(Constants.BC_STRENGTH);
		return passwordEncoder.encode(password);
	}
}
