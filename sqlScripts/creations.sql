
CREATE TABLE customer
(
  f_name character varying(25) NOT NULL,
  l_name character varying(25),
  phone character varying(10) NOT NULL,
  email character varying,
  CONSTRAINT customer_pkey PRIMARY KEY (phone)
);


CREATE TABLE supplier
(
  s_id serial NOT NULL,
  s_name character varying(45) NOT NULL,
  contact_name character varying(25) NOT NULL,
  contact_phone character varying(25) NOT NULL,
  CONSTRAINT supplier_pkey PRIMARY KEY (s_id)
);

CREATE TABLE rik_order
(
  order_num serial NOT NULL,
  o_date date NOT NULL DEFAULT ('now'::text)::date,
  description text,
  phone character varying(10),
  s_id serial NOT NULL,
  branch integer NOT NULL,
  CONSTRAINT rik_order_pkey PRIMARY KEY (order_num),
  CONSTRAINT rik_order_phone_fkey FOREIGN KEY (phone)
      REFERENCES customer (phone) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT rik_order_s_id_fkey FOREIGN KEY (s_id)
      REFERENCES supplier (s_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



