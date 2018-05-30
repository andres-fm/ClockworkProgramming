/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clockwork.pumask.controlador;
import com.clockwork.pumask.modelo.EntityProvider;
import com.clockwork.pumask.modelo.Usuario;
import com.clockwork.pumask.modelo.Administrador;
import com.clockwork.pumask.modelo.Pregunta;
import com.clockwork.pumask.modelo.Respuesta;
import com.clockwork.pumask.modelo.UsuarioJpaController;
import com.clockwork.pumask.modelo.AdministradorJpaController;
import com.clockwork.pumask.modelo.PreguntaJpaController;
import com.clockwork.pumask.modelo.RespuestaJpaController;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *
 * @author andres
 */
@ManagedBean
@SessionScoped
public class EliminacionUsuario {

    private EntityManagerFactory emf;
    private AdministradorJpaController administradorJpa;
    private PreguntaJpaController preguntaJpa;
    private RespuestaJpaController respuestaJpa;
    private UsuarioJpaController usuarioJpa;
    private Usuario usuario;

	/**
 * Constructor único.
 */
public EliminacionUsuario() {
    FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
    emf = EntityProvider.provider();
    administradorJpa = new AdministradorJpaController(emf);
    usuarioJpa = new UsuarioJpaController(emf);
    preguntaJpa = new PreguntaJpaController(emf);
    respuestaJpa = new RespuestaJpaController(emf);
    FacesContext context = getCurrentInstance();
    usuario = ((Usuario) context.getExternalContext().getSessionMap().get("usuario"));
    }

    public Usuario getUsuario(){
        return usuario;
    }

	/**
	 * Nos dice si el usuario en la sesion actual es administrador. 
	 * @return <code>true</code> si lo es, <code>false</code> si no.
	 */
	public boolean esAdministrador() {
	    return administradorJpa.findAdministrador(usuario.getCorreo()) != null;
	}

    public List<Usuario> getUsuarios(){
        List<Usuario> lista = new LinkedList<Usuario>();
        for(Usuario u: usuarioJpa.findUsuarioEntities())
            if(u.getCorreo() != this.usuario.getCorreo())
                lista.add(u);
        return lista;
    }


    public void eliminaUsuario(Usuario us){
        try{
            for(Pregunta p: us.getPreguntaCollection()){
		for(Respuesta r: p.getRespuestaCollection()){
		    respuestaJpa.destroy(r.getIdRespuesta());
		}
                preguntaJpa.destroy(p.getIdPregunta());
            }
            for(Respuesta r : us.getRespuestaCollection())
            	respuestaJpa.destroy(r.getIdRespuesta());
            usuarioJpa.destroy(us.getCorreo());
            FacesContext.getCurrentInstance().addMessage(null
                      , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario eliminado exitosamente", ""));
        }catch(Exception e){
	    System.out.println("Error al eliminar usuario");
	    System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null
                      , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al intentar eliminar, intente más tarde", ""));
        }
    }


	 
}
