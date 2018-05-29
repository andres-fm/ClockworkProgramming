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
import com.clockwork.pumask.modelo.exceptions.NonexistentEntityException;
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
 * @author io
 */
@ManagedBean
@SessionScoped
public class EliminacionRespuesta {

    private EntityManagerFactory emf;
    private RespuestaJpaController jpaRespuesta;
    private Usuario usuario;
    private Respuesta respuesta;

    /**
    * Constructor único.
    */
    public EliminacionRespuesta () {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        emf = EntityProvider.provider();
        jpaRespuesta = new RespuestaJpaController(emf);
    }

    /**
    * Regresa una lista de respuestas según el usuario.
    * @param correo El correo para identificar al usuario.
    * @return Una lista de respuestas según el usuario.
     List<Respuesta>*/
    public List<Respuesta> obtenRespuestasUsuario(String correo) {
        return jpaRespuesta.obtenRespuestasUsuario(correo);
    }

    /**
    * Elimina una respuesta dada.
    * @param respuesta La pregunta a eliminar.
    */
    public void eliminaRespuesta(Respuesta respuesta) {
        try {
            jpaRespuesta.destroy(respuesta.getIdRespuesta());
        } catch(NonexistentEntityException e) {
            System.out.println("NO SE PUDO :'c");
        }
    }

    /**
    * Regresa la respuesta.
    * @return la respuesta.
    */
	public Respuesta getRespuesta() {
		return respuesta;
	}

}
