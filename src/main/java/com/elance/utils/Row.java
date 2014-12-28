package com.elance.utils;

public class Row {
    private String companyName;
    private String sourceType;
    private String year;
    private String format;
    private String url;

    public Row() {
    }

    public Row(String companyName, String sourceType, String year, String format, String url) {
        this.companyName = companyName;
        this.sourceType = sourceType;
        this.year = year;
        this.format = format;
        this.url = url;
    }
    public Row(String companyName, String sourceType, String year, String format) {
        this.companyName = companyName;
        this.sourceType = sourceType;
        this.year = year;
        this.format = format;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

	@Override
	public String toString() {
		return "Row [companyName=" + companyName + ", sourceType=" + sourceType
				+ ", year=" + year + ", format=" + format + ", url=" + url
				+ "]";
	}
    
    
}
