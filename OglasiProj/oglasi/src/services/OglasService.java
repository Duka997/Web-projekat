package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Oglas;
import beans.Poruka;
import beans.Recenzija;
import beans.Uloga;
import beans.User;
import dao.KategorijaDAO;
import dao.OglasDAO;
import dao.UserDAO;

@Path("/oglas")
public class OglasService {

	public OglasService() {
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
	@Path("/allOglasi")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOglasi(@Context HttpServletRequest request) {

		OglasDAO oglasi = (OglasDAO) context.getAttribute("oglasDAO");
		if (oglasi == null) {
			return Response.status(400).entity("Lista oglasa nije pronadjena").build();
		}

		return Response.status(200).entity(oglasi.getOglasi().values()).build();
	}

	@POST
	@Path("/oglasDostavljen")
	public Response oglasDostavljen(String nazivOglasa, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		if (!user.getPoruceniOglasi().remove(nazivOglasa)) {
			return Response.status(400).entity("Oglas prethodno mora piti porucen!").build();
		}

		user.getDostavljeniOglasi().add(nazivOglasa);

		User seller = users.findSeller(nazivOglasa);

		if (!seller.getuRealizacijiOglasi().remove(user.getKorisnickoIme() + "/" + nazivOglasa)) {
			return Response.status(400).entity("Oglas prethodno mora piti porucen!").build();
		}

		seller.getIsporuceniOglasi().add(user.getKorisnickoIme() + "/" + nazivOglasa);
		seller.sendGenericMessage(
				"Korisniku " + user.getKorisnickoIme() + " je upravo dostavljen vas oglas: " + nazivOglasa);

		users.save();

		return Response.status(200).entity("Proizvod je dostavljen").build();
	}

	@POST
	@Path("/oglasPorucen")
	public Response oglasPorucen(String nazivOglasa, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		if (user.getOmiljeniOglasi().contains(nazivOglasa)) {
			user.getOmiljeniOglasi().remove(nazivOglasa);
		}

		user.getPoruceniOglasi().add(nazivOglasa);

		User seller = users.findSeller(nazivOglasa);
		seller.getuRealizacijiOglasi().add(user.getKorisnickoIme() + "/" + nazivOglasa);

		users.save();

		return Response.status(200).entity("Proizvod je porucen").build();
	}

	@POST
	@Path("/dodajOmiljeniOglas")
	public Response dodajOmiljeniOglas(String nazivOglasa, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		user.getOmiljeniOglasi().add(nazivOglasa);

		users.save();

		return Response.status(200).entity("Proizvod je dodat u omiljene").build();
	}

	@POST
	@Path("/dodajRecenziju")
	@Consumes(value = { MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
	public Response dodajRecenziju(@FormDataParam("fajl") InputStream uploadedInputStream,
			@FormDataParam("recenzija") String recenzijaStr, @FormDataParam("slika") String imeSlike,
			@Context HttpServletRequest request) {

		ObjectMapper mapper = new ObjectMapper();
		Recenzija recenzija;

		try {
			recenzija = mapper.readValue(recenzijaStr, Recenzija.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity("Neuspesno preuzeta recenzija").build();
		}

		OglasDAO oglasi = (OglasDAO) context.getAttribute("oglasDAO");
		if (oglasi == null) {
			return Response.status(400).entity("Lista oglasa nije pronadjena").build();
		}

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}


		writeToFile(uploadedInputStream, context.getRealPath("/") + "/Images/" + imeSlike);
		recenzija.setSlika(context.getContextPath() + "/Images/" + imeSlike);

		oglasi.getOglasi().get(recenzija.getOglas()).getRecenzije().add(recenzija);

		users.findSeller(recenzija.getOglas()).sendGenericMessage("Korisnik " + recenzija.getRecenzent()
				+ " je upravo ostavio recenziju na vas oglas: " + recenzija.getOglas());

		oglasi.save();
		users.save();

		return Response.status(200).entity(context.getContextPath() + "/Images/" + imeSlike).build();
	}

	@POST
	@Path("/izmeniRecenziju")
	@Consumes(value = { MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
	public Response izmeniRecenziju(@FormDataParam("fajl") InputStream uploadedInputStream,
			@FormDataParam("recenzija") String recenzijaStr, @FormDataParam("slika") String imeSlike,
			@Context HttpServletRequest request) {

		ObjectMapper mapper = new ObjectMapper();
		Recenzija recenzija;

		try {
			recenzija = mapper.readValue(recenzijaStr, Recenzija.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity("Neuspesno preuzeta recenzija").build();
		}

		OglasDAO oglasi = (OglasDAO) context.getAttribute("oglasDAO");
		if (oglasi == null) {
			return Response.status(400).entity("Lista oglasa nije pronadjena").build();
		}

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		writeToFile(uploadedInputStream, context.getRealPath("/") + "/Images/" + imeSlike);
		recenzija.setSlika(context.getContextPath() + "/Images/" + imeSlike);

		List<Recenzija> recenzije = oglasi.getOglasi().get(recenzija.getOglas()).getRecenzije();
		for (int i = 0; i < recenzije.size(); i++) {
			if (recenzije.get(i).getNaslov().equals(recenzija.getNaslov())) {
				oglasi.getOglasi().get(recenzija.getOglas()).getRecenzije().get(i).setSadrzaj(recenzija.getSadrzaj());
				oglasi.getOglasi().get(recenzija.getOglas()).getRecenzije().get(i).setSlika(recenzija.getSlika());
				oglasi.getOglasi().get(recenzija.getOglas()).getRecenzije().get(i)
						.setIspostovanDogovor(recenzija.isIspostovanDogovor());
				oglasi.getOglasi().get(recenzija.getOglas()).getRecenzije().get(i).setTacan(recenzija.isTacan());
				break;
			}
		}

		users.findSeller(recenzija.getOglas()).sendGenericMessage("Korisnik " + recenzija.getRecenzent()
				+ " je upravo ostavio recenziju na vas oglas: " + recenzija.getOglas());

		oglasi.save();
		users.save();

		return Response.status(200).entity("recenzija je dodata").build();
	}

	@POST
	@Path("/izbrisiRecenziju")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response izbrisiRecenziju(Recenzija recenzija, @Context HttpServletRequest request) {

		OglasDAO oglasi = (OglasDAO) context.getAttribute("oglasDAO");
		if (oglasi == null) {
			return Response.status(400).entity("Lista oglasa nije pronadjena").build();
		}

		List<Recenzija> recenzije = oglasi.getOglasi().get(recenzija.getOglas()).getRecenzije();
		for (int i = 0; i < recenzije.size(); i++) {
			if (recenzije.get(i).getNaslov().equals(recenzija.getNaslov())) {
				oglasi.getOglasi().get(recenzija.getOglas()).getRecenzije().get(i).setAktivan(false);
				break;
			}
		}

		oglasi.save();

		return Response.status(200).entity("recenzija je izbbrisana").build();
	}

	@POST
	@Path("/likeOglas")
	public Response likeOglas(String nazivOglasa, @Context HttpServletRequest request) {

		OglasDAO oglasi = (OglasDAO) context.getAttribute("oglasDAO");
		if (oglasi == null) {
			return Response.status(400).entity("Lista oglasa nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		oglasi.getOglasi().get(nazivOglasa).getLike().add(user.getKorisnickoIme());

		oglasi.save();

		return Response.status(200).entity("oglas je lajkovan").build();
	}

	@POST
	@Path("/dislikeOglas")
	public Response dislikeOglas(String nazivOglasa, @Context HttpServletRequest request) {

		OglasDAO oglasi = (OglasDAO) context.getAttribute("oglasDAO");
		if (oglasi == null) {
			return Response.status(400).entity("Lista oglasa nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		oglasi.getOglasi().get(nazivOglasa).getDislike().add(user.getKorisnickoIme());

		oglasi.save();

		return Response.status(200).entity("oglas je dislajkovan").build();
	}

	@POST
	@Path("/addOglas/{nazivKategorije}")
	@Consumes(value = { MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addOglas(@FormDataParam("fajl") InputStream uploadedInputStream,
			@FormDataParam("oglas") String oglasStr, @FormDataParam("slika") String imeSlike,
			@PathParam("nazivKategorije") String nazivKategorije, @Context HttpServletRequest request) {

		ObjectMapper mapper = new ObjectMapper();
		Oglas oglas;

		try {
			oglas = mapper.readValue(oglasStr, Oglas.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity("Neuspesno preuzeta recenzija").build();
		}

		OglasDAO oglasi = (OglasDAO) context.getAttribute("oglasDAO");
		if (oglasi == null) {
			return Response.status(400).entity("Lista oglasa nije pronadjena").build();
		}

		KategorijaDAO kategorije = (KategorijaDAO) context.getAttribute("kategorijaDAO");
		if (kategorije == null) {
			return Response.status(400).entity("Lista kategorija nije pronadjena").build();
		}

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		if (oglasi.getOglasi().containsKey(oglas.getNaziv())) {
			if (oglasi.getOglasi().get(oglas.getNaziv()).isAktivan()) {
				return Response.status(400).entity("Oglas sa tim nazivom vec postoji").build();
			}
		}

		writeToFile(uploadedInputStream, context.getRealPath("/") + "/Images/" + imeSlike);
		oglas.setSlika(context.getContextPath() + "/Images/" + imeSlike);

		oglasi.getOglasi().put(oglas.getNaziv(), oglas);
		kategorije.getKategorije().get(nazivKategorije).getOglasi().add(oglas.getNaziv());
		user.getObjavljeniOglasi().add(oglas.getNaziv());

		oglasi.save();
		kategorije.save();
		users.save();

		return Response.status(200).entity(oglas).build();
	}

	@POST
	@Path("/editOglas")
	@Consumes(value = { MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
	public Response editOglas(@FormDataParam("fajl") InputStream uploadedInputStream,
			@FormDataParam("oglas") String oglasStr, @FormDataParam("slika") String imeSlike,
			@Context HttpServletRequest request) {

		ObjectMapper mapper = new ObjectMapper();
		Oglas oglas;

		try {
			oglas = mapper.readValue(oglasStr, Oglas.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity("Neuspesno preuzeta recenzija").build();
		}

		OglasDAO oglasi = (OglasDAO) context.getAttribute("oglasDAO");
		if (oglasi == null) {
			return Response.status(400).entity("Lista oglasa nije pronadjena").build();
		}

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		writeToFile(uploadedInputStream, context.getRealPath("/") + "/Images/" + imeSlike);
		oglas.setSlika(context.getContextPath() + "/Images/" + imeSlike);

		Oglas oglasTemp = oglasi.getOglasi().get(oglas.getNaziv());

		oglasTemp.setOpis(oglas.getOpis());
		oglasTemp.setCena(oglas.getCena());
		oglasTemp.setGrad(oglas.getGrad());
		oglasTemp.setSlika(oglas.getSlika());

		if (user.getUloga() == Uloga.Administrator) {
			users.findSeller(oglas.getNaziv())
					.sendGenericMessage("Administrator je upravo izmenio vas oglas: " + oglas.getNaziv());
		}

		oglasi.save();
		users.save();

		return Response.status(200).entity("Oglas je uspesno promenjen").build();
	}

	@POST
	@Path("/deleteOglas")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteOglas(Oglas oglas, @Context HttpServletRequest request) {

		OglasDAO oglasi = (OglasDAO) context.getAttribute("oglasDAO");
		if (oglasi == null) {
			return Response.status(400).entity("Lista oglasa nije pronadjena").build();
		}

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		for (String poruceniOglasi : user.getuRealizacijiOglasi()) {
			String nazivoglasa = poruceniOglasi.split("/")[1];
			if (nazivoglasa.equals(oglas.getNaziv())) {
				return Response.status(400).entity("Oglas je nemoguce obbrisati jer je porucen").build();
			}
		}

		for (String dostavljeniOglasi : user.getIsporuceniOglasi()) {
			String nazivoglasa = dostavljeniOglasi.split("/")[1];
			if (nazivoglasa.equals(oglas.getNaziv())) {
				return Response.status(400).entity("Oglas je nemoguce obrisati jer je dostavljen").build();
			}
		}

		oglasi.getOglasi().get(oglas.getNaziv()).setAktivan(false);

		if (user.getUloga() == Uloga.Prodavac) {
			for (User userTemp : users.getUsers().values()) {
				if (userTemp.getUloga() == Uloga.Administrator) {
					userTemp.sendGenericMessage(
							"Korisnik " + user.getKorisnickoIme() + " je izbrisao oglas: " + oglas.getNaziv());
				}
			}
		} else if (user.getUloga() == Uloga.Administrator) {
			users.findSeller(oglas.getNaziv())
					.sendGenericMessage("Administrator je upravo izbrisao vas oglas: " + oglas.getNaziv());
		}

		oglasi.save();
		users.save();

		return Response.status(200).entity("Oglas je uspesno promenjen").build();
	}

	@POST
	@Path("/getSeller")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSeller(String nazivOglasa, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		for (User user : users.getUsers().values()) {
			if (user.getUloga() == Uloga.Prodavac) {
				for (String oglas : user.getObjavljeniOglasi()) {
					if (oglas.equals(nazivOglasa)) {
						return Response.status(200).entity(user).build();
					}
				}
			}
		}

		return Response.status(400).entity("Ne postoji korisnik koji je postavio oglas sa ovim nazivom").build();
	}

	@POST
	@Path("/reportSeller")
	public Response reportSeller(String korisnickoImeUseraIOglasa, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		String korisnickoImeUsera = korisnickoImeUseraIOglasa.split("/")[0];
		String nazivOglasa = korisnickoImeUseraIOglasa.split("/")[1];

		users.getUsers().get(korisnickoImeUsera).getBrojPrijava().add(user.getKorisnickoIme() + "/" + nazivOglasa);

		users.getUsers().get(korisnickoImeUsera).sendGenericMessage("Upravo ste prijavljeni! Trenutno imate "
				+ users.getUsers().get(korisnickoImeUsera).getBrojPrijava().size()
				+ " prijava!");
		;

		users.save();

		return Response.status(200).entity("Prodavac je prijavljen").build();
	}

	@POST
	@Path("/likeSeller")
	public Response likeSeller(String korisnickoImeUsera, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		users.getUsers().get(korisnickoImeUsera).getLike().add(user.getKorisnickoIme());

		users.save();

		return Response.status(200).entity("Prodavac je lajkovan").build();
	}

	@POST
	@Path("/dislikeSeller")
	public Response dislikeSeller(String korisnickoImeUsera, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return Response.status(400).entity("Korisnik nije ulogovan").build();
		}

		users.getUsers().get(korisnickoImeUsera).getDislike().add(user.getKorisnickoIme());

		users.save();

		return Response.status(200).entity("Prodavac je lajkovan").build();
	}

	@POST
	@Path("/dodajRecenzijuProdavcu/{korisnickoImeProdavca}")
	@Consumes(value = { MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
	public Response dodajRecenzijuProdavcu(@FormDataParam("fajl") InputStream uploadedInputStream,
			@FormDataParam("recenzija") String recenzijaStr, @FormDataParam("slika") String imeSlike,
			@PathParam("korisnickoImeProdavca") String korisnickoImeProdavca, @Context HttpServletRequest request) {

		ObjectMapper mapper = new ObjectMapper();
		Recenzija recenzija;

		try {
			recenzija = mapper.readValue(recenzijaStr, Recenzija.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity("Neuspesno preuzeta recenzija").build();
		}

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		writeToFile(uploadedInputStream, context.getRealPath("/") + "/Images/" + imeSlike);
		recenzija.setSlika(context.getContextPath() + "/Images/" + imeSlike);

		users.getUsers().get(korisnickoImeProdavca).getRecenzije().add(recenzija);

		users.save();

		return Response.status(200).entity(context.getContextPath() + "/Images/" + imeSlike).build();
	}

	@POST
	@Path("/izmeniRecenzijuProdavcu/{korisnickoImeProdavca}")
	@Consumes(value = { MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
	public Response izmeniRecenzijuProdavcu(@FormDataParam("fajl") InputStream uploadedInputStream,
			@FormDataParam("recenzija") String recenzijaStr, @FormDataParam("slika") String imeSlike,
			@PathParam("korisnickoImeProdavca") String korisnickoImeProdavca, @Context HttpServletRequest request) {

		ObjectMapper mapper = new ObjectMapper();
		Recenzija recenzija;

		try {
			recenzija = mapper.readValue(recenzijaStr, Recenzija.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity("Neuspesno preuzeta recenzija").build();
		}

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		writeToFile(uploadedInputStream, context.getRealPath("/") + "/Images/" + imeSlike);
		recenzija.setSlika(context.getContextPath() + "/Images/" + imeSlike);

		List<Recenzija> recenzije = users.getUsers().get(korisnickoImeProdavca).getRecenzije();
		for (int i = 0; i < recenzije.size(); i++) {
			if (recenzije.get(i).getNaslov().equals(recenzija.getNaslov())) {
				users.getUsers().get(korisnickoImeProdavca).getRecenzije().get(i).setSadrzaj(recenzija.getSadrzaj());
				users.getUsers().get(korisnickoImeProdavca).getRecenzije().get(i).setSlika(recenzija.getSlika());
				users.getUsers().get(korisnickoImeProdavca).getRecenzije().get(i)
						.setIspostovanDogovor(recenzija.isIspostovanDogovor());
				users.getUsers().get(korisnickoImeProdavca).getRecenzije().get(i).setTacan(recenzija.isTacan());
				break;
			}
		}

		users.save();

		return Response.status(200).entity("recenzija je izmenjena").build();
	}

	@POST
	@Path("/izbrisiRecenzijuProdavcu/{korisnickoImeProdavca}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response izbrisiRecenzijuProdavcu(Recenzija recenzija,
			@PathParam("korisnickoImeProdavca") String korisnickoImeProdavca, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		if (users == null) {
			return Response.status(400).entity("Lista korisnika nije pronadjena").build();
		}

		List<Recenzija> recenzije = users.getUsers().get(korisnickoImeProdavca).getRecenzije();
		for (int i = 0; i < recenzije.size(); i++) {
			if (recenzije.get(i).getNaslov().equals(recenzija.getNaslov())) {
				users.getUsers().get(korisnickoImeProdavca).getRecenzije().get(i).setAktivan(false);
				break;
			}
		}

		users.save();

		return Response.status(200).entity("recenzija je izbrisana").build();
	}

	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
