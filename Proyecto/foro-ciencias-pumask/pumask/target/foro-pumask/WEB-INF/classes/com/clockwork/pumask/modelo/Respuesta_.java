package com.clockwork.pumask.modelo;

import com.clockwork.pumask.modelo.Pregunta;
import com.clockwork.pumask.modelo.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-07T17:55:50")
@StaticMetamodel(Respuesta.class)
public class Respuesta_ { 

    public static volatile SingularAttribute<Respuesta, String> contenido;
    public static volatile SingularAttribute<Respuesta, Usuario> usuarioCorreo;
    public static volatile SingularAttribute<Respuesta, Integer> dislikes;
    public static volatile SingularAttribute<Respuesta, Date> fechaPublicacion;
    public static volatile SingularAttribute<Respuesta, Integer> idRespuesta;
    public static volatile SingularAttribute<Respuesta, Integer> likes;
    public static volatile SingularAttribute<Respuesta, Pregunta> idPregunta;

}