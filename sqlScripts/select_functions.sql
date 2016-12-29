CREATE OR REPLACE FUNCTION by_phone(search_phone varchar) RETURNS 
		TABLE (o_date date, description text, contact_name varchar, 
		f_name varchar, l_name varchar,
		phone varchar, s_name varchar, 
		branch integer, email varchar, contact_phone varchar)  as $$ 
		
	BEGIN

	RETURN QUERY
	select rik_order.o_date, rik_order.description, supplier.contact_name, 
		customer.f_name, customer.l_name, customer.phone,
		supplier.s_name, rik_order.branch, customer.email, supplier.contact_phone
	from rik_order NATURAL JOIN customer NATURAL JOIN supplier
	where customer.phone = search_phone
	ORDER BY o_date;

	

	END; $$ language plpgsql;