# Github Statistics

Exploring Github API V4 using GraphQL and making Statistics on github data.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development purposes.

### Prerequisites

Be sure to have Maven installed

### Installing

Clone the repository

```
git clone https://github.com/MoussaaK/Github-Statistics

```

And run jetty

```

cd your-clone-directory

mvn jetty:run

```

And go to the index : <http://localhost:8080/> or <http://localhost:8080/index.html> to see the statistics

## Built With

* [GaphQL](http://graphql.org/) - A query language for your API
* [Maven](https://maven.apache.org/) - Dependency Management
* [JSON](https://www.json.org/) - Used for mapping and forming http posts headers
* [Jetty](https://www.eclipse.org/jetty/) - Jetty Server version : jetty-9.4.6.v20170531

## Versioning

We use [git](https://git-scm.com/) for versioning. 

## Authors

* **KONATE Moussa** - *Initial work* - [MoussaaK](https://github.com/MoussaaK)

* **SOKOBA Aboubacar** - *Initial work* - [ACSokoba](https://github.com/ACSokoba)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

Hat tip to Shards Dashboard Team <https://github.com/DesignRevision/shards-dashboard>

## Quering the local graphql server

<http://localhost:8080/graphql?query={someCompaniesData{companyName,numberOfMembers,numberOfRepositories,avarageCommitCount,totalRepositoriesDiskUsage}}>

<http://localhost:8080/graphql?query={allUsers{login,name,repositories{totalCount}}}>

<http://localhost:8080/graphql?query={allLinks{url,description}}>

<http://localhost:8080/graphql?query={someRepositories{name,primaryLanguage}}>

<http://localhost:8080/graphql?query={allClosedIssueCount}>

<http://localhost:8080/graphql?query={allOpenIssueCount}>

<http://localhost:8080/graphql?query={allUsersCount}>

<http://localhost:8080/graphql?query={allRepositoryCount}>

<http://localhost:8080/graphql?query={someLanguages{name,languageFrequency}}>

<http://localhost:8080/graphql?query={allRepoPerYear{name,languageFrequency}}>

## For more

go to <https://www.howtographql.com/>
