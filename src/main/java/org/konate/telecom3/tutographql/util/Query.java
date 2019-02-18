package org.konate.telecom3.tutographql.util;

import java.util.List;

import org.konate.telecom3.tutographql.model.Company;
import org.konate.telecom3.tutographql.model.Link;
import org.konate.telecom3.tutographql.model.User;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

public class Query implements GraphQLRootResolver {
    
    private final LinkRepository linkRepository;

    public Query(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> allLinks() {
        return linkRepository.getAllLinks();
    }
    
    public List<User> allUsers() {
        return linkRepository.getAllUsers();
    }
    
    public List<Company> getCompanyData() {
        return linkRepository.getCompanyData();
    }
}
