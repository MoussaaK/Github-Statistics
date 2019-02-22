## Get commit details
{
  repository(owner: "MoussaaK", name: "learngraphql") {
    name
    description
    ref(qualifiedName: "master") {
      target {
        ... on Commit {
          history(first: 5) {
            pageInfo {
              hasNextPage
            }
            edges {
              node {
                messageHeadline
                message
                author {
                  name
                  email
                  date
                }
              }
            }
          }
        }
      }
    }
  }
}

## Search JAVA REPOS and COMMITS
{
  search(query: "language:java", type: REPOSITORY, last: 100) {
    repositoryCount
    pageInfo {
      endCursor
      startCursor
      hasNextPage
    }
    edges {
      node {
        ... on Repository {
          nameWithOwner
          primaryLanguage {
            name
          }
          pushedAt
          defaultBranchRef {
            target {
              ... on Commit {
                history {
                  totalCount
                }
              }
            }
          }
        }
      }
    }
  }
}

## Get first 100 Users with FOLLOWERS
{
  search(query: "type:user", first: 100, type: USER) {
    userCount
    pageInfo {
      endCursor
      hasNextPage
    }
    edges {
      node {
        ... on User {
          login
          name
          createdAt
          followers {
            totalCount
          }
        }
      }
    }
  }
}

## List all Repos and their main LANGUAGE
{
  search(query: "is:public", first: 100, type: REPOSITORY) {
    repositoryCount
    pageInfo {
      endCursor
      startCursor
      hasNextPage
    }
    edges {
      node {
        ... on Repository {
          nameWithOwner
          name
          primaryLanguage {
            name
          }
          pushedAt
        }
      }
    }
  }
}

## Issues Per First 100 Users
{
  search(query: "is:public", first: 100, type: ISSUE) {
    repositoryCount
    pageInfo {
      endCursor
      startCursor
      hasNextPage
    }
    edges {
      node {
        ... on Issue {
          createdAt
          title
          url,
          repository {
            name
          }
        }
      }
    }
  }
}

## Number of repos per user
{
  search(query: "type:user", first: 100, type: USER) {
    userCount
    pageInfo {
      endCursor
      hasNextPage
    }
    edges {
      node {
        ... on User {
          login
          name
          createdAt
          repositories {
            totalCount
          }
        }
      }
    }
  }
}


## Forks, Stars and PR per Java repositories
{
  search(query: "language:java stars:>10000", type: REPOSITORY, first: 100) {
    repositoryCount
    pageInfo {
      endCursor
      startCursor
      hasNextPage
    }
    edges {
      node {
        ... on Repository {
          nameWithOwner
          forks {
            totalCount
          }
          pullRequests {
            totalCount
          }
          stargazers {
            totalCount
          }
        }
      }
    }
  }
}

## Organization
{
  repositoryOwner(login: "google") {
    ... on Organization {
      name
      membersWithRole {
        totalCount
      }
      repositories {
        totalCount
      }
    }
  }
}

## Eclipse Foundation's first 100 repos' commit size 
{
  repositoryOwner(login: "eclipse") {
    ... on Organization {
      name
      membersWithRole {
        totalCount
      }
      repositories(first: 100) {
        totalDiskUsage
        totalCount
        nodes {
          ... on Repository {
            defaultBranchRef {
              target {
                ... on Commit {
                  history {
                    totalCount
                  }
                  commitObjects: tree {
                    commits: entries {
                      object {
                        ... on Blob {
                          byteSize
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

## Links

<http://localhost:8080/graphql?query={someCompaniesData{companyName,numberOfMembers,numberOfRepositories,avarageCommitCount,totalRepositoriesDiskUsage}}>

<http://localhost:8080/graphql?query={allUsers{login,name,repositories{totalCount}}}>

<http://localhost:8080/graphql?query={allLinks{url,description}}>

<http://localhost:8080/graphql?query={someRepositories{name,primaryLanguage}}>

<http://localhost:8080/graphql?query={allClosedIssueCount}>

<http://localhost:8080/graphql?query={allOpenIssueCount}>

<http://localhost:8080/graphql?query={allUsersCount}>

<http://localhost:8080/graphql?query={allRepositoryCount}>



