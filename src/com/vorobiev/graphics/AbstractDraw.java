package com.vorobiev.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.vorobiev.entity.Card;
import com.vorobiev.resources.Assets;

public abstract class AbstractDraw implements GraphicsCard {
	public static final Font f = new Font(Font.SANS_SERIF, 20, 20);
	public static final int width = 1200;
	public static final int height = 600;
	private static BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage dbuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	public static void clear() {
		Graphics2D g2 = (Graphics2D)bi.getGraphics();
		g2.drawImage(Assets.table, 0, 0, bi.getWidth(), bi.getHeight(), null);
	}
	
	protected static void paintEntity(Image im, int[] coords, int x, int y, int width, int height) {
		int halfWidth = width / 2;
		int halfHeight = height / 2;
		Graphics2D g2 = (Graphics2D)bi.getGraphics();
		g2.drawImage(im,  x - halfWidth, y - halfHeight, x + halfWidth, y + halfHeight,
				coords[0], coords[1], coords[2], coords[3], null);
	}
	public static synchronized void swap() {
		BufferedImage temp = dbuffer;
		dbuffer = bi;
		bi = temp;
	}
	public static synchronized void drawAll(Graphics g) {
		g.drawImage(dbuffer, 0, 0, null);
	}
	public static void drawString(String str, int x, int y) {
		Graphics g = bi.getGraphics();
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawString(str, x, y);
	}
	protected static void paint(Card c) {
		int number = c.getNumber();
		int halfWidth = c.getWidth() / 2;
		int halfHeight = c.getHeight() / 2;
		int x = c.getPosX();
		int y = c.getPosY();
		Graphics2D g2 = (Graphics2D)bi.getGraphics();
		if(!c.isVisibled()) {
			g2.drawImage(Assets.cards, x - halfWidth, y - halfHeight, x + halfWidth, y + halfHeight, 
					Assets.cardDown[0], Assets.cardDown[1], Assets.cardDown[2], 
					Assets.cardDown[3], null);
		} else {
		g2.drawImage(Assets.cards, x - halfWidth, y - halfHeight, x + halfWidth, y + halfHeight, 
				Assets.cardCoords[number][0], Assets.cardCoords[number][1], Assets.cardCoords[number][2], 
				Assets.cardCoords[number][3], null);
		}
	}
}
