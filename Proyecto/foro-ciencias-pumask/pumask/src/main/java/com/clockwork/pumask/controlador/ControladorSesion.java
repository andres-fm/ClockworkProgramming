/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clockwork.pumask.controlador;

import com.clockwork.pumask.modelo.EntityProvider;
import com.clockwork.pumask.modelo.Usuario;
import com.clockwork.pumask.modelo.UsuarioJpaController;
import com.clockwork.pumask.modelo.AdministradorJpaController;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.faces.application.FacesMessage;

import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 * Clase controlador de los casos de uso de iniciar y cerrar sesion
 * @author andres
 */
@ManagedBean
@SessionScoped
public class ControladorSesion {

    private EntityManagerFactory emf;
    private UsuarioJpaController jpaController;
    private AdministradorJpaController administradorJpa;
    private String correo;
    private String contrasenia;

    //Contrusctor de la clase
    public ControladorSesion() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        emf = EntityProvider.provider();
        jpaController = new UsuarioJpaController(emf);
        administradorJpa = new AdministradorJpaController(emf);
        correo = "";
        contrasenia = "";
    }
    
    //Regresa el correo ingresado por parte del usuario en la forma de iniciar sesion
	public String getCorreo() {
		return correo;
	}

    //Coloca como valor del correo la cadena recibida
	public void setCorreo(String correo) {
		this.correo = correo;
	}

    //Regresa la contrasenia ingresada por el usuario
	public String getContrasenia() {
		return contrasenia;
	}

    //Coloca como valor de contrasenia la cadena recibida
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

    //Se encarga de iniciar sesion con los datos ingresados por el usuario, en caso de que no sea posible iniciar sesion se redirige al index
    public String iniciarSesion() {
        Usuario l = jpaController.findLogin(correo, contrasenia);
        boolean logged = l != null;
        if (logged) {
            FacesContext context = getCurrentInstance();
            context.getExternalContext().getSessionMap().put("usuario", l);
            return "/index?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null
                      , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrectos", ""));
        return "";
    }

    //Se encarga de salir de la sesion actual, te redirige al index una vez que se sale de la sesion
    public String cerrarSesion() {
        FacesContext context = getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    //Determina si en el contexto actual hay una sesion activa
    public boolean estaEnSesion() {
        FacesContext context = getCurrentInstance();
        Usuario l = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
        return l != null;
    }

    //Funcion auxiliar que determina si los botones de registro e iniciar sesion se muestran o no, dependiendo de si actualmente ya se encuentran en alguna de esas paginas
    public boolean mostrarBotones() {
	String context = getCurrentInstance().getViewRoot().getViewId();
  	return !context.equals("/forma_iniciar_sesion.xhtml") && !context.equals("/registro.xhtml");	
    }

    //Regresa al usuario de la sesion actual
    public Usuario getUsuario() {
        FacesContext context = getCurrentInstance();
        return (Usuario) context.getExternalContext().getSessionMap().get("usuario");
    }

    //Funcion auxiliar que te dirige a la forma de iniciar sesion
    public String formaIniciarSesion() {
	return "/forma_iniciar_sesion.xhtml?faces-redirect=true";
    }


    //Funcion auxiliar que te dirige a la forma de ajustes
    public String ajustes() {
	return "/secured/ajustes.xhtml?faces-redirect=true";
    }

    //Funcion auxiliar que te dirige a la forma de mi perfil
    public String miPerfil() {
	return "/secured/mi_perfil.xhtml?faces-redirect=true";
    }

    public String regresar(){
	return "/index.xhtml?faces-redirect=true";
    }


		/**
	 * Nos dice si el usuario en la sesion actual es administrador. 
	 * @return <code>true</code> si lo es, <code>false</code> si no.
	 */
	public boolean esAdministrador() {
		FacesContext context = getCurrentInstance();
        Usuario l = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
	    if (l != null)
	    	return administradorJpa.findAdministrador(l.getCorreo()) != null;
	    else
	    	return false;
	}


}
