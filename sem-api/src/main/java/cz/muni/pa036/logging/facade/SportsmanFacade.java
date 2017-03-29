package cz.muni.pa036.logging.facade;

import cz.muni.pa036.logging.dto.SportsmanUpdateDTO;
import cz.muni.pa036.logging.dto.SportsmanCreateDTO;
import cz.muni.pa036.logging.dto.SportsmanDTO;

import java.util.List;

/**
 * @author Matej Majdis
 */
public interface SportsmanFacade {

	SportsmanDTO create(SportsmanCreateDTO sportCreateDTO);

	SportsmanDTO getById(Long id);

	List<SportsmanDTO> getByName(String name);

	List<SportsmanDTO> getBySurname(String surname);

	SportsmanDTO getByEmail(String email);

	List<SportsmanDTO> getAll();

	void update(SportsmanUpdateDTO sportsmanUpdateDTO);

	void delete(Long id);

	List<SportsmanDTO> findBySubstring(String substring, Long event_id);
}
