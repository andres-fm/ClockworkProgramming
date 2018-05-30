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

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.mail.*;
import javax.mail.internet.*;

import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.persistence.EntityManager;

import javax.enterprise.context.SessionScoped;
import com.google.inject.spi.Message;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import sun.rmi.transport.Transport;

/**
 *
 * @author andres
 */
@ManagedBean
@RequestScoped
public class ControladorRegistro extends HttpServlet{

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
     * Envia correo de confirmacion de registro.
     */
    public void enviarCorreo(String recipient, String key){
        String to = recipient;
        final String username = "pumask@gmx.es";
        final String password = "foro-pumask";
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
                return new PasswordAuthentication(usuario.getNombreUsuario(), usuario.getContrasenia());
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("pumask@gmx.es"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject("Confirmar Registro");
            message.setText("Copie y pegue el siguiente link en su navegador:"
                    + "\n\n" + "localhost:8080/pumask/confirmacionCuenta?llave=" + key);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
           throw new RuntimeException(e);
        }
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
        
        enviarCorreo(usuario.getCorreo(),usuario.getContrasenia());
        
        return null;
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void foo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try (PrintWriter pw = response.getWriter()) {
            pw.println("<HTML><HEAD><TITLE>Leyendo parámetros</TITLE></HEAD>");
            pw.println("<BODY BGCOLOR=\"#CCBBAA\">");
            pw.println("<H2>Leyendo parámetros desde un formulario html</H2><P>");
            pw.println("<UL>\n");
            pw.println("</BODY></HTML>");
        }
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String llave = request.getParameter("contrasenia");
        EntityManager em = emf.createEntityManager();
        
        List unvs = em.createNamedQuery("Usuario.contrasenia").setParameter("contrasenia", llave).getResultList();
        
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        pw.println("<HTML><HEAD><TITLE>Confirmación de cuenta</TITLE></HEAD>");
        if (unvs == null || unvs.isEmpty()) {
            pw.println("<H2>Error de confirmación.</H2><P>");
            pw.println("</BODY></HTML>");
        } else {
            //Usuarionovalidado unv = (Usuarionovalidado) unvs.get(0);
            //Integer id = unv.getIdusuario();
            pw.println("<H2>Éxito en la confirmación</H2><P>");
            pw.println("Puede proceder a la página inicial\nlocalhost:8080/SciAid/");
            //UsuarionovalidadoJpaController unvjpa = new UsuarionovalidadoJpaController(emf);
            UsuarioJpaController ujpa = new UsuarioJpaController(emf);
            //unv = unvjpa.findUsuarionovalidado(id);
            //Usuario u = (Usuario) em.find(Usuario.class, id);
            em.getTransaction().begin();
            //u.setEsbloqueado(false);
            em.getTransaction().commit();
            pw.println("\n");
            //try {
                //unvjpa.destroy(id);
            //} catch (NonexistentEntityException ex) {
            //    Logger.getLogger(ControladorConfirmacionSolicitudRegistroUsuario.class.getName()).log(Level.SEVERE, null, ex);
            //}
            pw.println("\n");
            pw.println("</BODY></HTML>");
            pw.close();
        }
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        foo(request, response);
    }
}

