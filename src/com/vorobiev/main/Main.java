package com.vorobiev.main;

import java.util.LinkedList;

import com.vorobiev.graphics.MyFrame;
import com.vorobiev.graphics.Renderer;
import com.vorobiev.graphics.Screen;
import com.vorobiev.physics.Combination;

public class Main {
	public static String pathResources = System.getProperty("user.dir") + "\\src\\resources";
	public static void main(String[] args) throws InterruptedException {
		start();
	}
	private static void start() throws InterruptedException {
		MyFrame frame = new MyFrame(new Screen());
		Renderer r = new Renderer();
		frame.addKeyListener(r);
		r.start();
		while(frame != null) {
			Thread.sleep(10);
			frame.repaint();
		}
		r.stop();
	}
}
