package com.javacoding.marked.constants;

public enum BookGenre {
	
	ART("Art"),
	BIOGRAPHY("Biography"),
	CHILDREN("Children"),
	FICTION("Fiction"),
	HISTORY("History"),
	MYSTERY("Mistery"),
	PHILOSOPHY("Philosophy"),
	RELIGION("Religion"),
	ROMANCE("Romance"),
	SELF_HELP("Self help"),
	TECHNICAL("Technical");
	
	private BookGenre (String name) {
		this.name = name;
	}
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	
	/* public class BookGenre {
		
		private BookGenre () {
			
		}

		
		public static final String ART = "Art";
		public static final String BIOGRAPHY = "Biography";
		public static final String CHILDREN = "Children";
		public static final String FICTION = "Fiction";
		public static final String HISTORY = "History";
		public static final String MYSTERY = "Mystery";
		public static final String PHILOSOPHY = "Philosophy";
		public static final String RELIGION = "Religion";
		public static final String ROMANCE = "Romance";
		public static final String SELF_HELP = "Self Help";
		public static final String TECHNICAL = "Technical";
	}*/


	
}







