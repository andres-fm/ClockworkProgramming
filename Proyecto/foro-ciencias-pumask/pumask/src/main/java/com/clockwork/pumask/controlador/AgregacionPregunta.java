/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clockwork.pumask.controlador;

import com.clockwork.pumask.modelo.EntityProvider;
import com.clockwork.pumask.modelo.Usuario;
import com.clockwork.pumask.modelo.Pregunta;
import com.clockwork.pumask.modelo.Respuesta;
import com.clockwork.pumask.modelo.PreguntaJpaController;
import java.util.Locale;
import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Date;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *
 * @author dimitri
 */
@ManagedBean
@SessionScoped
public class AgregacionPregunta {

    private EntityManagerFactory emf;
    private PreguntaJpaController jpaController;
    //private Usuario usuario;
	private Pregunta pregunta;
	private Integer nuevaDir;

	/**
	 * Constructor único.
	 */
    public AgregacionPregunta() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        emf = EntityProvider.provider();
        jpaController = new PreguntaJpaController(emf);
        pregunta = new Pregunta();
        pregunta.setCarrera(obtenerPaginaActual());
        pregunta.setCategoria("titulacion");
        nuevaDir = new Integer(1);
    }

	/**
	 * Nos dice si la pregunta es válida.
	 * @return <code>true</code> si la pregunta es válida, <code>false</code> si no.
	 */
	public boolean validaPregunta() {
		return !(pregunta.getContenido() == null);
	}

	/**
	 * Agrega una pregunta a la base de datos.
	 */
	public void agregaPregunta() {
		pregunta.setFechaCreacion(new Date());
		String carrera = obtenerPaginaActual();
		pregunta.setCarrera(carrera);
		pregunta.setIdPregunta(100);
		FacesContext context = getCurrentInstance();
        pregunta.setCorreoUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuario"));
        try {
			jpaController.create(pregunta);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pregunta agregada exitosamente"));
		}catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocurrio un error, intentar más tarde"));
		}
	}

	/**
	 * Obtiene el URL de la página actual.
	 * @return El URL de la página actual.
	 */
	private static String obtenerPaginaActual() {
		String context = getCurrentInstance().getViewRoot().getViewId();
		String carrera = "";
		if (context.equals("/carreras/actuaria.xhtml"))
			carrera = "actuaria";
		else if(context.equals("/carreras/biologia.xhtml"))
			carrera = "biología";
		else if(context.equals("/carreras/ciencias_ambientales.jsp"))
			carrera = "ciencias ambientales";
		else if(context.equals("/carreras/ciencias_de_la_computacion.xhtml"))
			carrera = "ciencias de la computación";
		else if(context.equals("/carreras/ciencias_de_la_tierra.xhtml"))
			carrera = "ciencias de la tierra";
		else if(context.equals("/carreras/fisica.xhtml"))
			carrera = "fisica";
		else if(context.equals("/carreras/fisica_biomedica.xhtml"))
			carrera = "física biomédica";
		else if(context.equals("/carreras/general.xhtml"))
			carrera = "general";
		else if(context.equals("/carreras/manejo_sustentable_de_zonas_costeras.xhtml"))
			carrera = "manejo sustentable de zonas costeras";
		else if(context.equals("/carreras/matematicas.xhtml"))
			carrera = "matematicas";
		else if(context.equals("/carreras/matematicas_aplicadas.xhtml"))
			carrera = "matematicas aplicadas";
		else if(context.equals("/carreras/neurociencias.xhtml"))
			carrera = "neurociencias";
		return carrera;
	}


	/**
	 * Regresa las pregunts sobre titulación.
	 * @return Una lista con las preguntas sobre titulación.
	 */
	public List<Pregunta> obtenerPreguntasTitulacion() {
		return obtenerPreguntas("titulacion");
	}

	/**
	 * Regresa las preguntas sobre servicio social.
	 * @return Una lista con las preguntas sobre servicio social.
	 */
	public List<Pregunta> obtenerPreguntasServicioSocial() {
		return obtenerPreguntas("servicio social");
	}

	/**
	 * Regresa las preguntas según la categoría dada.
	 * @param categoria La categoría sobre la que se obtendrán las preguntas.
	 * @return Una lista con las preguntas sobre la categoría dada.
	 */
	public List<Pregunta> obtenerPreguntas(String categoria) { 	
		return jpaController.obtenPreguntasCC(obtenerPaginaActual(), categoria);
	}

	/**
	 * Regresa la pregunta asociada al controlador
	 * @return Pregunta La pregunta asociada al controlador
	 **/
	public Pregunta getPregunta() {
		return pregunta;
	}
	
	public Collection<Respuesta> obtenRespuestas() {
		FacesContext context = getCurrentInstance();
		Pregunta preg = (Pregunta) (context.getExternalContext().getSessionMap().get("pregunta"));
		return jpaController.findPregunta(preg.getIdPregunta()).getRespuestaCollection();
	
	} 
	
	public void setNuevaDir(Integer preg) {
		this.nuevaDir = preg;
	}
	
	public String verRespuestas() {
	    Pregunta l = jpaController.findPregunta(nuevaDir);
	    //System.out.println(l.getContenido());
        if (validaPregunta()) {
            FacesContext context = getCurrentInstance();
            context.getExternalContext().getSessionMap().put("pregunta", l);
            return "/PreguntaIH.xhtml?faces-redirect=true";
        }
        return "/index?faces-redirect=true";
    }

}
