package org.awsaf.javabrains.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.awsaf.javabrains.messenger.model.Messages;
import org.awsaf.javabrains.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResources {
	
	private MessageService ms = new MessageService();
	
	@GET
	public List<Messages> getMessages(@QueryParam("year") int year,
									  @QueryParam("start") int start,
									  @QueryParam("size") int size) {
		if(year > 0) {
			return ms.getAllMessagesForYear(year);
		}
		
		if(start >= 0 && size > 0) {
			return ms.getAllMessagesPaginated(start, size);
		}
		
		return ms.getAllMessages();
	}
	
	@POST
	public Response addMessage(Messages message, @Context UriInfo uriInfo) {
		Messages newMessage = ms.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Messages updateMessage(@PathParam("messageId") long id, Messages message) {
		message.setId(id);
		return ms.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		ms.removeMessage(id);
	}
	
	@GET
	@Path("/{messageId}")
	public Messages getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Messages message = ms.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComment(uriInfo, message), "comment");
		return message;
	}

	private String getUriForComment(UriInfo uriInfo, Messages message) {
		String uri = uriInfo.getBaseUriBuilder()
							.path(MessageResources.class)
							.path(MessageResources.class, "getCommentResource")
							.resolveTemplate("messageId", message.getId())
							.build()
							.toString();
							
		return uri;
	}

	private String getUriForSelf(UriInfo uriInfo, Messages message) {
		String uri = uriInfo.getBaseUriBuilder()
							   .path(MessageResources.class)
							   .path(Long.toString(message.getId()))
							   .build()
							   .toString();
		return uri;
	}
	
	private String getUriForProfile(UriInfo uriInfo, Messages message) {
		String uri = uriInfo.getBaseUriBuilder()
							   .path(ProfileResources.class)
							   .path(message.getAuthor())
							   .build()
							   .toString();
		return uri;
	}
	
	@Path("/{messageId}/comments")
	public CommentResources getCommentResource() {
		return new CommentResources();
	}
}
