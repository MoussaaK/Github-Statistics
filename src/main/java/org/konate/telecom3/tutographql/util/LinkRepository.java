package org.konate.telecom3.tutographql.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.konate.telecom3.tutographql.model.Company;
import org.konate.telecom3.tutographql.model.Language;
import org.konate.telecom3.tutographql.model.Link;
import org.konate.telecom3.tutographql.model.Node;
import org.konate.telecom3.tutographql.model.Repository;
import org.konate.telecom3.tutographql.model.User;

public class LinkRepository {

	private final List<Link> links;
	private final List<User> users;
	private final List<Company> companies;
	private final List<Node> repositories;
	private final List<Language> languages;

	public LinkRepository() {
		links = new ArrayList<>();
		users = new ArrayList<>();
		companies = new ArrayList<>();
		repositories = new ArrayList<>();
		languages = new ArrayList<>();
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
			if (!jsonArray.getJSONObject(i).isNull("node")) {

				String name = null;
				try {
					name = jsonArray.getJSONObject(i).getJSONObject("node").getString("name");

				} catch (Exception e) {

				}
				saveUser(new User(jsonArray.getJSONObject(i).getJSONObject("node").getString("login"),
						name,
						repository));
			}
		}

		//Fetching more Users
		//Exception thrown because some names are chinese => not String
		users.addAll(Utility.getMoreUsers(responseFromGithub,
				"{ search(query: \"type:user\", first: 100,",
				" type: USER) { userCount pageInfo { endCursor hasNextPage } edges { node { ... on User { login name repositories { totalCount }} } } } }"));

		return users;
	}

	/*Getting data related to some companies' github account*/
	public List<Company> getSomeCompaniesData() {
		String queryString = null;
		String[] companiesName = {"microsoft", "facebook", "oracle", "eclipse", "linkedin", "alibaba", "docker", "heroku", "redhatofficial", "cloudera"};
		for (String name : companiesName) {
			queryString = "{ repositoryOwner(login: " + name + ") { ... on Organization { name members { totalCount } repositories(first: 100) { totalDiskUsage totalCount nodes { ... on Repository { defaultBranchRef { commitCount: target { ... on Commit { history { totalCount } } } } } } }}}}"; 
			JSONObject responseFromGithub = new JSONObject(Utility.getQueryResponse(queryString).toString());
			JSONObject jsonNode = responseFromGithub.getJSONObject("data");
			JSONObject node = jsonNode.getJSONObject("repositoryOwner");

			JSONArray nodes = node.getJSONObject("repositories").getJSONArray("nodes");
			int avarageCommitCount = 0;
			for (int i = 0; i < nodes.length(); i++) {
				if (!nodes.getJSONObject(i).isNull("defaultBranchRef")) {
					avarageCommitCount += nodes.getJSONObject(i)
							.getJSONObject("defaultBranchRef")
							.getJSONObject("commitCount")
							.getJSONObject("history")
							.getInt("totalCount");
				}
			}
			avarageCommitCount = avarageCommitCount/nodes.length();
			saveCompany(
					new Company(
							node.getString("name"),
							node.getJSONObject("members").getInt("totalCount"),
							node.getJSONObject("repositories").getInt("totalCount"),
							node.getJSONObject("repositories").getInt("totalDiskUsage"),
							avarageCommitCount)
					);
		}
		List<Company> companiesTmp = companies.stream()
				.distinct()
				.collect(Collectors.toList());
		return companiesTmp;
	}

	public List<Node> getSomeRepositories() {
		String queryString = "{ search(query: \"is:public stars:>10000\", first: 100, type: REPOSITORY) { repositoryCount pageInfo { endCursor startCursor hasNextPage } repositories: edges { repository: node { ... on Repository { nameWithOwner name primaryLanguage { name } } } } } }";
		JSONObject responseFromGithub = new JSONObject(Utility.getQueryResponse(queryString).toString());
		JSONArray jsonArray = responseFromGithub.getJSONObject("data")
				.getJSONObject("search")
				.getJSONArray("repositories");

		for (int i = 0; i < jsonArray.length(); i++) {
			if (!jsonArray.getJSONObject(i).getJSONObject("repository").isNull("primaryLanguage")) {
				saveRepository(new Node(jsonArray.getJSONObject(i).getJSONObject("repository").getString("name"),
						jsonArray.getJSONObject(i).getJSONObject("repository").getJSONObject("primaryLanguage").getString("name")));
			}
		}

		//Fetching more Repositories
		repositories.addAll(Utility.getMoreRepositories(responseFromGithub,
				"{ search(query: \"is:public stars:>10000\", first: 100, ",
				", type: REPOSITORY) { repositoryCount pageInfo { endCursor startCursor hasNextPage } repositories: edges { repository: node { ... on Repository { nameWithOwner name primaryLanguage { name } } } } } }"));
		return repositories;
	}

	public int getAllUsersCount() {
		String queryString = "{ search(query: \"type:user\", first: 100, type: USER) { userCount } }";
		JSONObject responseFromGithub = new JSONObject(Utility.getQueryResponse(queryString).toString());

		return  responseFromGithub.getJSONObject("data")
				.getJSONObject("search")
				.getInt("userCount");
	}

	public int getAllRepositoryCount() {
		String queryString = "{ search(query: \"is:public\", first: 100, type: REPOSITORY) { repositoryCount } }";
		JSONObject responseFromGithub = new JSONObject(Utility.getQueryResponse(queryString).toString());

		return  responseFromGithub.getJSONObject("data")
				.getJSONObject("search")
				.getInt("repositoryCount");
	}

	public int getAllOpenIssueCount() {
		String queryString = "{ search(query: \"type:issue is:open\", first: 100, type: ISSUE) { issueCount } }";
		JSONObject responseFromGithub = new JSONObject(Utility.getQueryResponse(queryString).toString());

		return  responseFromGithub.getJSONObject("data")
				.getJSONObject("search")
				.getInt("issueCount");
	}

	public int getAllClosedIssueCount() {
		String queryString = "{ search(query: \"type:issue is:closed\", first: 100, type: ISSUE) { issueCount } }";
		JSONObject responseFromGithub = new JSONObject(Utility.getQueryResponse(queryString).toString());

		return  responseFromGithub.getJSONObject("data")
				.getJSONObject("search")
				.getInt("issueCount");
	}

	public List<Language> getSomeLanguages(){
		getSomeRepositories();
		List<String> strings = new ArrayList<>();
		repositories.forEach(repo -> strings.add(repo.getPrimaryLanguage()));

		Map<String, Integer> frequencies = 
				strings.stream().collect(Collectors.groupingBy(Function.identity(),
						Collectors.summingInt(s -> 1)));
		for (Entry<String, Integer> entry : frequencies.entrySet()) {
			languages.add(new Language(entry.getKey(), entry.getValue()));
		}
		
		return languages;
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

	public void saveRepository(Node repository) {
		repositories.add(repository);
	}
}