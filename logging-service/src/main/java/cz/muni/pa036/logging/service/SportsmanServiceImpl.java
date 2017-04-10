package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.dao.SportsmanDAO;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.utils.Constants;
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
			throw new IllegalArgumentException("Invalid argument - sportsman");
		}

		sportsman.setPassword(hashPassword(sportsman.getPassword()));
		CRUD_LOGGER.logCreate(sportsman);
		sportsmanDAO.create(sportsman);
	}

	@Override
	public Sportsman findById(Long id) {

		CRUD_LOGGER.logFindBy("ID", id);
		return sportsmanDAO.findById(id);
	}

	@Override
	public List<Sportsman> findByName(String name) {

		CRUD_LOGGER.logFindBy("name", name);
		return sportsmanDAO.findByName(name);
	}

	@Override
	public List<Sportsman> findBySurname(String surname) {

		CRUD_LOGGER.logFindBy("surname", surname);
		return sportsmanDAO.findBySurname(surname);
	}

	@Override
	public Sportsman findByEmail(String email) {

		CRUD_LOGGER.logFindBy("email", email);
		return sportsmanDAO.findByEmail(email);
	}

	@Override
	public List<Sportsman> findAll() {

		CRUD_LOGGER.logFindAll();
		return sportsmanDAO.findAll();
	}

	@Override
	public void update(Sportsman sportsman) {

		CRUD_LOGGER.logUpdate(sportsman);
		sportsmanDAO.update(sportsman);
	}

	@Override
	public List<Sportsman> findBySubstring(String substring, Long event_id) {

		CRUD_LOGGER.logFindBy("substring", substring);
		return sportsmanDAO.findBySubstring(substring, event_id);
	}

	@Override
	public void delete(Sportsman sportsman) {

		CRUD_LOGGER.logDelete(sportsman);
		sportsmanDAO.delete(sportsman);
	}

	private String hashPassword(String password) {

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(Constants.BC_STRENGTH);
		return passwordEncoder.encode(password);
	}
}
