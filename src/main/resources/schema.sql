CREATE TABLE if not exists public.users (
  id SERIAL NOT NULL,
  name varchar(40) NOT NULL,
  email varchar(255) NOT NULL,
  login varchar(255),
  birthday date NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE if not exists public.friendship (
  id SERIAL NOT NULL,
  initiator_id int8 NOT NULL,
  recipient_id int8 NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT friendship_initiator_id_fkey FOREIGN KEY (initiator_id) REFERENCES public.users(id),
  CONSTRAINT friendship_recipient_id_fkey FOREIGN KEY (recipient_id) REFERENCES public.users(id)
);

CREATE TABLE if not exists public.genre (
    id SERIAL not null,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE if not exists public.mparating (
    id SERIAL not null,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE if not exists public.films (
  id SERIAL NOT NULL,
  name varchar(255) NOT NULL,
  releaseDate date,
  MPArating_id int4,
  duration int4,
  description varchar(1024),
  PRIMARY KEY (id),
  CONSTRAINT films_MPArating_id_fkey FOREIGN KEY (MPArating_id) REFERENCES public.mparating(id)
);

CREATE TABLE IF NOT EXISTS public.film_genre (
  id serial not null,
  film_id int4,
  genre_id int4,
  PRIMARY KEY (id),
  CONSTRAINT film_genre_film_id_fkey FOREIGN KEY (film_id) REFERENCES public.films(id),
  CONSTRAINT film_genre_genre_id_fkey FOREIGN KEY (genre_id) REFERENCES public.genre(id)
);


CREATE TABLE if not exists public.likes (
  id SERIAL NOT NULL,
  film_id int4 NOT NULL,
  user_id int8 NOT NULL,
  mark_time timestamp NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT likes_film_id_fkey FOREIGN KEY (film_id) REFERENCES public.films(id),
  CONSTRAINT likes_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);

