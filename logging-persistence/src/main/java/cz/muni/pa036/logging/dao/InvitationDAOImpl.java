package cz.muni.pa036.logging.dao;

import cz.muni.pa036.logging.entity.Event;
import cz.muni.pa036.logging.entity.Invitation;
import cz.muni.pa036.logging.entity.Sportsman;
import cz.muni.pa036.logging.utils.DAOLogger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matej Majdis
 */
@Repository
public class InvitationDAOImpl implements InvitationDAO {

	private final DAOLogger LOGGER = new DAOLogger(LoggerFactory.getLogger(this.getClass()), "INVITATION");

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void create(Invitation invitation) {
		LOGGER.logCreate(invitation);
		if (invitation == null) {
			throw new IllegalArgumentException("Invitation is null");
		}
		entityManager.persist(invitation);
	}

	@Override
	public Invitation findById(Long id) {
        LOGGER.logFindBy("ID", id);
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		return entityManager.find(Invitation.class, id);
	}

	@Override
	public List<Invitation> findByEvent(Event event) {
	    LOGGER.logFindBy("event", event, true);
		if (event == null) {
			throw new IllegalArgumentException("event is null");
		}
		try {
			return entityManager.createQuery("select i from Invitation i where event = :event",
					Invitation.class).setParameter("event", event).getResultList();
		} catch (NoResultException nrf) {
			return null;
		}
	}

	@Override
	public List<Invitation> findByInvitee(Sportsman invitee) {
		LOGGER.logFindBy("invitee", invitee, true);
		if (invitee == null) {
			throw new IllegalArgumentException("invitee is null");
		}
		try {
			return entityManager.createQuery("select i from Invitation i where invitee = :invitee",
					Invitation.class).setParameter("invitee", invitee).getResultList();
		} catch (NoResultException nrf) {
			return null;
		}
	}

	@Override
	public Invitation findByEventAndInvitee(Event event, Sportsman invitee) {
		Map<Object, Object> findBy = new HashMap<>();
		findBy.put("event", event);
		findBy.put("invitee", invitee);

		LOGGER.logFindBy(findBy);
		if (event == null) {
			throw new IllegalArgumentException("event is null");
		}
		if (invitee == null) {
			throw new IllegalArgumentException("invitee is null");
		}
		List<Invitation> invitations;
		try {
			invitations = entityManager.createQuery("select i from Invitation i where event = :event and invitee = :invitee",
					Invitation.class)
					.setParameter("event", event)
					.setParameter("invitee", invitee)
					.getResultList();
		} catch (NoResultException nrf) {
			return null;
		}

		if(invitations == null || invitations.isEmpty()) {
			return null;
		}

		if(invitations.size() > 1)  {
			throw new IllegalStateException("Multiple Invitations found for same Event and Sportsman");
		}

		return invitations.get(0);
	}

	@Override
	public void update(Invitation invitation) {
		LOGGER.logUpdate(invitation);
		if (invitation == null) {
			throw new IllegalArgumentException("Invitation is null");
		}
		entityManager.merge(invitation);
	}

	@Override
	public void delete(Invitation invitation) {
		LOGGER.logDelete(invitation);
		if (invitation == null) {
			throw new IllegalArgumentException("Invitation is null");
		}
		entityManager.remove(invitation);
	}

	@Override
	public List<Invitation> findAll() {
		LOGGER.logFindAll();
		Query query = entityManager.createQuery("SELECT i FROM Invitation i");
		return query.getResultList();
	}
}
