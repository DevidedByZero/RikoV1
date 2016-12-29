package orders;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("loginBtn") != null){
			
			if(req.getParameterMap().containsKey("userName") && req.getParameterMap().containsKey("password")
					&& req.getParameter("userName").equals("1501") && req.getParameter("password").equals("1501")){
				HttpSession session = req.getSession();
				
				session.setAttribute("user", "1501");
				
				resp.sendRedirect(Constants.SERVER_URL + Constants.SLASH + Constants.WELCOME_PG);
			}
			
			else{
				req.setAttribute("error", "Login failed! Try again");
				doGet(req, resp);
			}
			
		}
	}

}
