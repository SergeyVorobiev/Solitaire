package com.vorobiev.entity;

import com.vorobiev.graphics.GraphicsCard;

public class CardFan extends CardRepository {
	public CardFan(GraphicsCard gcard, int posX, int posY) {
		this(gcard, posX, posY, 0, 0);
	}
	public CardFan(GraphicsCard gcard, int posX, int posY, int offsetX, int offsetY) {
		super(gcard, posX, posY, offsetX, offsetY);
	}
	public Card[] getLastThree() {
		int size = deck.size();
		if(size == 0) return null;
		if(size > 3) size = 3;
		Card[] cards = new Card[size];
		deck.subList(deck.size() - size, deck.size()).toArray(cards);
		return cards;
	}
	@Override
	public void rechangeAllAfterAddingAndRemoving() {
		int i = 0;
		for(Card c : deck) {
			c.setPosXY(posX + (i % 3) * 20, posY);
			i++;
		}
	}
	@Override
	public Card removeCard() {
		return super.removeCard();
	}
	@Override
	public void addCard(Card c, int visibled) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void addCards(Card[] c, int visibled) {
		int size = c.length;
		int sizeDeck = deck.size();
		int k;
		for(int i = 0; i < size; i++) {
			k = (i + sizeDeck) % 3;
			c[i].setVisible(Card.VISIBLE);
			c[i].setPosXY(posX + k * 20, posY);
			deck.add(c[i]);
		}
	}
	@Override
	public void addReverseCards(Card[] c, int visibled) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Card[] removeCards(int count) {
		return super.removeCards(count);
		//if(count == 1) return new Card[]{super.removeCard()};
		//throw new UnsupportedOperationException();
	}
	/*
	@Override
	public Card[] removeAll() {
		return super.removeAll();
	}*/
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
}
