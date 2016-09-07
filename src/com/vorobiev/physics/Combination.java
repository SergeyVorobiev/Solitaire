package com.vorobiev.physics;

import com.vorobiev.entity.Card;
import com.vorobiev.entity.Names;

public class Combination {
	private int cFrom = -1;
	private int cTo = -1;
	private int rFrom;
	private int rTo;
	private int count;
	public static final int CHANGE = 1;
	private int status = 0;
	private int visible;
	private int card;
	private int repository;
	private int debugNumber = -1;
	
	public Combination(int card, int visible, int repository, int status) {
		this.card = card;
		this.visible = visible;
		this.repository = repository;
		this.status = status;
	}
	public Combination setDebugNumber(int debugNumber) {
		this.debugNumber = debugNumber;
		return this;
	}
	public Combination(Combination c) {
		this.cFrom = c.cFrom;
		this.cTo = c.cTo;
		this.rFrom = c.rFrom;
		this.rTo = c.rTo;
		this.count = c.count;
		this.card = c.card;
		this.status = c.status;
		this.visible = c.visible;
		this.repository = c.repository;
		this.debugNumber = c.debugNumber;
	}
	public Combination(int rFrom, int rTo, int count) {
		this.rFrom = rFrom;
		this.rTo = rTo;
		this.count = count;
	}
	public Combination(int rFrom, int rTo, int cFrom, int cTo, int count) {
		this.rFrom = rFrom;
		this.rTo = rTo;
		this.count = count;
		
		//this(rFrom, rTo, count);
		this.cFrom = cFrom;
		this.cTo = cTo;
	}
	public int getRepository() {
		return repository;
	}
	public int getDebugNumber() {
		return debugNumber;
	}
	public int getStatus() {
		return status;
	}
	

	public void reverse() {
		if(status != CHANGE) {
			int temp = rTo;
			rTo = rFrom;
			rFrom = temp;
		} else {
			if(visible == Card.VISIBLE) visible = Card.INVISIBLE;
			else visible = Card.VISIBLE;
		}
	}
	public int getVisibility() {
		return visible;
	}
	public int getCardForChangeVisibility() {
		return card;
	}
	public int getFrom() {
		return rFrom;
	}
	public int getTo() {
		return rTo;
	}
	public int getCount() {
		return count;
	}
	public String toString() {
		String str = null;
		if(status != CHANGE) {
			str = "From: " + Names.getStringName(rFrom) + "; To: " + Names.getStringName(rTo) +
				"; Numbers: " + count;
			if(cFrom != -1 && cTo != -1) {
				str += " (From: " + Card.whatCardIs(cFrom) + " To: " + Card.whatCardIs(cTo) + ")" + " #" + debugNumber;
			} else {
				str += " #" + debugNumber;
			}
		} else {
			str = Card.whatCardIs(card) + " : " + visible + " #" + debugNumber;
		}
		return  str;
	}
}
