package aadd.mongo.dao;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import aadd.beans.Actividad;
import aadd.beans.TipoActividad;

public class ActividadCodecDAO extends MongoCodecDAO<Actividad> {

	private static ActividadCodecDAO actividadCodecDAO;

	public static ActividadCodecDAO getActividadCodecDAO() {
		if (actividadCodecDAO == null) {
			actividadCodecDAO = new ActividadCodecDAO();
		}
		return actividadCodecDAO;
	}

	@Override
	public void createCollection() {
		collection = db.getCollection("actividad", Actividad.class).withCodecRegistry(defaultCodecRegistry);
	}

	public Actividad insertActividad(String idActividad, LocalDate fecha, LocalTime hora, Integer plazas, String nombre,
			String descripcion, TipoActividad tipoActividad) {

		Actividad act = new Actividad();
		act.setIdActividad(idActividad);
		act.setFecha(fecha);
		act.setHora(hora);
		act.setPlazas(plazas);
		act.setNombre(nombre);
		act.setDescripcion(descripcion);
		act.setTipoActividad(tipoActividad);

		collection.insertOne(act);
		return act;
	}

	public Actividad insertActividad(Actividad act) throws MongoWriteException {
		collection.insertOne(act);
		return act;
	}

	public ArrayList<Actividad> findActividades() {

		FindIterable<Actividad> actividades = collection.find();
		MongoCursor<Actividad> it = actividades.iterator();
		ArrayList<Actividad> resultados = new ArrayList<Actividad>();
		while (it.hasNext()) {
			Actividad act = it.next();
			resultados.add(act);
		}

		if (resultados.isEmpty()) {
			return null;
		}

		return resultados;
	}

	public void updatePlazas(Actividad act) {
		Bson filter = Filters.eq("_id", act.getIdActividad());
		Bson update = Updates.inc("plazas", -1);
		collection.updateOne(filter, update);
	}
	
	public Actividad findById(String clave) {
		Bson filter = Filters.eq("_id", clave);
		Actividad result = (Actividad) collection.find(filter);
		return result;
	}

	public List<Actividad> getActividadesDisponibles() {
		Bson filter = Filters.and(Filters.gt("fecha", LocalDate.now()), Filters.gt("plazas", 0));

		FindIterable<Actividad> actividades = collection.find(filter);
		MongoCursor<Actividad> it = actividades.iterator();
		ArrayList<Actividad> resultados = new ArrayList<Actividad>();
		while (it.hasNext()) {
			Actividad act = it.next();
			resultados.add(act);
		}

		if (resultados.isEmpty()) {
			return null;
		}

		return resultados;
	}

	public List<Actividad> findByNombre(String keyword) {
		Bson filter = Filters.regex("nombre", keyword);

		FindIterable<Actividad> actividades = collection.find(filter);
		MongoCursor<Actividad> it = actividades.iterator();
		ArrayList<Actividad> resultados = new ArrayList<Actividad>();
		while (it.hasNext()) {
			Actividad act = it.next();
			resultados.add(act);
		}

		if (resultados.isEmpty()) {
			return null;
		}

		return resultados;
	}

	public List<Actividad> findMesAnyo(int mes, int anyo) {
		Bson filter;
		if (mes < 12) {
			filter = Filters.and(Filters.gte("fecha", LocalDate.of(anyo, mes, 1)), Filters.lt("fecha", LocalDate.of(anyo, mes+1, 1)));
		}
		else {
			filter = Filters.and(Filters.gte("fecha", LocalDate.of(anyo, mes, 1)), Filters.lt("fecha", LocalDate.of(anyo+1, 1, 1)));
		}
		
		FindIterable<Actividad> actividades = collection.find(filter);
		MongoCursor<Actividad> it = actividades.iterator();
		ArrayList<Actividad> resultados = new ArrayList<Actividad>();
		while (it.hasNext()) {
			Actividad act = it.next();
			resultados.add(act);
		}

		if (resultados.isEmpty()) {
			return null;
		}

		return resultados;
	}

	public List<Actividad> findTipo(String tipo) {
		Bson filter = Filters.eq("tipoActividad", tipo);
		
		FindIterable<Actividad> actividades = collection.find(filter);
		MongoCursor<Actividad> it = actividades.iterator();
		ArrayList<Actividad> resultados = new ArrayList<Actividad>();
		while (it.hasNext()) {
			Actividad act = it.next();
			resultados.add(act);
		}

		if (resultados.isEmpty()) {
			return null;
		}

		return resultados;
	}
	
	

}