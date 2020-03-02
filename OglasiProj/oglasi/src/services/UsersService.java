package services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Recenzija;
import beans.Uloga;
import beans.User;
import dao.KategorijaDAO;
import dao.OglasDAO;
import dao.UserDAO;

@Path("/user")
public class UsersService {

	public UsersService() {
		super();
	}

	@Context
	ServletContext context;

	@PostConstruct
	public void init() {
		String contextPath = context.getRealPath("/");
		if (context.getAttribute("oglasDAO") == null) {
			context.setAttribute("oglasDAO", new OglasDAO(contextPath));
		}

		if (context.getAttribute("userDAO") == null) {
			context.setAttribute("userDAO", new UserDAO(contextPath));
		}

		if (context.getAttribute("kategorijaDAO") == null) {
			context.setAttribute("kategorijaDAO", new KategorijaDAO(contextPath));
		}
	}

	@GET
	@Path("/allUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOglasi(@Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		return Response.status(200).entity(users.getUsers().values()).build();
	}

	@POST
	@Path("/promeniUlogu")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response promeniUlogu(User user, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User userTemp = users.getUsers().get(user.getKorisnickoIme());

		userTemp.setUloga(user.getUloga());


		users.save();

		return Response.status(200).entity("Uloga je izmenjena").build();
	}


}
