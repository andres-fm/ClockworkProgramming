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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import java.security.MessageDigest;
import org.postgresql.util.PSQLException;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


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
	private String claveConf;
	private UploadedFile fotografia;


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
	
	
	 public UploadedFile getFotografia() {
        return fotografia;
    }

    public void setFotografia(UploadedFile fotografia) {
        this.fotografia = fotografia;
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

	public void fileUploadListener(FileUploadEvent e) {
		System.out.println("Asignamos el evento");
		this.fotografia = e.getFile();
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
        	try{
        		MessageDigest md = MessageDigest.getInstance("MD5");
            	md.update(usuario.getCorreo().getBytes());
            	byte[] digest = md.digest();
            	StringBuffer sb = new StringBuffer();
            	for (byte b : digest) {
            	    sb.append(String.format("%02x", b & 0xff));
            	}
            	this.claveConf = sb.toString();
				usuario.setFechaCreacion(new Date());
				System.out.println("Vamos a ver si la imagen es null");
				if (fotografia != null) {
                	usuario.setUrlAvatar(fotografia.getContents());
                	System.out.println("Guardamos imagen");
				}
				System.out.println("Vamos a enviar el correo");
				enviarCorreoConfirmacion(usuario.getCorreo(), sb.toString());
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
    
    
    private void enviarCorreoConfirmacion(String recipient, String key) {
        String to = recipient;
        final String username = "pumaskciencias@gmail.com";
        final String password = "pumask123";
        // Assuming you are sending email from localhost
        String host = "localhost";
        // Get system properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("pumaskciencias@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject("Validación cuenta pumask");
            message.setText("Copie y pegue el siguiente link en su navegador:"
                    + "\n\n" + "localhost:8080/pumask/confirmarCuenta?key=" + key);


			System.out.println("Enviamos el correo");
            Transport.send(message);

            System.out.println("El correo se envio");

        } catch (MessagingException e) {
            System.out.println("Error");
            //throw new RuntimeException(e);
        }
    }
}

