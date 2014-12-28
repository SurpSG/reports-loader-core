package com.elance.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ReportTypePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8932543114271351092L;

	public static final String PANEL_NAME = "Type of report";
	
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 20;
	private static final int INPUT_COMPONENTS_WIDTH = 250;
	private static final int INPUT_COMPONENTS_HEIGHT = 20;
	private static final int INPUT_ROWS_COUNT = 4;
	
	private JLabel chooseReportLabel ;
	private JLabel chosenReportLabel ;
	
	
	private JComboBox<String> dataList;
	private DefaultComboBoxModel<String> listModel;
	private JList<String> selectedItems;
	private JButton removeButton;
	
	private List<String> items;

    List<JComboBox> comboBoxes = new ArrayList<>();

	public ReportTypePanel(List<String> items) {
		this.items = new ArrayList<>();
		this.items.add("");
		this.items.addAll(items);

		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		initComboBox();
		initJList();
		initRemoveButton();
		initLabels();

		Box box = Box.createVerticalBox();
		
		
		box.add(chooseReportLabel);
		JPanel reportsListComponents = new JPanel();
		reportsListComponents.setLayout(new FlowLayout(FlowLayout.LEFT));
		reportsListComponents.add(dataList);
		box.add(reportsListComponents);
		
		box.add(chosenReportLabel);
		
		JPanel selectedReportsListComponents = new JPanel();
		selectedReportsListComponents.setLayout(new FlowLayout(FlowLayout.LEFT));
		JScrollPane selectedItemsListScroll = new JScrollPane(selectedItems);
		selectedItemsListScroll.setPreferredSize(new Dimension(INPUT_COMPONENTS_WIDTH, INPUT_COMPONENTS_HEIGHT * INPUT_ROWS_COUNT));
		selectedReportsListComponents.add(selectedItemsListScroll);
		selectedReportsListComponents.add(removeButton);
		box.add(selectedReportsListComponents);
		
		add(box);
		
	}
	
	private void initLabels(){
		chooseReportLabel = new JLabel("Choose report");
		chosenReportLabel = new JLabel("Chosen reports");
	}

	private void initComboBox() {
		dataList = new JComboBox<>();
		dataList.setPreferredSize(new Dimension(INPUT_COMPONENTS_WIDTH, INPUT_COMPONENTS_HEIGHT));
		addItems(items);
		dataList.setSelectedItem(0);
		
		dataList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					
					String item = (String) e.getItem();
					
					if(item.length() < 1){
						return;
					}
					
					addItemToJList(item);
					removeItemFromList(item);
					
					dataList.setSelectedItem(0);
				}
			}
		});

	}
	
	private void addItems(Collection<String> items){
		for (String string : items) {
			dataList.addItem(string);
		}
	}

	private void addItemToJList(String item) {
		listModel.addElement(item);
	}
	
	
	private void removeItemFromList(String item) {
		Vector<String> vector = new Vector<>();
		for (int i = 0; i < dataList.getItemCount(); i++) {
			String tempItem = dataList.getItemAt(i);
			if(!tempItem.equals(item)){
				vector.add(tempItem);
			}
		}
		
		dataList.removeAllItems();
		addItems(vector);
	}


	private void initJList() {
		listModel = new  DefaultComboBoxModel<>();
		selectedItems = new JList<String>(listModel);
//		selectedItems.setPreferredSize(new Dimension(INPUT_COMPONENTS_WIDTH, ROWS_COUNT * INPUT_COMPONENTS_HEIGHT));
	}
	
	private void initRemoveButton(){
		removeButton = new JButton("remove");
		removeButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = selectedItems.getSelectedIndex();
				if(index >=0 ){
					dataList.addItem(selectedItems.getSelectedValue());
					listModel.removeElementAt(index);
				}
			}
		});
	}
	
	public List<String> getSelectedData(){
		List<String> data = new ArrayList<>();
		
		for (int i = 0; i < listModel.getSize(); i++) {
			data.add(listModel.getElementAt(i));
		}
		return data;
	}
	
}
