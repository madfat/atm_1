CREATE TABLE account
(
  account_id character varying(255) NOT NULL,
  account_no character varying(6),
  balance double precision NOT NULL,
  name character varying(255),
  pin character varying(6),
  status boolean,
  CONSTRAINT account_pkey PRIMARY KEY (account_id)
);

CREATE TABLE menu
(
  menu_id character varying(255) NOT NULL,
  description character varying(255),
  route character varying(255),
  screen_owner character varying(255),
  sequence integer,
  status boolean,
  CONSTRAINT menu_pkey PRIMARY KEY (menu_id)
);

CREATE TABLE transaction
(
  transaction_id character varying(255) NOT NULL,
  amount double precision,
  balance double precision,
  destination_account character varying(255),
  source_account character varying(6),
  transaction_date character varying(255),
  type character varying(255),
  CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id)
);

create table product (
  id identity not null,
  name varchar (255) not null,
  price double not null
);
