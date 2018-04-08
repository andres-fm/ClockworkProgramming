/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clockwork.pumask.controlador;

import com.clockwork.pumask.modelo.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *
 * @author miguel
 */
@ManagedBean
@SessionScoped
public class UsuarioBean {

    /**
     * Creates a new instance of UserBean
     */
    public UsuarioBean() {
    }

    public boolean isLogged() {
        FacesContext context = getCurrentInstance();
        Usuario l = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
        return l != null;
    }

    public Usuario getUsuario() {
        FacesContext context = getCurrentInstance();
        return (Usuario) context.getExternalContext().getSessionMap().get("usuario");
    }

}
