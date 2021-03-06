begin;


DROP TABLE respuesta;
DROP TABLE administrador;
DROP TABLE pregunta;
DROP TABLE usuario;

drop extension if exists pgcrypto;
create extension pgcrypto;

CREATE TABLE usuario (
    correo             text CONSTRAINT llave_prim_usuario PRIMARY KEY,
    nombre_usuario     varchar(30) UNIQUE NOT NULL,
    contrasenia        text NOT NULL,
    url_avatar         bytea,
    fecha_creacion     date NOT NULL,
    CONSTRAINT correo_valido CHECK (correo ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')
);

CREATE TABLE administrador (
    correo_admin       text CONSTRAINT llave_prim_admin PRIMARY KEY,
    FOREIGN KEY (correo_admin) REFERENCES usuario(correo)
);


CREATE TABLE pregunta (
	id_pregunta        Serial,
    correo_usuario     text NOT NULL,
    categoria          varchar(50) NOT NULL,
    contenido          varchar(250) NOT NULL,
    carrera            varchar(60) NOT NULL,
    detalle            text,
    fecha_creacion     date NOT NULL,
    CONSTRAINT llave_pregunta PRIMARY KEY(id_pregunta),
    FOREIGN KEY (correo_usuario) REFERENCES usuario(correo)
);


CREATE TABLE respuesta (
	id_respuesta       Serial,
	id_pregunta        integer,
    usuario_correo     text NOT NULL,
    contenido          varchar(1000) NOT NULL,
    fecha_publicacion  date NOT NULL,
    likes              int NOT NULL,
    dislikes           int NOT NULL,
    CONSTRAINT llave_respuesta PRIMARY KEY(id_respuesta),
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
    FOREIGN KEY (usuario_correo) REFERENCES usuario(correo)
);



comment on table usuario
is
'El usuario USUARIO tiene la contraseña PASS después de aplicarle un hash';

create or replace function usuarioHash() returns trigger as $$
  begin
    if TG_OP = 'INSERT' then
       new.contrasenia = crypt(new.contrasenia, gen_salt('bf', 8)::text);
    end if;
    return new;
  end;
$$ language plpgsql;

comment on function usuarioHash()
is
'Cifra la contraseña del usuario al guardarla en la base de datos.';

create trigger cifra
before insert on usuario
for each row execute procedure usuarioHash();

create or replace function loginBase(correo_usuario text, password text) returns boolean as $$
  select exists(select 1
                  from usuario
                 where correo = correo_usuario and
                       contrasenia = crypt(password, contrasenia));
$$ language sql stable;


insert into usuario (correo, nombre_usuario, contrasenia, url_avatar, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'oscar', 'password', 'no sirve este url', '2018-03-31');
insert into usuario (correo, nombre_usuario, contrasenia, url_avatar, fecha_creacion) values ('user@ciencias.unam.mx', 'matt', '12345', 'no sirve este url', '2018-03-31');
insert into usuario (correo, nombre_usuario, contrasenia, url_avatar, fecha_creacion) values ('admin@ciencias.unam.mx', 'Admin', '12345', 'no sirve este url', '2018-03-31');
insert into administrador (correo_admin) values ('admin@ciencias.unam.mx');




insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'titulacion', 'pregunta 1', 'actuaria', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'titulacion', 'pregunta 2', 'fisica', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'titulacion', 'pregunta 3', 'fisica', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'titulacion', 'pregunta 4', 'actuaria', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'titulacion', 'pregunta 5', 'fisica', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'titulacion', 'pregunta 6', 'actuaria', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'titulacion', 'pregunta 7', 'actuaria', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'titulacion', 'pregunta 8', 'fisica', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'titulacion', 'pregunta 9', 'actuaria', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'titulacion', 'pregunta 10', 'fisica', '2018-03-31');


insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'servicio social', 'pregunta 1', 'actuaria', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'servicio social', 'pregunta 2', 'actuaria', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'servicio social', 'pregunta 3', 'actuaria', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'servicio social', 'pregunta 4', 'actuaria', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'servicio social', 'pregunta 5', 'fisica', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'servicio social', 'pregunta 6', 'fisica', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'servicio social', 'pregunta 7', 'fisica', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'servicio social', 'pregunta 8', 'fisica', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'servicio social', 'pregunta 9', 'fisica', '2018-03-31');
insert into pregunta (correo_usuario, categoria, contenido, carrera, fecha_creacion) values ('micorreo@ciencias.unam.mx', 'servicio social', 'pregunta 10', 'fisica', '2018-03-31');


commit;
