package com.elance.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

	private static final String SEPARATOR = ";";
	private File file; 
	
	public FileParser(String filePath){
		file = new File(filePath);
	}
	
	public List<Data> parseData(){
		List<Data> result = new ArrayList<>();
		int line = 0;
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
	
	private Data parseRow(String[] row) throws IndexOutOfBoundsException {
		Data data = new Data();
		data.setKey(row[0]);
		data.setValue(row[1]);
		return data;
	}
	
	private List<String[]> readFile(File file) {
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
	
	
	public static void main(String[] args) {
		FileParser p = new FileParser("1.txt");
		System.out.println(p.parseData());
	}
}
