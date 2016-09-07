package com.vorobiev.main;

public class Timer {
	private long time;
	public void start() {
		time = System.nanoTime();
	}
	public float stop() {
		return (float)((System.nanoTime() - time) / 1000000000f);
		
	}
}
