/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clockwork.pumask.controlador;
import java.util.Date;
import com.clockwork.pumask.modelo.EntityProvider;
import com.clockwork.pumask.modelo.Usuario;
import com.clockwork.pumask.modelo.UsuarioJpaController;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.faces.application.FacesMessage;

import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *
 * @author andres
 */
@ManagedBean
@RequestScoped
public class ControladorRegistro {

    private EntityManagerFactory emf;
    private UsuarioJpaController usuarioJpa;
	private Usuario usuario; 
	private String confirmacion;

	/**
	 * Constructor único.
	 */
	public ControladorRegistro(){
		usuario = new Usuario();
		confirmacion = "";
		emf = EntityProvider.provider();
		usuarioJpa = new UsuarioJpaController(emf);
	}

	/**
	 * Regresa el usuario.
	 * @return una instancia de Usuario.
	 */
	public Usuario getUsuario(){
		return usuario;
	}

	/**
	 * Asigna el usuario al usuario dado.
	 * @param usuario El usuario dado.
	 */
	public void setUsuario(Usuario usuario){
		this.usuario = usuario;
	}
	
	/**
	* Regresa una cadena con la confirmación.
	* @return Una cadena con la confirmación.
	*/
	public String getConfirmacion(){
		return confirmacion;
	}

	/**
	* Asigna la confirmación a la cadena dada.
	* @param conf La confirmación dada.
	*/
	public void setConfirmacion(String conf){
		this.confirmacion = conf;
	}

	/**
	* Regresa una cadena con parámetros para el URL del registro.
	* @return una cadena con parámetros para el URL del registro.
	*/
    public String formaRegistro() {
       return "/registro.xhtml?faces-redirect=true";
    }

    /**
    * Agrega un usuario.
    * @return Un mensaje dependiendo de qué sucedió con el registro.
    */
	public String agregaUsuario() {
        if (!usuario.getContrasenia().equals(confirmacion)) {
            FacesContext.getCurrentInstance().addMessage(null
                      , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo de registro: Las contraseñas deben coincidir", ""));
        } else {
			usuario.setFechaCreacion(new Date());
			try{	
			    usuarioJpa.create(usuario);	
            }catch(Exception e){
                FacesContext.getCurrentInstance().addMessage(null
                      , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo de registro: el usuario ya existe", ""));
				return null;	

			}
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Felicidades, el registro se ha realizado correctamente", ""));
            usuario = null;
			confirmacion = null;	
        }
        return null;
    }
}

