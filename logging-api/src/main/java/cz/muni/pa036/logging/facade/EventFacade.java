package cz.muni.pa036.logging.facade;

import cz.muni.pa036.logging.dto.EventUpdateDTO;
import cz.muni.pa036.logging.dto.EventCreateDTO;
import cz.muni.pa036.logging.dto.EventDTO;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;

import java.util.Calendar;
import java.util.List;

/**
 * @author Vit Hovezak
 */
public interface EventFacade {

    EventDTO create(EventCreateDTO eventCreateDTO) throws CreateException;

    EventDTO findById(Long eventId) throws FindByException;

    List<EventDTO> findByName(String name) throws FindByException;

    List<EventDTO> findByDate(Calendar date) throws FindByException;

    List<EventDTO> findBySport(Long sportId) throws FindByException;

    List<EventDTO> findByCity(String city) throws FindByException;

    List<EventDTO> findByAdmin(Long adminId) throws FindByException;

    List<EventDTO> findByParticipant(Long participantId) throws FindByException;

    List<EventDTO> findAll() throws FindByException;

    void update(EventUpdateDTO eventUpdateDTO) throws FindByException, UpdateException;

    void delete(Long eventId) throws FindByException, DeleteException;

}
