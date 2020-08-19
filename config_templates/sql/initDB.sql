DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_seq;
DROP TYPE IF EXISTS user_flag;

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');

CREATE SEQUENCE user_seq START 100000;

CREATE TABLE users (
  id        INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  full_name TEXT NOT NULL,
  email     TEXT NOT NULL,
  flag      user_flag NOT NULL
);

CREATE UNIQUE INDEX email_idx ON users (email);
ALTER TABLE public.users ADD city_fk INT NULL;

DROP TABLE IF EXISTS cities;
CREATE TABLE cities
(
  id SERIAL PRIMARY KEY NOT NULL,
  short_name TEXT,
  value TEXT
);
CREATE UNIQUE INDEX cities_id_uindex ON public.cities (id);
COMMENT ON TABLE public.cities IS 'Таблица городов пользователей';

DROP TABLE IF EXISTS projects;
CREATE TABLE projects
(
  id SERIAL PRIMARY KEY NOT NULL,
  name TEXT,
  description TEXT
);
CREATE UNIQUE INDEX projects_id_uindex ON public.projects (id);
COMMENT ON TABLE public.projects IS 'Таблица проектов';

DROP TABLE IF EXISTS groups;
CREATE TABLE groups
(
  id SERIAL PRIMARY KEY NOT NULL,
  name TEXT,
  type group_type NOT NULL,
  project_fk  INT NULL
);
CREATE UNIQUE INDEX groups_id_uindex ON public.groups (id);
COMMENT ON TABLE public.groups IS 'Таблица групп';

CREATE TYPE group_type AS ENUM ('registering', 'current', 'finished');