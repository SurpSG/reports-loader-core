package com.elance.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.pdfbox.util.ExtensionFileFilter;

import com.elance.utils.PropertiesReader;

public class FileChooserPanel extends JPanel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7028083678144074246L;
	
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 20;
	private static final int INPUT_COMPONENTS_WIDTH = 250;
	private static final int INPUT_COMPONENTS_HEIGHT = 20;
	
	protected JFileChooser fileChooser;
	
	private JButton chooseButton;
	protected JTextField chosenFilePathTextField;
	
	private File file;
	
	private String propertyName;
	
	public FileChooserPanel(String propertyName){
		this.propertyName = propertyName;
		initFileChooser();
		initChooseButton();
		initFilePathTextField();
		
		add(chosenFilePathTextField);
		add(chooseButton);
        fileChooser.setCurrentDirectory(new File(getDefaultDir()));
        chosenFilePathTextField.setText(getDefaultDir());

	}

    public File getFile(){
		return file;
	}
	
	protected void initFileChooser(){
		fileChooser = new JFileChooser(".");
		ExtensionFileFilter filter = new ExtensionFileFilter(new String[] { "csv" },"CSV File");
		fileChooser.setFileFilter(filter);
	}
	
	private void initChooseButton(){
		chooseButton = new JButton("Choose");
		chooseButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		chooseButton.addActionListener(new ChooseButtonListener());
	}
	
	private void initFilePathTextField(){
		chosenFilePathTextField = new JTextField();
		chosenFilePathTextField.setEditable(false);
		chosenFilePathTextField.setPreferredSize(new Dimension(INPUT_COMPONENTS_WIDTH, INPUT_COMPONENTS_HEIGHT));
	}
	
	private File chooseFile(){
		int status = fileChooser.showOpenDialog(null);
	    if (status == JFileChooser.APPROVE_OPTION) {
	      return fileChooser.getSelectedFile();
	    }
	    
	    return null;
	}
	
	
	
	private class ChooseButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			File temp_file = chooseFile();
			if(temp_file != null){
				file = temp_file;
				chosenFilePathTextField.setText(file.getAbsolutePath());
				
				String path = file.getAbsolutePath();
				String base = new File(".").getAbsolutePath();
				String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
				PropertiesReader.getInstance().updateProperty(propertyName, relative);
			}
		}
	}
    protected String getDefaultDir() {
        return PropertiesReader.getInstance().getProperty(PropertiesReader.INPUT_PROPERTY_NAME);
    }
}












