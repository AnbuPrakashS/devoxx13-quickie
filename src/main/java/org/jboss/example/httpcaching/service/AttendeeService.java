/**
 * 
 */
package org.jboss.example.httpcaching.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.example.httpcaching.domain.Attendee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Attendee Service.
 * 
 * @author xcoulon
 * 
 */
@Singleton
@Startup
public class AttendeeService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AttendeeService.class);

	private final Map<Integer, Attendee> attendees = new HashMap<>();
	
	private AtomicInteger idSequence = new AtomicInteger(1);

	@PostConstruct
	public void initData() {
		final Attendee xavier = new Attendee(0, "Xavier");
		attendees.put(xavier.getId(), xavier);
		final Attendee max = new Attendee(1, "Max");
		attendees.put(max.getId(), max);
		LOGGER.info("Attendees Data loaded.");
	}

	/**
	 * Returns the {@link Attendee} identified by the given {@code id} or
	 * {@code null} if none was found.
	 * 
	 * @param id
	 *            the attendee id
	 * @return the attendee with the given {@code id} or {@code null}.
	 */
	public Attendee getAttendee(final int id) {
		return attendees.get(id);
	}

	/**
	 * @return a collection containing all {@link Attendee}s in the application.
	 */
	public Collection<Attendee> getAllAttendees() {
		return attendees.values();
	}

	/**
	 * Sets the given {@link Attendee}'s ID and stores it.
	 * @param attendee the attendee to store
	 * @return the stored attendee
	 */
	public Attendee createAttendee(final Attendee attendee) {
		attendee.setId(idSequence.incrementAndGet());
		attendees.put(attendee.getId(), attendee);
		return attendee;
	}

	/**
	 * Removes the {@link Attendee} identified by the given id from the memory store. 
	 * @param id the id of the attendee to remove
	 */
	public void deleteAttendee(final int id) {
		attendees.remove(id);
	}

	/**
	 * Updates the given {@link Attendee} in the memory store. 
	 * @param id the id of the attendee to update
	 */
	public void updateAttendee(final Attendee attendee) {
		attendees.put(attendee.getId(), attendee);
	}

}
