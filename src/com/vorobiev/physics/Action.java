package com.vorobiev.physics;

import java.util.LinkedList;
import java.util.TreeMap;

import com.vorobiev.entity.Card;
import com.vorobiev.entity.CardRepository;
import com.vorobiev.entity.CardStack;
import com.vorobiev.entity.MoveContainer;
import com.vorobiev.entity.Names;

public class Action {
	private static final int ADD = 0;
	private static final int MOVE = 1;
	private int curPartAction = ADD;
	private LinkedList<Combination> list = new LinkedList<Combination>();
	public void start(Combination comb) {
		list.add(comb);
	}
	public void doIt(Combination comb, TreeMap<Integer, CardRepository> map) {
		if(comb.getStatus() == Combination.CHANGE) {
			map.get(comb.getRepository()).findCard(comb.getCardForChangeVisibility()).setVisible(comb.getVisibility());
			return;
		} 
		CardRepository cfrom = map.get(comb.getFrom());
		CardRepository cto = map.get(comb.getTo());
		Card[] cards = cfrom.removeCards(comb.getCount());
		if(cto instanceof CardStack) {
			for(int i = cards.length - 1; i >= 0; i--) {
				cto.addCard(cards[i], Card.ASIS);
			}
		} else {
			cto.addCards(cards, Card.ASIS);
		}
	}
	public void update(TreeMap<Integer, CardRepository> map) {
		if(list.isEmpty()) return;
		Combination comb = list.getFirst();
		if(comb.getStatus() != Combination.CHANGE) {
			fromTo(map, list.getFirst());
		} else {
			map.get(comb.getRepository()).findCard(comb.getCardForChangeVisibility()).setVisible(comb.getVisibility());
			list.removeFirst();
		}
	}
	public boolean isAction() {
		return !list.isEmpty();
	}
	private void fromTo(TreeMap<Integer, CardRepository> map, Combination comb) {
		MoveContainer mc = (MoveContainer)map.get(Names.MoveContainer);
		int from = comb.getFrom();
		int to = comb.getTo();
		int sizeCard = comb.getCount();
		CardRepository cfrom = map.get(from);
		CardRepository cto = map.get(to);
		if(curPartAction == ADD) {
			
			Card[] cards = null;
			cards = cfrom.removeCards(sizeCard);
		
			if(cards == null) {
				System.err.println(Names.getStringName(from) + " : " + sizeCard);
				throw new RuntimeException("Container is empty");
				/*
				if(cfrom instanceof CardDeck && cto instanceof CardFan) {	
					cards = cto.removeAll();
					cfrom.addReverseCards(cards, Card.INVISIBLE);
					cards = cfrom.removeCards(sizeCard);
				} else throw new RuntimeException("MoveContainer - from is empty");
			*/
			}
			
			mc.addReverseCards(cards, Card.ASIS);
			
			curPartAction = MOVE;
			mc.start(cto);
			mc.status = MoveContainer.MOVE_START;
		}
		if(curPartAction == MOVE && mc.status == MoveContainer.MOVE_END) {
			Card[] c = null;
			if(cfrom instanceof CardStack && cto instanceof CardStack) {
				c = mc.removeAll();
			} else c = mc.removeCards(mc.getSize());
			cto.addCards(c, Card.VISIBLE);
			curPartAction = ADD;
			list.removeFirst();
		}
	}
}
