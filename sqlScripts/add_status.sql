CREATE OR REPLACE FUNCTION add_status(norder_num integer, nstatus character varying) RETURNS VOID as $$ 

	
	BEGIN

	

	INSERT INTO status (order_num, curr_status) VALUES (norder_num, nstatus::statuses);

	END; $$ language plpgsql;