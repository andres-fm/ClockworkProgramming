--begin;

create role andres with superuser;
alter role andres with login;

create database pumask -O andres;

drop schema if exists login cascade;
create schema login;

drop extension if exists pgcrypto;
create extension pgcrypto;

drop table if exists login.login;

create table login.login (
  id serial primary key
  , usuario text not null
  , password text not null
  , constraint usuarioUnico unique (usuario)
);

comment on table login.login
is
'El usuario USUARIO tiene la contraseña PASS después de aplicarle un hash';

create or replace function login.hash() returns trigger as $$
  begin
    if TG_OP = 'INSERT' then
       new.password = crypt(new.password, gen_salt('bf', 8)::text);
    end if;
    return new;
  end;
$$ language plpgsql;

comment on function login.hash()
is
'Cifra la contraseña del usuario al guardarla en la base de datos.';

create trigger cifra
before insert on login.login
for each row execute procedure login.hash();

create or replace function login.login(usuario text, contraseña text) returns boolean as $$
  select exists(select 1
                  from login.login
                 where usuario = usuario and
                       password = crypt(contraseña, password));
$$ language sql stable;

insert into login.login (usuario, password) values ('Miguel', 'password');
insert into login.login (usuario, password) values ('andres', 'abc123');


--
--a partir de aqui son nuestras tablas
CREATE TABLE usuario (
    nombre_usuario    varchar(30) UNIQUE NOT NULL,
    apel_mat           varchar(30)NOT NULL,
    apel_pat           varchar(30)NOT NULL,
    nombre             varchar(30)NOT NULL,
    url_avatar         varchar(50)NOT NULL,
    contrasenia        varchar(128) NOT NULL,
    correo             varchar(50) NOT NULL,
    creacion           date NOT NULL,
    carrera            varchar(30),
    fecha_nac          date NOT NULL,	
    CONSTRAINT llave_usuario PRIMARY KEY(correo)
);

CREATE TABLE pregunta (
    id_pregunta        int NOT NULL,
    correo_usuario     varchar(50) NOT NULL,
    detalle            varchar(150) NOT NULL,
    contenido          varchar(150) NOT NULL,
    fecha_publicacion  date NOT NULL,
    carrera            varchar(30) NOT NULL, 
    categoria          varchar(30) NOT NULL, 
    CONSTRAINT llave_pregunta PRIMARY KEY(id_pregunta),
    FOREIGN KEY (correo_usuario) REFERENCES usuario(correo)
);

CREATE TABLE respuesta (
    id_respuesta       int NOT NULL,
    id_pregunta        int NOT NULL,
    correo_usuario     varchar(50) NOT NULL,
    contenido          varchar(1000) NOT NULL,
    fecha_publicacion  date NOT NULL,
    likes              int NOT NULL,
    dislikes           int NOT NULL,
    CONSTRAINT llave_respuesta PRIMARY KEY(id_respuesta),
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
    FOREIGN KEY (correo_usuario) REFERENCES usuario(correo)

);

CREATE TABLE administrador (
    id_administrador int NOT NULL
) INHERITS (usuario);

commit;

