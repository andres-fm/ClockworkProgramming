
CREATE TABLE usuario (
    id_usuario         int CONSTRAINT llave_primaria PRIMARY KEY,
    usuario            varchar(30) UNIQUE,
    contrasenia        varchar(128) NOT NULL,
    email              varchar(50) NOT NULL,
    fecha_creacion     date NOT NULL,
    foto               bytea,
    administrador      boolean NOT NULL,
    CONSTRAINT llave_usuario PRIMARY KEY(id_usuario)
);

CREATE TABLE pregunta (
	id_pregunta        int NOT NULL,
    id_usuario         int NOT NULL,
    pregunta           varchar(150) NOT NULL
    contenido          text,
    fecha_creacion     date NOT NULL,
    likes              int NOT NULL,
    dislikes           int NOT NULL,
    carrera            varchar(30) NOT NULL, 
    categoria          varchar(30) NOT NULL, 
    CONSTRAINT llave_pregunta PRIMARY KEY(id_pregunta),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);


CREATE TABLE respuesta (
	id_respuesta       int NOT NULL,
	id_pregunta        int NOT NULL,
    usuario_respuesta  int NOT NULL,
    contenido          varchar(1000) NOT NULL,
    fecha_publicacion  date NOT NULL,
    likes              int NOT NULL,,
    dislikes           int NOT NULL,,
    CONSTRAINT llave_respuesta PRIMARY KEY(id_respuesta),
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
    FOREIGN KEY (usuario_respuesta) REFERENCES usuario(id_usuario),
);