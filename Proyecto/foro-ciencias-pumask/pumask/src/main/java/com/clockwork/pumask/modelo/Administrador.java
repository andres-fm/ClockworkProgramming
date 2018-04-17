/**
 * Paquete que representa el modelo, en el patron de diseño
 * Vista-Controldor.
 * El modelo provee una representacion del Diseño de las Entidades
 * que se decidieron en el diseño.
 */
package com.clockwork.pumask.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase generada con ingenieria inversa, con netbeans basada en el diseño
 * de las Entidades de la base de datos.
 * @author dima
 */
@Entity
@Table(name = "administrador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administrador.findAll", query = "SELECT a FROM Administrador a")
    , @NamedQuery(name = "Administrador.findByCorreoAdmin", query = "SELECT a FROM Administrador a WHERE a.correoAdmin = :correoAdmin")})
public class Administrador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "correo_admin")
    private String correoAdmin;
    @JoinColumn(name = "correo_admin", referencedColumnName = "correo", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;

    /**
     * Constructor vacio para la clase Administrador.
     */
    public Administrador() {
    }

    /**
     * Constructor Usuario Administrador con un correo.
     * @param correoAdmin el correo Asociado al Administrador.
     */
    public Administrador(String correoAdmin) {
        this.correoAdmin = correoAdmin;
    }

    /**
     * M&eacute;todo de acceso al correo asociado al Administrador.
     * @return el correo asociado al Usuario Administrador.
     */
    public String getCorreoAdmin() {
        return correoAdmin;
    }

    /**
     * M&eacute;todo de modificaci&oacute;n del correo del Administrador
     * el Administrador podra actualizar su correo por medio de este.
     * @param correoAdmin el nuevo correo del Administrador.
     */
    public void setCorreoAdmin(String correoAdmin) {
        this.correoAdmin = correoAdmin;
    }

    /**
     * M&eacute;todo de acceso Usuario.
     * @return el Usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * M&eacute;todo de modificacion de Usuario.
     * @param usuario el nuevo usuario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    /**
     * Cifra correoAdmin y lo devuelve.
     * @return el hash del correoAdmin.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (correoAdmin != null ? correoAdmin.hashCode() : 0);
        return hash;
    }

    /**
     * Sobre carga del metodo equals de la clase Object.
     * @param object el objeto a comparar.
     * @return el resultado de la comparaci&oacute;n de igualdad.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrador)) {
            return false;
        }
        Administrador other = (Administrador) object;
        if ((this.correoAdmin == null && other.correoAdmin != null) || (this.correoAdmin != null && !this.correoAdmin.equals(other.correoAdmin))) {
            return false;
        }
        return true;
    }

    /**
     * Sobrecarga del metodo toString de Object.
     * @return regresa el correo del Administrador como cadena.
     */
    @Override
    public String toString() {
        return "com.clockwork.pumask.modelo.Administrador[ correoAdmin=" + correoAdmin + " ]";
    }

}
