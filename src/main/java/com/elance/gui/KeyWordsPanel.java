package com.elance.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class KeyWordsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3795295413441576947L;

	public static final String PANEL_NAME = "Keywords";
	
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 20;
	private static final int INPUT_COMPONENTS_WIDTH = 250;
	private static final int INPUT_COMPONENTS_HEIGHT = 20;
	private static final int INPUT_ROWS_COUNT = 4;
	
	private DefaultComboBoxModel<String> listModel;
	private JList<String> keyWordsBoxList;
	private JTextField newKeyWordField;
	private JButton addButton;
	private JButton deleteButton;

	private JLabel addNewKeywordLabel;
	private JLabel addedKeywordsLabel;
	
	private List<String> keyWords;
	
	public KeyWordsPanel() {
		keyWords = new ArrayList<>();
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		initKeyWordsBoxList();
		initKeyWordsField();
		initAddButton();
		initDeleteButton();
		initLables();
		
		Box box = Box.createVerticalBox();
		box.add(addNewKeywordLabel);
		
		JPanel newKeywordComponents = new JPanel();
		newKeywordComponents.add(newKeyWordField);
		newKeywordComponents.add(addButton);
		newKeywordComponents.setLayout(new FlowLayout(FlowLayout.LEFT));
		box.add(newKeywordComponents);
		
		box.add(addedKeywordsLabel);
		
		JPanel addedKeywordsComponents = new JPanel();
		JScrollPane boxListScroll = new JScrollPane(keyWordsBoxList);
		boxListScroll.setPreferredSize(new Dimension(INPUT_COMPONENTS_WIDTH, INPUT_COMPONENTS_HEIGHT * INPUT_ROWS_COUNT));
		addedKeywordsComponents.add(boxListScroll);
		addedKeywordsComponents.add(deleteButton);
		addedKeywordsComponents.setLayout(new FlowLayout(FlowLayout.LEFT));
		box.add(addedKeywordsComponents);
		
		add(box);
	}
	
	private void initLables() {
		addNewKeywordLabel = new JLabel("Add keyword");
		addedKeywordsLabel = new JLabel("Added keywords");
	}
	
	private void initKeyWordsBoxList() {
		listModel = new  DefaultComboBoxModel<>();
		keyWordsBoxList = new JList<>(listModel);
	}

	private void initKeyWordsField() {
		newKeyWordField = new JTextField();
		newKeyWordField.setPreferredSize(new Dimension(INPUT_COMPONENTS_WIDTH, INPUT_COMPONENTS_HEIGHT));
	}

	private void initAddButton() {
		addButton = new JButton("Add");
		addButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		addButton.addActionListener(new AddButtonListener());
	}

	private void initDeleteButton() {
		deleteButton = new JButton("Delete");
		deleteButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		deleteButton.addActionListener(new DeleteButtonListener());
	}
	
	public List<String> getKeyWords(){
		return keyWords;
	}
	
	
	private class AddButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String word = newKeyWordField.getText();
			if(word.replace(" ", "").length() > 0 && !keyWords.contains(word)){
				listModel.addElement(word);
				keyWords.add(word);
				newKeyWordField.setText("");
			}
		}
		
	}
	
	private class DeleteButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = keyWordsBoxList.getSelectedIndex();
			if(index >=0 ){
				listModel.removeElementAt(index);
				keyWords.remove(index);
			}
		}
		
	}
	
}























