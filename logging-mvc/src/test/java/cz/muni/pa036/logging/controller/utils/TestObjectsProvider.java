package cz.muni.pa036.logging.controller.utils;

import cz.muni.pa036.logging.dto.EventDTO;
import cz.muni.pa036.logging.dto.EventUpdateDTO;
import cz.muni.pa036.logging.dto.SportDTO;
import cz.muni.pa036.logging.dto.SportsmanDTO;

import java.util.Calendar;

public class TestObjectsProvider {

    public static EventDTO createEventDTO() {
        EventDTO eventDTO = new EventDTO();

        eventDTO.setId(1L);
        eventDTO.setName("Custom Event");
        eventDTO.setDescription("My custom event");
        eventDTO.setCity("Brno");
        eventDTO.setAddress("Somewhere over the rainbow");
        eventDTO.setCapacity(8);
        eventDTO.setDate(TestObjectsProvider.createTomorrow());

        eventDTO.setAdmin(TestObjectsProvider.createSportsmanDTO());
        eventDTO.setSport(TestObjectsProvider.createSportDTO());

        return eventDTO;
    }

    public static EventUpdateDTO createEventUpdateDTO() {
        EventUpdateDTO eventUpdateDTO = new EventUpdateDTO();

        eventUpdateDTO.setId(1L);
        eventUpdateDTO.setName("Custom Event");
        eventUpdateDTO.setDescription("My custom event");
        eventUpdateDTO.setCity("Brno");
        eventUpdateDTO.setAddress("Somewhere over the rainbow");
        eventUpdateDTO.setCapacity(8);
        eventUpdateDTO.setDate(TestObjectsProvider.createTomorrow());

        eventUpdateDTO.setAdmin(TestObjectsProvider.createSportsmanDTO());
        eventUpdateDTO.setSport(TestObjectsProvider.createSportDTO());

        return eventUpdateDTO;
    }

    public static SportsmanDTO createSportsmanDTO() {
        SportsmanDTO sportsmanDTO = new SportsmanDTO();

        sportsmanDTO.setId(1L);
        sportsmanDTO.setName("Usama");
        sportsmanDTO.setSurname("Bolt");
        sportsmanDTO.setBirthDate(TestObjectsProvider.createTomorrow());
        sportsmanDTO.setEmail("bolt@faster.eti");
        sportsmanDTO.setPassword("really fast");
        sportsmanDTO.setIsManager(true);

        return sportsmanDTO;
    }

    public static SportDTO createSportDTO() {
        SportDTO sportDTO = new SportDTO();

        sportDTO.setId(1L);
        sportDTO.setName("Custom Sport");
        sportDTO.setDescription("My custom sport");

        return sportDTO;
    }

    private static Calendar createTomorrow() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);

        return tomorrow;
    }
}
