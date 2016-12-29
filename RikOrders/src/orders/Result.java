package orders;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Collator;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Result
 */
@WebServlet("/result")
public class Result extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String[] SUPPLIERS = {"הכל", "טי אנד איי", "קל גב", 
			"שרש", "אפוד", "גו נייצר", "טופגאן", 
			"ולוסיטי", "קולמן", "כללי"};

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();


		Calendar cal = Calendar.getInstance();
		if(session.getAttribute("user")==null || !session.getAttribute("user").equals("1501"))
			resp.sendRedirect(Constants.SERVER_URL + Constants.SLASH + Constants.LOGIN_PG);

		else{

			DBController dbc = new DBController("satyr", "11223344");
			ArrayList<Row> rows;

			int bySupp = 0;
			String byStatus = "";
			if(req.getParameterMap().containsKey("byStatus"))
				byStatus = req.getParameter("byStatus");
			else 
				byStatus = "all";

			if(req.getParameterMap().containsKey("phone") && !(req.getParameter("phone")==null) && !req.getParameter("phone").equals("")){
				String phone = req.getParameter("phone");
				dbc.selectByPhone(phone);

				rows = dbc.getList();
				req.setCharacterEncoding("UTF-8");

				req.setAttribute("rows", rows);
				req.getRequestDispatcher("result.jsp").forward(req, resp);
				dbc.close();

				return;

			}
			else if(byStatus.equals("closed")){
				try {
					dbc.select(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			else{

				if(req.getParameterMap().containsKey("bySupp")){
					if(!req.getParameter("bySupp").equals(""))
						bySupp = Integer.parseInt(req.getParameter("bySupp"));
				}


				String byTime = "";

				if(req.getParameterMap().containsKey("byTime"))
					byTime = req.getParameter("byTime");
				else
					byTime = "all";

				byTime.trim();


				if(byTime.equals("week2"))
					cal.add(Calendar.DAY_OF_MONTH, -15);

				else if(byTime.equals("week"))
					cal.add(Calendar.DAY_OF_MONTH, -8);

				else if(byTime.equals("month"))
					cal.add(Calendar.MONTH, -1);
				else if(byTime.equals("all"))
					cal.add(Calendar.YEAR, -1);

				try {
					dbc.select(false);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			rows = dbc.getList();
			Date date = cal.getTime();

			if(byStatus.equals("closed")){
				req.setCharacterEncoding("UTF-8");

				req.setAttribute("rows", rows);
				req.getRequestDispatcher("result.jsp").forward(req, resp);
				dbc.close();
				return;
			}

			int bySupplier = bySupp;
			List<Row> result = rows.stream().filter((x) -> x.getOrderDate().after(date)).collect(Collectors.toList());
			if(bySupplier != 0)
				result = result.stream().filter(x -> x.getSupNum() == bySupplier ).collect(Collectors.toList());

			Collator col = Collator.getInstance(new Locale("HE"));

			if(byStatus.equals("customer"))
				result = result.stream().filter(x -> col.compare(x.getStatus(), "לקוח")==0 ).collect(Collectors.toList());
			if(byStatus.equals("ordered"))
				result = result.stream().filter(x -> col.compare(x.getStatus(), "הוזמן מספק")==0 ).collect(Collectors.toList());
			if(byStatus.equals("arrived"))
				result = result.stream().filter(x -> col.compare(x.getStatus(), "הגיע לחנות")==0 ).collect(Collectors.toList());
			if(byStatus.equals("notified"))
				result = result.stream().filter(x -> col.compare(x.getStatus(), "נאמר ללקוח")==0 ).collect(Collectors.toList());


			LocalDate justDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			req.setAttribute("time", justDate);
			req.setAttribute("status", byStatus);
			req.setAttribute("supp", SUPPLIERS[bySupplier]);

			req.setCharacterEncoding("UTF-8");

			req.setAttribute("rows", result);
			req.getRequestDispatcher("result.jsp").forward(req, resp);
			dbc.close();

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DBController dbc = new DBController("satyr", "11223344");
		req.setCharacterEncoding("UTF-8");
		String updateBtn = req.getParameter("updateBtn");
		String changeBtn = req.getParameter("changeBtn");



		if(changeBtn != null){

			req.setCharacterEncoding("UTF-8");

			String[] param =  req.getParameterValues("toChange");

			if(param == null )
				req.setAttribute("noneSelected", "לא התבצעה בחירה" );
			else{
				int[] toChange = new int[param.length];



				for(int i=0; i<param.length; i++){
					toChange[i] = Integer.parseInt(param[i]);
				}

				for(int i=0; i<toChange.length; i++){
					dbc.changeStatus(toChange[i], req.getParameter("newStatus"));
				}
			}
			doGet(req, resp);
			dbc.close();

		}

		else if(updateBtn != null){
			req.setCharacterEncoding("UTF-8");

			String orderNum = req.getParameter("orderNumber");
			String newDesc = req.getParameter("newDescription");

			dbc.updateDesc(orderNum, newDesc);

			doGet(req, resp);
			dbc.close();
		}


	}

}
