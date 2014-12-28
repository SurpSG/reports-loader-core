package com.elance.utils;


import com.elance.gui.CheckBoxReportTypePanel;
import com.elance.gui.CompanyPanel;
import com.elance.gui.FormatPanel;
import com.elance.gui.KeyWordsPanel;
import com.elance.gui.ReportTypePanel;
import com.elance.gui.YearRangePanel;

import java.util.ArrayList;
import java.util.List;

public class DataGatherer {

    private CompanyPanel companyPanel;
    private KeyWordsPanel keyWordsPanel;
    private FormatPanel formatPanel;
    private CheckBoxReportTypePanel reportTypePanel;
    private YearRangePanel yearRangePanel;

    public DataGatherer(CompanyPanel companyPanel, KeyWordsPanel keyWordsPanel,
                        FormatPanel formatPanel, CheckBoxReportTypePanel reportTypePanel, YearRangePanel yearRangePanel) {
        this.companyPanel = companyPanel;
        this.keyWordsPanel = keyWordsPanel;
        this.formatPanel = formatPanel;
        this.reportTypePanel = reportTypePanel;
        this.yearRangePanel = yearRangePanel;
    }
    
    public static List<Row> getInputRows(String company, List<String> reportTypes, List<String> years, String format) {
        List<Row> inputRows = new ArrayList<>();
        for (String reportType : reportTypes) {
            for (String year : years) {
                inputRows.add(new Row(company, reportType, year, format));
            }
        }
        return inputRows;
    }

    private List<Row> getInputRows() {
        String company = companyPanel.getCompanyName();
        List<String> reportTypes = reportTypePanel.getSelectedReportTypes();
        List<String> years = yearRangePanel.getYears();
        String format = formatPanel.getFormat();
        List<Row> inputRows = new ArrayList<>();
        for (String reportType : reportTypes) {
            for (String year : years) {
                inputRows.add(new Row(company, reportType, year, format));
            }
        }
        return inputRows;
    }

}
