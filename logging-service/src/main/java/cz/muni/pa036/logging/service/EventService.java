package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Sport;
import cz.muni.pa036.logging.entity.Sportsman;

import java.util.Calendar;
import java.util.List;

/**
 * @author Vit Hovezak
 */
public interface EventService {

    void create(Event event);

    Event findById(Long id);

    List<Event> findByName(String name);

    List<Event> findByDate(Calendar date);

    List<Event> findBySport(Sport sport);

    List<Event> findByCity(String city);

    List<Event> findByAdmin(Sportsman admin);

    List<Event> findByParticipant(Sportsman participant);

    List<Event> findAll();

    void update(Event event);

    void delete(Event event);
}
