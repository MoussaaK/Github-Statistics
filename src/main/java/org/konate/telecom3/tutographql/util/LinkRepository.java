package org.konate.telecom3.tutographql.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.konate.telecom3.tutographql.model.Link;

 //This is where the responses from the requests are going to be stored
public class LinkRepository {

	private final List<Link> links;

	public LinkRepository() {
		links = new ArrayList<>();
	}

	
	//Processing the data retrieved from the JSON
	//Then saving it
	public List<Link> getAllLinks() {

		JSONObject responseFromGithub = new JSONObject(Utility.getData().toString());
		JSONArray jsonArray = responseFromGithub.getJSONObject("data")
				.getJSONObject("user")
				.getJSONObject("repositories")
				.getJSONArray("nodes");

		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				saveLink(new Link(jsonArray.getJSONObject(i).getString("url"),
						jsonArray.getJSONObject(i).getString("description")));
			}
			catch(JSONException e){
				e.printStackTrace();
			}
		}

		return links;
	}

	public void saveLink(Link link) {
		links.add(link);
	}
}
