package com.vorobiev.graphics;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

public class Screen extends Component {
	public Screen() {
		dim = new Dimension(AbstractDraw.width, AbstractDraw.height);
	}
	private static final long serialVersionUID = 1L;
	private Dimension dim;
	@Override
	public Dimension getPreferredSize() {
		return dim;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		AbstractDraw.drawAll(g);
	}
}
