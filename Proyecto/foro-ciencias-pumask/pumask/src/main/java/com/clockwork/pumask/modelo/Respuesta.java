/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clockwork.pumask.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.*;

/**
 *
 * @author dima
 */
@Entity
@Table(name = "respuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Respuesta.findAll", query = "SELECT r FROM Respuesta r")
    , @NamedQuery(name = "Respuesta.findByIdRespuesta", query = "SELECT r FROM Respuesta r WHERE r.idRespuesta = :idRespuesta")
    , @NamedQuery(name = "Respuesta.findByContenido", query = "SELECT r FROM Respuesta r WHERE r.contenido = :contenido")
    , @NamedQuery(name = "Respuesta.findByFechaPublicacion", query = "SELECT r FROM Respuesta r WHERE r.fechaPublicacion = :fechaPublicacion")
    , @NamedQuery(name = "Respuesta.findByLikes", query = "SELECT r FROM Respuesta r WHERE r.likes = :likes")
    , @NamedQuery(name = "Respuesta.findByDislikes", query = "SELECT r FROM Respuesta r WHERE r.dislikes = :dislikes")})
public class Respuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_respuesta")
    private Integer idRespuesta;
    @Basic(optional = false)
    @Column(name = "contenido")
    private String contenido;
    @Basic(optional = false)
    @Column(name = "fecha_publicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    @Basic(optional = false)
    @Column(name = "likes")
    private int likes;
    @Basic(optional = false)
    @Column(name = "dislikes")
    private int dislikes;
    @JoinColumn(name = "id_pregunta", referencedColumnName = "id_pregunta")
    @ManyToOne(optional = false)
    private Pregunta idPregunta;
    @JoinColumn(name = "usuario_correo", referencedColumnName = "correo")
    @ManyToOne(optional = false)
    private Usuario usuarioCorreo;

    public Respuesta() {
    }

    public Respuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Respuesta(final Integer idRespuesta, final String contenido, final Date fechaPublicacion, final int likes, final int dislikes) {
        this.idRespuesta = idRespuesta;
        this.contenido = contenido;
        this.fechaPublicacion = fechaPublicacion;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public final Integer getIdRespuesta() {
        return idRespuesta;
    }

    public final void setIdRespuesta(final Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public final String getContenido() {
        return contenido;
    }

    public final void setContenido(final String contenido) {
        this.contenido = contenido;
    }

    public final Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public final void setFechaPublicacion(final Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public final int getLikes() {
        return likes;
    }

    public final void setLikes(final int likes) {
        this.likes = likes;
    }

    public final int getDislikes() {
        return dislikes;
    }

    public final void setDislikes(final int dislikes) {
        this.dislikes = dislikes;
    }

    public final Pregunta getIdPregunta() {
        return idPregunta;
    }

    public final void setIdPregunta(final Pregunta idPregunta) {
        this.idPregunta = idPregunta;
    }

    public final Usuario getUsuarioCorreo() {
        return usuarioCorreo;
    }

    public final void setUsuarioCorreo(final Usuario usuarioCorreo) {
        this.usuarioCorreo = usuarioCorreo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRespuesta != null ? idRespuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuesta)) {
            return false;
        }
        Respuesta other = (Respuesta) object;
        if ((this.idRespuesta == null && other.idRespuesta != null) || (this.idRespuesta != null && !this.idRespuesta.equals(other.idRespuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.clockwork.pumask.modelo.Respuesta[ idRespuesta=" + idRespuesta + " ]";
    }

}
