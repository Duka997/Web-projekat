package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Kategorija;
import dao.KategorijaDAO;
import dao.OglasDAO;
import dao.UserDAO;

@Path("/kategorija")
public class KategorijaService {

	public KategorijaService() {
		super();
	}
	
	@Context
	ServletContext context;
	
	@PostConstruct
	public void init() {
		String contextPath = context.getRealPath("/");
		if (context.getAttribute("kategorijaDAO") == null) {
			context.setAttribute("kategorijaDAO", new KategorijaDAO(contextPath));
		}
		
		if (context.getAttribute("oglasDAO") == null) {
			context.setAttribute("oglasDAO", new OglasDAO(contextPath));
		}
		
		if (context.getAttribute("userDAO") == null) {
			context.setAttribute("userDAO", new UserDAO(contextPath));
		}
	}
	
	@GET
	@Path("/allKategorije")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllKategorije(@Context HttpServletRequest request) {
		KategorijaDAO kategorije = (KategorijaDAO) context.getAttribute("kategorijaDAO");
		if (kategorije == null) {
			return Response.status(400).entity("Lista kategorija nije pronadjena").build();
		}
		
		return Response.status(200).entity(kategorije.getKategorije().values()).build();
	}
	
	@POST
	@Path("/dodajKategoriju")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dodajKategoriju(Kategorija kategorija, @Context HttpServletRequest request) {
		KategorijaDAO kategorije = (KategorijaDAO) context.getAttribute("kategorijaDAO");
		if (kategorije == null) {
			return Response.status(400).entity("Lista kategorija nije pronadjena").build();
		}
		
		if (kategorije.getKategorije().containsKey(kategorija.getNaziv())) {
			return Response.status(400).entity("Kategorija sa tim nazivom vec postoji").build();
		}
		
		kategorije.getKategorije().put(kategorija.getNaziv(), kategorija);
		
		kategorije.save();
		
		return Response.status(200).entity(kategorije.getKategorije().get(kategorija.getNaziv())).build();
	}
	
	@POST
	@Path("/izbrisiKategoriju")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response izbrisiKategoriju(Kategorija kategorija, @Context HttpServletRequest request) {
		
		KategorijaDAO kategorije = (KategorijaDAO) context.getAttribute("kategorijaDAO");
		if (kategorije == null) {
			return Response.status(400).entity("Lista kategorija nije pronadjena").build();
		}
		
		kategorije.getKategorije().get(kategorija.getNaziv()).setAktivan(false);
		
		kategorije.save();
		
		return Response.status(200).entity("Kategorija je izbrisana").build();
	}
	
	@POST
	@Path("/izmeniKategoriju")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response izmeniKategoriju(Kategorija kategorija, @Context HttpServletRequest request) {
		KategorijaDAO kategorije = (KategorijaDAO) context.getAttribute("kategorijaDAO");
		if (kategorije == null) {
			return Response.status(400).entity("Lista kategorija nije pronadjena").build();
		}
		
		Kategorija kategorijaTemp = kategorije.getKategorije().get(kategorija.getNaziv());
		
		//kategorijaTemp.setNaziv(kategorija.getNaziv());
		kategorijaTemp.setOpis(kategorija.getOpis());
		
		kategorije.save();
		
		return Response.status(200).entity("Kategorija je izmenjena").build();
	}
}
