package com.vorobiev.graphics;

import java.util.TreeMap;

import com.vorobiev.entity.CardAlignment;
import com.vorobiev.entity.CardDeck;
import com.vorobiev.entity.CardRepository;
import com.vorobiev.physics.AI;
import com.vorobiev.physics.Action;
import com.vorobiev.physics.Combination;
import com.vorobiev.physics.RunAI;

public class GameWorld {
	private int width;
	private int height;
	private TreeMap<Integer, CardRepository> map;
	private RunAI rai;
	private int[] cards;
	private boolean keyDown;
	private boolean newA;
	//private LinkedList<Combination> listComb;
	private Action action;
	public GameWorld(int width, int height) {
		this.width = width;
		this.height = height;
		init();
	}
	private void init() {
		Runtime.getRuntime().gc();
		cards = new int[52];
		keyDown = false;
		newA = false;
		action = new Action();
		for(int i = 0; i < 52; i++) {
			cards[i] = i;
		}
		CardDeck.mix(cards, 5000);
		map = CardAlignment.getInstance(cards);
		rai = new RunAI(CardAlignment.getInstance(cards), new AI());
		rai.start();
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void update(float deltaTime) {
		action.update(map);
		for(CardRepository cr : map.values()) {
			cr.update(deltaTime);
		}
	}
	public void receiveKey(char key) {
		System.out.println(key);
		if(newA) {
			rai.stop();
			init();
		} else {
			keyDown = true;
		}
	}
	public void repaint(float deltaTime) {
		AbstractDraw.clear();
		for(CardRepository cr : map.values()) {
			cr.paint(deltaTime);
		}
		if(rai.getWin() && keyDown) {
			newA = true;
			for(Combination c : rai.getWinCombinations()) {
				//System.out.println(c);
				action.start(c);
			}
		}
		rai.isEnd();
		int memory = (int)((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);
		AbstractDraw.drawString("Combinations: " + String.valueOf(rai.getSize()) + " Memory: " + memory + " MB", 20, 500);
		if(rai.getWin()) {
			AbstractDraw.drawString("WIN! Press any key to see the win alignment", 20, 550);
		}
		if(rai.getSize() > 5000000 && !rai.getWin() && !rai.isEnd()) {
			newA = true;
			AbstractDraw.drawString("Press any key to try to align other alignment", 20, 550);
		}
		if(rai.isEnd() && !rai.getWin()) {
			AbstractDraw.drawString("Must not win! Press any key to try to align other alignment", 20, 600);
			newA = true;
		}
		AbstractDraw.swap();
	}
}
