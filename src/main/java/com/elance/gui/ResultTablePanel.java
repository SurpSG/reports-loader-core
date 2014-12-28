package com.elance.gui;

import com.elance.utils.Row;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ResultTablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8555091637741108014L;

	private static final String[] ROW_COLUMN_NAMES = { "company name",
			"report type", "year", "URL" };
	private static final String[] KEYWORDS_COLUMN_NAMES = { "keywords", "sentence"};

	private static final int MIN_COLUMN_WIDTH = 120;

	private DefaultTableModel rowTableModel;
	private JTable rowTable;
	private JScrollPane rowScrollPane;

	private DefaultTableModel keywordsTableModel;
	private JTable keywordsTable;
	private JScrollPane keywordsScrollPane;


	private Map<Row, Map<String, Set<String>>> dat;

	/**
	 * 
	 */
	public ResultTablePanel() {

		setLayout(new GridLayout(2, 1));
		initAndAddTables(new Object[][] {}, new Object[][] {});
	}

	/**
	 * 
	 * @param rowData
	 * @param keywordsData
	 */
	private void initAndAddTables(Object[][] rowData, Object[][] keywordsData) {
		initRowTable(rowData);
		initKeywordsTable(keywordsData);
	}

	/**
	 * 
	 * @param data
	 */
	private void initRowTable(Object[][] data) {
		if (rowScrollPane != null) {
			remove(rowScrollPane);
		}
		rowTableModel = new DefaultTableModel(data, ROW_COLUMN_NAMES);
		rowTable = new JTable(rowTableModel);

		rowTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnsWidthByContent(rowTable, data);

		rowTable.getSelectionModel().addListSelectionListener(
				new RowTableSelectionListener());
		
		if(data.length > 0){
			rowTable.setRowSelectionInterval(0, 0);
			showKeywordTableForSelectedRow(0);
		}
		
		rowScrollPane = new JScrollPane(rowTable);

		rowScrollPane.setBorder(new TitledBorder("Documents"));

		add(rowScrollPane);
	}

	/**
	 * 
	 * @param data
	 */
	private void initKeywordsTable(Object[][] data) {
		if (keywordsScrollPane != null) {
			remove(keywordsScrollPane);
		}
		
		keywordsTableModel = new DefaultTableModel(data, KEYWORDS_COLUMN_NAMES);
		keywordsTable = new JTable(keywordsTableModel);

		keywordsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnsWidthByContent(keywordsTable, data);
		keywordsScrollPane = new JScrollPane(keywordsTable);

		keywordsScrollPane.setBorder(new TitledBorder("Keywords"));

		add(keywordsScrollPane);
	}

	/**
	 * 
	 * @param table
	 * @param data
	 */
	private void setColumnsWidthByContent(JTable table, Object[][] data) {
		for (int column = 0; column < table.getColumnCount(); column++) {
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = tableColumn.getMinWidth();
			int maxWidth = tableColumn.getMaxWidth();

			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer cellRenderer = table.getCellRenderer(row,
						column);
				Component c = table.prepareRenderer(cellRenderer, row, column);
				int width = c.getPreferredSize().width
						+ table.getIntercellSpacing().width;
				preferredWidth = Math.max(preferredWidth, width);

				if (preferredWidth >= maxWidth) {
					preferredWidth = maxWidth;
					break;
				}
			}

			if (data.length < 1) {
				preferredWidth = (preferredWidth < MIN_COLUMN_WIDTH) ? MIN_COLUMN_WIDTH
						: preferredWidth;
			}
			tableColumn.setPreferredWidth(preferredWidth);
		}
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	private Object[][] rowCollectionToObjectArray(Collection<Row> list) {
		Object[][] result = new Object[list.size()][];

		int i = 0;
		for (Row row : list) {
			result[i] = new Object[4];
			result[i][0] = row.getCompanyName();
			result[i][1] = row.getSourceType();
			result[i][2] = row.getYear();
			result[i][3] = row.getUrl();
			i++;
		}

		return result;
	}

	/**
	 * 
	 * @param keywordsSentences
	 * @return
	 */
	private Object[][] keywordsCollectionToObjectArray(
			Map<String, Set<String>> keywordsSentences) {
		if(keywordsSentences.size() < 1){
			return new Object[][]{};
		}
		Object[][] result = new Object[keywordsSentences.size()][];

		int i = 0;
		for (String sentence : keywordsSentences.keySet()) {
			result[i] = new Object[2];
			result[i][1] = sentence;
			String buff = "";
			for (String keyword : keywordsSentences.get(sentence)) {
				buff += ", " + keyword;
			}
			buff = (buff.length() < 1) ? buff : buff.substring(2);
			result[i][0] = buff;
			i++;
		}

		return result;
	}

	public void setKeywordsData(Map<String, Set<String>> keywordsSentences) {
		keywordsCollectionToObjectArray(keywordsSentences);
	}

	public void setData(Map<Row, Map<String, Set<String>>> data) {
		dat = data;
		
		Object[][] rowData = rowCollectionToObjectArray(data.keySet());
		initRowTable(rowData);
		
		Object[][] keywordsData = new Object[0][2];
		for (Row str : dat.keySet()) {
			keywordsData = keywordsCollectionToObjectArray(dat.get(str));
			break;
		}
		
		if(keywordsData != null){
			initKeywordsTable(keywordsData);
		}
	}
	
	private void showKeywordTableForSelectedRow(int selectedRowIndex){
		int i = 0;
		for (Row row : dat.keySet()) {
			if (i == selectedRowIndex) {
				Object[][] keywordsData = keywordsCollectionToObjectArray(dat.get(row));
				initKeywordsTable(keywordsData);
				break;
			}
			i++;
		}
	}

	private class RowTableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			showKeywordTableForSelectedRow(rowTable.getSelectedRow());
			MyFrame.repaintFrame();
		}
	}
}
