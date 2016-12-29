drop function by_phone(character varying);

CREATE OR REPLACE FUNCTION by_phone(IN search_phone character varying)
  RETURNS TABLE(curr_status statuses, order_num integer, o_date date, description text, contact_name character varying, 
	f_name character varying, l_name character varying, phone character varying, 
	s_name character varying, branch integer, email character varying, 
	contact_phone character varying, s_date date, s_id integer) AS
$BODY$ 
		
	BEGIN

	RETURN QUERY
	select DISTINCT ON(rik_order.order_num) status.curr_status, rik_order.order_num, rik_order.o_date, rik_order.description, supplier.contact_name, 
		customer.f_name, customer.l_name, customer.phone,
		supplier.s_name, rik_order.branch, customer.email, supplier.contact_phone,
		status.s_date, supplier.s_id
	FROM  rik_order NATURAL JOIN customer NATURAL JOIN supplier NATURAL JOIN status
	where customer.phone = search_phone
	ORDER BY rik_order.order_num, status.s_date DESC, status.curr_status DESC;

	

	END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION by_phone(character varying)
  OWNER TO satyr;