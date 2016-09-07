package com.vorobiev.entity;

import com.vorobiev.graphics.GraphicsCard;

public class CardStorage extends CardRepository {
	public CardStorage(GraphicsCard gcard, int posX, int posY) {
		super(gcard, posX, posY);
		this.destX = posX;
		this.destY = posY;
	}
	public boolean isFull() {
		return deck.getLast().getCost() == 11;
	}
	@Override
	public Card removeCard() {
		throw new UnsupportedOperationException();
	}
	@Override
	protected void rechangeAllAfterAddingAndRemoving() {
		
	}
	@Override
	public void addCard(Card c, int visibled) {
		super.addCard(c, visibled);
	}
	@Override
	public void addCards(Card[] c, int visibled) {
		if(c.length == 1) {super.addCard(c[0],  visibled); return;}
		throw new UnsupportedOperationException();
	}
	@Override
	public void addReverseCards(Card[] c, int visibled) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Card[] removeCards(int count) {
		return new Card[]{super.removeCard()};
		//throw new UnsupportedOperationException();
	}
	/*
	@Override
	public Card[] removeAll() {
		throw new UnsupportedOperationException();
	}*/
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
