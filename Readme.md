## Get commit count
{
  repository(owner: "MoussaaK", name: "learngraphql") {
    object(expression:"master") {
      ... on Commit {
        history {
          totalCount
        }
      }
    }
    languages(last:5) {
      totalCount
      nodes {
        name
        color
        id
      }
    }
  }
}


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



## Search java repos
{
  rateLimit {
    cost
    limit
    remaining
    resetAt
  }
  search(query: "java", type: REPOSITORY, first: 10) {
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
          primaryLanguage{
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



