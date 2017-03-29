package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Invitation;
import cz.muni.pa036.logging.entity.Sportsman;

import java.util.Set;

/**
 * @author Matej Majdis
 */
public interface NotificationService {

	boolean notifyInvitationAccepted(Invitation invitation);

	boolean notifyEventEdited(Set<Sportsman> participants, Event event);

	boolean notifyEventCanceled(Set<Sportsman> participants, Event event);
}
