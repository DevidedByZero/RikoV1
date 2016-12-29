CREATE OR REPLACE FUNCTION add_order(nf_name varchar, nl_name varchar, nphone varchar,
	ndescription text, ns_id integer, nbranch integer) RETURNS VOID as $$ 


	DECLARE
	num integer;
	id integer;
	
	BEGIN

	IF EXISTS
		(
		SELECT *
		FROM supplier
		WHERE s_id = ns_id
		)
	THEN 
		id = ns_id;
	ELSE
		id = 999;
	END IF;
	

	IF EXISTS
		(
		SELECT phone
		FROM customer
		WHERE customer.phone = nphone
		)

	THEN
	ELSE
	INSERT INTO customer (f_name, l_name, phone) VALUES (nf_name, nl_name, nphone);
	END IF;

 
	INSERT INTO rik_order (description, phone, s_id, branch) VALUES (ndescription, nphone, ns_id, nbranch) 
		RETURNING order_num INTO num;

	INSERT INTO status (order_num, curr_status) VALUES (num, 'לקוח');

	END; $$ language plpgsql;