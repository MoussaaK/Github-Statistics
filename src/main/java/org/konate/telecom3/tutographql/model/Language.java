package org.konate.telecom3.tutographql.model;

public class Language implements Comparable<Language>{
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + languageFrequency;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		if (languageFrequency != other.languageFrequency)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public int compareTo(Language language) {
	  return Integer.compare(languageFrequency, language.languageFrequency);
	}
}
