package com.elance.gui;


import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 * Date: 19.08.14
 * Time: 1:29
 */
public class CheckBoxReportTypePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 14600394577467984L;
	private List<JCheckBox> checkBoxes = new ArrayList<>();
    
    public CheckBoxReportTypePanel(List<String> reportTypes) {
    	setLayout(new FlowLayout(FlowLayout.LEFT));
    	Box box = Box.createVerticalBox();
        for (String reportType : reportTypes) {
            JCheckBox checkBox = new JCheckBox(reportType);
            box.add(checkBox);
            checkBoxes.add(checkBox);
        }
        add(box);
    }

    public List<String> getSelectedReportTypes() {
        List<String> selectedReportTypes = new ArrayList<>();
        for (JCheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected())
                selectedReportTypes.add(checkBox.getText());
        }
        return selectedReportTypes;
    }
}