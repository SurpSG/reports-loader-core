package com.elance.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class YearRangePanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 2094288362262643653L;

    public static final String PANEL_NAME = "Year range";

    private static final int INPUT_COMPONENTS_WIDTH = 120;
    private static final int INPUT_COMPONENTS_HEIGHT = 45;

    private JComboBox<String> from;
    private JComboBox<String> to;

    private List<String> years;

    public YearRangePanel() {
        years = createYearsList();

        setLayout(new FlowLayout(FlowLayout.LEFT));

        initComboBoxes();

//		add(panelName);
        add(from);
        add(to);
    }

    private void initComboBoxes() {
        from = new JComboBox<>(new Vector<>(years));
        from.addItemListener(new FromItemListener());
        from.setBorder(new TitledBorder("From"));
        from.setPreferredSize(new Dimension(INPUT_COMPONENTS_WIDTH, INPUT_COMPONENTS_HEIGHT));


        to = new JComboBox<>(new Vector<>(years));
        to.addItemListener(new ToItemListener());
        to.setPreferredSize(new Dimension(INPUT_COMPONENTS_WIDTH, INPUT_COMPONENTS_HEIGHT));
        to.setBorder(new TitledBorder("To"));
    }

    private List<String> createYearsList() {
        List<String> years = new ArrayList<>();
        years.add("");
        int start = 1990;
        int end = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = start; i <= end; i++) {
            years.add(i + "");
        }

        return years;
    }

    private void addItems(Collection<String> items, JComboBox<String> comboBox) {
        for (String string : items) {
            comboBox.addItem(string);
        }
    }

    private class FromItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) e.getItem();
                int itemIndex = years.indexOf(item);
                cut_TO_Range(itemIndex);
            }
        }


        private void cut_TO_Range(int from) {
            String selected_TO_item = (String) to.getSelectedItem();
            to.removeAllItems();
            List<String> newYearsRange = new ArrayList<>();
            newYearsRange.add("");
            newYearsRange.addAll(years.subList(from, years.size()));
            addItems(newYearsRange, to);

            if (years.indexOf(selected_TO_item) >= from) {
                to.setSelectedItem(selected_TO_item);
            }
        }
    }

    private class ToItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
            }
        }

    }

    public List<String> getYears() {
        List<String> years = new ArrayList<>();

        Integer fromYear = getValueFromInputField(from);
        Integer toYear = getValueFromInputField(to);
        if(toYear < fromYear){
        	toYear = fromYear;
        	to.setSelectedItem(toYear);
        }

        for (int i = fromYear; i <= toYear; i++) {
            years.add(String.valueOf(i));
        }
        return years;
    }

    
    private Integer getValueFromInputField(JComboBox<String> input){
    	String value = (String) input.getSelectedItem();
    	if(value.length() > 0){
    		return Integer.parseInt(value);
    	}else{
    		String firstItem = (String) input.getItemAt(1);
    		input.setSelectedItem(firstItem);
    		return Integer.parseInt(firstItem);
    	}
    }

}


















