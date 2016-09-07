package com.vorobiev.graphics;

import java.util.NoSuchElementException;

import com.vorobiev.entity.Card;
import com.vorobiev.entity.CardRepository;
import com.vorobiev.resources.Assets;

public class DrawStorage extends AbstractDraw {
	public DrawStorage() { 
	}
	@Override
	public void paint(CardRepository cs, float deltaTime) {
		try {
			Card c = cs.getDeck().getLast();
			paint(c);
		} catch(NoSuchElementException e) {
			paintEntity(Assets.cards, Assets.cardJoker, cs.getPosX(), cs.getPosY(), 80, 122);
		}
	}

}
