package org.konate.telecom3.tutographql.model;

public class Node {
	private String name;
	private String description;
	private String primaryLanguage;
	
	public Node() {
		super();
	}
	public Node(String name, String primaryLanguage) {
		super();
		this.name = name;
		this.primaryLanguage = primaryLanguage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrimaryLanguage() {
		return primaryLanguage;
	}
	public void setPrimaryLanguage(String primaryLanguage) {
		this.primaryLanguage = primaryLanguage;
	}
}