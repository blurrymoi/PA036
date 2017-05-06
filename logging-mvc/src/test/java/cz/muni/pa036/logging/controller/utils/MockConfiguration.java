package cz.muni.pa036.logging.controller.utils;

import cz.muni.pa036.logging.facade.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
}
