package com.vorobiev.graphics;

import com.vorobiev.entity.CardRepository;

public interface GraphicsCard {
	public void paint(CardRepository cs, float deltaTime);
}
