package org.konate.telecom3.tutographql.model;

public class Company {
	
	private String companyName;
	private int numberOfMembers;
	private int numberOfRepositories;
	private int totalRepositoriesDiskUsage;
	private int avarageCommitCount;

	public Company() {
		super();
	}

	public Company(String companyName,
				   int numberOfMembers, 
				   int numberOfRepositories,
				   int totalRepositoriesDiskUsage,
				   int avarageCommitCount) {
		this.setCompanyName(companyName);
		this.numberOfMembers = numberOfMembers;
		this.numberOfRepositories = numberOfRepositories;
		this.totalRepositoriesDiskUsage = totalRepositoriesDiskUsage;
		this.avarageCommitCount = avarageCommitCount;
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

	public int getTotalRepositoriesDiskUsage() {
		return totalRepositoriesDiskUsage;
	}

	public void setTotalRepositoriesDiskUsage(int totalRepositoriesDiskUsage) {
		this.totalRepositoriesDiskUsage = totalRepositoriesDiskUsage;
	}

	public int getAvarageCommitCount() {
		return avarageCommitCount;
	}

	public void setAvarageCommitCount(int avarageCommitCount) {
		this.avarageCommitCount = avarageCommitCount;
	}
}
