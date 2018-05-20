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
import com.clockwork.pumask.modelo.RespuestaJpaController;
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
 * @author Juan
 */
@ManagedBean
@SessionScoped
public class EliminacionPregunta {

    private EntityManagerFactory emf;
    private PreguntaJpaController jpaController;
    private RespuestaJpaController jpaRespuesta;
    private Usuario usuario;
	private Pregunta pregunta;

    /**
    * Constructor único.
    */
    public EliminacionPregunta() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        emf = EntityProvider.provider();
        jpaController = new PreguntaJpaController(emf);
        jpaRespuesta = new RespuestaJpaController(emf);
    }

    /**
    * Agrega una pregunta
    * @return Un mensaje dependiendo de qué ocurrió en la transacción.
    */
	public String agregaPregunta() {
		pregunta.setFechaCreacion(new Date());
		try {
			jpaController.create(pregunta);
			return "Pregunta publicada exitosamente";
		}catch (Exception e) {
			return "Ocurrio un error al intentar publicar la pregunta, por favor intente mas tarde";
		}
	}

    /**
    * Regresa una lista de preguntas según el usuario.
    * @param correo El correo para identificar al usuario.
    * @return Una lista de preguntas según el usuario.
    */
	public List<Pregunta> obtenPreguntasUsuario(String correo) {
		return jpaController.obtenPreguntasUsuario(correo);
	}

    /**
    * Elimina una pregunta dada.
    * @param pregunta La pregunta a eliminar.
    */
    public void eliminaPregunta(Pregunta pregunta) {
        try {
            for(Respuesta r: pregunta.getRespuestaCollection())
                jpaRespuesta.destroy(r.getIdRespuesta());
            jpaController.destroy(pregunta.getIdPregunta());
        } catch(Exception e) {
        
        }
    }

    /**
    * Regresa la pregunta.
    * @return la pregunta.
    */
	public Pregunta getPregunta() {
		return pregunta;
	}

}
