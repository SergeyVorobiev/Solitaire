package com.vorobiev.graphics;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public MyFrame(Screen world) {
		super("Пасьянс");	
		//действи¤ при закрытии, добавление мира, установка размера и позиции
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(world, BorderLayout.CENTER);
		this.pack();
		this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2 - 20);
		this.setVisible(true);
	}
}