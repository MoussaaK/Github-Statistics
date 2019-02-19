package org.konate.telecom3.tutographql.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.konate.telecom3.tutographql.model.Company;
import org.konate.telecom3.tutographql.model.Link;
import org.konate.telecom3.tutographql.model.Repository;
import org.konate.telecom3.tutographql.model.User;

public class LinkRepository {

	private final List<Link> links;
	private final List<User> users;
	private final List<Company> companies;

	public LinkRepository() {
		links = new ArrayList<>();
		users = new ArrayList<>();
		companies = new ArrayList<>();
	}

	public List<Link> getAllLinks()  {
		String queryString = "{user(login: \"MoussaaK\") { name repositories(last: 100) { nodes { url, description}}}}";
		JSONObject responseFromGithub = new JSONObject(Utility.getQueryResponse(queryString).toString());

		JSONArray jsonArray = responseFromGithub.getJSONObject("data")
				.getJSONObject("user")
				.getJSONObject("repositories")
				.getJSONArray("nodes");

		String description;
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				description = jsonArray.getJSONObject(i).getString("description");
			} catch (Exception e) {
				description = "";
			}

			saveLink(new Link(jsonArray.getJSONObject(i).getString("url"),
					description));
		}

		return links;
	}

	public List<User> getAllUsers() {
		String queryString = "{ search(query: \"type:user\", first: 100, type: USER) { userCount pageInfo { endCursor hasNextPage } edges { node { ... on User { login name repositories { totalCount }} } } } }";
		JSONObject responseFromGithub = new JSONObject(Utility.getQueryResponse(queryString).toString());
		JSONArray jsonArray = responseFromGithub.getJSONObject("data")
				.getJSONObject("search")
				.getJSONArray("edges");

		for (int i = 0; i < jsonArray.length(); i++) {
			Repository repository = new Repository(jsonArray.getJSONObject(i)
					.getJSONObject("node")
					.getJSONObject("repositories")
					.getInt("totalCount"));

			saveUser(new User(jsonArray.getJSONObject(i).getJSONObject("node").getString("login"),
					jsonArray.getJSONObject(i).getJSONObject("node").getString("name"),
					repository));
		}

		/*REQUETEE POUR UPDATE LE CURSOR*/


		return users;
	}

	/*Getting data related to a company's github account*/
	public List<Company> getCompanyData() {

		String queryString = "{\n" + 
				"  repositoryOwner(login: \"google\") {\n" + 
				"    ... on User {\n" + 
				"      avatarUrl\n" + 
				"      bio\n" + 
				"    }\n" + 
				"    ... on Organization {\n" + 
				"      name\n" + 
				"      members {\n" + 
				"        totalCount\n" + 
				"      }\n" + 
				"      repositories {\n" + 
				"        totalCount\n" + 
				"      }\n" + 
				"    }\n" + 
				"  }\n" + 
				"}";
		JSONObject responseFromGithub = new JSONObject(Utility.getQueryResponse(queryString).toString());
		JSONObject jsonNode = responseFromGithub.getJSONObject("data");

		saveCompany(
				new Company(
						jsonNode.getJSONObject("repositoryOwner").getString("name"),
						jsonNode.getJSONObject("members").getInt("totalcount"),
						jsonNode.getJSONObject("repositories").getInt("totalcount")
						)
				);

		return companies;
	}

	public void saveLink(Link link) {
		links.add(link);
	}

	public void saveUser(User user) {
		users.add(user);
	}

	public void saveCompany(Company company) {
		companies.add(company);
	}
}
