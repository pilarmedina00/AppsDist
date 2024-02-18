package aadd.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mongodb.MongoWriteException;

import aadd.mongo.dao.ActividadCodecDAO;

@Named
@ViewScoped
public class ActividadRespaldo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String idActividad = "Id de la actividad";
	private String fecha = "01/01/2022";
	private String hora = null;
	private Integer plazas = 1;
	private String nombre = "Nombre de la actividad";
	private String descripcion = null;
	private TipoActividad tipoActividad;
	
	private Integer mes = 1;
	private Integer anyo = 2000;
	
	private String idReserva = "Id de la actividad";
	private String idFiltro = "Id a buscar";
	private TipoActividad tipo;
	
	@Inject
	FacesContext facesContext;
	
	public List<Actividad> getProgramadas() {
		return  ActividadCodecDAO.getActividadCodecDAO().findMesAnyo(mes.intValue(), anyo.intValue());
	}

	public void consultar() {
		
	}
	
	public List<Actividad> getFiltrada() {
		List<Actividad> filtrada = new ArrayList<Actividad>();
		List<Actividad> actividades = ActividadCodecDAO.getActividadCodecDAO().findActividades();
		for (Actividad act : actividades) {
			if (act.getIdActividad().equals(idFiltro)) {
				filtrada.add(act);
			}
		}
		if (filtrada.isEmpty()) {
			return null;
		}

		return filtrada;
	}
	
	public List<Actividad> getTipos() {
		List<Actividad> tipos = new ArrayList<Actividad>();
		List<Actividad> actividades = ActividadCodecDAO.getActividadCodecDAO().findActividades();
		for (Actividad act : actividades) {
			if (act.getTipoActividad().equals(tipo)) {
				tipos.add(act);
			}
		}
		if (tipos.isEmpty()) {
			return null;
		}

		return tipos;
	}
	
	public void buscar() {
		
	}
	
	public String reservar() {
		boolean encontrada = false;
		Actividad reserva = null;
		List<Actividad> actividades = ActividadCodecDAO.getActividadCodecDAO().findActividades();
		for (Actividad act: actividades) {
			if (act.getIdActividad().equals(idReserva)) {
				encontrada = true;
				reserva = act;
			}
		}
        if (encontrada == true) {
        	if (reserva.getPlazas() > 0) {
        		ActividadCodecDAO.getActividadCodecDAO().updatePlazas(reserva);
        		facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Actividad reservada: ","Has reservado una plaza en la actividad " + idReserva + "."));
        	}
        	else {
        		facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Reserva fallida: ","No quedan plazas en la actividad " + idReserva + ".")); 
        	}       
        }
        else {
        	facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Reserva fallida: ", "No existe una actividad con ese identificador."));        
        }
        
        return "reserva.xhtml";  
	}

	public List<Actividad> getActividades() {
		return ActividadCodecDAO.getActividadCodecDAO().findActividades();
	}
	
	public String registrar() {
		Actividad nueva = null;
		try {
			nueva = ActividadCodecDAO.getActividadCodecDAO().insertActividad(idActividad, LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy")), null, plazas, nombre, descripcion, tipoActividad);
		} catch (MongoWriteException e) {
			facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro fallido: ","La actividad " + idActividad + " no ha sido creada: ya existe una registrada con ese id."));
		}
		
        if (nueva != null) {
            facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Actividad registrada: ","La actividad " + idActividad + " ha sido creada"));        
        }
        
        return "registro.xhtml";  
	}

	public String getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(String idActividad) {
		this.idActividad = idActividad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Integer getPlazas() {
		return plazas;
	}

	public void setPlazas(Integer plazas) {
		this.plazas = plazas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoActividad getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(TipoActividad tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAnyo() {
		return anyo;
	}

	public void setAnyo(Integer anyo) {
		this.anyo = anyo;
	}

	public String getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(String idReserva) {
		this.idReserva = idReserva;
	}

	public String getIdFiltro() {
		return idFiltro;
	}

	public void setIdFiltro(String idFiltro) {
		this.idFiltro = idFiltro;
	}

	
	public void setTipo(TipoActividad tipo) {
		this.tipo = tipo;
	}

	public TipoActividad getTipo() {
		return tipo;
	}
	
	
				
}
