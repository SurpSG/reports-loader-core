package com.elance.utils;

import com.elance.PDFParser.PDFParserApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Date: 16.08.14 Time: 21:50
 */
public class FileScraper {
	public static void saveFile(String year, String address, String dir)
			throws IOException {
		URL url = new URL(address);
		InputStream in = url.openStream();
		FileOutputStream fos = new FileOutputStream(new File(extractPageName(
				year, address, dir)));

		System.out.println("reading file...");
		int length = -1;
		byte[] buffer = new byte[1024];// buffer for portion of data from
		// connection
		while ((length = in.read(buffer)) > -1) {
			fos.write(buffer, 0, length);
		}
		fos.close();
		in.close();
		System.out.println("file was downloaded");
	}

	private static String extractPageName(String year, String url, String dir) {
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		return dir + "/" + year + "_" + url.substring(url.lastIndexOf('/') + 1);
	}

	public static Map<Row, Map<String, Set<String>>> downloadReports(List<Row> rows, String dir, String format, List<String> keywords) {
		switch (format.toLowerCase()) {
		case "pdf":
			return downloadPdfReports(rows, keywords, dir);
		case "html":
			return downloadHtmlReports(rows, dir, keywords);
		}
		
		return null;
	}

	private static Map<Row, Map<String, Set<String>>> downloadHtmlReports(List<Row> rows, String dir, List<String> keywords) {
		Map<Row, Map<String, Set<String>>> documentsSentencesKeywords = new LinkedHashMap<>();
		for (Row row : rows) {
			String fileName = row.getCompanyName()+"."+row.getSourceType()+"."+row.getYear();
			HtmlSaver saver = new HtmlSaver(fileName,row.getUrl(), dir, keywords);
			try {
				saver.saveHTML();
				Map<String, Set<String>> sentenceKeywords = saver.getFoundedDocuments();
				if(sentenceKeywords != null){
					documentsSentencesKeywords.put(row, sentenceKeywords);
				}
			} catch (IOException e) {
				ErrorMessageShower.showError("Can not save file: "+row.getUrl());
				e.printStackTrace();
			} catch (URISyntaxException e) {
				ErrorMessageShower.showError("Incorrect URL: "+row.getUrl());
				e.printStackTrace();
			}
		}
		return documentsSentencesKeywords;
	}

	private static Map<Row, Map<String, Set<String>>> downloadPdfReports(List<Row> rows, List<String> keywords, String dir) {
            return PDFParserApp.parse(rows, keywords, dir);

    }
}
