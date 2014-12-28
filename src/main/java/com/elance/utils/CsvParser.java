package com.elance.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 18.08.14
 * Time: 1:26
 */
public class CsvParser {
    public List<Row> parseRows(String filename) {
        String line = "";
        String cvsSplitBy = ";";
        List<Row> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        	br.readLine();//skip header row
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                rows.add(new Row(data[0], data[1], data[2], null, data[3]));
            }

        } catch (FileNotFoundException e) {
            ErrorMessageShower.showError("Cannot find the database file");
        } catch (IOException e) {
            ErrorMessageShower.showError("Cannot find the database file");
        }
        return rows;
    }
}
