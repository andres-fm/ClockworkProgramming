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
);


CREATE TABLE respuesta (
	id_respuesta       int NOT NULL,
	id_pregunta        int NOT NULL, --la pregunta que se está respondiendo
    usuario_respuesta  varchar(30) NOT NULL, --el usuario creador de la respuesta
    contenido          varchar(1000) NOT NULL,
    fecha_publicacion  date NOT NULL,
    likes              int NOT NULL,,
    dislikes           int NOT NULL,,
    CONSTRAINT llave_respuesta PRIMARY KEY(id_respuesta),
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
    FOREIGN KEY (usuario_respuesta) REFERENCES cuenta_usuario(usuario),
);


/*quizá necesitamos tablas para cada carrera, y otra para cada categoría, y que los atributos carrera
 y categoria de la tabla pregunta sean llaves foráneas*/