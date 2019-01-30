package org.konate.telecom3.tutographql;

import com.coxautodev.graphql.tools.GraphQLResolver;

public class Mutation implements GraphQLResolver<Link> {
	private final LinkRepository linkRepository;

	public Mutation(LinkRepository linkRepository) {
		super();
		this.linkRepository = linkRepository;
	}
	
	public Link createLink(String url, String description) {
		Link link = new Link(url, description);
		linkRepository.saveLink(link);
		return link;
	}
}
