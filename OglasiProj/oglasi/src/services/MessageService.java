package services;

import java.util.ArrayList;
import java.util.List;

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

import beans.Poruka;
import beans.Uloga;
import beans.User;
import dao.KategorijaDAO;
import dao.OglasDAO;
import dao.UserDAO;

@Path("/message")
public class MessageService {

	public MessageService() {
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
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		Uloga uloga = Uloga.Kupac;

		if (user.getUloga() == Uloga.Kupac) {
			uloga = Uloga.Prodavac;
		} else if (user.getUloga() == Uloga.Prodavac) {
			uloga = Uloga.Administrator;
		}

		List<User> usersTemp = new ArrayList<User>();

		if (uloga == Uloga.Kupac) {
			return Response.status(200).entity(users.getUsers().values()).build();
		} else {
			for (User userTemp : users.getUsers().values()) {
				if (userTemp.getUloga() == uloga) {
					usersTemp.add(userTemp);
				}
			}
		}

		return Response.status(200).entity(usersTemp).build();
	}

	@GET
	@Path("/usersAds/{korisnickoIme}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsersAds(@PathParam("korisnickoIme") String korisnickoIme, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}
		List<String> oglasi = new ArrayList<String>();

		if (user.getUloga() == Uloga.Kupac) {
			oglasi = users.getUsers().get(korisnickoIme).getObjavljeniOglasi();
		} else if (user.getUloga() == Uloga.Prodavac) {
			oglasi = users.getUsers().get(user.getKorisnickoIme()).getObjavljeniOglasi();
		}

		return Response.status(200).entity(oglasi).build();
	}

	@POST
	@Path("/sendMessage/{korisnickoIme}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendMessage(Poruka poruka, @PathParam("korisnickoIme") String korisnickoIme,
			@Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}
		
		users.getUsers().get(korisnickoIme).getPoruke().add(poruka);
		
		users.save();

		return Response.status(200).entity("Poruka je uspesno poslata "+korisnickoIme).build();
	}
	
	@GET
	@Path("/user/{korisnickoIme}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("korisnickoIme") String korisnickoIme, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		return Response.status(200).entity(users.getUsers().get(korisnickoIme)).build();
	}
	
	@POST
	@Path("/read")
	public Response readMessage(String indexStr, @Context HttpServletRequest request) {

		int index = Integer.parseInt(indexStr);
		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}
		
		user.getPoruke().get(index).setProcitana(true);
		
		users.save();
		return Response.status(200).entity("Poruka je procitana").build();
	}
	
	@POST
	@Path("/delete")
	public Response deleteMessage(String indexStr, @Context HttpServletRequest request) {
		
		int index = Integer.parseInt(indexStr);
		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}
		
		user.getPoruke().get(index).setAktivan(false);
		
		users.save();
		return Response.status(200).entity("Poruka je obrisana").build();
	}
}
