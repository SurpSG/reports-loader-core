package com.elance.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlKeywordsSearcher {

	private Document document;
	private List<String> keywords;
	private Map<String, Set<String>> foundedDocuments;//key - sentence, value keywords separated by comma
	
	public HtmlKeywordsSearcher(Document document, List<String> keywords){
		this.document = document;
		this.keywords = keywords;
		foundedDocuments = new LinkedHashMap<>();
		find();
	}
	
	
	public Map<String, Set<String>> getFoundedDocuments() {
		return foundedDocuments;
	}



	private void find(){
		for (String keyword : keywords) {
			Elements elements = document.getElementsContainingOwnText(keyword);
			for (Element element : elements) {
				List<String> sentences = extractSentencesFromTextWithKeyword(element.text(), keyword);
				addSentenceWithKeywordToMap(keyword, sentences);
			}
		}
	}
	
	private List<String> extractSentencesFromTextWithKeyword(String text, String keyword){
		List<String> result = new ArrayList<>();
		
		String[] sentences = text.split("(?i)(?<=[.?!])\\S+(?=[a-z])");
		for (String sentence : sentences) {
			if(sentence.toLowerCase().contains(keyword.toLowerCase())){
				result.add(sentence);
			}
		}
		
		return result;
	}
	
	private void addSentenceWithKeywordToMap(String keyword, List<String> sentences){
		for (String sentence : sentences) {
			if(foundedDocuments.containsKey(sentence)){
				foundedDocuments.get(sentence).add(keyword);
			}else{
				Set<String> keywords = new LinkedHashSet<>();
				keywords.add(keyword);
				foundedDocuments.put(sentence, keywords);
			}
		}
	}
	
}
