package com.javacoding.marked.services;

import java.util.Collection;
//import java.net.MalformedURLException;
//import java.net.URISyntaxException;
import java.util.List;

import com.javacoding.marked.constants.BookGenre;
import com.javacoding.marked.constants.KidFriendlyStatus;
import com.javacoding.marked.constants.MovieGenre;
import com.javacoding.marked.dao.BookmarkDao;
import com.javacoding.marked.entities.Book;
import com.javacoding.marked.entities.Bookmark;
import com.javacoding.marked.entities.Movie;
import com.javacoding.marked.entities.User;
import com.javacoding.marked.entities.UserBookmark;
import com.javacoding.marked.entities.WebLink;
//import com.javacoding.marked.util.HttpConnect;
//import com.javacoding.marked.util.IOUtil;

public class BookmarkService {
	
	private static BookmarkService instance = new BookmarkService();
	
	private static BookmarkDao bkmDao = new BookmarkDao();
	
		
	public static BookmarkService getBkmInstance(){
		return instance;
	}
	
	
	public Book createBook(long id, String title, int publicationYear, String publisher,
			String[] authors, BookGenre genre, double amazonRating) {
		
		Book book = new Book();
		
		book.setId(id);
		book.setTitle(title);
		book.setPublicationYear(publicationYear);
		book.setPublisher(publisher);
		book.setAuthors(authors);
		book.setGenre(genre);
		book.setAmazonRating(amazonRating);		
		
		return book;
	}
	
	
	//overloaded method for webapp  
	public Book createBook(long id, String title, String imageUrl, int publicationYear, String publisher,
			String[] authors, BookGenre genre, double amazonRating) {
		
		Book book = new Book();
		
		book.setId(id);
		book.setTitle(title);
		
		book.setImageUrl(imageUrl);
		
		book.setPublicationYear(publicationYear);
		book.setPublisher(publisher);
		book.setAuthors(authors);
		book.setGenre(genre);
		book.setAmazonRating(amazonRating);
		
		return book;
	}
	
	
	
	
	public Movie createMovie(long id, String title, String profileUrl, int releaseYear, String[] cast,
			String[] directors, MovieGenre genre, double imdbRating) {
		
		Movie movie = new Movie();
		
		movie.setId(id);
		movie.setTitle(title);
		movie.setProfileUrl(profileUrl);
		movie.setReleaseYear(releaseYear);
		movie.setCast(cast);
		movie.setDirectors(directors);
		movie.setGenre(genre);
		movie.setImdbRating(imdbRating);
		
		
		return movie;
	}
	
	
	public WebLink createWebLink(long id, String title, String url, String host) {
		
		WebLink webLink = new WebLink();
		
		webLink.setId(id);
		webLink.setTitle(title);
		webLink.setUrl(url);
		webLink.setHost(host);
		
		return webLink;
		
	}
	
	public List<List<Bookmark>> getBookmarks() { 
		
		return bkmDao.getBookmarks();
	}


	public void saveUserBookmark(User user, Bookmark bookmark) {
		
		UserBookmark userBookmark = new UserBookmark();
		
		userBookmark.setUser(user);
		userBookmark.setBookmark(bookmark);
		
		/*   replaced with WebpageDownloadTask
		
		if (bookmark instanceof WebLink) {
			try {
				String url = ((WebLink) bookmark).getUrl();
				if(!url.endsWith(".pdf")) {
					String webpage = HttpConnect.download(((WebLink) bookmark).getUrl());
					if(webpage != null) {
						IOUtil.write(webpage, bookmark.getId());
					}
				}
			} catch (MalformedURLException e) {
				// TODO: handle exception
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		*/
		
		bkmDao.saveUserBookmark(userBookmark);
	}


	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {
		
		bookmark.setKidFriendlyStatus(kidFriendlyStatus);
		bookmark.setKidFriendlyMarkedBy(user);
		System.out.println("Kid-Frinedly status set to: " + kidFriendlyStatus + ", Marked by " + user.getEmail() + ", " + bookmark);
		
		bkmDao.updateKidFriendlyStatus(bookmark);
		
		
		
	}


	public void share(User user, Bookmark bookmark) {		
		bookmark.setSharedBy(user);
		
		System.out.println("Data to be shared: ");
		if(bookmark instanceof Book) {
			System.out.println(((Book) bookmark).getItemData());
		} else if(bookmark instanceof WebLink) {
			System.out.println(((WebLink) bookmark).getItemData());
		}
		
		bkmDao.sharedByInfo(bookmark);
		
	}


	public Collection<Bookmark> getBooks(boolean isBookmarked, long id) {
		
		return bkmDao.getBooks(isBookmarked, id);
	}


	public Bookmark getBook(long bookId) {		
		return bkmDao.getBook(bookId);
	}	

}

















