package com.vorobiev.graphics;

import com.vorobiev.entity.Card;
import com.vorobiev.entity.CardFan;
import com.vorobiev.entity.CardRepository;

public class DrawFan extends AbstractDraw {
	
	@Override
	public void paint(CardRepository cs, float deltaTime) {
		Card[] cards = ((CardFan)cs).getLastThree();
		if(cards != null) {
			for(int i = 0; i < cards.length; i++) {
				paint(cards[i]);
			}
		}
	}

}
