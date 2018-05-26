package com.clockwork.pumask.modelo;

import com.clockwork.pumask.modelo.Respuesta;
import com.clockwork.pumask.modelo.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-20T14:52:58")
@StaticMetamodel(Pregunta.class)
public class Pregunta_ { 

    public static volatile CollectionAttribute<Pregunta, Respuesta> respuestaCollection;
    public static volatile SingularAttribute<Pregunta, String> contenido;
    public static volatile SingularAttribute<Pregunta, Usuario> correoUsuario;
    public static volatile SingularAttribute<Pregunta, String> categoria;
    public static volatile SingularAttribute<Pregunta, Date> fechaCreacion;
    public static volatile SingularAttribute<Pregunta, String> carrera;
    public static volatile SingularAttribute<Pregunta, Integer> idPregunta;
    public static volatile SingularAttribute<Pregunta, String> detalle;

}