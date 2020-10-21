package ui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BeanShortestPath;
import util.Graph;
import util.Reader;

/**
 * Servlet implementation class InputStations
 */
@WebServlet("/InputStations")
public class InputStations extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public InputStations() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");	//设置请求的字符集
		response.setContentType("text/html;charset=utf-8");		//设置文本类型
		
		
		Graph graph = Reader.readSubwayInfo("E:\\EclipsForJava\\workspace\\2020-2021-1\\SoftwareEngineering\\Lab1\\SubwayShortestPath-Web\\地铁线路信息.txt");
		
		String startStationName = request.getParameter("startStationName");
		String endStationName = request.getParameter("endStationName");
		BeanShortestPath shortestPath = graph.getShortestPath(startStationName, endStationName);
		
		request.setAttribute("result", shortestPath.toString());
		request.getRequestDispatcher("OutputPath.jsp").forward(request,response);

	}

}
