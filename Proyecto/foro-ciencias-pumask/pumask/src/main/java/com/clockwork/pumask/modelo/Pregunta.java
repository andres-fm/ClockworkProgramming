/**
 * Paquete que representa el modelo, en el patron de diseño
 * Vista-Controldor.
 * El modelo provee una representacion del Diseño de las Entidades
 * que se decidieron en el diseño.
 */
package com.clockwork.pumask.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.*;

/**
 * Clase generada con Ingenieria inversa por Netbeans.
 * Provee los metodos de acceso y modificacion de la entidad Pregunta.
 * @author dima
 */
@Entity
@Table(name = "pregunta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pregunta.findAll", query = "SELECT p FROM Pregunta p"),
    @NamedQuery(name = "Pregunta.findByUser", query = "SELECT p FROM Pregunta p WHERE p.correoUsuario.correo = :correo")
    , @NamedQuery(name = "Pregunta.findByIdPregunta", query = "SELECT p FROM Pregunta p WHERE p.idPregunta = :idPregunta")
    , @NamedQuery(name = "Pregunta.findByCategoria", query = "SELECT p FROM Pregunta p WHERE p.categoria = :categoria")
    , @NamedQuery(name = "Pregunta.findByContenido", query = "SELECT p FROM Pregunta p WHERE p.contenido = :contenido")
    , @NamedQuery(name = "Pregunta.findByCarrera", query = "SELECT p FROM Pregunta p WHERE p.carrera = :carrera")
    , @NamedQuery(name = "Pregunta.findByDetalle", query = "SELECT p FROM Pregunta p WHERE p.detalle = :detalle")
    , @NamedQuery(name = "Pregunta.findByFechaCreacion", query = "SELECT p FROM Pregunta p WHERE p.fechaCreacion = :fechaCreacion")})
@NamedNativeQueries(value = {
    @NamedNativeQuery(
            name = "encontrarPorCategoriaCarrera",
            query = "select * from pregunta where categoria = ?1 and carrera = ?2",
            resultClass = Pregunta.class
    )
})
public class Pregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pregunta")
    private Integer idPregunta;
    @Basic(optional = false)
    @Column(name = "categoria")
    private String categoria;
    @Basic(optional = false)
    @Column(name = "contenido")
    private String contenido;
    @Basic(optional = false)
    @Column(name = "carrera")
    private String carrera;
    @Column(name = "detalle")
    private String detalle;
    @Basic(optional = false)
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPregunta")
    private Collection<Respuesta> respuestaCollection;
    @JoinColumn(name = "correo_usuario", referencedColumnName = "correo")
    @ManyToOne(optional = false)
    private Usuario correoUsuario;

    /**
     * Constructor vacio.
     */
    public Pregunta() {
    }
    
    /**
     * Metodo de modificacion id.
     * @param idPregunta 
     */
    public Pregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }
    /**
     * Metodo de modificacion atribuuutos Pregunta.
     * @param idPregunta
     * @param categoria
     * @param contenido
     * @param carrera
     * @param fechaCreacion 
     */
    public Pregunta(Integer idPregunta, String categoria, String contenido, String carrera, Date fechaCreacion) {
        this.idPregunta = idPregunta;
        this.categoria = categoria;
        this.contenido = contenido;
        this.carrera = carrera;
        this.fechaCreacion = fechaCreacion;
    }
    
    /**
     * Metodo de accceso idPregunta.
     * @return 
     */
    public Integer getIdPregunta() {
        return idPregunta;
    }
    /**
     * Metodo de modificacion idPregunta.
     * @param idPregunta 
     */
    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }
    
    /**
     * Metodo de acceso categoria, la categoria a la que pertenece la 
     * Pregunta.
     * @return 
     */
    public String getCategoria() {
        return categoria;
    }
    /**
     * Metodo de modificacion categoria.
     * @param categoria 
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    /**
     * Metodo de acceso contenido.
     * @return 
     */
    public String getContenido() {
        return contenido;
    }
    
    /**
     * Metodo de modificacion contenido.
     * @param contenido 
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    /**
     * Metodo de acceso carrera.
     * @return 
     */
    public String getCarrera() {
        return carrera;
    }
    
    /**
     * Metodo de modificacion carrera.
     * @param carrera 
     */
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    
    /**
     * Metodo de acceso detalle.
     * @return 
     */
    public String getDetalle() {
        return detalle;
    }
    
    /**
     * metodo de modificacoin detalle.
     * @param detalle 
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
    /**
     * Metodo de acceso Fecha.
     * @return 
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    
    /**
     * 
     * @param fechaCreacion 
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Obtiene todas las respuestas a la pregunta.
     * @return 
     */
    @XmlTransient
    public Collection<Respuesta> getRespuestaCollection() {
        return respuestaCollection;
    }
    
    /**
     * Modifica las respuestas a la pregunta.
     * @param respuestaCollection 
     */
    public void setRespuestaCollection(Collection<Respuesta> respuestaCollection) {
        this.respuestaCollection = respuestaCollection;
    }
    
    /**
     * Metodo de acceso correo Usuario.
     * @return 
     */
    public Usuario getCorreoUsuario() {
        return correoUsuario;
    }
    
    /**
     * Metodo de modificacion correo Usuario.
     * @param correoUsuario 
     */
    public void setCorreoUsuario(Usuario correoUsuario) {
        this.correoUsuario = correoUsuario;
    }
    
    /**
     * Metodo que cifra y devuelve el idPregunta
     * @return el hash de idPregunta
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPregunta != null ? idPregunta.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo de igualdad entre preguntas.
     * @param object
     * @return 
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pregunta)) {
            return false;
        }
        Pregunta other = (Pregunta) object;
        if ((this.idPregunta == null && other.idPregunta != null) || (this.idPregunta != null && !this.idPregunta.equals(other.idPregunta))) {
            return false;
        }
        return true;
    }
    
    /**
     * Representacion de Pregunta como su id en una cadena.
     * @return 
     */
    @Override
    public String toString() {
        return "com.clockwork.pumask.modelo.Pregunta[ idPregunta=" + idPregunta + " ]";
    }
    
}
