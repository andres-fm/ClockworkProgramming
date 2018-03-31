CREATE TABLE usuario (
    usuario            varchar(30) UNIQUE,
    contrasenia        varchar(128) NOT NULL,
    email              varchar(50) NOT NULL,
    fecha_creacion     date NOT NULL,
    url_foto               varchar(100),
    administrador      boolean NOT NULL,
    CONSTRAINT llave_usuario PRIMARY KEY(email)
);

CREATE TABLE pregunta (
	id_pregunta        int NOT NULL,
    id_usuario         varchar(50) NOT NULL,
    pregunta           varchar(150) NOT NULL,
    contenido          text,
    fecha_creacion     date NOT NULL,
    fecha_actualizacion date NOT NULL,
    likes              int NOT NULL,
    dislikes           int NOT NULL,
    carrera            varchar(30) NOT NULL, 
    categoria          varchar(30) NOT NULL, 
    CONSTRAINT llave_pregunta PRIMARY KEY(id_pregunta),
    FOREIGN KEY (id_usuario) REFERENCES usuario(email)
);

CREATE TABLE respuesta (
	 id_respuesta       int NOT NULL,
	 id_pregunta        int NOT NULL,
    usuario_respuesta  int NOT NULL,
    id_usuario         varchar(50) NOT NULL,
    contenido          varchar(1000) NOT NULL,
    fecha_publicacion  date NOT NULL,
    likes              int NOT NULL,
    dislikes           int NOT NULL,
    CONSTRAINT llave_respuesta PRIMARY KEY(id_respuesta),
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
    FOREIGN KEY (id_usuario) REFERENCES usuario(email),    
);
