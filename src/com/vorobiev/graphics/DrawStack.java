package com.vorobiev.graphics;

import com.vorobiev.entity.Card;
import com.vorobiev.entity.CardRepository;

public class DrawStack extends AbstractDraw {
	
	@Override
	public void paint(CardRepository cs, float deltaTime) {
		for(Card c : cs.getDeck()) {
			paint(c);
		}
	}
}
