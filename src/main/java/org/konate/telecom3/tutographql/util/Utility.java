package org.konate.telecom3.tutographql.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.konate.telecom3.tutographql.model.Node;
import org.konate.telecom3.tutographql.model.User;

public class Utility {

	//Token read from a file for security constraints
	private static String token = getToken("./token.txt").toString();

	//Using http and the github api to fetch data using 
	//Graphql langage. Saving it in a JSON object 
	public static StringBuilder getQueryResponse(String queryString) {
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		HttpPost httpPost = new HttpPost("https://api.github.com/graphql");
		httpPost.addHeader("Authorization", token);
		httpPost.addHeader("Accept", "application/json");

		JSONObject GraphQLQuery = new JSONObject();
		GraphQLQuery.put("query", queryString);

		StringEntity entity = null;
		StringBuilder builder = new StringBuilder();
		try {
			entity = new StringEntity(GraphQLQuery.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(entity);

		try {
			response = client.execute(httpPost);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try (BufferedReader reader =
				new BufferedReader(new InputStreamReader(response.getEntity()
						.getContent()));){

			String line = null;

			while((line = reader.readLine()) != null){
				builder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Ensure proper release of system resources
		try {
			response.close();
		} catch (IOException e) {
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

	//Fetch more User given the query cursor, resource limitation : 500,000 nodes
	public static List<User> getMoreUsers(JSONObject firstResonseFromGithub, String queryStringBeforeEndCursor, String queryStringAfterEndCursor) {

		boolean hasNextPage = firstResonseFromGithub.getJSONObject("data")
				.getJSONObject("search")
				.getJSONObject("pageInfo")
				.getBoolean("hasNextPage");
		String endCursor = firstResonseFromGithub.getJSONObject("data")
				.getJSONObject("search")
				.getJSONObject("pageInfo")
				.getString("endCursor");

		JSONArray array = null;
		List<JSONArray> jsonArrays = new ArrayList<>();
		List<User> moreUsers = new ArrayList<>();
		String queryString;
		while (hasNextPage) {
			queryString = queryStringBeforeEndCursor + "after:\"" + endCursor + "\"" + queryStringAfterEndCursor;
			JSONObject newResponseFromGithub = new JSONObject(Utility.getQueryResponse(queryString).toString());

			hasNextPage = newResponseFromGithub.getJSONObject("data")
					.getJSONObject("search")
					.getJSONObject("pageInfo")
					.getBoolean("hasNextPage");

			endCursor = newResponseFromGithub.getJSONObject("data")
					.getJSONObject("search")
					.getJSONObject("pageInfo")
					.getString("endCursor");

			array = newResponseFromGithub.getJSONObject("data")
					.getJSONObject("search")
					.getJSONArray("edges");
			jsonArrays.add(array);
		}

		for (JSONArray jsonArray : jsonArrays) {
			for (int i = 0; i < jsonArray.length(); i++) {
				if (!jsonArray.getJSONObject(i).isNull("node")) {
					String name = null;
					try {
						name = jsonArray.getJSONObject(i).getJSONObject("node").getString("name");

					} catch (Exception e) {

					}
					moreUsers.add(new User(jsonArray.getJSONObject(i).getJSONObject("node").getString("login"),
							name));
				}
			}
		}

		return moreUsers;
	}


	//Fetch more Repository given the query cursor, resource limitation : 500,000 nodes
	public static List<Node> getMoreRepositories(JSONObject firstResonseFromGithub, String queryStringBeforeEndCursor, String queryStringAfterEndCursor) {

		boolean hasNextPage = firstResonseFromGithub.getJSONObject("data")
				.getJSONObject("search")
				.getJSONObject("pageInfo")
				.getBoolean("hasNextPage");
		String endCursor = firstResonseFromGithub.getJSONObject("data")
				.getJSONObject("search")
				.getJSONObject("pageInfo")
				.getString("endCursor");
		List<Node> moreRepositories = new ArrayList<>();

		JSONArray array = null;
		List<JSONArray> jsonArrays = new ArrayList<>();
		String queryString;
		while (hasNextPage) {
			queryString = queryStringBeforeEndCursor + "after:\"" + endCursor + "\"" + queryStringAfterEndCursor;
			JSONObject newResponseFromGithub = new JSONObject(Utility.getQueryResponse(queryString).toString());

			hasNextPage = newResponseFromGithub.getJSONObject("data")
					.getJSONObject("search")
					.getJSONObject("pageInfo")
					.getBoolean("hasNextPage");

			endCursor = newResponseFromGithub.getJSONObject("data")
					.getJSONObject("search")
					.getJSONObject("pageInfo")
					.getString("endCursor");

			array = newResponseFromGithub.getJSONObject("data")
					.getJSONObject("search")
					.getJSONArray("repositories");
			jsonArrays.add(array);
		}

		for (JSONArray jsonArray : jsonArrays) {
			for (int i = 0; i < jsonArray.length(); i++) {
				if (!jsonArray.getJSONObject(i).getJSONObject("repository").isNull("primaryLanguage")) {
					moreRepositories.add(new Node(jsonArray.getJSONObject(i).getJSONObject("repository").getString("name"),
							jsonArray.getJSONObject(i).getJSONObject("repository").getJSONObject("primaryLanguage").getString("name")));

				}
			}
		}

		return moreRepositories;
	}
}