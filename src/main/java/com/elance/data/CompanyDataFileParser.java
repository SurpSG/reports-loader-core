package com.elance.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDataFileParser {

	private static final String SEPARATOR = ";";
	private static final String FILE_PATH = "companies_data.txt";
	
	public static List<KeyValueData> parseData(){
		List<KeyValueData> result = new ArrayList<>();
		int line = 0;
		File file = new File(FILE_PATH);
		for (String[] row : readFile(file)) {
			line++;
			try {
				result.add(parseRow(row));
			} catch (IndexOutOfBoundsException e) {
				System.err.format("incorrect data in file: %s, line:%s", file,line);
			}
			
		}
		
		return result;
	}
	
	private static KeyValueData parseRow(String[] row) throws IndexOutOfBoundsException {
		KeyValueData data = new KeyValueData();
		data.setKey(row[0]);
		data.setValue(row[1]);
		return data;
	}
	
	private static List<String[]> readFile(File file) {
		List<String[]> result = new ArrayList<>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				String[] textline = line.split(SEPARATOR);
				result.add(textline);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return result;
	}
}
