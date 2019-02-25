package org.konate.telecom3.tutographql.model;

public class Language {
	private String name;
	private int languageFrequency;
	
	public Language() {
		super();
	}
	
	public Language(String name, int languageFrequency) {
		super();
		this.name = name;
		this.languageFrequency = languageFrequency;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getLanguageFrequency() {
		return languageFrequency;
	}
	
	public void setLanguageFrequency(int languageFrequency) {
		this.languageFrequency = languageFrequency;
	}
}
