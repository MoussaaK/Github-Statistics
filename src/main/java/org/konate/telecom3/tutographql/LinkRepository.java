package org.konate.telecom3.tutographql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

public class LinkRepository {
    
    private final List<Link> links;

    public LinkRepository() {
        links = new ArrayList<>();
    }
    
    public void getData() {
    	CloseableHttpClient client= null;
		CloseableHttpResponse response= null;

		client= HttpClients.createDefault();
		HttpPost httpPost= new HttpPost("https://api.github.com/graphql");

		httpPost.addHeader("Authorization","Bearer 4fc69cbe8a3f12c7a41c217235210362527b6fc1");
		httpPost.addHeader("Accept","application/json");

		JSONObject GraphQLQuery = new JSONObject(); 
		GraphQLQuery.put("query", "query {user(login: \"MoussaaK\") { name repositories(last: 10) { nodes { url description}}}}");

		StringEntity entity = null;
		try {
			entity = new StringEntity(GraphQLQuery.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpPost.setEntity(entity);
		try {
			response = client.execute(httpPost);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			BufferedReader reader= new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line= null;
			StringBuilder builder= new StringBuilder();
			while((line=reader.readLine())!= null){

				builder.append(line);

			}
			
			JSONObject responseFromGithub = new JSONObject(builder.toString());
			JSONArray jsonArray = responseFromGithub.getJSONObject("data")
											  .getJSONObject("user")
											  .getJSONObject("repositories")
											  .getJSONArray("nodes");
			
			for (int i = 0; i < jsonArray.length(); i++) {
				saveLink(new Link(jsonArray.getJSONObject(i).getString("url"),
								   jsonArray.getJSONObject(i).getString("description")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public List<Link> getAllLinks() {
    	getData();
        return links;
    }
    
    public void saveLink(Link link) {
        links.add(link);
    }
}
