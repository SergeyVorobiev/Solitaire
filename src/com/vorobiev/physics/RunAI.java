package com.vorobiev.physics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import com.vorobiev.entity.CardRepository;
import com.vorobiev.entity.Names;
import com.vorobiev.main.Timer;

public class RunAI implements Runnable {
	private LinkedList<Combination> allCombinations = new LinkedList<Combination>();
	private LinkedList<Combination> winCombinations = new LinkedList<Combination>();
	private TreeMap<Integer, CardRepository> map;
	private Action action = new Action();
	private boolean win = false;
	private boolean end = false;
	private int size = 0;
	private AI ai;
	private Timer timer = new Timer();
	private Thread thread = new Thread(this);
	public RunAI(TreeMap<Integer, CardRepository> map, AI ai) {
		this.map = map;
		this.ai = ai;
	}
	public void start() {
		thread.start();
	}
	public int getSize() {
		return size;
	}
	public boolean getWin() {
		return win;
	}
	public synchronized LinkedList<Combination> getAllCombinations() {
		LinkedList<Combination> list = new LinkedList<Combination>();
		for(Combination c : allCombinations) {
			list.add(new Combination(c));
		}
		allCombinations.clear();
		return list;
	}
	public synchronized LinkedList<Combination> getWinCombinations() {
		LinkedList<Combination> list = new LinkedList<Combination>();
		for(Combination c : winCombinations) {
			list.add(c);
		}
		winCombinations.clear();
		return list;
	}
	@Override
	public void run() {
			try {
				timer.start();
				recursion(ai.getCombinations(map));
				float t = timer.stop();
				System.err.println("Time: " + t + " : Combinations: " + size);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			System.out.println("END");
			end = true;
	}
	public boolean isEnd() {
		return end;
	}
	private boolean isWin() {
		//if(map.get(Names.CardDeck).isEmpty() && map.get(Names.CardFan).isEmpty()) return true;
		for(int i = 0; i < 4; i++) {
			CardRepository cr = map.get(Names.CardStorage1 + i);
			if(cr.whatLastCard() == null) return false;
			if(cr.whatLastCard().getCost() != 11) return false;
		}
		System.out.println("WIN");
		return true;
	}
	public void stop() {
		win = true;
	}
	private synchronized void recursion(LinkedList<LinkedList<Combination>> list) throws InterruptedException {
		if(win) return;
		//System.out.println(size);
		for(LinkedList<Combination> ll : list) {
			if(win) break;
			for(Combination c : ll) {
				size++;
				allCombinations.add(c);
				action.doIt(c, map);
			}
			if(!win && isWin()) win = true;
			if(!win) {
				LinkedList<LinkedList<Combination>> llnew = null;
				llnew = ai.getCombinations(map);
				recursion(llnew);
			}
			if(!win) {
				
				Iterator<Combination> iter = ll.descendingIterator();
				while(iter.hasNext()) {
					Combination comb = iter.next();
					comb.reverse();
					
					//allCombinations.add(combR);
					action.doIt(comb, map);
				}
			} else {
				Iterator<Combination> iter = ll.descendingIterator();
				while(iter.hasNext()) {winCombinations.addFirst(iter.next());}
			}
		}	
	}
}
