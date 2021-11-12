package com.javacoding.marked.controllers;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javacoding.marked.constants.KidFriendlyStatus;
import com.javacoding.marked.entities.Book;
import com.javacoding.marked.entities.Bookmark;
import com.javacoding.marked.entities.User;
import com.javacoding.marked.services.BookmarkService;
import com.javacoding.marked.services.UserService;


@WebServlet( urlPatterns = {"/bookmark" , "/bookmark/save" , "/bookmark/mybooks" } )
public class BookmarkController extends HttpServlet {
	
	/*  tomcat container creates singleton instance of the servlet
	private static BookmarkController bkmCntInstance = new BookmarkController();	
	private BookmarkController () {
		
	}	
	public static BookmarkController getBkmCntInstance() {
		return bkmCntInstance;
	}
	*/
	
	public BookmarkController() {
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		RequestDispatcher dispatcher = null;
		System.out.println("Servlet path: " + request.getServletPath());
		
		if( request.getSession().getAttribute("userId") != null) {
			
			long userId = (long) request.getSession().getAttribute("userId");
			
			if (request.getServletPath().contains("save")){
				//save
				dispatcher = request.getRequestDispatcher("/mybooks.jsp");
				
				String bid = request.getParameter("bid");
				
				User user = UserService.getInstance().getUser(userId);
				Bookmark bookmark =  BookmarkService.getBkmInstance().getBook(Long.parseLong(bid));
				
				BookmarkService.getBkmInstance().saveUserBookmark(user, bookmark);
				
				
				Collection<Bookmark> bookList = BookmarkService.getBkmInstance().getBooks(true, userId);	
				request.setAttribute("books", bookList);
				
			} else if (request.getServletPath().contains("mybooks")) {			
				dispatcher = request.getRequestDispatcher("/mybooks.jsp");
				
				Collection<Bookmark> bookList = BookmarkService.getBkmInstance().getBooks(true, userId);			
				request.setAttribute("books", bookList);
				
			} else {			
				dispatcher = request.getRequestDispatcher("/browse.jsp");
				
				Collection<Bookmark> bookList = BookmarkService.getBkmInstance().getBooks(false, userId);			
				request.setAttribute("books", bookList);
			}
			
		} else {			
			dispatcher = request.getRequestDispatcher("/login.jsp");
		}
		
		// request.getRequestDispatcher("/browse.jsp").forward(request, response);			
		dispatcher.forward(request, response);
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	

	public void saveUserBookmark(User user, Bookmark bookmark) {		
		BookmarkService.getBkmInstance().saveUserBookmark(user, bookmark);		 
	}
	
	

	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {		
		BookmarkService.getBkmInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);		
	}
	
	

	public void share(User user, Bookmark bookmark) {		
		BookmarkService.getBkmInstance().share(user, bookmark);		
	}
	

} 
