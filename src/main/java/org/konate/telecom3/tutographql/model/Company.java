package org.konate.telecom3.tutographql.model;

public class Company {
	private String companyName;
	private int numberOfMembers;
	private int numberOfRepositories;

	public Company() {
		super();
	}

	public Company(String companyName, int numberOfMembers,int numberOfRepositories) {
		this.setCompanyName(companyName);
		this.numberOfMembers = numberOfMembers;
		this.numberOfMembers = numberOfMembers;

	}

	public int getNumberOfMembers() {
		return numberOfMembers;
	}

	public void setNumberOfMembers(int numberOfMembers) {
		this.numberOfMembers = numberOfMembers;
	}

	public int getNumberOfRepositories() {
		return numberOfRepositories;
	}

	public void setNumberOfRepositories(int numberOfRepositories) {
		this.numberOfRepositories = numberOfRepositories;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


}
