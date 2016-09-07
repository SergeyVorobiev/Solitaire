package com.vorobiev.entity;

import com.vorobiev.graphics.GraphicsCard;
import com.vorobiev.physics.Physics;

public class MoveContainer extends CardRepository {
	public static int MOVE_START = 0;
	public static int MOVE_END = 1;
	public int status = MOVE_END;
	private CardRepository cr;
	public MoveContainer(GraphicsCard gcard, int posX, int posY) {
		super(gcard, posX, posY);
	}
	public void start(CardRepository cr) {
		this.cr = cr;
		int i = 0;
		float[] dxdy = null;
		for(Card c : deck) {
			float destX = cr.getDestX() + i * cr.getOffsetX();
			float destY = cr.getDestY() + i * cr.getOffsetY();
			dxdy = Physics.getDxDy(c.getPosX(), c.getPosY(), destX, destY, 500);
			c.setDXDY(dxdy[0], dxdy[1]);
			i++;
		}
		status = MOVE_START;
	}
	@Override
	protected void rechangeAllAfterAddingAndRemoving() {
		
	}
	
	@Override
	public Card[] removeAll() {
		cr = null;
		return super.removeAll();
	}
	@Override
	public void update(float deltaTime) {
		if(status == MOVE_START) {
			boolean end = true;
			int i = 0;
			for(Card c : deck) {
				int destX = cr.getDestX() + i * cr.getOffsetX();
				int destY = cr.getDestY() + i * cr.getOffsetY();
				i++;
				if(achieve(c, destX, destY)) {
					continue;
				}
				end = false;
				c.move(deltaTime);
			}
			if(end) status = MOVE_END;
		}
		
	}
	private boolean achieve(Card c, int destX, int destY) {
		/*
		c.setPosXY((int)destX, (int)destY);
		return true;
		*/
		float distanceX = destX - c.getPosX();
		float distanceY = destY - c.getPosY();
		if(distanceX * c.getDX() <= 0 && distanceY * c.getDY() <= 0) {
			c.setPosXY((int)destX, (int)destY);
			return true;
		}
		return false;
	}
	
}
