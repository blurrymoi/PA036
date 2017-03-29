package cz.muni.pa036.logging.facade;

import cz.muni.pa036.logging.dto.SportDTO;
import cz.muni.pa036.logging.dto.SportUpdateDTO;
import cz.muni.pa036.logging.dto.SportCreateDTO;

import java.util.List;

/**
 * @author Kamil Triscik
 */
public interface SportFacade {

    SportDTO create(SportCreateDTO sportCreateDTO);

    SportDTO getSportById(Long id);

    SportDTO getSportByName(String name);

    List<SportDTO> getAllSports();

    void update(SportUpdateDTO sportUpdateDTO);

    void delete(Long sportId);
}
