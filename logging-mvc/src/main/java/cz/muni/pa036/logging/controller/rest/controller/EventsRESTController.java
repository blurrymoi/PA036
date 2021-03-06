package cz.muni.pa036.logging.controller.rest.controller;

import cz.muni.pa036.logging.dto.EventCreateDTO;
import cz.muni.pa036.logging.dto.EventDTO;
import cz.muni.pa036.logging.dto.EventUpdateDTO;
import cz.muni.pa036.logging.facade.EventFacade;
import cz.muni.pa036.logging.util.REST_URI;
import cz.muni.pa036.logging.controller.rest.exception.*;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Kamil Triscik.
 */
@RestController
@RequestMapping(REST_URI.ROOT_EVENTS_URI)
public class EventsRESTController {

    @Inject
    private EventFacade eventFacade;

    /**
     * Find all events (by name - optional)
     * CURL example in README.md
     *
     * @param name optional param
     * @return requested objects
     * @throws ResourceNotFoundException in case requested objects not found
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> getAllEvents(@RequestParam(value="name", required = false) String name) {
        try {
            List<EventDTO> sportsmanDTOs = eventFacade.findAll();
            if(name != null) {
                sportsmanDTOs.retainAll(eventFacade.findByName(name));
            }
            return sportsmanDTOs;
        } catch (Exception e) {
            throw new ResourceNotFoundException(e);
        }
    }

    /**
     * Find object by id
     * CURL example in README.md
     *
     * @param id of object we want to find
     * @return requested object
     * @throws ResourceNotFoundException in case requested object not found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final EventDTO getEvent(@PathVariable("id") long id) {
        try {
            return eventFacade.findById(id);
        } catch (Exception e){
            throw new ResourceNotFoundException(e);
        }
    }

    /**
     * Create new event
     * CURL example in README.md
     *
     * @param event EventCreateDTO object
     * @param bindingResult eventUpdateDTO values validator
     * @return created event
     * @throws InvalidResourceException in case of validation error
     * @throws ResourceNotModifiedException in case of update failure
     * @throws ResourceNotFoundException in case of updated object not found
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final EventDTO createEvent(@Valid @RequestBody EventCreateDTO event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidResourceException();
        }
        try {
            return eventFacade.create(event);
        } catch (Exception e) {
            throw new ExistingResourceException(e);
        }
    }

    /**
     * Update already existing event
     * CURL example in README.md
     *
     * @param eventUpdateDTO object we want to update
     * @param bindingResult object fields values validator
     * @return updated event
     * @throws InvalidResourceException in case of validation error
     * @throws ResourceNotModifiedException in case of update failure
     * @throws ResourceNotFoundException in case of updated object not found
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final EventDTO updateEvent(@Valid @RequestBody EventUpdateDTO eventUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidResourceException();
        }
        try {
            eventFacade.update(eventUpdateDTO);
        } catch (Exception e) {
            throw new ResourceNotModifiedException(e);
        }
        try {
            return eventFacade.findById(eventUpdateDTO.getId());
        } catch (Exception e) {
            throw new ResourceNotFoundException(e);
        }
    }

    /**
     * Delete event by id
     * CURL example in README.md
     *
     * @param id of object to delete
     * @throws ResourceDeleteException in case of delete failure
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteEvent(@PathVariable("id") long id) {
        try {
            eventFacade.delete(id);
        } catch (Exception e) {
            throw new ResourceDeleteException(e);
        }
    }
}