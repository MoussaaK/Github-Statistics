package org.konate.telecom3.tutographql.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.konate.telecom3.tutographql.model.Link;
import org.konate.telecom3.tutographql.model.User;

public class LinkRepository {

	private final List<Link> links;
	private final List<User> users;

	public LinkRepository() {
		links = new ArrayList<>();
		users = new ArrayList<>();
	}

	public List<Link> getAllLinks() {

		JSONObject responseFromGithub = new JSONObject(Utility.getData().toString());
		JSONArray jsonArray = responseFromGithub.getJSONObject("data")
				.getJSONObject("user")
				.getJSONObject("repositories")
				.getJSONArray("nodes");

		for (int i = 0; i < jsonArray.length(); i++) {
			saveLink(new Link(jsonArray.getJSONObject(i).getString("url"),
					jsonArray.getJSONObject(i).getString("description")));
		}

		return links;
	}

	public List<User> getAllUsers() {

		JSONObject responseFromGithub = new JSONObject(Utility.getData().toString());
		JSONArray jsonArray = responseFromGithub.getJSONObject("data")
				.getJSONObject("search")
				.getJSONArray("edges");


		for (int i = 0; i < jsonArray.length(); i++) {
			saveUser(new User(jsonArray.getJSONObject(i).getJSONObject("node").getString("login")));
			
			/*REQUETEE POUR UPDATE LE CURSOR*/
			
			
		}

		return users;
	}




	public void saveLink(Link link) {
		links.add(link);
	}
	
	public void saveUser(User user) {
		users.add(user);
	}
}
