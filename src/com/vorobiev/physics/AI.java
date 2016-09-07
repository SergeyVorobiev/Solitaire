package com.vorobiev.physics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import com.vorobiev.entity.Card;
import com.vorobiev.entity.CardRepository;
import com.vorobiev.entity.CardStack;
import com.vorobiev.entity.Names;

public class AI {
	private LinkedList<LinkedList<Combination>> list;
	public LinkedList<LinkedList<Combination>> getCombinations(TreeMap<Integer, CardRepository> map) {
		list = new LinkedList<LinkedList<Combination>>();
		fromStackToStack(map);
		fromStackToStorage(map);
		fromFanToStack(map);
		fromFanToStorage(map);
		fromDeck(map);
		return list;
	}
	private void fromDeck(TreeMap<Integer, CardRepository> map) {
		//��������� �� ������
		CardRepository deck = map.get(Names.CardDeck);
		CardRepository fan = map.get(Names.CardFan);
		Iterator<Card> iDeck = deck.getDeck().descendingIterator();
		int i = 0;
		LinkedList<Combination> prev = new LinkedList<Combination>();
		while(true) {
			if(!iDeck.hasNext()) {
				//if(true)
				//break;
				if(fan.isEmpty()) break;
				//������� ����� ������ 
				LinkedList<Card> newList = new LinkedList<Card>(map.get(Names.CardDeck).getDeck());
				Iterator<Card> iFan = fan.getDeck().descendingIterator();
				prev.add(new Combination(Names.CardFan, Names.CardDeck, fan.getSize() + deck.getSize()));
				while(iFan.hasNext()) {
					newList.addLast(iFan.next());
				}
				Iterator<Card> iNew = newList.descendingIterator();
				i = 0;
				while(iNew.hasNext()) {
					Card c = iNew.next();
					i++;
					//������ �����
					if(i == 3 || !iNew.hasNext()) {
						prev.add(new Combination(Names.CardDeck, Names.CardFan, i));
						deckToCardStorage(map, c, prev);
						deckToCardStack(map, c, prev);
						i = 0;
					}
				}
				break;
			}
			Card c = iDeck.next();
			i++;
			//������ �����
			if(i == 3 || (!iDeck.hasNext() && i > 0)) {
				prev.add(new Combination(Names.CardDeck, Names.CardFan, i));
				deckToCardStorage(map, c, prev);
				deckToCardStack(map, c, prev);
				i = 0;
			}
		}
	}
	private void deckToCardStorage(TreeMap<Integer, CardRepository> map, Card c, LinkedList<Combination> prev) {
		for(int j = 0; j < 4; j++) {
			CardRepository storage = map.get(Names.CardStorage1 + j);
			Card cardStorage = storage.whatLastCard();
			if(canIPutToStorage(c, cardStorage)) {
				LinkedList<Combination> l = new LinkedList<Combination>();
				//#1
				for(Combination comb : prev) {
					l.add(new Combination(comb).setDebugNumber(1));
				}
				//#2
				l.add(new Combination(Names.CardFan, Names.CardStorage1 + j, getNumber(c), getNumber(cardStorage), 1).setDebugNumber(2));
				list.add(l);
				break;
			}
		}
	}
	private void deckToCardStack(TreeMap<Integer, CardRepository> map, Card c, LinkedList<Combination> prev) {
		for(int j = 0; j < 7; j++) {
			CardRepository stack = map.get(Names.CardStack1 + j);
			Card cardStack = stack.whatLastCard();
			if(canIPutToStack(c, cardStack)) {
				LinkedList<Combination> l = new LinkedList<Combination>();
				//#3
				for(Combination comb : prev) {
					l.add(new Combination(comb).setDebugNumber(3));
				}
				//#4
				l.add(new Combination(Names.CardFan, Names.CardStack1 + j, getNumber(c), getNumber(cardStack), 1).setDebugNumber(4));
				list.add(l);
				break;
			}
		}
	}
	private void fromFanToStorage(TreeMap<Integer, CardRepository> map) {
		CardRepository fan = map.get(Names.CardFan);
		Card cardFan = fan.whatLastCard();
		for(int storageIndex = 0; storageIndex < 4; storageIndex++) {
			CardRepository storage = map.get(Names.CardStorage1 + storageIndex);
			Card cardStorage = storage.whatLastCard();
			//#5
			if(canIPutToStorage(cardFan, cardStorage)) {
				add(Names.CardFan, Names.CardStorage1 + storageIndex, cardFan, cardStorage, 1, 5);
				break;
			}
		}	
	}
	private void add(int from, int to, Card cfrom, Card cto, int size, int debugNumber) {
		LinkedList<Combination> l = new LinkedList<Combination>();
		l.add(new Combination(from, to, getNumber(cfrom), getNumber(cto), size).setDebugNumber(debugNumber));
		list.add(l);
	}
	private void fromFanToStack(TreeMap<Integer, CardRepository> map) {
		CardRepository fan = map.get(Names.CardFan);
		Card cardFan = fan.whatLastCard();
		for(int stackIndex = 0; stackIndex < 7; stackIndex++) {
			CardRepository stack = map.get(Names.CardStack1 + stackIndex);
			Card cardStack = stack.whatLastCard();
			//#6
			if(canIPutToStack(cardFan, cardStack)) {
				add(Names.CardFan, Names.CardStack1 + stackIndex, cardFan, cardStack, 1, 6);
				break;
			}
		}	
	}
	private void fromStackToStorage(TreeMap<Integer, CardRepository> map) {
		for(int stackIndex = 0; stackIndex < 7; stackIndex++) {
			CardStack stack = (CardStack)map.get(Names.CardStack1 + stackIndex);
			for(int storageIndex = 0; storageIndex < 4; storageIndex++) {
				CardRepository storage = map.get(Names.CardStorage1 + storageIndex);
				Card stackCard = stack.whatLastCard();
				Card storageCard = storage.whatLastCard();
				if(canIPutToStorage(stackCard, storageCard)) {
					//#7
					add(Names.CardStack1 + stackIndex, Names.CardStorage1 + storageIndex, stackCard, storageCard, 1, 7);
					//#8
					//if(stackCard.getNumber() == stack.getLastClosed().getNumber()) throw new RuntimeException( "isEqual");
					if(stack.getSizeOpenedCards() == 1 && stack.getSize() > 1) {
						list.getLast().add(new Combination(stack.getLastClosed().getNumber(), Card.VISIBLE, Names.CardStack1 + stackIndex, 1).setDebugNumber(8));
					}
					break;
				}
			}	
		}
	}
	private int getNumber(Card card) {
		if(card == null) return -1;
		return card.getNumber();
	}
	private void fromStackToStack(TreeMap<Integer, CardRepository> map) {
		//�������� ���������� �� ������ ����.
		boolean empty = false;
		for(int i = 0; i < 7; i++) {
			CardRepository stack = map.get(Names.CardStack1 + i);
			if(stack.isEmpty()) {empty = true; break;}
		}
		for(int iStack = 0; iStack < 7; iStack++) {
			CardStack stack = (CardStack)map.get(Names.CardStack1 + iStack);
			if(stack.isEmpty()) continue;
			Card[] openedCards = stack.getAllOpenedCards();
			for(int i = 0; i < openedCards.length; i++) { //������ ����� ������
				int sizeCards = openedCards.length - i;
				Card cardFrom = openedCards[i];
				for(int iStack2 = 0; iStack2 < 7; iStack2++) {
					if(iStack == iStack2) continue; //����� �������
					CardRepository stack2 = map.get(Names.CardStack1 + iStack2);
					Card cardTo = stack2.whatLastCard();
					//������������� ��� �������� ����� ����� ������ ����� ��������� ��������
					if((i == 0 && stack.getSize() > openedCards.length && cardFrom.getCost() != 11)) {
						//#9
						if(canIPutToStack(cardFrom, cardTo)) {
							add(Names.CardStack1 + iStack, Names.CardStack1 + iStack2, cardFrom, cardTo, sizeCards, 9);
							//System.err.println(Card.whatCardIs(stack.getLastClosed().getNumber()) + " : " + Names.getStringName(Names.CardStack1 + iStack));
							list.getLast().add(new Combination(stack.getLastClosed().getNumber(), Card.VISIBLE, Names.CardStack1 + iStack, 1).setDebugNumber(99));
							break;
						}
						//#10
					} else if(i > 0 && cardFrom.getCost() != 11) { //������������� ����� ���������� ��������� � ���������
						if(canIPutToStack(cardFrom, cardTo)) {
							Card prev = openedCards[i - 1];
							for(int storageIndex = 0; storageIndex < 4; storageIndex++) {
								CardRepository storage = map.get(Names.CardStorage1 + storageIndex);
								Card cardStorage = storage.whatLastCard();
								if(canIPutToStorage(prev, cardStorage)) {
									LinkedList<Combination> l = new LinkedList<Combination>();
									l.add(new Combination(Names.CardStack1 + iStack, Names.CardStack1 + iStack2, getNumber(cardFrom), 
											getNumber(cardTo), sizeCards).setDebugNumber(10));
									l.add(new Combination(Names.CardStack1 + iStack, Names.CardStorage1 + storageIndex, getNumber(prev), 
											getNumber(cardStorage), 1).setDebugNumber(10));
									//��� ������ �����
									if(i - 1 == 0 && stack.getSize() > openedCards.length) {
										l.add(new Combination(stack.getLastClosed().getNumber(), Card.VISIBLE, Names.CardStack1 + iStack , 1).setDebugNumber(10));
									}
									list.add(l);
									break;
								}
							}	
						}
					}
					//���� ������
					if(cardFrom.getCost() == 11) {
						//���������� ���� ��������� � �� ������
						if(openedCards.length == stack.getSize() && i == 0) continue;
						if(canIPutToStack(cardFrom, cardTo)) {
							//#11
							add(Names.CardStack1 + iStack, Names.CardStack1 + iStack2, cardFrom, cardTo, sizeCards, 11);
							if(i == 0 && stack.getSize() > openedCards.length) {
								list.getLast().add(new Combination(stack.getLastClosed().getNumber(), Card.VISIBLE, Names.CardStack1 + iStack, 1).setDebugNumber(11));
							}
							break;
						}
					}
					//#12
					//���� ����� ������ �� �� ������(����� �������� ������)
					
					if(openedCards.length == stack.getSize() && i == 0 && cardFrom.getCost() != 11 && !empty) {
						if(canIPutToStack(cardFrom, cardTo)) {
							add(Names.CardStack1 + iStack, Names.CardStack1 + iStack2, cardFrom, cardTo, sizeCards, 12);
							break;
						}
					}
				}
			}
		}
	}
	private boolean canIPutToStack(Card from, Card to) {
		if(from == null) return false;
		//������ ������ � ������
		if(to == null && from.getCost() == 11) return true;
		else if(to == null) return false;
		//��������� ������ ������ �� ����
		if(to.getCost() == 12) return false;
		//������ �� ���� � ������ �����
		if(to.getCost() - from.getCost() == 1) {
			if(from.getColor() != to.getColor()) return true;
		}
		return false;
	}
	private boolean canIPutToStorage(Card from, Card to) {
		if(from == null) return false;
		//��� �� ������ �����
		if(to == null && from.getCost() == 12) return true;
		else if(to == null) return false;
		//������ �� ����
		if(to.getCost() == 12 && from.getCost() == 0 && from.getSuitNumber() == to.getSuitNumber()) return true;
		//����� �� ����������� �������� �����
		if(from.getCost() - to.getCost() == 1 && from.getSuitNumber() == to.getSuitNumber()) return true;
		return false;
	}
}
