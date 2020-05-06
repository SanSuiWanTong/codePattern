//java servlet

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

class DJViewServlet extends HttpServlet{
	private static final long serialVersionUID = 2L;
	
	public void init() throws ServletException{
		BeatModel beatModel = new BeatModel();
		beatModel.initialize();
		getServletContext().setAttribute("beatModel",beatModel);
	}
}
