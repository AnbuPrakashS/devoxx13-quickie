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
package org.jboss.example.httpcaching.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.example.httpcaching.domain.Attendee;
import org.jboss.example.httpcaching.rest.caching.CacheControlBinding;
import org.jboss.example.httpcaching.rest.caching.CacheInvalidationBinding;
import org.jboss.example.httpcaching.rest.dto.AttendeeRepresentation;
import org.jboss.example.httpcaching.service.AttendeeService;

/**
 * The JAX-RS Resource associated with the {@link Attendee} domain class, and using the {@link AttendeeService} to
 * manipulate the data.
 * 
 * @author Xavier Coulon
 *
 */
@RequestScoped
@Path("/attendees")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class AttendeesResource {

	@Inject
	private AttendeeService attendeeService;

	@Context
	private UriInfo uriInfo;

	@GET
	@CacheControlBinding
	public Response getAllAttendees() {
		final List<AttendeeRepresentation> attendees = attendeeService.getAllAttendees().stream()
				.map(a -> AttendeeRepresentation.from(a)).collect(Collectors.toList());
		return Response.ok(attendees).build();
	}

	@POST
	@CacheInvalidationBinding
	public Response createAttendee(final AttendeeRepresentation entity) {
		final Attendee attendee = new Attendee(entity.getFirstName());
		attendeeService.createAttendee(attendee);
		final URI uri = uriInfo.getAbsolutePathBuilder().path(AttendeesResource.class, "getAttendee")
				.build(attendee.getId());
		return Response.created(uri).entity(AttendeeRepresentation.from(attendee)).build();
	}

	@GET
	@Path("/{id}")
	@CacheControlBinding
	public AttendeeRepresentation getAttendee(@PathParam("id") final int id) {
		Attendee attendee = attendeeService.getAttendee(id);
		return AttendeeRepresentation.from(attendee);
	}

	@PUT
	@Path("/{id}")
	@CacheInvalidationBinding
	public AttendeeRepresentation updateAttendee(@PathParam("id") final int id, final AttendeeRepresentation entity) {
		final Attendee attendee = new Attendee(entity.getId(), entity.getFirstName());
		attendeeService.updateAttendee(attendee);
		return AttendeeRepresentation.from(attendee);
	}

	@DELETE
	@Consumes("text/plain")
	@Produces("text/plain")
	@Path("/{id}")
	@CacheInvalidationBinding
	public void deleteAttendee(@PathParam("id") final int id) {
		attendeeService.deleteAttendee(id);
	}

}
