/**
 * Copyright (c) 2014 Xavier Coulon and contributors (see git log)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.jboss.example.httpcaching.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.jboss.example.httpcaching.domain.Attendee;
import org.slf4j.Logger;

/**
 * The Attendee Service.
 * 
 * @author Xavier Coulon
 * 
 */
@Singleton
@Startup
public class AttendeeService {

	@Inject
	private Logger logger;
	
	private final Map<Integer, Attendee> attendees = new HashMap<>();
	
	private AtomicInteger idSequence = new AtomicInteger(1);

	/**
	 * Loads the data in the {@code attendees} {@link Map} when the application
	 * starts, so that we have some data to play with ;-)
	 */
	@PostConstruct
	public void initData() {
		final Attendee xavier = new Attendee(0, "Xavier");
		attendees.put(xavier.getId(), xavier);
		final Attendee max = new Attendee(1, "Max");
		attendees.put(max.getId(), max);
		logger.info("Attendees Data loaded.");
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
