package services;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.User;
import dao.KategorijaDAO;
import dao.OglasDAO;
import dao.UserDAO;

@Path("/checkIn")
public class CheckInService {

	public CheckInService() {
		super();
	}

	@Context
	ServletContext context;

	@PostConstruct
	public void init() {
		String contextPath = context.getRealPath("/");
		if (context.getAttribute("userDAO") == null) {
			context.setAttribute("userDAO", new UserDAO(contextPath));
		}
		
		if (context.getAttribute("oglasDAO") == null) {
			context.setAttribute("oglasDAO", new OglasDAO(contextPath));
		}
		
		if (context.getAttribute("kategorijaDAO") == null) {
			context.setAttribute("kategorijaDAO", new KategorijaDAO(contextPath));
		}
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User u = users.find(user);
		if (u == null) {
			return Response.status(400).entity("Korisnicko Ime ili Lozinka nisu ispravni").build();
		}

		request.getSession().setAttribute("user", u);

		return Response.status(200).entity(u).build();
	}

	@POST
	@Path("/logout")
	public void logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User u = users.add(user);

		if (u == null) {
			return Response.status(400).entity("Korisnik vec postoji").build();
		}

		request.getSession().setAttribute("user", u);
		
		users.save();

		return Response.status(200).entity(u).build();

	}

	@GET
	@Path("/currentUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentUser(@Context HttpServletRequest request) {
		
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		return Response.status(200).entity(user).build();
	}

}
