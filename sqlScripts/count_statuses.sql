CREATE OR REPLACE FUNCTION count_statuses()
  RETURNS SETOF int AS $BODY$

	DECLARE 
	cCustomer int;
	cOrdered int;
	cArrived int;
	cNotified int;
	resultArr int[4]; 
		
	BEGIN

	SELECT COUNT(temp1.order_num) INTO cCustomer
	FROM(SELECT DISTINCT ON(curr_status) order_num
	     FROM status
	     WHERE curr_status = 'לקוח'
	     ORDER BY curr_status, s_date DESC
	)AS temp1 ;

 
	SELECT COUNT(temp2.order_num) INTO cOrdered
	FROM(SELECT DISTINCT ON(curr_status) order_num
	     FROM status
	     WHERE curr_status = 'הוזמן מספק'
	     ORDER BY curr_status, s_date DESC	
	)AS temp2;
 
	SELECT COUNT(temp3.order_num) INTO cArrived
	FROM(SELECT DISTINCT ON(curr_status) order_num 
	     FROM status
	     WHERE curr_status = 'הגיע לחנות'
	     ORDER BY curr_status, s_date DESC	
	)AS temp3;
	
 
	SELECT COUNT(temp4.order_num) INTO cNotified
	FROM(SELECT DISTINCT ON(curr_status) order_num 
	     FROM status
	     WHERE curr_status = 'נאמר ללקוח'
	     ORDER BY curr_status, s_date DESC
	)AS temp4;
	

	resultArr[0] = cCustomer;
	resultArr[1] = cOrdered;
	resultArr[2] = cArrived;
	resultArr[3] = cNotified;

	RETURN NEXT cCustomer;
	RETURN NEXT cOrdered;
	RETURN NEXT cArrived;
	RETURN NEXT cNotified;

	END; $BODY$
  LANGUAGE plpgsql VOLATILE
