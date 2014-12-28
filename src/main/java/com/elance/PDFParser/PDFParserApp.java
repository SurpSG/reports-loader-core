package com.elance.PDFParser;

import com.elance.utils.ErrorMessageShower;
import com.elance.utils.FileUtils;
import com.elance.utils.Row;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PDFParserApp {

    public static HttpClient client = new HttpClient();
    public static Map<Row, Map<String, Set<String>>> parse(List<Row> companies, List<String> keyWords, String dir) {
        Map<Row, Map<String, Set<String>>> companyKeywordMap = new HashMap<>();
        if (keyWords == null || keyWords.isEmpty()) {
            for (Row row : companies) {
                companyKeywordMap.put(row, new HashMap<String, Set<String>>());
            }
            return companyKeywordMap;
        }
        client.getParams().setSoTimeout(10000);
        client.getParams().setConnectionManagerTimeout(10000L);
        String copyTo = dir + "/";

        int counter = 0;

        for (Row company : companies) {
            String companyName = company.getCompanyName();
            ++counter;
            System.out.println("Searching for pdf #: " + counter);
            String url = company.getUrl();
            String filename = company.getYear() + url.substring(url.lastIndexOf("/") + 1).trim();
            String parsedText = downloadPhoto(url, copyTo, filename);
            parsedText = downloadPdf(copyTo, filename);
            parsedText = parsedText.replaceAll("\\r", " ");
            parsedText = parsedText.replaceAll("\\\\r", " ");
            parsedText = parsedText.replaceAll("\\n", " ");
            parsedText = parsedText.replaceAll("\\\\n", " ");
            parsedText = parsedText.replaceAll("\\s+", " ").trim();
            String[] sentences = parsedText.split("(?<=[.?!])\\s+(?=[a-zA-Z])");

            Map<String, Set<String>> sentenceKeywords = new HashMap<>();
            for (String sentence : sentences) {
                Set<String> keywordSet = new HashSet<>();

                for (String keyword : keyWords) {
                    if (sentence.toLowerCase().indexOf(keyword.toLowerCase()) >= 0) {
                        keywordSet.add(keyword);
                    }
                }
                if (!keywordSet.isEmpty()) {
                    sentenceKeywords.put(sentence, keywordSet);
                }
            }
            if (!sentenceKeywords.isEmpty()) {
                companyKeywordMap.put(company, sentenceKeywords);
            } else {
                FileUtils.deleteFile(copyTo + filename);
            }
        }

        return companyKeywordMap;
    }

    private static String downloadPdf(String copyTo, String filename) {
       String parsedText = "";
        COSDocument cosDoc = null;
        try {
            PDFParser parser = new PDFParser(new FileInputStream(new File(copyTo + filename)));
            parser.parse();
            cosDoc = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
        } catch (Exception e) {
            ErrorMessageShower.showError("Error downloading PDF " + filename);
        }
        finally {
            if (cosDoc!=null) {
                try {
                    cosDoc.close();
                } catch (IOException e) {
                    ErrorMessageShower.showError("Error closing PDF " + filename);
                }
            }

        }
        return parsedText;
    }

    public static String downloadPhoto(String address, String path, String fileName) {
        try {
            URL html = new URL(address);
            URI method = new URI(html.getProtocol(), html.getUserInfo(), html.getHost(), html.getPort(), html.getPath(), html.getQuery(), html.getRef());
            address = method.toASCIIString();
        } catch (Exception var16) {
            ;
        }

        String var19 = "";
        if (!path.endsWith("\\")) {
            path = path + "\\";
        }

        GetMethod var20 = null;

        for (int j = 0; j < 20; ++j) {
            try {
                var19 = "";
                var20 = new GetMethod(address);
                client.executeMethod(var20);
                byte[] ex = var20.getResponseBody();
                FileOutputStream e = new FileOutputStream(path + fileName);
                e.write(ex);
                e.close();
                break;
            } catch (Exception var17) {
                var17.printStackTrace();
                System.out.println(address);
                if (var17.getClass().toString().indexOf("UnknownHostException") >= 0) {
                    try {
                        --j;
                        Thread.sleep(10000L);
                    } catch (InterruptedException var15) {
                        var15.printStackTrace();
                    }
                } else if (var17.getClass().toString().indexOf("FileNotFoundException") >= 0) {
                    j = 111;
                } else {
                    try {
                        Thread.sleep(10000L);
                    } catch (InterruptedException var14) {
                        var14.printStackTrace();
                    }
                }
            } finally {
                if (var20 != null) {
                    var20.releaseConnection();
                }

            }
        }

        return var19;
    }

    public static String getHTML(String address) {
        String html = "";
        GetMethod method = null;

        for (int j = 0; j < 20; ++j) {
            try {
                html = "";
                method = new GetMethod(address);
                method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:19.0) Gecko/20100101 Firefox/19.0");
                client.executeMethod(method);
                byte[] ex = method.getResponseBody();
                html = new String(ex);
                break;
            } catch (Exception var13) {
                var13.printStackTrace();
                System.out.println(address);
                if (var13.getClass().toString().indexOf("UnknownHostException") < 0 && var13.getClass().toString().indexOf("Connect") < 0 && var13.getClass().toString().indexOf("Socket") < 0) {
                    if (var13.getClass().toString().indexOf("FileNotFoundException") >= 0) {
                        j = 111;
                    } else {
                        try {
                            Thread.sleep(10000L);
                        } catch (InterruptedException var11) {
                            var11.printStackTrace();
                        }
                    }
                } else {
                    try {
                        --j;
                        Thread.sleep(10000L);
                    } catch (InterruptedException var12) {
                        var12.printStackTrace();
                    }
                }
            } finally {
                method.releaseConnection();
            }
        }

        return html;
    }

    private static String inputfile = "inin.txt";
    private static String outfile = "thefile.txt";
    private static String folder = "output";

    public static void main(String[] args) throws Exception {
//        parse(outfile,inputfile, folder);
        Row row = new Row();
        row.setCompanyName("Anglo American");
        row.setUrl("http://www.angloamerican.com/~/media/Files/A/Anglo-American-Plc/reports/angloamerican-sd-reporthjz-2010.pdf");
        List<Row> rows = new ArrayList<>();
        rows.add(row);
        List<String> keywords = new ArrayList<>();
        keywords.add("our core");
        Map<Row, Map<String, Set<String>>> parsed = parse(rows, keywords, "output");
        for (Map.Entry<Row, Map<String, Set<String>>> entry : parsed.entrySet()) {
            System.out.print(entry.getKey() + " ");
            for (Map.Entry<String, Set<String>> keyWordEntry : entry.getValue().entrySet()) {
                System.out.print(keyWordEntry.getKey() + " ");
                for (String keyword : keyWordEntry.getValue()) {
                    System.out.print(keyword + " ");
                }
            }
            System.out.println();
        }
    }
}
