package org.konate.telecom3.tutographql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

public class Mutation implements GraphQLRootResolver {
	private final LinkRepository linkRepository;

	public Mutation(LinkRepository linkRepository) {
		this.linkRepository = linkRepository;
	}
	
	public Link createLink(String url, String description) {
		Link link = new Link(url, description);
		linkRepository.saveLink(link);
		return link;
	}
}
