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
import com.clockwork.pumask.modelo.Respuesta;
import com.clockwork.pumask.modelo.RespuestaJpaController;
import java.util.Locale;
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
public class AgregacionRespuesta {

    private EntityManagerFactory emf;
    private RespuestaJpaController jpaController;
    //private Usuario usuario;
	private Respuesta respuesta;

	/**
	 * Constructor único.
	 */
    public AgregacionRespuesta() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        emf = EntityProvider.provider();
        jpaController = new RespuestaJpaController(emf);
        respuesta = new Respuesta();
        respuesta.setLikes(0);
        respuesta.setDislikes(0);
        //respuesta.setCategoria("titulacion");
    }


	/**
	 * Agrega una pregunta a la base de datos.
	 */
	public void agregaPregunta() {
		respuesta.setFechaPublicacion(new Date());
		FacesContext context = getCurrentInstance();
        respuesta.setUsuarioCorreo((Usuario) context.getExternalContext().getSessionMap().get("usuario"));
        respuesta.setIdPregunta((Pregunta) context.getExternalContext().getSessionMap().get("pregunta"));
        try {
			jpaController.create(respuesta);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Respuesta agregada exitosamente"));
		}catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocurrio un error, intentar más tarde"));
		}
	}

}
