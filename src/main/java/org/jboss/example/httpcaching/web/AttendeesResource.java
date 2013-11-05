/**
 * 
 */
package org.jboss.example.httpcaching.web;

import java.net.URI;

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
import org.jboss.example.httpcaching.service.AttendeeService;
import org.jboss.example.httpcaching.web.caching.CacheInvalidationBinding;
import org.jboss.example.httpcaching.web.dto.AttendeeRepresentation;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * @author xcoulon
 *
 */
@RequestScoped
@Path("/attendees")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@CacheInvalidationBinding
public class AttendeesResource {

	@Inject
	private AttendeeService attendeeService;
	
	@Context UriInfo uriInfo;

	@GET
	public Response getAllAttendees() {
		final Iterable<AttendeeRepresentation> attendees = 
				Lists.newArrayList(Iterables.transform(attendeeService.getAllAttendees(), new Function<Attendee, AttendeeRepresentation>() {
			@Override
			public AttendeeRepresentation apply(final Attendee attendee) {
				return AttendeeRepresentation.from(attendee);
			}
		}));
		return Response.ok(attendees).build();
	}
	
	@POST
	@CacheInvalidationBinding
	public Response createAttendee(final AttendeeRepresentation entity) {
		final Attendee attendee = new Attendee(entity.getFirstName());
		attendeeService.createAttendee(attendee);
		final URI uri = uriInfo.getAbsolutePathBuilder().path(AttendeesResource.class, "getAttendee").build(attendee.getId());
		return Response.created(uri).entity(AttendeeRepresentation.from(attendee)).build();
	}
	
	@GET
	@Path("/{id}")
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
