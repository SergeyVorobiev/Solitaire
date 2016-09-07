package com.vorobiev.entity;

import java.util.TreeMap;

import com.vorobiev.graphics.DrawDeck;
import com.vorobiev.graphics.DrawFan;
import com.vorobiev.graphics.DrawMove;
import com.vorobiev.graphics.DrawStack;
import com.vorobiev.graphics.DrawStorage;

public class CardAlignment {
	public static TreeMap<Integer, CardRepository> getInstance(int cards[]) {
		TreeMap<Integer, CardRepository> map = new TreeMap<Integer, CardRepository>();
		map.put(Names.CardDeck, new CardDeck(cards, new DrawDeck(), 1050, 450, 2, 1));
		for(int i = 0; i < 7; i++) {
			map.put(Names.CardStack1 + i,
				new CardStack(((CardDeck)map.get(Names.CardDeck)).removeCards(1 + i), new DrawStack()
						, 50 + i * 100, 80));
		}
		
		for(int i = 0; i < 4; i++) {
			map.put(Names.CardStorage1 + i, new CardStorage(new DrawStorage(), 800 + i * 90, 80));
		}
		map.put(Names.CardFan, new CardFan(new DrawFan(), 800, 450, 0, 0));
		map.put(Names.MoveContainer, new MoveContainer(new DrawMove(), 0, 0));
		return map;
	}
}
