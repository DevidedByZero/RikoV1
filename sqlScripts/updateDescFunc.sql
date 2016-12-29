CREATE OR REPLACE FUNCTION update_description(sn integer, newDecription text) RETURNS void AS 
$BODY$
BEGIN
	UPDATE rik_order
	SET description = newDecription
	WHERE order_num = sn;
END;
$BODY$
  LANGUAGE plpgsql