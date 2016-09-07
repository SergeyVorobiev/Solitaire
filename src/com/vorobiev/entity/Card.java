package com.vorobiev.entity;

public class Card {
	private static final String[] names = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
	private static final String[] suitNames = {"Timbrel", "Clubs", "Heart", "Peak"};
	public static final boolean RED = false;
	public static final boolean BLACK = true;
	public static final int VISIBLE = 1;
	public static final int INVISIBLE = 0;
	public static final int ASIS = 2;
	public static final int UP = 10;
	public static final int DOWN = 11;
	public static final int NORMAL = 12;
	private int visibled = INVISIBLE;
	private float posX;
	private float posY;
	private float dx = 0;
	private float dy = 0;
	private int width;
	private int height;
	private String name;
	private int cost;
	private String suitName;
	private int suitNumber;
	private boolean color;
	private int number;
	public Card(Card card) {
		
	}
	public Card(int number, int width, int height) {
		this.width = width;
		this.height = height;
		this.number = number;
		cost = number % 13;
		name = names[cost];
		suitNumber = number / 13;
		suitName = suitNames[suitNumber];
		if(suitNumber == 0 || suitNumber == 2) color = RED;
		else color = BLACK;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getPosX() {
		return (int)posX;
	}
	public int getPosY() {
		return (int)posY;
	}
	public void setPosXY(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	public void setDXDY(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}
	public float getDX() {
		return this.dx;
	}
	public float getDY() {
		return this.dy;
	}
	public int getSuitNumber() {
		return suitNumber;
	}
	public static String whatCardIs(int number) {
		String str = "Name: " + names[number % 13] + "; Cost: " + number % 13 + "; Suit: " + suitNames[number / 13];
		return str;
	}
	@Override
	public String toString() {
		String str = "Name: " + name + "; Cost: " + cost + "; Suit: " + suitName + "; Number: " + number;
		if(visibled == Card.VISIBLE) {
			str += "visible";
		} else {
			str += "invisible";
		}
		return str;
	}
	public int getCost() {
		return cost;
	}
	public boolean isVisibled() {
		return visibled == VISIBLE;
	}
	public boolean getColor() {
		return color;
	}
	public void setVisible(int visibled) {
		if(visibled == ASIS) return;
		this.visibled = visibled;
	}
	public int getNumber() {
		return number;
	}
	public void move(float deltaTime) {
		posX += dx * deltaTime;
		posY += dy * deltaTime;
	}
}
