package com.elance.utils;


import java.util.ArrayList;
import java.util.List;

public class RowMatcher {
    private static final String PDF = "pdf";

    public static List<Row> findMatchingRows(List<Row> inputRows) {
        List<Row> matchedRows = new ArrayList<>();
        CsvParser csvParser = new CsvParser();
        List<Row> parsedRows = csvParser.parseRows(PropertiesReader.getInstance().getProperty("database"));

        for (Row inputRow : inputRows)
            for (Row parsedRow : parsedRows) {
                if (match(inputRow, parsedRow)) {
                    parsedRow.setFormat(inputRow.getFormat());
                    matchedRows.add(parsedRow);
                }
            }
        return matchedRows;
    }

    private static boolean match(Row inputRow, Row parsedRow) {
        return inputRow.getCompanyName().equalsIgnoreCase(parsedRow.getCompanyName()) &&
                isFormatMatched(parsedRow, inputRow) &&
                inputRow.getSourceType().equalsIgnoreCase(parsedRow.getSourceType()) &&
                inputRow.getYear().equalsIgnoreCase(parsedRow.getYear());
    }

    private static boolean isFormatMatched(Row parsedRow, Row inputRow) {
        if (inputRow.getFormat().equalsIgnoreCase(PDF)) {
            return parsedRow.getUrl().toLowerCase().endsWith(inputRow.getFormat().toLowerCase());
        } else {
            return !parsedRow.getUrl().toLowerCase().endsWith(PDF);
        }
    }
}
