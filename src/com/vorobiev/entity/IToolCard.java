package com.vorobiev.entity;

public interface IToolCard {
	public void addCard(Card c, int visibled);
	public void addCards(Card[] c, int visibled);
	public void addReverseCards(Card[] c, int visibled);
	public Card[] removeCards(int count);
	public Card removeCard();
	public Card[] removeAll();
}
