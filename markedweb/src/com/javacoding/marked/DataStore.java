package com.javacoding.marked;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.javacoding.marked.constants.BookGenre;
//import com.javacoding.marked.constants.BookGenre;
import com.javacoding.marked.constants.Gender;
import com.javacoding.marked.constants.MovieGenre;
import com.javacoding.marked.constants.UserType;
//import com.javacoding.marked.constants.MovieGenre;
//import com.javacoding.marked.constants.UserType;
import com.javacoding.marked.entities.Bookmark;
import com.javacoding.marked.entities.User;
import com.javacoding.marked.entities.UserBookmark;
import com.javacoding.marked.services.BookmarkService;
import com.javacoding.marked.services.UserService;
//import com.javacoding.marked.util.IOUtil;

public class DataStore {
	
	/*
	public static final int USER_BOOKMARK_LIMIT = 5;
	public static final int BOOKMARK_COUNT_PER_TYPE = 5;
	public static final int BOOKMARKS_TYPES_COUNT = 3;
	public static final int TOTAL_USER_COUNT = 5;
	*/	

	//private static User[] users = new User[TOTAL_USER_COUNT]; 
	private static List<User> users = new ArrayList<>();
	
	//public static User[] getUsers
	public static List<User> getUsers() {
		return users;
	}
	
	

	//private static Bookmark[][] bookmarks = new Bookmark[BOOKMARKS_TYPES_COUNT][BOOKMARK_COUNT_PER_TYPE];
	private static List<List<Bookmark>> bookmarks = new ArrayList<>();
	
	//public static Bookmark[][] getBookmarks
	public static List<List<Bookmark>> getBookmarks() {
		return bookmarks;
	}


	//private static UserBookmark[] userBookmarks= new UserBookmark[TOTAL_USER_COUNT*USER_BOOKMARK_LIMIT];
	private static List<UserBookmark> userBookmarks = new ArrayList<>();	
	
	//private static int usrBkmIndex;
	
	
	 
	public static void loadData() {
		/* loadUsers();
		loadWebLinks();
		loadMovies();
		loadBooks(); */		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");     //com.mysql.cj.jdbc.Driver  => NEW   |   com.mysql.jdbc.Driver => deprecated.
			
			/* OR . . .
			
			new com.mysql.jdbc.Driver();
			
			System.setProperty("jdbc.drivers" , "com.mysql.jdbc.Driver");
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());    sql ex
			
			*/
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//try with resources ==>  connection & statement will be closed
		// connection string --   <protocol>   :    <sub-protocol>   :   <data-source details>		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/marked_project", "root", "root"); 
				Statement stmnt = conn.createStatement();) {
			loadUsers(stmnt);
			loadWebLinks(stmnt);
			loadMovies(stmnt);
			loadBooks(stmnt);			
		} catch (SQLException e) {			
			e.printStackTrace();
		}		
	}	
	
	
	private static void loadUsers(Statement stmnt) throws SQLException {
		
		String query = "Select * from User";		
		ResultSet rs = stmnt.executeQuery(query);		
		
		while(rs.next()) {
			
			long id = rs.getLong("id");
			String email = rs.getString("email");
			String password = rs.getString("password");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			int genderId = rs.getInt("gender_id");
			Gender gender = Gender.values()[genderId];
			int userTypeId = rs.getInt("user_type_id");
			UserType userType = UserType.values()[userTypeId];			

			
			Date createdDate = rs.getDate("created_date");
			System.out.println("createdDate" + createdDate);
			
			Timestamp timeStamp = rs.getTimestamp(8);
			System.out.println("timeStamp" + timeStamp);
			System.out.println("localDateTime" + timeStamp.toLocalDateTime());			
			
			
			User user =UserService.getInstance().createUser(id, email, password, firstName, lastName, gender, userType);
			users.add(user);
		}
		
	}
	
	
	public static void loadWebLinks(Statement stmnt) throws SQLException {
		
		String query = "Select * from Weblink";		
		ResultSet rs = stmnt.executeQuery(query);
		
		List<Bookmark> bookmarkList = new ArrayList<>();
		
		while(rs.next()) {
			long id = rs.getLong("id");
			String title = rs.getString("title");
			String url = rs.getString("url");
			String host = rs.getString("host");
			
			
			Date createdDate = rs.getDate("created_date");
			System.out.println("createdDate" + createdDate);
			
			Timestamp timeStamp = rs.getTimestamp(8);  //after ALTER table  column number changed to 8
			System.out.println("timeStamp" + timeStamp);
			System.out.println("localDateTime" + timeStamp.toLocalDateTime());
			
			
			Bookmark bookmark = BookmarkService.getBkmInstance().createWebLink(id, title, url, host /* , values[4] */ );
			bookmarkList.add(bookmark);
		}
		bookmarks.add(bookmarkList);
				
	}
	
	public static void loadMovies(Statement stmnt) throws SQLException {
		
		String query = "Select m.id, title, release_year, movie_genre_id, imdb_rating, created_date, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS actors, "
				+ " GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors from Movie m, Actor a, Director d, movie_actor ma, movie_director md "
				+ " where m.id = ma.movie_id and ma.actor_id = a.id  and m.id = md.movie_id and md.director_id = d.id  GROUP BY m.id";
		
		ResultSet rs = stmnt.executeQuery(query);
		
		List<Bookmark> bookmarkList = new ArrayList<>();
		
		while(rs.next()) {
			
			long id = rs.getLong("id");
			String title = rs.getString("title");
						
			String profileUrlMock = "";
			
			int releaseYear = rs.getInt("release_year");
			
			String[] cast = rs.getString("actors").split(",");
			String[] directors = rs.getString("directors").split(",");
			
			int mGenreId = rs.getInt("movie_genre_id");
			MovieGenre mGenre = MovieGenre.values()[mGenreId];
			
			double imdbRating = rs.getDouble("imdb_rating");
			
			Date createdDate = rs.getDate("created_date");
			System.out.println("createdDate" + createdDate);
			
			Timestamp timeStamp = rs.getTimestamp(6);
			System.out.println("timeStamp" + timeStamp);
			System.out.println("localDateTime" + timeStamp.toLocalDateTime());		
			
			
			Bookmark bookmark = BookmarkService.getBkmInstance().createMovie(id, title, profileUrlMock, releaseYear, cast, directors, mGenre, imdbRating /* , values[4] */);
			bookmarkList.add(bookmark);
		}
		bookmarks.add(bookmarkList);
	}
	
	
	
	private static void loadBooks(Statement stmnt) throws SQLException {
		
		String query = "Select b.id, title, publication_year, p.name, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
				+ " amazon_rating, created_date from Book b, Publisher p, Author a, Book_Author ba "
				+ " where b.publisher_id = p.id and b.id=ba.book_id and ba.author_id = a.id GROUP BY b.id" ;
		
		ResultSet rs = stmnt.executeQuery(query);
		
		List<Bookmark> bookmarkList = new ArrayList<>();
		while(rs.next()) {
			
			long id = rs.getLong("id");
			String title = rs.getString("title");
			int publicationYear = rs.getInt("publication_year");
			String publisher = rs.getString("name");
			String[] authors = rs.getString("authors").split(",");
			int genreId = rs.getInt("book_genre_id");
			BookGenre bGenre = BookGenre.values()[genreId];
			double amazonRating = rs.getDouble("amazon_rating");
			
			Date createdDate = rs.getDate("created_date");
			System.out.println("createdDate" + createdDate);
			
			Timestamp timeStamp = rs.getTimestamp(8);
			System.out.println("timeStamp" + timeStamp);
			System.out.println("localDateTime" + timeStamp.toLocalDateTime());			
			
			System.out.println("id: " + id + ", title: " + title + ", publication year: " + publicationYear + " ... ! ");			
			
			Bookmark bookmark= BookmarkService.getBkmInstance().createBook(id, title, publicationYear, publisher, authors, bGenre,  amazonRating /*, values[7] */ );
			bookmarkList.add(bookmark);
			
		}
		bookmarks.add(bookmarkList);
	}
	

	
	/* private static void loadUsers() {
		
	/*	users[0]= UserService.getInstance().createUser(1000, "user0@email.com", "test", "John", "M", Gender.MALE, UserType.USER);
		users[1]= UserService.getInstance().createUser(1001, "user1@email.com", "test", "Sam", "M", Gender.MALE, UserType.USER);
		users[2]= UserService.getInstance().createUser(1002, "user2@email.com", "test", "Anita", "M", Gender.FEMALE, UserType.EDITOR);
		users[3]= UserService.getInstance().createUser(1003, "user3@email.com", "test", "Sara", "M", Gender.FEMALE, UserType.EDITOR);
		users[4]= UserService.getInstance().createUser(1004, "user4@email.com", "test", "Dheeru", "M", Gender.MALE, UserType.CHIEF_EDITOR);
	* /	
		//String[] data = new String [TOTAL_USER_COUNT];
		
		List<String> data = new ArrayList<>();
		
		IOUtil.read(data, "User");
		//int rowNum = 0;
		for (String row : data) {
			String[] values = row.split("\t");
			
			Gender gender = Gender.MALE;
			if(values[5].equals("f")) {
				gender = Gender.FEMALE;
			} else if(values[5].equals("t")) {
				gender = Gender.TRANSGENDER;
			}
			
			//users[rowNum++]
			User user =UserService.getInstance().createUser(Long.parseLong(values[0]), values[1], values[2], values[3], values[4], gender, UserType.valueOf(values[6]));
			users.add(user);
		}
		
	} */
	
	
	/* private static void loadWebLinks() {
		
	/*	bookmarks[0][0] = BookmarkService.getBkmInstance().createWebLink(2000,	"Taming Tiger, Part 2",	"http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html",	"http://www.javaworld.com");                                                
		bookmarks[0][1] = BookmarkService.getBkmInstance().createWebLink(2001,	"How do I import a pre-existing Java project into Eclipse and get up and running?",	"http://stackoverflow.com/questions/142863/how-do-i-import-a-pre-existing-java-project-into-eclipse-and-get-up-and-running",	"http://www.stackoverflow.com");
		bookmarks[0][2] = BookmarkService.getBkmInstance().createWebLink(2002,	"Interface vs Abstract Class",	"http://mindprod.com/jgloss/interfacevsabstract.html",	"http://mindprod.com");
		bookmarks[0][3] = BookmarkService.getBkmInstance().createWebLink(2003,	"NIO tutorial by Greg Travis",	"http://cs.brown.edu/courses/cs161/papers/j-nio-ltr.pdf",	"http://cs.brown.edu");
		bookmarks[0][4] = BookmarkService.getBkmInstance().createWebLink(2000,	"Virtual Hosting and Tomcat",	"http://tomcat.apache.org/tomcat-6.0-doc/virtual-hosting-howto.html",	"http://tomcat.apache.org");
	* /
		//String[] data = new String [BOOKMARK_COUNT_PER_TYPE];
		
		List<String> data = new ArrayList<>();
				
		IOUtil.read(data, "WebLink");
		//int colNum = 0;
		
		List<Bookmark> bookmarkList = new ArrayList<>();
		
		for (String row : data) {
			String[] values = row.split("\t");		
			
			//bookmarks[0][colNum++]
			Bookmark bookmark = BookmarkService.getBkmInstance().createWebLink(Long.parseLong(values[0]), values[1], values[2], values[3] /* , values[4] * / );
			bookmarkList.add(bookmark);
		}
		bookmarks.add(bookmarkList);
	} */
	
	
	
	
	
	/* private static void loadMovies() {
		
	/*	bookmarks[1][0]= BookmarkService.getBkmInstance().createMovie(3000,	"Citizen Kane", "",	1941,	new String[] { "Orson Welles","Joseph Cotten" },  new String[] {"Orson Welles"}, MovieGenre.CLASSICS, 8.5);
		bookmarks[1][1]= BookmarkService.getBkmInstance().createMovie(3001,	"The Grapes of Wrath", "",	1940,	new String[] { "Henry Fonda", "Jane Darwell" },  new String[] {"John Ford"}, MovieGenre.CLASSICS, 8.2);
		bookmarks[1][2]= BookmarkService.getBkmInstance().createMovie(3002,	"A Touch of Greatness", "",	2004,	new String[] { "Albert Cullum"},  new String[] {"Leslie Sullivan"}, MovieGenre.DOCUMENTARIES, 7.3);
		bookmarks[1][3]= BookmarkService.getBkmInstance().createMovie(3003,	"The Big Bang Theory", "",	2007,	new String[] { "Kaley Cuoco", "Jim Parsons" },  new String[] {"Chuck Lorre" ,"Bill Prady"}, MovieGenre.TV_SHOWS, 8.7);
		bookmarks[1][4]= BookmarkService.getBkmInstance().createMovie(3004,	"Ikiru", "",	1952,	new String[] { "Takashi Shimura" ," Minoru Chiaki" },  new String[] {"Akira Kurosawa"}, MovieGenre.FOREIGN_MOVIES, 8.4);
	* /	
			
		//String[] data = new String [BOOKMARK_COUNT_PER_TYPE];
		
		List<String> data = new ArrayList<>();
		
		IOUtil.read(data, "Movie");
		//int colNum = 0;
		
		List<Bookmark> bookmarkList = new ArrayList<>();
		
		for (String row : data) {
			String[] values = row.split("\t");
			String[] cast = values[3].split(",");
			String[] directors = values[4].split(",");
			
			//bookmarks[1][colNum++]
			Bookmark bookmark = BookmarkService.getBkmInstance().createMovie(Long.parseLong(values[0]), values[1], "", Integer.parseInt(values[2]), cast, directors, MovieGenre.valueOf(values[5]), Double.parseDouble(values[6]) /*, values[7] * / );
			bookmarkList.add(bookmark);
		}
		bookmarks.add(bookmarkList);
		
	} */
	
	
	
	
	/*private static void loadBooks() {
			
	/*	bookmarks[2][0]= BookmarkService.getBkmInstance().createBook(4000,	"Walden",	1854, "Wilder Publications", new String []{"Henry David Thoreau"}, BookGenre.PHILOSOPHY, 4.3);
		bookmarks[2][1]= BookmarkService.getBkmInstance().createBook(4001,	"Self-Reliance and Other Essays",	1993, "Dover Publications", new String []{"Ralph Waldo Emerson"}, BookGenre.PHILOSOPHY, 4.5);
		bookmarks[2][2]= BookmarkService.getBkmInstance().createBook(4002,	"Light From Many Lamps",	1988, "Touchstone", new String []{"Lillian Eichler Watson"}, BookGenre.PHILOSOPHY, 5.0);
		bookmarks[2][3]= BookmarkService.getBkmInstance().createBook(4003,	"Head First Design Patterns",	2004, "O'Reilly Media", new String []{"Eric Freeman","Bert Bates","Kathy Sierra","Elisabeth Robson"}, BookGenre.TECHNICAL, 4.5);
		bookmarks[2][4]= BookmarkService.getBkmInstance().createBook(4004,	"Effective Java Programming Language Guide",	2007, "Prentice Hall", new String []{"Joshua Bloch"}, BookGenre.TECHNICAL, 4.9);
	* /	
		//String[] data = new String [BOOKMARK_COUNT_PER_TYPE];
		
		List<String> data = new ArrayList<>();
		
		IOUtil.read(data, "Book");
		//int colNum = 0;
		
		List<Bookmark> bookmarkList = new ArrayList<>();
		
		for (String row : data) {
			String[] values = row.split("\t");
			String[] authors = values[4].split(",");			
			
			//bookmarks[2][colNum++]
			Bookmark bookmark= BookmarkService.getBkmInstance().createBook(Long.parseLong(values[0]), values[1], Integer.parseInt(values[2]), values[3], authors, BookGenre.valueOf(values[5]), Double.parseDouble(values[6]) /*, values[7] * / );
			bookmarkList.add(bookmark);
		}
		bookmarks.add(bookmarkList);		
	} */


	public static void add(UserBookmark userBookmark) {		
		//userBookmarks[usrBkmIndex] = userBookmark;
		userBookmarks.add(userBookmark);		
		
		//usrBkmIndex++;		
	}
	
}








