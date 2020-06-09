package Controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Help")
public class Help extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public Help() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("help")!=null) {
			RequestDispatcher rq = request.getRequestDispatcher("/WEB-INF/View/help.jsp");
			rq.forward(request, response);
		}else if(request.getParameter("contacts")!=null) {
			RequestDispatcher rq = request.getRequestDispatcher("/WEB-INF/View/contacts.jsp");
			rq.forward(request, response);
		}else if(request.getParameter("faq")!=null) {
			RequestDispatcher rq = request.getRequestDispatcher("/WEB-INF/View/faq.jsp");
			rq.forward(request, response);
		}else {
			RequestDispatcher rq = request.getRequestDispatcher("./");
			rq.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
