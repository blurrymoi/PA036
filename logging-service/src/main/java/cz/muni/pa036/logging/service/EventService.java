package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.exceptions.CreateException;
import cz.muni.pa036.logging.exceptions.DeleteException;
import cz.muni.pa036.logging.exceptions.FindByException;
import cz.muni.pa036.logging.exceptions.UpdateException;

import java.util.Calendar;
import java.util.List;

/**
 * @author Vit Hovezak
 */
public interface EventService {

    void create(Event event) throws CreateException;

    Event findById(Long id) throws FindByException;

    List<Event> findByName(String name) throws FindByException;

    List<Event> findByDate(Calendar date) throws FindByException;

    List<Event> findBySport(Sport sport) throws FindByException;

    List<Event> findByCity(String city) throws FindByException;

    List<Event> findByAdmin(Sportsman admin) throws FindByException;

    List<Event> findByParticipant(Sportsman participant) throws FindByException;

    List<Event> findAll() throws FindByException;

    void update(Event event) throws UpdateException;

    void delete(Event event) throws FindByException, DeleteException;
}
