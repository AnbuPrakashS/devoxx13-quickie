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
package org.jboss.example.httpcaching.rest.dto;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.example.httpcaching.domain.Attendee;

/**
 * DTO/Representation of a {@link Attendee}. Can be serialized in JSON or XML
 * to be exchanged with clients.
 * 
 * @author Xavier Coulon
 *
 */
@XmlRootElement(name="customer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class AttendeeRepresentation {

	/**
	 * Creates an {@link AttendeeRepresentation} from the given {@code attendee}.
	 * @param attendee
	 *            the {@link Attendee} to marshall
	 * @return an {@link AttendeeRepresentation} of the given {@code attendee},
	 *         or {@code null} if the given {@code attendee} was {@code null}.
	 */
	public static AttendeeRepresentation from(final Attendee attendee) {
		if(attendee == null) {
			return null;
		}
		final AttendeeRepresentation r = new AttendeeRepresentation();
		r.setId(attendee.getId());
		r.setFirstName(attendee.getFirstName());
		return r;
	}
	
	/** the attendee id. */
	@XmlAttribute(name="id")
	private int id;
	
	/** the attendee's first name.*/
	@XmlAttribute(name="firstName")
	private String firstName;

	/**
	 * Empty Constructor.
	 */
	public AttendeeRepresentation() {
		super();
	}

	/**
	 * Constructor
	 * @param attendee the attendee
	 */
	public AttendeeRepresentation(final Attendee attendee) {
		super();
		this.id = attendee.getId();
		this.firstName = attendee.getFirstName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "CustomerRepresentation [id=" + id + ", firstName=" + firstName
				+ "]";
	}

}
