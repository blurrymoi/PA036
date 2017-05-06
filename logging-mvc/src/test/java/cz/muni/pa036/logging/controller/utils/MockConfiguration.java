package cz.muni.pa036.logging.controller.utils;

import cz.muni.pa036.logging.dto.*;
import cz.muni.pa036.logging.facade.*;
import cz.muni.pa036.logging.service.BeanMappingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.mock;

@Configuration
public class MockConfiguration {

    @Bean
    @Primary
    public EventFacade eventFacade() {
        return mock(EventFacade.class);
    }

    @Bean
    @Primary
    public InvitationFacade invitationFacade() {
        return mock(InvitationFacade.class);
    }

    @Bean
    @Primary
    public ResultFacade resultFacade() {
        return mock(ResultFacade.class);
    }

    @Bean
    @Primary
    public SportFacade sportFacade() {
        return mock(SportFacade.class);
    }

    @Bean
    @Primary
    public SportsmanFacade sportsmanFacade() {
        return mock(SportsmanFacade.class);
    }

    class EventFacadeStub implements EventFacade {

        @Override
        public EventDTO create(EventCreateDTO eventCreateDTO) {
            return null;
        }

        @Override
        public EventDTO findById(Long eventId) {
            return null;
        }

        @Override
        public List<EventDTO> findByName(String name) {
            return null;
        }

        @Override
        public List<EventDTO> findByDate(Calendar date) {
            return null;
        }

        @Override
        public List<EventDTO> findBySport(Long sportId) {
            return null;
        }

        @Override
        public List<EventDTO> findByCity(String city) {
            return null;
        }

        @Override
        public List<EventDTO> findByAdmin(Long adminId) {
            return null;
        }

        @Override
        public List<EventDTO> findByParticipant(Long participantId) {
            return null;
        }

        @Override
        public List<EventDTO> findAll() {
            return null;
        }

        @Override
        public void update(EventUpdateDTO eventUpdateDTO) {

        }

        @Override
        public void delete(Long eventId) {

        }
    }

    class InvitationFacadeStub implements InvitationFacade {

        @Override
        public InvitationDTO invite(long eventId, long inviteeId) {
            return null;
        }

        @Override
        public InvitationDTO invite(EventDTO event, SportsmanDTO invitee) {
            return null;
        }

        @Override
        public InvitationDTO invite(InvitationCreateDTO invitationCreate) {
            return null;
        }

        @Override
        public InvitationDTO accept(InvitationUpdateDTO invitationUpdate) {
            return null;
        }

        @Override
        public InvitationDTO simpleAccept(InvitationUpdateDTO invitationUpdate) {
            return null;
        }

        @Override
        public InvitationDTO decline(InvitationUpdateDTO invitationUpdate) {
            return null;
        }

        @Override
        public InvitationDTO findById(Long id) {
            return null;
        }

        @Override
        public InvitationDTO findByEventAndInvitee(EventDTO event, SportsmanDTO invitee) {
            return null;
        }

        @Override
        public List<InvitationDTO> findByEvent(EventDTO event) {
            return null;
        }

        @Override
        public List<InvitationDTO> findByInvitee(SportsmanDTO invitee) {
            return null;
        }

        @Override
        public List<InvitationDTO> findAll() {
            return null;
        }
    }

    class ResultFacadeStub implements ResultFacade {

        @Override
        public ResultDTO create(ResultCreateDTO result) {
            return null;
        }

        @Override
        public ResultDTO findById(Long id) {
            return null;
        }

        @Override
        public List<ResultDTO> findBySportsman(SportsmanDTO sportsman) {
            return null;
        }

        @Override
        public List<ResultDTO> findByEvent(EventDTO event) {
            return null;
        }

        @Override
        public ResultDTO findBySportsmanAndEvent(SportsmanDTO sportsman, EventDTO event) {
            return null;
        }

        @Override
        public List<ResultDTO> findBySport(SportDTO sport) {
            return null;
        }

        @Override
        public List<ResultDTO> findByPerformance(Double performance) {
            return null;
        }

        @Override
        public List<ResultDTO> findByPosition(Integer position) {
            return null;
        }

        @Override
        public List<ResultDTO> findByNote(String note) {
            return null;
        }

        @Override
        public List<ResultDTO> findAll() {
            return null;
        }

        @Override
        public void update(ResultUpdateDTO result) {

        }

        @Override
        public void delete(Long resultId) {

        }
    }

    class SportFacadeStub implements SportFacade {

        @Override
        public SportDTO create(SportCreateDTO sportCreateDTO) {
            return null;
        }

        @Override
        public SportDTO getSportById(Long id) {
            return null;
        }

        @Override
        public SportDTO getSportByName(String name) {
            return null;
        }

        @Override
        public List<SportDTO> getAllSports() {
            return null;
        }

        @Override
        public void update(SportUpdateDTO sportUpdateDTO) {

        }

        @Override
        public void delete(Long sportId) {

        }
    }

    class SportsmanFacadeStub implements SportsmanFacade {

        @Override
        public SportsmanDTO create(SportsmanCreateDTO sportCreateDTO) {
            return null;
        }

        @Override
        public SportsmanDTO getById(Long id) {
            return null;
        }

        @Override
        public List<SportsmanDTO> getByName(String name) {
            return null;
        }

        @Override
        public List<SportsmanDTO> getBySurname(String surname) {
            return null;
        }

        @Override
        public SportsmanDTO getByEmail(String email) {
            return null;
        }

        @Override
        public List<SportsmanDTO> getAll() {
            return null;
        }

        @Override
        public void update(SportsmanUpdateDTO sportsmanUpdateDTO) {

        }

        @Override
        public void delete(Long id) {

        }

        @Override
        public List<SportsmanDTO> findBySubstring(String substring, Long event_id) {
            return null;
        }
    }

    class BeanMappingServiceStub implements BeanMappingService {

        @Override
        public <T> List<T> mapTo(List<?> list, Class<T> aClass) {
            return null;
        }

        @Override
        public <T> T mapTo(Object o, Class<T> aClass) {
            return null;
        }
    }
}
