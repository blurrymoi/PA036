ALTER TABLE IF EXISTS event
  DROP CONSTRAINT IF EXISTS event_admin_fk;

ALTER TABLE IF EXISTS event
  DROP CONSTRAINT IF EXISTS event_sport_fk;

ALTER TABLE IF EXISTS event_sportsman
  DROP CONSTRAINT IF EXISTS event_sportman_fk;

ALTER TABLE IF EXISTS event_sportsman
  DROP CONSTRAINT IF EXISTS event_sportman_event_fk;

ALTER TABLE IF EXISTS invitation
  DROP CONSTRAINT IF EXISTS invitation_event_fk;

ALTER TABLE IF EXISTS invitation
  DROP CONSTRAINT IF EXISTS invitation_sportsman_fk;

ALTER TABLE IF EXISTS result
  DROP CONSTRAINT IF EXISTS result_event_fk;

ALTER TABLE IF EXISTS result
  DROP CONSTRAINT IF EXISTS result_sportsman_fk;

DROP TABLE IF EXISTS event;

DROP TABLE IF EXISTS event_sportsman;

DROP TABLE IF EXISTS invitation;

DROP TABLE IF EXISTS result;

DROP TABLE IF EXISTS sport;

DROP TABLE IF EXISTS sportsman;

CREATE TABLE event (
  id SERIAL,
  address VARCHAR(255) NOT NULL,
  capacity integer NOT NULL,
  city VARCHAR(255) NOT NULL,
  event_date DATE NOT NULL,
  description VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  admin BIGINT NOT NULL,
  sport BIGINT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE event_sportsman (
  events_id SERIAL,
  participants_id BIGINT NOT NULL,
  PRIMARY KEY (events_id, participants_id)
);

CREATE TABLE invitation (
  id SERIAL,
  state integer NOT NULL,
  event BIGINT NOT NULL,
  invitee BIGINT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE result (
  id SERIAL,
  NOTe VARCHAR(255),
  performance double PRECISION NOT NULL,
  performance_unit integer NOT NULL,
  position integer NOT NULL,
  event BIGINT NOT NULL,
  sportsman BIGINT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE sport (
  id SERIAL,
  description VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE sportsman (
  id SERIAL,
  birth_DATE TIMESTAMP NOT NULL,
  email VARCHAR(255) NOT NULL,
  is_manager boolean NOT NULL,
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  surname VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);


ALTER TABLE sport
  ADD CONSTRAINT  sport_name_unique UNIQUE (name);

ALTER TABLE sportsman
  ADD CONSTRAINT  sportsman_email_unique UNIQUE (email);

ALTER TABLE event
  ADD CONSTRAINT  event_admin_fk
  FOREIGN KEY  (admin)
  REFERENCES  sportsman;

ALTER TABLE event
  ADD CONSTRAINT  event_sport_fk
  FOREIGN KEY  (sport)
  REFERENCES  sport;

ALTER TABLE event_sportsman
  ADD CONSTRAINT  event_sportman_fk
  FOREIGN KEY  (participants_id)
  REFERENCES  sportsman;

ALTER TABLE event_sportsman
  ADD CONSTRAINT  event_sportman_event_fk
  FOREIGN KEY  (events_id)
  REFERENCES  event;

ALTER TABLE invitation
  ADD CONSTRAINT  invitation_event_fk
  FOREIGN KEY  (event)
  REFERENCES  event;

ALTER TABLE invitation
  ADD CONSTRAINT  invitation_sportsman_fk
  FOREIGN KEY  (invitee)
  REFERENCES  sportsman;

ALTER TABLE result
  ADD CONSTRAINT  result_event_fk
  FOREIGN KEY  (event)
  REFERENCES  event;

ALTER TABLE result
  ADD CONSTRAINT  result_sportsman_fk
  FOREIGN KEY  (sportsman)
  REFERENCES  sportsman;