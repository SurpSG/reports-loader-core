package com.elance.utils;

import java.util.List;

public class ReportsController {

	public static List<Row> findReports(String company, List<String> reportTypes, List<String> years, String format){
		List<Row> inputRows = DataGatherer.getInputRows(company, reportTypes, years, format);
		return RowMatcher.findMatchingRows(inputRows);
	}
	
}
