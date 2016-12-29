SELECT by_phone('0543166935');

SELECT DISTINCT ON(rik_order.order_num) * 
FROM rik_order NATURAL JOIN customer NATURAL JOIN status NATURAL JOIN supplier
ORDER BY rik_order.order_num, status.s_date DESC, status.curr_status DESC;