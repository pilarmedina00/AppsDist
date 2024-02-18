package test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import aadd.beans.Actividad;
import aadd.beans.TipoActividad;
import aadd.mongo.dao.ActividadCodecDAO;
import aadd.mongo.dao.ActividadDAO;

public class TestConsultasActividad {

	@org.junit.jupiter.api.Test
	void testIntroducirActividades() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		// Esta actividad no debe salirnos en ningún test, puesto que es del año 2021
		ActividadCodecDAO.getActividadCodecDAO().insertActividad("25Nov2021", LocalDate.parse("25/11/2021", formatter),
				null, 5, "Sesión cine", "Cinefilos", TipoActividad.CINE);
		
		//Insertamos actividades que ya han ocurrido y que tienen plazas
		ActividadCodecDAO.getActividadCodecDAO().insertActividad("12Ene2022", LocalDate.parse("12/01/2022", formatter),
				null, 15, "Pilates", "Clase de pilates", TipoActividad.FOTOGRAFIA);
		ActividadCodecDAO.getActividadCodecDAO().insertActividad("5Nov2022", LocalDate.parse("05/11/2022", formatter),
				null, 2, "Fotografía de los 90.1", "Exposición de fotografía de los 90", TipoActividad.FOTOGRAFIA);
		ActividadCodecDAO.getActividadCodecDAO().insertActividad("6Nov2022", LocalDate.parse("06/11/2022", formatter),
				null, 2, "Fotografía de los 90.2", "Exposición de fotografía de los 90", TipoActividad.MINDANDBODY);
		
		// Insertamos 3 actividades que estan disponibles
		ActividadCodecDAO.getActividadCodecDAO().insertActividad("25Nov2022", LocalDate.parse("25/11/2022", formatter),
				null, 5, "Yoga", "Clase de yoga especializada en personas mayores", TipoActividad.MINDANDBODY);
		ActividadCodecDAO.getActividadCodecDAO().insertActividad("11Dec2022", LocalDate.parse("11/12/2022", formatter),
				null, 4, "Mantenimiento.1", "Clase de mantenimiento físico", TipoActividad.MINDANDBODY);
		ActividadCodecDAO.getActividadCodecDAO().insertActividad("12Dec2022", LocalDate.parse("12/12/2022", formatter),
				null, 15, "Pilates", "Clase de pilates", TipoActividad.MINDANDBODY);

		// Insertamos una actividad que no ha ocurrido todavía y que tiene no plazas
		ActividadCodecDAO.getActividadCodecDAO().insertActividad("13Dec2022", LocalDate.parse("13/12/2022", formatter),
				null, 0, "Mantenimiento.2", "Clase de mantenimiento físico", TipoActividad.MINDANDBODY);
		
	}

	@org.junit.jupiter.api.Test
	void testActividadesDisponibles() {

		//Deben imprimirse los ids y nombres de las 3 actividades que estan disponibles 
		assertNotNull(ActividadCodecDAO.getActividadCodecDAO().getActividadesDisponibles());

		for (Actividad act : ActividadCodecDAO.getActividadCodecDAO().getActividadesDisponibles()) {
			System.out.println(act.getIdActividad() + ", " + act.getNombre());
		}

	}

	@org.junit.jupiter.api.Test
	void testAggregatePlazasMesTipo() {

		// Si vaciamos la colección entera y ejecutamos la clase test de cero:
		// Deben aparecer 2 actividades en el mes de noviembre de tipo mindandbody(con
		// un total de 7 plazas)
		// Deben aparecer 3 actividades en el mes de dicimebre de tipo mindandbody(con
		// total de 19 plazas)
		// Además de una actividad de tipo fotografía para los meses de enero y
		// noviembre con 2 y 15 plazas respectivamente
		
		ActividadDAO.getActividadDAO().getTotalPlazasMesTipo();

	}
}
