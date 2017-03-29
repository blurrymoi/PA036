package cz.muni.pa036.logging.service;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Invitation;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.utils.MessageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Matej Majdis
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private EmailService emailService;

	@Override
	public boolean notifyInvitationAccepted(Invitation invitation) {

		final String subject = "SEM Notification";
		boolean check = true;

		for (Sportsman participant : invitation.getEvent().getParticipants()) {
			if (!participant.equals(invitation.getInvitee())) {
				final String body = MessageGenerator.generateInvitationAcceptBody(
						participant.getName(),
						invitation.getInvitee(),
						invitation.getEvent().getName()
				);
				check = check && emailService.sendMessage(subject, body, participant.getEmail());
			}
		}

		final String body = MessageGenerator.generateInvitationAcceptBody(
				invitation.getEvent().getAdmin().getName(),
				invitation.getInvitee(),
				invitation.getEvent().getName()
		);
		return check && emailService.sendMessage(subject, body, invitation.getEvent().getAdmin().getEmail());
	}

	@Override
	public boolean notifyEventEdited(Set<Sportsman> participants, Event event) {

		final String subject = "SEM Notification";
		boolean check = true;

		for (Sportsman participant : participants) {
			final String body = MessageGenerator.generateEventEditBody(participant.getName(), event);
			check = check && emailService.sendMessage(subject, body, participant.getEmail());
		}

		return check;
	}

	@Override
	public boolean notifyEventCanceled(Set<Sportsman> participants, Event event) {

		final String subject = "SEM Notification";
		boolean check = true;

		for (Sportsman participant : participants) {
			final String body = MessageGenerator.generateEventCancelBody(participant.getName(), event);
			check = check && emailService.sendMessage(subject, body, participant.getEmail());
		}

		return check;
	}
}
