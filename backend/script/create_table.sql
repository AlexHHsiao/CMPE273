SET SCHEMA 'wlb';
SET default_tablespace = 'pg_default';

CREATE TABLE IF NOT EXISTS wlb.user (
  id UUID PRIMARY KEY,
	username VARCHAR ( 50 ) UNIQUE NOT NULL,
	password varchar NOT NULL,
	email VARCHAR ( 255 ) NOT NULL,
  role  VARCHAR(128) NOT NULL,
  is_active boolean NOT NULL,
  legal_name varchar(50) NOT NULL,
  phone varchar(30) NOT NULL,
  birthday varchar(50) NOT NULL,
  ssn varchar(30) NOT NULL,
  credit_score Integer NOT NULL,
  created TIMESTAMP WITH TIME zone NOT NULL DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
  updated TIMESTAMP WITH TIME zone DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC')
);

CREATE TABLE IF NOT EXISTS wlb.cookie (
  cookie VARCHAR ( 255 ) PRIMARY KEY,
	user_id UUID NOT NULL,
  expire_duration Integer,
  role  VARCHAR(128),
  created TIMESTAMP WITH TIME zone NOT NULL DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC')
);
CREATE INDEX IF NOT EXISTS idx_cookie ON wlb.cookie (user_id);

--CREATE TABLE IF NOT EXISTS wlb.home (
--  home_id UUID PRIMARY KEY,
--  owner_id UUID NOT NULL,
--  home_type VARCHAR(128) NOT NULL,
--  zip VARCHAR(20) NOT NULL,
--  price DECIMAL(18,2) NOT NULL,
--  street_number VARCHAR(10) NOT NULL,
--  street1 VARCHAR(50) NOT NULL,
--  street2 VARCHAR(50),
--  city VARCHAR(50) NOT NULL,
--  state VARCHAR(50) NOT NULL,
--  num_bedroom Integer NOT NULL,
--  num_bathroom Integer NOT NULL,
--  sqft DECIMAL(10,2) NOT NULL,
--  has_parking boolean NOT NULL,
--  offer_type VARCHAR(10) NOT NULL,
--  created TIMESTAMP WITH TIME zone NOT NULL DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
--  updated TIMESTAMP WITH TIME zone DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC')
--);
--CREATE INDEX IF NOT EXISTS idx_home_owner ON wlb.home (owner_id);

CREATE TABLE IF NOT EXISTS wlb.home (
  home_id UUID PRIMARY KEY,
  owner_id UUID NOT NULL,
  home_type VARCHAR(128) NOT NULL,
  available boolean NOT NULL,
  zip VARCHAR(20) NOT NULL,
  price DECIMAL(18,2) NOT NULL,
  street_number VARCHAR(10) NOT NULL,
  street1 VARCHAR(50) NOT NULL,
  street2 VARCHAR(50),
  city VARCHAR(50) NOT NULL,
  state VARCHAR(50) NOT NULL,
  num_bedroom Integer NOT NULL,
  num_bathroom Integer NOT NULL,
  sqft DECIMAL(10,2) NOT NULL,
  has_parking boolean NOT NULL,
  offer_type VARCHAR(10) NOT NULL,
  offer_list VARCHAR,
  description varchar,
  open_hour varchar,
  image_url_list json,
  created TIMESTAMP WITH TIME zone NOT NULL DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
  updated TIMESTAMP WITH TIME zone DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC')
);

CREATE INDEX IF NOT EXISTS idx_home_owner ON wlb.home (owner_id);

CREATE TABLE IF NOT EXISTS wlb.search (
    user_id UUID PRIMARY KEY,
    search_list json,
    home_list json
);

