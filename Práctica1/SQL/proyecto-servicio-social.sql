CREATE TABLE usuario(
    id int CONSTRAINT key PRIMARY KEY,
    nombre             varchar(30),
    email              varchar (30),
    contrasenia        varchar(128),
    email              varchar(50) NOT NULL,
    fecha_creacion     date NOT NULL,
    foto               bytea,
    tipo_administrador boolean NOT NULL,
    CONSTRAINT key PRIMARY KEY(id)
);

CREATE TABLE pregunta (
	  id_pregunta        int NOT NULL,
    id                 int NOT NULL,
    pregunta           varchar(150) NOT NULL
    contenido          varchar(1000) NOT NULL,
    fecha_creacion     date NOT NULL,
    likes              int NOT NULL,
    dislikes           int NOT NULL,
    carrera            varchar(30) NOT NULL,
    CONSTRAINT llave_pregunta PRIMARY KEY(id_pregunta),
    FOREIGN KEY (id) REFERENCES usuario(id)
);

CREATE TABLE respuesta (
	  id_respuesta       int NOT NULL,
	  id_pregunta        int NOT NULL,
    usuario_respuesta  int NOT NULL,
    contenido          varchar(1000) NOT NULL,
    fecha_publicacion  date NOT NULL,
    likes              int NOT NULL,
    dislikes           int NOT NULL,
    CONSTRAINT llave_respuesta PRIMARY KEY(id_respuesta),
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
    FOREIGN KEY (usuario_respuesta) REFERENCES usuario(id)
);
