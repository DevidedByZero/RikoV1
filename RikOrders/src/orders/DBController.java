package orders;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBController {

	private String name, pass;
	Connection connection;
	ArrayList<Row> rows;

	public DBController(String name, String pass){

		this.name = name;
		this.pass = pass;
		rows = new ArrayList<Row>();

		init();

	}

	private void init() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://pgrik.cqsqruzrlud3.us-west-2.rds.amazonaws.com:5432/rikoshet", name, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean updateDesc(String orderNumStr, String desc ){

		boolean success = false;

		CallableStatement cStmt = null;

		if(orderNumStr == null || orderNumStr.equals(""))
			return false;

		int orderNum = Integer.parseInt(orderNumStr); 


		try {
			cStmt = connection.prepareCall("{call update_description (?,?)}");

			cStmt.setInt(1, orderNum);
			cStmt.setString(2, desc);

			success = cStmt.execute();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return success;

	}

	public boolean changeStatus(int orderNum, String newStatus){
		CallableStatement cStmt = null;
		boolean success = false;

		try {
			cStmt = connection.prepareCall("{call add_status (?,?)}");

			cStmt.setInt(1, orderNum);
			cStmt.setString(2, newStatus);

			success = cStmt.execute();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return success;

	}

	public boolean insert(Row row){
		CallableStatement cStmt = null;
		boolean success = false;

		try {
			cStmt = connection.prepareCall("{call add_order (?,?,?,?,?,?)}");

			cStmt.setString(1, row.getFirstName());
			cStmt.setString(2, row.getLastName());
			cStmt.setString(3, row.getPhone());
			cStmt.setString(4, row.getDescription());
			cStmt.setInt(5, row.getSupNum());
			cStmt.setInt(6, row.getBranch());

			success = cStmt.execute();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			cStmt.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;

	}

	public boolean selectByPhone(String phone){
		CallableStatement cStmt = null;
		boolean success = false;
		ResultSet rs = null;

		try {
			cStmt = connection.prepareCall("{call by_phone (?)}");

			cStmt.setString(1, phone);

			success = cStmt.execute();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(success){
			try {
				rs = cStmt.getResultSet();

				selectParse(rs);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		try {
			cStmt.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return success;
	}

	public void select(boolean closed) throws SQLException{
		Statement stmt = null;

		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ResultSet rs = null;
		if(!closed){
			try {
				rs = stmt.executeQuery( "SELECT * FROM (SELECT DISTINCT ON(rik_order.order_num) * FROM rik_order NATURAL JOIN customer NATURAL JOIN supplier NATURAL JOIN status ORDER BY rik_order.order_num, status.s_date DESC, status.curr_status DESC) AS a WHERE a.curr_status != 'נסגר' ;");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				rs = stmt.executeQuery( "SELECT * FROM (SELECT DISTINCT ON(rik_order.order_num) * FROM rik_order NATURAL JOIN customer NATURAL JOIN supplier NATURAL JOIN status ORDER BY rik_order.order_num, status.s_date DESC, status.curr_status DESC) AS a WHERE a.curr_status = 'נסגר' ;");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		selectParse(rs);

		rs.close();
		stmt.close();
		connection.close();

	}

	public ArrayList<Row> getList(){
		return rows;
	}

	private void selectParse(ResultSet rs){

		try{
			while ( rs.next() ) {
				Row row = new Row();
				row.setContactName(rs.getString("contact_name"));
				row.setContactPhone(rs.getString("contact_phone"));
				row.setDescription(rs.getString("description"));
				row.setFirstName(rs.getString("f_name"));
				row.setLastName(rs.getString("l_name"));
				row.setOrderDate(rs.getDate("o_date"));
				row.setPhone(rs.getString("phone"));
				row.setSupplierName(rs.getString("s_name"));
				row.setBranch(rs.getInt("branch"));
				row.setStatus(rs.getString("curr_status"));
				row.setStatusDate(rs.getDate("s_date"));
				row.setOrderNum(rs.getInt("order_num"));
				row.setSupNum(rs.getInt("s_id"));

				rows.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int[] countByStatuses() {
		try {
			select(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int[] result = new int[4];

		for(Row row: rows){
			if(row.getStatus().equals("לקוח")){
				result[0]++;
			}
			if(row.getStatus().equals("הוזמן מספק")){
				result[1]++;
			}
			if(row.getStatus().equals("הגיע לחנות")){
				result[2]++;
			}
			if(row.getStatus().equals("נאמר ללקוח")){
				result[3]++;
			}

		}


		return result;
	}
}
