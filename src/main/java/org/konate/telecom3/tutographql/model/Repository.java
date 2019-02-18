package org.konate.telecom3.tutographql.model;

public class Repository {
	private Nodes node;
	private int totalCount;

	public Repository() {
		super();
	}
	
	public Repository(int totalCount) {
		super();
		this.totalCount = totalCount;
	}

	public Nodes getNode() {
		return node;
	}

	public void setNode(Nodes node) {
		this.node = node;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
