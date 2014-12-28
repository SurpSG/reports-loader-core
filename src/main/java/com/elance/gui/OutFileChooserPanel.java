package com.elance.gui;

import com.elance.utils.PropertiesReader;

import javax.swing.JFileChooser;
import java.io.File;
import java.util.Locale;

public class OutFileChooserPanel extends FileChooserPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7028083678144074246L;
	
	public OutFileChooserPanel(String propertyName){
		super(propertyName);
        fileChooser.setCurrentDirectory(new File(PropertiesReader.getInstance().getProperty(PropertiesReader.OUTPUT_PROPERTY_NAME)));
	}
	
	protected void initFileChooser(){
		fileChooser = new JFileChooser(".");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}
    protected String getDefaultDir() {
        return PropertiesReader.getInstance().getProperty(PropertiesReader.OUTPUT_PROPERTY_NAME);
    }

}
