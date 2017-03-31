package cz.muni.pa036.logging.facade;

import cz.muni.pa036.logging.dto.EventUpdateDTO;
import cz.muni.pa036.logging.dto.EventCreateDTO;
import cz.muni.pa036.logging.dto.EventDTO;

import java.util.Calendar;
import java.util.List;

/**
 * @author Vit Hovezak
 */
public interface EventFacade {

    EventDTO create(EventCreateDTO eventCreateDTO);

    EventDTO findById(Long eventId);

    List<EventDTO> findByName(String name);

    List<EventDTO> findByDate(Calendar date);

    List<EventDTO> findBySport(Long sportId);

    List<EventDTO> findByCity(String city);

    List<EventDTO> findByAdmin(Long adminId);

    List<EventDTO> findByParticipant(Long participantId);

    List<EventDTO> findAll();

    void update(EventUpdateDTO eventUpdateDTO);

    void delete(Long eventId);

}
