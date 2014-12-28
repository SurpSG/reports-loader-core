package com.elance.gui;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class FormatPanel extends JPanel{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2481744961416112695L;

	private static final String[] FORMATS = {"PDF", "HTML"};
	
	public static final String PANEL_NAME = "Format";
	
	private ButtonGroup radioButtonGroup;
	private JRadioButton[] radioButtons; 
	
	public FormatPanel(){
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		initRadioButtons();
		
		for (int i = 0; i < radioButtons.length; i++) {
			add(radioButtons[i]);
		}
	}
	
	
	private void initRadioButtons(){
		radioButtonGroup = new ButtonGroup();
		radioButtons = new JRadioButton[FORMATS.length];
		for (int i = 0; i < radioButtons.length; i++) {
			radioButtons[i] = new JRadioButton(FORMATS[i], true);
			radioButtonGroup.add(radioButtons[i]);
		}
	}
	
	private int getSelectedButton(){
		for (int i = 0; i < radioButtons.length; i++) {
			if(radioButtons[i].isSelected()){
				return i;
			}
		}
		return -1;
	}
	
	public String getFormat() {
		int selectedButton = getSelectedButton();
		if(selectedButton >= 0){
			return FORMATS[selectedButton];
		}
		return null;
	}
}
