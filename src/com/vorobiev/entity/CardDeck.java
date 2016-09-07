package com.vorobiev.entity;

import java.util.Random;

import com.vorobiev.graphics.GraphicsCard;

public class CardDeck extends CardRepository {
	private static Random rnd = new Random();
	public CardDeck(int array[], GraphicsCard gcard, int posX, int posY, int offsetX, int offsetY) {
		this(gcard, posX, posY, offsetX, offsetY);
		createCards(array);
	}
	public CardDeck(GraphicsCard gcard, int posX, int posY) {
		this(gcard, posX, posY, 0, 0);
		mixDeck();
	}
	public CardDeck(GraphicsCard gcard, int posX, int posY, int offsetX, int offsetY) {
		super(gcard, posX, posY, offsetX, offsetY);
		mixDeck();
	}
	private void createCards(int[] array) {
		deck.clear();
		for(int i = 0; i < 52; i++) {
			Card card = new Card(array[i], 80, 122);
			card.setPosXY(posX + i * offsetX, posY + i * offsetY);
			deck.add(card);
		}
	}
	public void mixDeck() {
		int[] array = new int[52];
		for(int i = 0; i < 52; i++) {
			array[i] = i;
		}
		mix(array, 5000);
		createCards(array);
		
	}
	@Override
	public void addCard(Card card, int invisible) {
		super.addCard(card, Card.INVISIBLE);
	}
	@Override
	public void addReverseCards(Card[] cards, int invisible) {
		super.addReverseCards(cards, Card.INVISIBLE);
	}
	@Override
	public void addCards(Card[] card, int invisible) {
		super.addCards(card, Card.INVISIBLE);
	}
	public static void mix(int[] array, int iter) {
		
		int size = array.length;
		for(int i = 0; i < iter; i++) {
			int first = rnd.nextInt(size);
			int second = rnd.nextInt(size);
			int temp = array[first];
			array[first] = array[second];
			array[second] = temp;
		}
	}
	@Override
	public void rechangeAllAfterAddingAndRemoving() {
		this.rechangePosAndDestAll();
	}
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
