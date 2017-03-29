package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Invitation;
import cz.muni.pa036.logging.entity.Sportsman;

import java.util.List;

/**
 * @author Matej Majdis
 */
public interface InvitationService {

	Invitation invite(long eventId, long inviteeId);

	Invitation invite(Event event, Sportsman invitee);

	Invitation accept(Invitation invitation);

	Invitation simpleAccept(Invitation invitation);

	Invitation decline(Invitation invitation);

	Invitation findById(Long id);

	Invitation findByEventAndInvitee(Event event, Sportsman invitee);

	List<Invitation> findByEvent(Event event);

	List<Invitation> findByInvitee(Sportsman invitee);

	List<Invitation> findAll();
}
