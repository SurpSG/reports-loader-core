package com.elance.main;

import javax.swing.SwingUtilities;

import com.elance.gui.MyFrame;


public class Main {

public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MyFrame();
			}
		});
	}
}
