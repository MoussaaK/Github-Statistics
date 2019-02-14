package org.konate.telecom3.tutographql.model;

public class User {
//	private String name;
//    private Repository repositories;
//    
	public User() {
		super();
	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//	
//	public String getName() {
//		return name;
//	}
//
//	public Repository getRepositories() {
//		return repositories;
//	}
//
//	public void setRepositories(Repository repositories) {
//		this.repositories = repositories;
//	}
//
//	@Override
//	public String toString() {
//		return "User [name=" + name + ", repositories=" + repositories + "]";
//	}

	
	private String login;
 
    public User(String login) {
        this.login = login;
    }
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	
}
