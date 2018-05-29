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
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *
 * @author Juan
 */
@ManagedBean
@SessionScoped
public class BusquedaPregunta {

    private EntityManagerFactory emf;
    private PreguntaJpaController jpaController;
	private Pregunta pregunta;
    private String query;
    private List<Pregunta> results;

    /**
    * Constructor único.
    */
    public BusquedaPregunta() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        emf = EntityProvider.provider();
        jpaController = new PreguntaJpaController(emf);
    }

    /**
    * Agrega una pregunta
    * @return Un mensaje dependiendo de qué ocurrió en la transacción.
    */
	public String buscarPregunta(String q) {
		try {
            this.query = q;
			this.results = jpaController.obtenPreguntasPorPalabrasClave(q);
		} catch (Exception e) {
            e.printStackTrace();
		}
		return "/results.xhtml?faces-redirect=true";
	}

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Pregunta> getResults() {
        return this.results;
    }

}
