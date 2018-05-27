<<<<<<< HEAD
<<<<<<< HEAD
CREATE TABLE cuenta_usuario (
    usuario            varchar(30) CONSTRAINT llave_usuario PRIMARY KEY,
    hash_contrasenia   varchar(128) NOT NULL,
    email              varchar(50) NOT NULL,
    fecha_creacion     date NOT NULL
);

CREATE TABLE cuenta_administrador (
    id                 int --quizá no necesite id, no sé que mas poner
) INHERITS (cuenta_usuario);

CREATE TABLE pregunta (
	id_pregunta        int NOT NULL,
    usuario            varchar(30) NOT NULL,
    contenido          varchar(1000) NOT NULL,
    fecha_publicacion  date NOT NULL,
    likes              int NOT NULL,
    dislikes           int NOT NULL,
    carrera            varchar(30) NOT NULL, --recordemos que nuestra página se divide por carreras
    categoria          varchar(30) NOT NULL, --seccion/tema dentro de la carrera a la que pertenece    
    CONSTRAINT llave_pregunta PRIMARY KEY(id_pregunta),
    FOREIGN KEY (usuario) REFERENCES cuenta_usuario(usuario)
=======

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
>>>>>>> Dimitri-Semenov-Practica1
);


CREATE TABLE respuesta (
	id_respuesta       int NOT NULL,
<<<<<<< HEAD
	id_pregunta        int NOT NULL, --la pregunta que se está respondiendo
    usuario_respuesta  varchar(30) NOT NULL, --el usuario creador de la respuesta
=======
	id_pregunta        int NOT NULL,
    usuario_respuesta  int NOT NULL,
>>>>>>> Dimitri-Semenov-Practica1
    contenido          varchar(1000) NOT NULL,
    fecha_publicacion  date NOT NULL,
    likes              int NOT NULL,,
    dislikes           int NOT NULL,,
    CONSTRAINT llave_respuesta PRIMARY KEY(id_respuesta),
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
<<<<<<< HEAD
    FOREIGN KEY (usuario_respuesta) REFERENCES cuenta_usuario(usuario),
);


/*quizá necesitamos tablas para cada carrera, y otra para cada categoría, y que los atributos carrera
 y categoria de la tabla pregunta sean llaves foráneas*/
=======
    FOREIGN KEY (usuario_respuesta) REFERENCES usuario(id_usuario),
);
>>>>>>> Dimitri-Semenov-Practica1
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
    contenido          varchar(1000) NOT NULL,
    fecha_publicacion  date NOT NULL,
    likes              int NOT NULL,
    dislikes           int NOT NULL,
    CONSTRAINT llave_respuesta PRIMARY KEY(id_respuesta),
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
    FOREIGN KEY (usuario_respuesta) REFERENCES usuario(id)
);
>>>>>>> Carlos-Naranjo-Practica1
