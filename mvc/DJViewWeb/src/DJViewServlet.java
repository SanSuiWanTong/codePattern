import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;

import DJView.BeatModel;
/**
 * Servlet implementation class DJViewServlet
 */
//not need set in web.xml use @WebServlet
@WebServlet("/DJViewServlet")
public class DJViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DJViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);           //must add,or getServletContext() throws ServletException
		BeatModel beatModel = new BeatModel();
		beatModel.initialize();
		getServletContext().setAttribute("beatModel", beatModel);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("------------ receive get");
		BeatModel viewModel = (BeatModel)getServletContext().getAttribute("beatModel");

		String bpm = request.getParameter("bpm");
		bpm = viewModel.getBPM() + "";
		System.out.println("bmp:" + bpm);
		
		request.setAttribute("beatModel", viewModel);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/djview.jsp");
		dispatcher.forward(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("------------ receive post");
		
		BeatModel viewModel = (BeatModel)getServletContext().getAttribute("beatModel");

		String on = request.getParameter("on");
		if(on!=null)
			viewModel.on();
		
		String off = request.getParameter("off");
		if(off!=null)
			viewModel.off();
				
		RequestDispatcher dispatcher = request.getRequestDispatcher("/djview.jsp");
		dispatcher.forward(request, response);	
	}

}
