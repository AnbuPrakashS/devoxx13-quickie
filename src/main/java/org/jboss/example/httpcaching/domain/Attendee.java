package org.jboss.example.httpcaching.domain;

/**
 * A conference attendee (who's going to drink a beer, eventually...)
 * 
 * @author xcoulon
 *
 */
public class Attendee {
	
	/** Customer id. */
	private int id;
	
	/** Customer's first name.*/
	private String firstName;

	/**
	 * Constructor
	 * @param id the customer's id
	 * @param firstName the curstomer's first name
	 */
	public Attendee(final int id, final String firstName) {
		super();
		this.id = id;
		this.firstName = firstName;
	}

	/**
	 * Constructor
	 * @param firstName the curstomer's first name
	 */
	public Attendee(final String firstName) {
		super();
		this.firstName = firstName;
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
	
	

	
	
}
