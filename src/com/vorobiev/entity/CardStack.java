package com.vorobiev.entity;

import java.util.Iterator;

import com.vorobiev.graphics.GraphicsCard;

public class CardStack extends CardRepository {
	private int maxOffsetY = 40;
	private int maxHeight = 400;
	public CardStack(GraphicsCard gcard, int posX, int posY) {
		super(gcard, posX, posY);
	}
	public CardStack(Card[] cards, GraphicsCard gcard, int posX, int posY) {
		super(gcard, posX, posY);
		offsetY = maxOffsetY;
		super.addCards(cards, Card.INVISIBLE);
		deck.getLast().setVisible(Card.VISIBLE);
	}
	public Card[] getAllOpenedCards() {
		int sizeOpened = getSizeOpenedCards();
		if(sizeOpened == 0) throw new RuntimeException("CardStack is empty");
		Card[] cards = new Card[sizeOpened];
		int size = deck.size();
		deck.subList(size - sizeOpened, size).toArray(cards);
		return cards;
	}
	@Override
	public Card[] removeCards(int count) {
		int sizeOpened = getSizeOpenedCards();
		if(sizeOpened < count) throw new RuntimeException("removeOpenedCards, count > sizeOpenedCards");
		Card[] cards = super.removeCards(count);
		if(!deck.isEmpty()) {
			if(getSizeOpenedCards() == 0) {
				deck.getLast().setVisible(Card.VISIBLE);
			}
		}
		return cards;
	}
	public int getSizeOpenedCards() {
		int size = 0;
		for(Card c : deck) {
			if(c.isVisibled()) {
				size++;
			}
		}
		return size;
	}
	public Card getLastClosed() {
		Iterator<Card> iter = deck.descendingIterator();
		while(iter.hasNext()) {
			Card c = iter.next();
			if(!c.isVisibled()) {
				return c;
			}
		}
		return null;
	}
	
	@Override
	public Card removeCard() {
		
		if(getSizeOpenedCards() == 0) throw new RuntimeException("removeOpenedCards, count > sizeOpenedCards");
		Card c = super.removeCard();
		if(!deck.isEmpty()) {
			if(getSizeOpenedCards() == 0) {
				deck.getLast().setVisible(Card.VISIBLE);
			}
		}
		return c;
	}
	@Override
	public void rechangeAllAfterAddingAndRemoving() {
		int size = deck.size();
		if(size > 1) {
			offsetY = (maxHeight - deck.getLast().getHeight()) / size;
			if(offsetY > maxOffsetY) offsetY = maxOffsetY;
		}
		super.rechangePosAndDestAll();
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
