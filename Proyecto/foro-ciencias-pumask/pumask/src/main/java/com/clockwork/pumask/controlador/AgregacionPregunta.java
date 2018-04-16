/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clockwork.pumask.controlador;

import com.clockwork.pumask.modelo.EntityProvider;
import com.clockwork.pumask.modelo.Usuario;
import com.clockwork.pumask.modelo.Pregunta;
import com.clockwork.pumask.modelo.PreguntaJpaController;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Date;

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

    public AgregacionPregunta() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        emf = EntityProvider.provider();
        jpaController = new PreguntaJpaController(emf);
        pregunta = new Pregunta();
        pregunta.setCategoria("oscar");
        pregunta.setCarrera("actuaria");
    }

	public boolean validaPregunta() {
		return !(pregunta.getContenido() == null);
	}

	public void agregaPregunta() {
		System.out.println("Hola inicial");
		pregunta.setFechaCreacion(new Date());
		String carrera = obtenerPaginaActual();
		pregunta.setCarrera(carrera);
		pregunta.setIdPregunta(100);
		System.out.println("Hola afuera dos");
		FacesContext context = getCurrentInstance();
        pregunta.setCorreoUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuario"));
        //pregunta.setIdPregunta(2);
		//pregunta.setsetCorreoUsuario(null);
		System.out.println("Hola afuera");
		try {
			jpaController.create(pregunta);
			System.out.println("Hola adentro");
		}catch (Exception e) {
		}
	}


	public String obtenerPaginaActual() {
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
			carrera = "fisíca";
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


	public List<Pregunta> obtenerPreguntasTitulacion() {
		return obtenerPreguntas("titulacion");
	}


	public List<Pregunta> obtenerPreguntasServicioSocial() {
		return obtenerPreguntas("servicio social");
	}


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

/*
    public Usuario getusuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    */


/*

    public String canLogin() {
        Usuario l = jpaController.findLogin(correo, contrasenia);
        boolean logged = l != null;
        if (logged) {
            FacesContext context = getCurrentInstance();
            context.getExternalContext().getSessionMap().put("usuario", l);
            return "index?faces-redirect=true";
        }
        return "index?faces-redirect=true";
    }

    public String logout() {
        FacesContext context = getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
*/
}
