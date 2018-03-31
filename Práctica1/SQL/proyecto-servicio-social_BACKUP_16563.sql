<<<<<<< HEAD

CREATE TABLE usuario (
    id_usuario         int CONSTRAINT llave_primaria PRIMARY KEY,
=======
CREATE TABLE usuario (
>>>>>>> Juan-Casas-Practica1
    usuario            varchar(30) UNIQUE,
    contrasenia        varchar(128) NOT NULL,
    email              varchar(50) NOT NULL,
    fecha_creacion     date NOT NULL,
<<<<<<< HEAD
    foto               bytea,
    administrador      boolean NOT NULL,
    CONSTRAINT llave_usuario PRIMARY KEY(id_usuario)
=======
    url_foto               varchar(100),
    administrador      boolean NOT NULL,
    CONSTRAINT llave_usuario PRIMARY KEY(email)
>>>>>>> Juan-Casas-Practica1
);

CREATE TABLE pregunta (
	id_pregunta        int NOT NULL,
<<<<<<< HEAD
    id_usuario         int NOT NULL,
    pregunta           varchar(150) NOT NULL,
    contenido          text,
    fecha_creacion     date NOT NULL,
=======
    id_usuario         varchar(50) NOT NULL,
    pregunta           varchar(150) NOT NULL,
    contenido          text,
    fecha_creacion     date NOT NULL,
    fecha_actualizacion date NOT NULL,
>>>>>>> Juan-Casas-Practica1
    likes              int NOT NULL,
    dislikes           int NOT NULL,
    carrera            varchar(30) NOT NULL, 
    categoria          varchar(30) NOT NULL, 
    CONSTRAINT llave_pregunta PRIMARY KEY(id_pregunta),
<<<<<<< HEAD
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
=======
    FOREIGN KEY (id_usuario) REFERENCES usuario(email)
>>>>>>> Juan-Casas-Practica1
);


CREATE TABLE respuesta (
	id_respuesta       int NOT NULL,
	id_pregunta        int NOT NULL,
<<<<<<< HEAD
    usuario_respuesta  int NOT NULL,
    contenido          varchar(1000) NOT NULL,
    fecha_publicacion  date NOT NULL,
    likes              int NOT NULL,
    dislikes           int NOT NULL,
    CONSTRAINT llave_respuesta PRIMARY KEY(id_respuesta),
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
    FOREIGN KEY (usuario_respuesta) REFERENCES usuario(id_usuario),
);
=======
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
=======
    id_usuario         varchar(50) NOT NULL,
>>>>>>> Juan-Casas-Practica1
    contenido          varchar(1000) NOT NULL,
    fecha_publicacion  date NOT NULL,
    likes              int NOT NULL,
    dislikes           int NOT NULL,
    CONSTRAINT llave_respuesta PRIMARY KEY(id_respuesta),
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
<<<<<<< HEAD
    FOREIGN KEY (usuario_respuesta) REFERENCES usuario(id)
);
>>>>>>> Carlos-Naranjo-Practica1
=======
    FOREIGN KEY (id_usuario) REFERENCES usuario(email),
);
>>>>>>> Juan-Casas-Practica1
