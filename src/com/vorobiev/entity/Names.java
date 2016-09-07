package com.vorobiev.entity;

public class Names {
	public static final int CardDeck = 0;
	public static final int CardFan = 1;
	public static final int CardStorage1 = 2;
	public static final int CardStorage2 = 3;
	public static final int CardStorage3 = 4;
	public static final int CardStorage4 = 5;
	public static final int CardStack1 = 6;
	public static final int CardStack2 = 7;
	public static final int CardStack3 = 8;
	public static final int CardStack4 = 9;
	public static final int CardStack5 = 10;
	public static final int CardStack6 = 11;
	public static final int CardStack7 = 12;
	public static final int MoveContainer = 13;
	private static String[] arrayStrings;
	static {
		arrayStrings = new String[]{"CardDeck", "CardFan", "CardStorage1", "CardStorage2",
				"CardStorage3", "CardStorage4", "CardStack1", "CardStack2", "CardStack3", 
				"CardStack4", "CardStack5", "CardStack6", "CardStack7", "MoveContainer"};
	}
	public static String getStringName(int number) {
		return arrayStrings[number];
	}
	
}
