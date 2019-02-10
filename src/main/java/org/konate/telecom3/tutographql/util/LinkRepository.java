package org.konate.telecom3.tutographql.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.konate.telecom3.tutographql.model.Link;

public class LinkRepository {
    
    private final List<Link> links;

    public LinkRepository() {
        links = new ArrayList<>();
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
    
    public void saveLink(Link link) {
        links.add(link);
    }
}
