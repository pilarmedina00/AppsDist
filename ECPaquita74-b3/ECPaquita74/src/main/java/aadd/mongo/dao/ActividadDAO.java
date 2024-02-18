package aadd.mongo.dao;

import java.time.LocalDate;


import java.time.LocalTime;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

import aadd.beans.Actividad;

public class ActividadDAO extends MongoDAO {

	private static ActividadDAO actividadDAO;

	public static ActividadDAO getActividadDAO() {
		if (actividadDAO == null) {
			actividadDAO = new ActividadDAO();
		}
		return actividadDAO;
	}

	@Override
	public void createCollection() {
		collection = db.getCollection("actividad");

	}

	public Document insertDocumentActividad(String idActividad, LocalDate fecha, LocalTime hora, Integer plazas,
			String nombre, String descripcion, String tipoActividad) {
		Document doc = new Document();
		doc.append("_id", idActividad);
		doc.append("fecha", fecha);
		doc.append("hora", hora);
		doc.append("plazas", plazas);
		doc.append("nombre", nombre);
		doc.append("descripcion", descripcion);
		doc.append("tipoActividad", tipoActividad);

		collection.insertOne(doc);
		return doc;
	}
	
	public Document insertDocumentActividad(Actividad act) {
		Document doc = new Document();
		doc.append("_id", act.getIdActividad());
		doc.append("fecha", act.getFecha());
		doc.append("hora", act.getHora());
		doc.append("plazas", act.getPlazas());
		doc.append("nombre", act.getNombre());
		doc.append("descripcion", act.getDescripcion());
		doc.append("tipoActividad", act.getTipoActividad().toString());

		collection.insertOne(doc);
		return doc;
	}

	public void getTotalPlazasMesTipo(){
		LocalDate fechaInit = LocalDate.of(2022, 1, 1);
		LocalDate fechaFin = LocalDate.of(2022, 12, 31);
	
		collection.aggregate(Arrays.asList(Aggregates.match(Filters.and(Filters.gte("fecha", fechaInit), Filters.lte("fecha", fechaFin))),
                							Aggregates.group(new Document().append("Mes", new Document("$month", "$fecha")).append("tipoActividad", "$tipoActividad"), 
                												Accumulators.sum("NumPlazas", "$plazas"), Accumulators.push("Nombres", "$nombre")))).forEach(t->System.out.println(t.toJson()));					
	}

}
