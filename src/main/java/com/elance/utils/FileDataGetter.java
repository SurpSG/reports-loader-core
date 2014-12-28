package com.elance.utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileDataGetter {
    public List<String> getDataFromFile(String fileName) {
        List<String> result = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str;
            while ((str = in.readLine()) != null)
                result.add(str);
            in.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Cannot find file " + fileName);
        }
        return result;
    }
}
