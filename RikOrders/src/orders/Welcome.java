package orders;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/welcome")
public class Welcome extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public Welcome() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 
		HttpSession session = req.getSession();
		if(session.getAttribute("user")==null || !session.getAttribute("user").equals("1501"))
			resp.sendRedirect(Constants.SERVER_URL + Constants.SLASH + Constants.LOGIN_PG);
		
		else{
			DBController dbc = new DBController("satyr", "11223344");
			
			int[] count = dbc.countByStatuses();
			
			
			req.setAttribute("custCount", count[0]);
			req.setAttribute("orderedCount", count[1]);
			req.setAttribute("arrivedCount", count[2]);
			req.setAttribute("notifiedCount", count[3]);
			req.getRequestDispatcher("form.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		
		DBController dbc = new DBController("satyr", "11223344");
		List<Row> rows = dbc.getList();

		req.setCharacterEncoding("UTF-8");
		if(req.getParameter("serach_phone") != null){

			resp.sendRedirect(Constants.SERVER_URL + Constants.SLASH + Constants.RESULT_PG + "?phone="+req.getParameter("phone"));

		}
		
		if(req.getParameter("searchSupp") != null){

			resp.sendRedirect(Constants.SERVER_URL + Constants.SLASH + Constants.RESULT_PG + "?bySupp="+req.getParameter("supplier"));

		}
		
		
		if(req.getParameter("insert_button") != null){
			req.setCharacterEncoding("UTF-8");

			Row row = new Row();

			row.setFirstName(req.getParameter("firstName"));
			row.setLastName(req.getParameter("lastname"));
			row.setPhone(req.getParameter("phone"));
			row.setDescription(req.getParameter("description"));
			
			int supplier = 999;
			try{
			supplier = Integer.parseInt(req.getParameter("supplier"));
			} catch(NumberFormatException e){
				
			}
			row.setSupNum(supplier);


			boolean success = dbc.insert(row);
			
			req.setAttribute("message", "success: " + success);

			//TODO need this part???
			rows = dbc.getList();
			req.setAttribute("rows", rows);
			req.getRequestDispatcher("form.jsp").forward(req, resp);


		}
		dbc.close();
	}

}
