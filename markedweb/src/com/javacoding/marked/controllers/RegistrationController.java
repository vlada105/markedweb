package com.javacoding.marked.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javacoding.marked.services.UserService;
import com.javacoding.marked.util.StringUtil;

/**
 * Servlet implementation class RegistrationController
 */
@WebServlet("/register")
public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.getRequestDispatcher("/register.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (!request.getParameter("firstName").isEmpty() & !request.getParameter("lastName").isEmpty()   //  for now but otherwise javascript FE check
				
			 &	!request.getParameter("email").isEmpty() & !request.getParameter("password").isEmpty()) {
					
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");					
			String password = request.getParameter("password");	
			
			if( UserService.getInstance().userCheck(email) == -1) {
				
			String encPass = StringUtil.encodePassword(password);
			
			UserService.getInstance().registerUser(firstName, lastName, email, encPass);
			
			request.getRequestDispatcher("/login.jsp").forward(request, response);			
			
			} else {
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
		
			
		} else {
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
				
	}
}

	






















