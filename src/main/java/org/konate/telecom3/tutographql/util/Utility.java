package org.konate.telecom3.tutographql.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class Utility {
	
	public static StringBuilder getData() {
    	CloseableHttpClient client= null;
		CloseableHttpResponse response= null;

		client= HttpClients.createDefault();
		HttpPost httpPost= new HttpPost("https://api.github.com/graphql");
		
		String token = Utility.getToken("./files/token.txt").toString();
		httpPost.addHeader("Authorization", token);
		httpPost.addHeader("Accept", "application/json");

		JSONObject GraphQLQuery = new JSONObject(); 
		GraphQLQuery.put("query",
						 "query {user(login: \"MoussaaK\") { name repositories(last: 10) { nodes { url description}}}}");

		StringEntity entity = null;
		StringBuilder builder= new StringBuilder();
		try {
			entity = new StringEntity(GraphQLQuery.toString());
		} catch (UnsupportedEncodingException e) {
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
			
			while((line=reader.readLine())!= null){
				builder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder;
    }
	
	public static String getToken(String fileName) {
		Path path = Paths.get(fileName);
		try (Stream<String> lines = Files.lines(path)) {
			return lines.collect(Collectors.joining(""));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	} 
}
