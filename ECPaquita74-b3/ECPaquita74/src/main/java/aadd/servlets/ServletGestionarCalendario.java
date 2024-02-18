package aadd.servlets;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoWriteException;

import aadd.beans.Actividad;
import aadd.beans.TipoActividad;
import aadd.mongo.dao.ActividadCodecDAO;

@WebServlet(name = "ServletGestionarCalendario", urlPatterns = { "/ServletGestionarCalendario", "/calendario" })
public class ServletGestionarCalendario extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ServletGestionarCalendario() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//ServletContext app = getServletConfig().getServletContext();

		// Establecemos el tipo MIME de la respuesta
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Intenta localizar la tabla de actividades
		ActividadCodecDAO actividades = ActividadCodecDAO.getActividadCodecDAO();
		actividades.createCollection();
		// HashMap<String, Actividad> actividadesHash = (HashMap<String, Actividad>)
		// app.getAttribute("actividades");

		if (request.getParameter("mes") != null && request.getParameter("anyo") != null) {
			int mes = Integer.parseInt(request.getParameter("mes"));
			int anyo = Integer.parseInt(request.getParameter("anyo"));

			List<Actividad> actividadesList = actividades.findActividades();

			if (actividadesList != null) {
				List<Actividad> actividadesEncontradas = new ArrayList<Actividad>();

				for (Actividad actividad : actividadesList) {
					if (actividad.getFecha().getMonthValue() == mes && actividad.getFecha().getYear() == anyo) {
						actividadesEncontradas.add(actividad);
					}
				}

				if (!actividadesEncontradas.isEmpty()) {
					out.println(
							"<html><head><h2>Actividades del mes " + mes + " del año " + anyo + "</h2></head><body>");

					for (Actividad act : actividadesEncontradas) {
						out.println("<b>Identificafor: </b>" + act.getIdActividad() + "<b><br>Nombre: </b>"
								+ act.getNombre() + "<b><br>Plazas: </b>" + act.getPlazas()
								+ "<b><br>Fecha: </b>" + act.getFecha().toString() + "<br><br><br>");
					}

				} else {
					// Obtiene la URL de gestionar calendario
					String referer = "/ECPaquita74/calendario";
					// Establece la cabecera de refresco
					response.setHeader("refresh", "5; URL=" + referer);

					out.println("<html><head><h2>No existen actividades en el mes " + mes + " del año " + anyo
							+ "</h2></head><body>");
					out.println("<h2>Prueba de nuevo en unos segundos.</h2>");
				}
				// Cierre de la página
				out.println("</body>");
				out.println("</html>");
				
			} else {
				// Obtiene la URL de gestionar calendario
				String referer = "/ECPaquita74/calendario";
				// Establece la cabecera de refresco
				response.setHeader("refresh", "5; URL=" + referer);

				out.println(
						"<html><head><h2>Aún no se han registrado actividades en la aplicación. </h2></head><body>");
				out.println("<h2>Prueba de nuevo en unos segundos.</h2>");
				// Cierre de la página
				out.println("</body>");
				out.println("</html>");
				
			}
		} else {
			// Obtenemos la ruta física al fichero del menú de opciones
			String pathFichero = getServletConfig().getServletContext().getRealPath("index.html");

			BufferedReader br = new BufferedReader(new FileReader(pathFichero));

			String linea;
			while ((linea = br.readLine()) != null) {
				out.println(linea);
			}
			br.close();

			doPost(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//ServletContext app = getServletConfig().getServletContext();

		// Establecemos el tipo MIME de la respuesta
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Intenta localizar la tabla de actividades
		ActividadCodecDAO actividades = ActividadCodecDAO.getActividadCodecDAO();
		actividades.createCollection();
		
		List<Actividad> actividadesList = actividades.findActividades();
		//HashMap<Actividad> actividadesHash = (HashMap<String, Actividad>) app.getAttribute("actividades");
		
		if (request.getParameter("mes") != null && request.getParameter("anyo") != null) {
			int mes = Integer.parseInt(request.getParameter("mes"));
			int anyo = Integer.parseInt(request.getParameter("anyo"));

			if (actividadesList != null) {
				List<Actividad> actividadesEncontradas = new ArrayList<Actividad>();

				for (Actividad actividad : actividadesList) {
					if (actividad.getFecha().getMonthValue() == mes && actividad.getFecha().getYear() == anyo) {
						actividadesEncontradas.add(actividad);
					}
				}

				if (!actividadesEncontradas.isEmpty()) {
					out.println(
							"<html><head><h2>Actividades del mes " + mes + " del año " + anyo + "</h2></head><body>");

					for (Actividad act : actividadesEncontradas) {
						out.println("<b>Identificafor: </b>" + act.getIdActividad() + "<b><br>Nombre: </b>"
								+ act.getNombre() + "<b><br>Plazas: </b>" + act.getPlazas()
								+ "<b><br>Fecha: </b>" + act.getFecha().toString() + "<br><br><br>");
					}

				} else {
					// Obtiene la URL de gestionar calendario
					String referer = "/ECPaquita74/calendario";
					// Establece la cabecera de refresco
					response.setHeader("refresh", "5; URL=" + referer);

					out.println("<html><head><h2>No existen actividades en el mes " + mes + " del año " + anyo
							+ "</h2></head><body>");
					out.println("<h2>Prueba de nuevo en unos segundos.</h2>");
				}
				// Cierre de la página
				out.println("</body>");
				out.println("</html>");
				
			} else {
				// Obtiene la URL de gestionar calendario
				String referer = "/ECPaquita74/calendario";
				// Establece la cabecera de refresco
				response.setHeader("refresh", "5; URL=" + referer);

				out.println(
						"<html><head><h2>Aún no se han registrado actividades en la aplicación. </h2></head><body>");
				out.println("<h2>Prueba de nuevo en unos segundos.</h2>");
				// Cierre de la página
				out.println("</body>");
				out.println("</html>");
				
			}
		}

		// Si no existe un mapa con las actividades ya, lo crea

		// Instanciamos el bean
		Actividad actividad = new Actividad();

		// Obtenemos los parámetros de la llamada
		actividad.setIdActividad(request.getParameter("idActividad"));

		// Si no hay una actividad con ese código la guardamos con sus parámetros

			if (request.getParameter("fecha") != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				actividad.setFecha(LocalDate.parse(request.getParameter("fecha"), formatter));
			}

			if (request.getParameter("hora") != null && !request.getParameter("hora").equals("")) {
				DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:mm");
				actividad.setHora(LocalTime.parse(request.getParameter("hora"), formatterTime));
			}

			if (request.getParameter("plazas") != null) {
				actividad.setPlazas(Integer.parseInt(request.getParameter("plazas")));
			}
			actividad.setNombre(request.getParameter("nombre"));
			actividad.setDescripcion(request.getParameter("descripcion"));

			switch (request.getParameter("tipoActividad")) {
			case "artesania":
				actividad.setTipoActividad(TipoActividad.ARTESANIA);
				break;
			case "cine":
				actividad.setTipoActividad(TipoActividad.CINE);
				break;
			case "fotografia":
				actividad.setTipoActividad(TipoActividad.FOTOGRAFIA);
				break;
			case "mindandbody":
				actividad.setTipoActividad(TipoActividad.MINDANDBODY);
				break;
			default:
				break;
			}
			try {			
				actividades.insertActividad(actividad);
				out.println("<html><head><h1>Registro de la actividad completado</h1></head><body>");
				out.println("<h2>La actividad " + actividad.getIdActividad() + " se ha registrado correctamente.</h2>");
			
			} catch (MongoWriteException e) {
				// Si ya hay una actividad con ese código, que ha de ser único
				// Obtiene la URL precedente
				String referer = request.getHeader("referer");
				// Establece la cabecera de refresco
				response.setHeader("refresh", "5; URL=" + referer);

				out.println("<html><head><h1>Registro de una actividad fallido</h1></head><body>");
				out.println("<h2>No se ha registrado la actividad. Ya existe una con el código "
								+ actividad.getIdActividad() + " en la aplicación.</h2>");
				out.println("<h2>Prueba de nuevo en unos segundos.</h2>");
			}

			

		
		/*else {
			// Obtiene la URL precedente
			String referer = request.getHeader("referer");
			// Establece la cabecera de refresco
			response.setHeader("refresh", "5; URL=" + referer);

			out.println("<html><head><h1>Registro de una actividad fallido</h1></head><body>");
			out.println("<h2>No se ha registrado la actividad. Ya existe una con el código "
							+ actividad.getIdActividad() + " en la aplicación.</h2>");
			out.println("<h2>Prueba de nuevo en unos segundos.</h2>");

		}*/
		// Cierre de la página
		out.println("</body>");
		out.println("</html>");
	}

}
