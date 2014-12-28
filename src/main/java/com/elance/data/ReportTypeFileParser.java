package com.elance.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportTypeFileParser {

	private static final String FILE_PATH = "reports_type.txt";
	
	public static List<String> parseData(){
		List<String> result = new ArrayList<>();
		for (String row : readFile(new File(FILE_PATH))) {
				result.add(row);
		}
		return result;
	}
	
	
	private static List<String> readFile(File file) {
		List<String> result = new ArrayList<>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				result.add(line);
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
