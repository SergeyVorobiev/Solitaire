package com.vorobiev.graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.Thread.State;

import com.vorobiev.main.Timer;

public class Renderer implements Runnable, KeyListener {
	private Thread thread = new Thread(this);
	private GameWorld world = new GameWorld(1200, 600);
	private Timer timer = new Timer();
	@Override
	public void run() {
		timer.start();
		while(!thread.isInterrupted()) {
			float t = timer.stop();
			timer.start();
			world.update(t);
			world.repaint(t);
			int mills = 10 - (int)(timer.stop() * 1000);
			try {
				Thread.sleep(mills < 0 ? 0 : mills);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	public void stop() {
		if(thread.getState() == State.RUNNABLE) {
			thread.interrupt();
		}
	}
	public void start() {
		if(thread.getState() == State.NEW) {
			thread.start();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		world.receiveKey(e.getKeyChar());
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
