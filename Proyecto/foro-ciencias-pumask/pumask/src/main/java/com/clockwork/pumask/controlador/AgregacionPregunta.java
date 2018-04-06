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
 * @author miguel
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
    }

	public boolean validaPregunta() {
		return !(pregunta.getContenido() == null);
	}

	public String agregaPregunta() {
		pregunta.setFechaCreacion(new Date());
		try {
			jpaController.create(pregunta);
			return "Pregunta publicada exitosamente";
		}catch (Exception e) {
			return "Ocurrio un error al intentar publicar la pregunta, por favor intente mas tarde";
		}
	}

	public List<Pregunta> obtenerPreguntasCarreras() {
		return jpaController.obtenPreguntasCarrera("actuaria");
	}


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
