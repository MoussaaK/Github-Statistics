package org.konate.telecom3.tutographql;

import javax.servlet.annotation.WebServlet;

import org.konate.telecom3.tutographql.util.LinkRepository;
import org.konate.telecom3.tutographql.util.Mutation;
import org.konate.telecom3.tutographql.util.Query;

import com.coxautodev.graphql.tools.SchemaParser;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

//The main and only endpoint of the application
@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

	private static final long serialVersionUID = 1L;

	public GraphQLEndpoint() {
        super(buildSchema());
    }
//Builder using schema definition file and the requests models
    private static GraphQLSchema buildSchema() {
        LinkRepository linkRepository = new LinkRepository();
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new Query(linkRepository),
                		   new Mutation(linkRepository))
                .build()
                .makeExecutableSchema();
    }    
    
}

