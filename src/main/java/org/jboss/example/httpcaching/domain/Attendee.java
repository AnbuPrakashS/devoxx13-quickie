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
package org.jboss.example.httpcaching.domain;

/**
 * A conference attendee
 * 
 * @author Xavier Coulon
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
