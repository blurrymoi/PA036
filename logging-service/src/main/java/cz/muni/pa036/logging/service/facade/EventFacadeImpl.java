package cz.muni.pa036.logging.service.facade;

import cz.muni.pa036.logging.dto.EventCreateDTO;
import cz.muni.pa036.logging.dto.EventDTO;
import cz.muni.pa036.logging.dto.EventUpdateDTO;
import cz.muni.pa036.logging.dto.SportDTO;
import cz.muni.pa036.logging.dto.SportsmanDTO;
import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;
import cz.muni.pa036.logging.facade.EventFacade;
import cz.muni.pa036.logging.service.BeanMappingService;
import cz.muni.pa036.logging.service.EventService;
import cz.muni.pa036.logging.service.SportService;
import cz.muni.pa036.logging.service.SportsmanService;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vit Hovezak
 */
@Service
@Transactional
public class EventFacadeImpl implements EventFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SportService sportService;

    @Autowired
    private SportsmanService sportsmanService;

    @Override
    public EventDTO create(EventCreateDTO eventCreateDTO) throws CreateException {
        Event event = new Event();
        event.setName(eventCreateDTO.getName());
        event.setDescription(eventCreateDTO.getDescription());
        event.setDate(eventCreateDTO.getDate());
        SportDTO sportDTO = eventCreateDTO.getSport();
        event.setSport(beanMappingService.mapTo(sportDTO, Sport.class));
        event.setCapacity(eventCreateDTO.getCapacity());
        event.setCity(eventCreateDTO.getCity());
        event.setAddress(eventCreateDTO.getAddress());
        SportsmanDTO sportsmanDTO = eventCreateDTO.getAdmin();
        event.setAdmin(beanMappingService.mapTo(sportsmanDTO, Sportsman.class));
        eventService.create(event);
        return beanMappingService.mapTo(event, EventDTO.class);
    }

    @Override
    public EventDTO findById(Long eventId) throws FindByException {
        Event result = eventService.findById(eventId);
        return beanMappingService.mapTo(result, EventDTO.class);
    }

    @Override
    public List<EventDTO> findByName(String name) throws FindByException {
        List<Event> result = eventService.findByName(name);
        return beanMappingService.mapTo(result, EventDTO.class);
    }

    @Override
    public List<EventDTO> findByDate(Calendar date) throws FindByException {
        List<Event> result = eventService.findByDate(date);
        return beanMappingService.mapTo(result, EventDTO.class);
    }

    @Override
    public List<EventDTO> findBySport(Long sportId) throws FindByException {
        Sport sport = sportService.findById(sportId);
        List<Event> result = eventService.findBySport(sport);
        return beanMappingService.mapTo(result, EventDTO.class);
    }

    @Override
    public List<EventDTO> findByCity(String city) throws FindByException {
        List<Event> result = eventService.findByCity(city);
        return beanMappingService.mapTo(result, EventDTO.class);
    }

    @Override
    public List<EventDTO> findByAdmin(Long adminId) throws FindByException {
        Sportsman sportsman = sportsmanService.findById(adminId);
        List<Event> result = eventService.findByAdmin(sportsman);
        return beanMappingService.mapTo(result, EventDTO.class);
    }

    // TODO: 15-Dec-16 bad preco je tam Id
    @Override
    public List<EventDTO> findByParticipant(Long participantId) throws FindByException {
        Sportsman sportsman = sportsmanService.findById(participantId);
        List<Event> result = eventService.findByParticipant(sportsman);
        return beanMappingService.mapTo(result, EventDTO.class);
    }

    @Override
    public List<EventDTO> findAll() throws FindByException {
        List<Event> result = eventService.findAll();
        return beanMappingService.mapTo(result, EventDTO.class);
    }

    @Override
    public void update(EventUpdateDTO eventUpdateDTO) throws FindByException, UpdateException {
        Event event = eventService.findById(eventUpdateDTO.getId());
        event.setName(eventUpdateDTO.getName());
        event.setDescription(eventUpdateDTO.getDescription());
        event.setDate(eventUpdateDTO.getDate());
        SportDTO sportDTO = eventUpdateDTO.getSport();
        event.setSport(beanMappingService.mapTo(sportDTO, Sport.class));
        event.setCapacity(eventUpdateDTO.getCapacity());
        event.setCity(eventUpdateDTO.getCity());
        event.setAddress(eventUpdateDTO.getAddress());
        SportsmanDTO sportsmanDTO = eventUpdateDTO.getAdmin();
        event.setAdmin(beanMappingService.mapTo(sportsmanDTO, Sportsman.class));
        eventService.update(event);
    }

    @Override
    public void delete(Long eventId) throws FindByException, DeleteException {
        Event event = eventService.findById(eventId);
        eventService.delete(event);
    }

    /**
     * Only for unit tests
     * @param beanMappingService
     */
    public void setBeanMappingService(BeanMappingService beanMappingService) {
        this.beanMappingService = beanMappingService;
    }

}
