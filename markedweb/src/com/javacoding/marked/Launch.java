package com.javacoding.marked;

import java.util.List;

import com.javacoding.marked.bgtasks.WebpageDownloaderTask;
import com.javacoding.marked.entities.Bookmark;
import com.javacoding.marked.entities.User;
import com.javacoding.marked.services.BookmarkService;
import com.javacoding.marked.services.UserService;
import com.javacoding.marked.util.StringUtil;

public class Launch {
	
	//private static User[] users;
	private static List<User> users;
	
	//private static Bookmark[][]
	private static List<List<Bookmark>> bookmarks;
	
	
	private static void loadData() {
		System.out.println("1. Loading data ... ");
		DataStore.loadData();
		
		users = UserService.getInstance().getUsers();
		
		bookmarks = BookmarkService.getBkmInstance().getBookmarks();
		
		
		System.out.println("Printing data ... ");
		
		printUserData();
		
		printBookmarkData();
		
	}
	

	private static void printBookmarkData() {
		for(List<Bookmark> bookmarklist : bookmarks) {
			for(Bookmark bookmark : bookmarklist) {
				System.out.println(bookmark);
			}
		}
		
	}


	private static void printUserData() {
		for(User user : users) {
			System.out.println(user);
		}
		
	}
	
	
	private static void startV2() {     //renamed from startBookmarking
		
		//System.out.println("\n2. Bookmarking ... ");
		
		for(User user : users) { 
			
			//View.bookmark(user, bookmarks);
			
			View.browse(user, bookmarks);
		}
				 
	}
	


	public static void main(String[] args) {

		//loadData();
		//startV2();
		
			//BackgroundTasks
		//runDownloaderTask();
		
		//test();
		
		//String pass = StringUtil.encodePassword("test");
		//System.out.println(pass);
		
	}
	
	
	private static void runDownloaderTask() {
		WebpageDownloaderTask task = new WebpageDownloaderTask(true);		
		(new Thread(task)).start();
	}
	
	
	private static void test() {
		
		User user = UserService.getInstance().getUser(5);
		
		//Bookmark bookmark = BookmarkService.getBkmInstance().getBook(4);
		
		//BookmarkService.getBkmInstance().saveUserBookmark(user, bookmark);
		
		System.out.println(user.toString());
		
	}
	
}










