package com.clockwork.pumask.modelo;

import com.clockwork.pumask.modelo.Administrador;
import com.clockwork.pumask.modelo.Pregunta;
import com.clockwork.pumask.modelo.Respuesta;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T11:46:47")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile CollectionAttribute<Usuario, Respuesta> respuestaCollection;
    public static volatile SingularAttribute<Usuario, Administrador> administrador;
    public static volatile SingularAttribute<Usuario, String> correo;
    public static volatile SingularAttribute<Usuario, Date> fechaCreacion;
    public static volatile SingularAttribute<Usuario, String> contrasenia;
    public static volatile SingularAttribute<Usuario, String> nombreUsuario;
    public static volatile CollectionAttribute<Usuario, Pregunta> preguntaCollection;
    public static volatile SingularAttribute<Usuario, String> urlAvatar;

}