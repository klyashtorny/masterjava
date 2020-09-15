DROP TABLE IF EXISTS mails;
CREATE TABLE mails (
  id             INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
  email          TEXT UNIQUE NOT NULL,
  result         TEXT UNIQUE NOT NULL
);