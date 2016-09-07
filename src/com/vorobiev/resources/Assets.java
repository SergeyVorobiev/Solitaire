package com.vorobiev.resources;

import java.awt.Image;
import java.awt.Toolkit;

import com.vorobiev.main.Main;

public class Assets {
	public static Image cards = Toolkit.getDefaultToolkit().createImage(Main.pathResources + "\\carddeck.png");
	public static Image table = Toolkit.getDefaultToolkit().createImage(Main.pathResources +"\\table.png");
	public static int cardCoords[][] = new int[52][4];
	public static int cardDown[] = new int[4];
	public static int cardJoker[] = new int[4];
	static {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 13; j++) {
				int index = i * 13 + j;
				cardCoords[index][0] = 152 * j;
				cardCoords[index][1] = 216 * i;
				cardCoords[index][2] = 152 * j + 152;
				cardCoords[index][3] = 216 * i + 216;
			}
		}
		cardDown[0] = 152 * 13;
		cardDown[1] = 216 * 2;
		cardDown[2] = 152 * 14;
		cardDown[3] = 216 * 3;
		cardJoker[0] = 152 * 13;
		cardJoker[1] = 0;
		cardJoker[2] = 152 * 14;
		cardJoker[3] = 216 * 1;
	}
	
}
