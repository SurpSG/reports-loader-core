package com.elance.gui;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Collection;
import java.util.Set;
import java.util.Vector;

public class CompanyPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044157451338048754L;

	public static final String PANEL_NAME = "Company name";

	private static final int INPUT_COMPONENTS_WIDTH = 250;
	private static final int INPUT_COMPONENTS_HEIGHT = 20;
	
	private Set<String> companies;

	private JTextField companyNameFilter;
	private JComboBox<String> companiesList;
	private JLabel companyFilterLabel;
	private JLabel companyNameLabel;
	
	public CompanyPanel(Set<String> companies) {
		this.companies = companies;

		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		initCompanyNameTextField();
		initCompaniesListComboBox();
		initJLabels();
		
		Box box = Box.createVerticalBox();
		box.add(companyFilterLabel);
		JPanel companyFilterComponents = new JPanel();
		companyFilterComponents.add(companyNameFilter);
		companyFilterComponents.setLayout(new FlowLayout(FlowLayout.LEFT));
		box.add(companyFilterComponents);
		
		box.add(companyNameLabel);
		JPanel companyListComponents = new JPanel();
		companyListComponents.add(companiesList);
		companyListComponents.setLayout(new FlowLayout(FlowLayout.LEFT));
		box.add(companyListComponents);
		
		add(box);
	}

	private void initCompanyNameTextField() {
		companyNameFilter = new JTextField();
		companyNameFilter.getDocument().addDocumentListener(new MyTextFieldListener());
		companyNameFilter.setPreferredSize(new Dimension(INPUT_COMPONENTS_WIDTH, INPUT_COMPONENTS_HEIGHT));
	}

	private void initCompaniesListComboBox() {
		companiesList = new JComboBox<>(new Vector<>(companies));
		companiesList.setPreferredSize(new Dimension(INPUT_COMPONENTS_WIDTH, INPUT_COMPONENTS_HEIGHT));
	}

	private void initJLabels() {
		companyFilterLabel = new JLabel("Company name filter");
		companyNameLabel = new JLabel("Choose company name*");
	}
	
	
	private void addItems(Collection<String> items){
		for (String string : items) {
			companiesList.addItem(string);
		}
	}
	
	public String getCompanyName(){
		return (String)companiesList.getSelectedItem();
	}
	
	
	/**
	 * 
	 * @author 
	 *
	 */
	private class MyTextFieldListener implements DocumentListener{

		public void changedUpdate(DocumentEvent e) {
			warn();
		}

		public void removeUpdate(DocumentEvent e) {
			warn();
		}

		public void insertUpdate(DocumentEvent e) {
			warn();
		}

		public void warn() {
			String key = companyNameFilter.getText();
			Vector<String> sievedByKey = sieveListByKey(key, companies);
			companiesList.removeAllItems();
			addItems(sievedByKey);
		}
		
		private Vector<String> sieveListByKey(String key, Collection<String> data){
			Vector<String> sieved = new Vector<>();
			
			for (String value : data) {
				if(value.toLowerCase().contains(key.toLowerCase())){
					sieved.add(value);
				}
			}
			return sieved;
		}

	}
}














