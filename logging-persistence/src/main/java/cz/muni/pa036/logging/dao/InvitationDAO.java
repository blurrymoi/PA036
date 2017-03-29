package cz.muni.pa036.logging.dao;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Invitation;
import cz.muni.pa036.logging.entity.Sportsman;

import java.util.List;

/**
 * @author Matej Majdis
 */
public interface InvitationDAO {

	void create(Invitation invitation);

	Invitation findById(Long id);

	List<Invitation> findByEvent(Event event);

	List<Invitation> findByInvitee(Sportsman invitee);

	Invitation findByEventAndInvitee(Event event, Sportsman invitee);

	void update(Invitation invitation);

	void delete(Invitation invitation);

	List<Invitation> findAll();
}
