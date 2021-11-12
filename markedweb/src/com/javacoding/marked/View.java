package com.javacoding.marked;


import java.util.List;

import com.javacoding.marked.constants.KidFriendlyStatus;
import com.javacoding.marked.constants.UserType;
import com.javacoding.marked.controllers.BookmarkController;
import com.javacoding.marked.entities.Bookmark;
import com.javacoding.marked.entities.User;
import com.javacoding.marked.partner.Shareable;

//Mock of UI
public class View {
	
	
	public static void browse(User user, List<List<Bookmark>> bookmarks) {

		System.out.println("\n" + user.getEmail() + " is browsing items...");

		//int bookmarkCount = 0;

		for (List<Bookmark> bookmarkList : bookmarks) {
			for (Bookmark bookmark : bookmarkList) {

				// Bookmarking

				//if (bookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {

					boolean isBookmarked = getBookmarkDecision(bookmark);
					if (isBookmarked) {
						//bookmarkCount++;

					//	BookmarkController.getBkmCntInstance().saveUserBookmark(user, bookmark);   converted to servlet !

						System.out.println("New Item Bookmarked -- " + bookmark);
					}

				//}
					
				
				if (user.getUserType().equals(UserType.EDITOR) || user.getUserType().equals(UserType.CHIEF_EDITOR)) {

					// Marking as kid friendly.
					if (bookmark.isKidFriendlyELigible() && bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
						
						KidFriendlyStatus kidFriendlyStatus = getKidFriendlyStatusDecision(bookmark);
						
						if (!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {							
			//				BookmarkController.getBkmCntInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);
						}
					}

					// Sharing.
					if (bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED) && bookmark instanceof Shareable) {
						
						boolean isShared = getShareDecision();
						if(isShared) {
			//				BookmarkController.getBkmCntInstance().share(user, bookmark);
						}
					}
				}
			}
		}
	}
	
	// TODO: Methods below randomly mock user actions.

	private static boolean getShareDecision() {
		return Math.random() < 0.5 ? true :false;
	}

	private static KidFriendlyStatus getKidFriendlyStatusDecision(Bookmark bookmark) {		
		/*
		return Math.random() < 0.4 ? KidFriendlyStatus.APPROVED : 			
			(Math.random() >= 0.4 && Math.random() <0.8) ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN;  */
		
		double decision = Math.random();		
		return decision < 0.4 ? KidFriendlyStatus.APPROVED :
			(decision >= 0.4 && decision < 0.8) ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN ;		
	}

	private static boolean getBookmarkDecision(Bookmark bookmark) {		
		return Math.random() < 0.5 ? true :false;		
	}
	
	
	
	/*public static void bookmark(User user, Bookmark[][] bookmarks) {      //updated with browse method
		
		System.out.println("\n" + user.getEmail() + " is bookmarking...");
		for(int i=0; i < DataStore.USER_BOOKMARK_LIMIT; i++ ) {
			
			int typeOffset = (int)( Math.random() * DataStore.BOOKMARKS_TYPES_COUNT);
			int bookmarkOffset = (int)( Math.random() * DataStore.BOOKMARK_COUNT_PER_TYPE);
			
			Bookmark bookmark = bookmarks[typeOffset][bookmarkOffset];
			
			BookmarkController.getBkmCntInstance().saveUserBookmark(user, bookmark);
			
			
			System.out.println(bookmark);
			
		}
		
	} */
}
















