package com.vorobiev.entity;

import java.util.LinkedList;

import com.vorobiev.graphics.GraphicsCard;

public abstract class CardRepository implements IToolCard {
	protected LinkedList<Card> deck = new LinkedList<Card>();
	protected GraphicsCard gcard;
	protected int destX;
	protected int destY;
	private int width = 0;
	private int height = 0;
	protected int posX = 0;
	protected int posY = 0;
	protected int offsetX;
	protected int offsetY;
	public CardRepository(GraphicsCard gcard, int posX, int posY) {
		this(gcard, posX, posY, 0, 0);
	}
	public CardRepository(GraphicsCard gcard, int posX, int posY, int offsetX, int offsetY) {
		this.gcard = gcard;
		this.posX = posX;
		this.posY = posY;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.destX = posX;
		this.destY = posY;
	}
	public void paint(float deltaTime) {
		gcard.paint(this, deltaTime);
	}
	
	public void setPosXY(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
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
	public boolean isEmpty() {
		return deck.isEmpty();
	}
	public LinkedList<Card> getDeck() {
		return deck;
	}
	public int getDestX() {
		return destX;
	}
	public int getDestY() {
		return destY;
	}
	public void setDestXY(int destX, int destY) {
		this.destX = destX;
		this.destY = destY;
	}
	public int getSize() {
		return deck.size();
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Card c : deck) {
			sb.append(c);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public Card whatLastCard() {
		if(deck.isEmpty()) return null;
		return deck.getLast();
	}
	public void clear() {
		deck.clear();
	}
	public int getOffsetX() {
		return offsetX;
	}
	public int getOffsetY() {
		return offsetY;
	}
	public void setOffsetXY(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	@Override
	public Card removeCard() {
		Card c = deck.removeLast();
		rechangeAllAfterAddingAndRemoving();
		return c;
	}
	@Override
	public void addCard(Card c, int visibled) {
		c.setVisible(visibled);
		deck.add(c);
		rechangeAllAfterAddingAndRemoving();
	}
	protected abstract void rechangeAllAfterAddingAndRemoving();
	protected void rechangePosAndDestAll() {
		if(deck.size() == 0) {
			destX = posX;
			destY = posY;
			return;
		}
		int i = 0;
		for(Card c : deck) {
			c.setPosXY(posX + (i) * offsetX, posY + (i++) * offsetY);
		}
		destX = deck.getLast().getPosX() + offsetX;
		destY = deck.getLast().getPosY() + offsetY;
	}
	@Override
	public void addCards(Card[] c, int visibled) {
		for(int i = 0; i < c.length; i++) {
			c[i].setVisible(visibled);
			deck.add(c[i]);
		}
		rechangeAllAfterAddingAndRemoving();
	}
	@Override
	public void addReverseCards(Card[] c, int visibled) {
		for(int i = c.length - 1; i >= 0; i--) {
			c[i].setVisible(visibled);
			deck.add(c[i]);
		}
		rechangeAllAfterAddingAndRemoving();
	}
	@Override
	public Card[] removeCards(int count) {
		int size = deck.size();
		if(size < count) count = size;
		if(size == 0) return null;
		Card[] c = new Card[count];
		for(int i = 0; i < count; i++) {
			c[i] = deck.removeLast();
		}
		rechangeAllAfterAddingAndRemoving();
		return c;
	}
	public Card findCard(int number) {
		for(Card c : deck) {
			if(c.getNumber() == number) return c;
		}
		return null;
	}
	@Override
	public Card[] removeAll() {
		Card[] c = new Card[deck.size()];
		deck.toArray(c);
		deck.clear();
		return c;
	}
	public abstract void update(float deltaTime);
}
