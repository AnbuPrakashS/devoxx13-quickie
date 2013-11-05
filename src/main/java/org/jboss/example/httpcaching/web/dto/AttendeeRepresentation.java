/**
 * 
 */
package org.jboss.example.httpcaching.web.dto;

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
 * @author xcoulon
 *
 */
@XmlRootElement(name="customer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class AttendeeRepresentation {

	public static AttendeeRepresentation from(Attendee attendee) {
		if(attendee == null) {
			return null;
		}
		final AttendeeRepresentation r = new AttendeeRepresentation();
		r.setId(attendee.getId());
		r.setFirstName(attendee.getFirstName());
		return r;
	}
	
	/** Customer id. */
	@XmlAttribute(name="id")
	private int id;
	
	/** Customer's first name.*/
	@XmlAttribute(name="firstName")
	private String firstName;

	/**
	 * Constructor
	 */
	public AttendeeRepresentation() {
		super();
	}

	/**
	 * Constructor
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
