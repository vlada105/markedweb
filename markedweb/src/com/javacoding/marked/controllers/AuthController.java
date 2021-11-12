package com.javacoding.marked.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javacoding.marked.services.UserService;

/**
 * Servlet implementation class AuthController
 */
@WebServlet(urlPatterns = {"/auth" , "/auth/logout"})
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { //invoked even for method="POST" since forwarded from doPost
		System.out.println("Servlet path: " + request.getServletPath());
		

		if ( !request.getServletPath().contains("logout") & request.getParameter("email") != null ) {
			
			
			
			String email = request.getParameter("email");
			
			String password = request.getParameter("password");
			
			long userId = UserService.getInstance().authenticate(email, password);
			
			if (userId != -1) {
				
				HttpSession session = request.getSession();   //session-id
				
				session.setAttribute("userId", userId);
				
				request.getRequestDispatcher("bookmark/mybooks").forward(request, response);  // forwarded to servlet , no need for starting /
				
			} else {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
						
		} else {
			request.getSession().invalidate();
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
