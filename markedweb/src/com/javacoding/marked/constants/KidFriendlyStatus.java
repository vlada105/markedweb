package com.javacoding.marked.constants;

public enum KidFriendlyStatus {
	
	
	APPROVED("approved"),
	REJECTED("rejected"),
	UNKNOWN("unknown");
	
	private KidFriendlyStatus(String name) {
		this.name = name;
	}
	
	private String name;
	
	public String getName() {
		return name;
	}

	
	
	/*
	private KidFriendlyStatus () {
		
	}
	
	public static final String APPROVED = "approved";
	public static final String REJECTED = "rejected";
	public static final String UNKNOWN = "unknown";
	
	*/

}
