package user;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import api.userApi;

/**
 * Servlet implementation class ResetPwdServlet
 */
@WebServlet("/ResetPwdServlet")
public class ResetPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPwdServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		// to reset pwd page
		request.getRequestDispatcher("reset-password.html").forward(request, response);
		// add user token to cookie
		String token = request.getParameter("token");
		Cookie tokenCookie = new Cookie("token", token);
		response.addCookie(tokenCookie);
		tokenCookie.setMaxAge(60*60*24); //Store cookie for 1 year		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		// read json
		BufferedReader reader = request.getReader();
		String json = reader.readLine();
		reader.close();
		System.out.println(json);
		userApi user = new userApi();
		String token = user.getValueFromCookie(request.getCookies(), "token");
		System.out.println(token);
		
		try {
			boolean result = user.resetPwdJsonAnalyzing(json, token);
			System.out.println(result);
			if (result == true) response.setStatus(HttpServletResponse.SC_OK); 
			else response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}