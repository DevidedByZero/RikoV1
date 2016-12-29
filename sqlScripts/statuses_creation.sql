DROP TYPE statuses CASCADE;
CREATE TYPE statuses AS ENUM ('לקוח', 'הוזמן מספק', 'הגיע לחנות', 'נאמר ללקוח', 'נסגר');

CREATE TABLE status
(
  order_num integer NOT NULL,
  curr_status statuses,
  s_date date NOT NULL DEFAULT ('now'::text)::date,
  CONSTRAINT status_pkey PRIMARY KEY (order_num),
  CONSTRAINT status_order_num_fkey FOREIGN KEY (order_num)
      REFERENCES rik_order (order_num) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
