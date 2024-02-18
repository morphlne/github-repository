# Github repository

Provides repositories and their branches by username

### How to run

Java 21 & Maven should be accessible on $PATH

```
mvn install
cd github-repository-service
mvn spring-boot:run
```

Request example
```
curl -i -X GET http://localhost:8080/repositories?username=morphlne
```

Response example
```json
{
  "repositories":[
    {
      "repositoryOwnerLogin":"morphlne",
      "repositoryName":"graph-api-authentication",
      "branches":[
        {
          "branchName":"master",
          "lastCommitSha":"761a3bf3c68168dfebdf0db5cab5d8c295c91a23"
        }
      ]
    }
  ]
}
```

### Swagger UI

Swagger UI is accessible on http://localhost:8080/swagger-ui.html and OpenAPI spec on http://localhost:8080/api.yaml

### Project structure

Project consists of 2 submodules:

1. github-repository-api

The API module is completely generated from [OpenAPI](https://spec.openapis.org/oas/v3.0.3) specification using [openapi-generator-maven-plugin](https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-maven-plugin/README.md)

Run `mvn verify` to generate

2. github-repository-service

The Service module is based on [Spring WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux.html) - [Project Reactor](https://projectreactor.io/) adoption for Spring ecosystem

Important components of the Service module:
* [WebClientConfiguration](https://github.com/morphlne/github-repository/blob/master/github-repository-service/src/main/java/io/pan/github/repository/configuration/WebClientConfiguration.java) - general behavior for all requests - headers, logging etc
* [GithubClient](https://github.com/morphlne/github-repository/blob/master/github-repository-service/src/main/java/io/pan/github/repository/client/GithubClient.java) - behavior for specific request - URI path, parameters etc
* [RepositoryService](https://github.com/morphlne/github-repository/blob/master/github-repository-service/src/main/java/io/pan/github/repository/service/RepositoryService.java) - sequence of calls & applied business-rules (like skip forks)

### Github REST API

Github REST API is used as a source of information about repositories and branches
* [List repositories for a user](https://docs.github.com/en/rest/repos/repos#list-repositories-for-a-user)
* [List branches](https://docs.github.com/en/rest/branches/branches)

### Github token

If you reached [rate limit for unauthenticated user](https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-api?apiVersion=2022-11-28#primary-rate-limit-for-unauthenticated-users), you can provide token to get a higher rate limit

```
github.header.auth: YOUR_TOKEN_HERE
```

The simplest way to get token

1. Go to https://github.com/settings/tokens
2. Click `Generate new token`

More about authentication on [docs.github.com](https://docs.github.com/en/rest/authentication/authenticating-to-the-rest-api)

### What's next

* Add [pagination](https://docs.github.com/en/rest/using-the-rest-api/using-pagination-in-the-rest-api) support. For now, only the first 100 repositories and the first 100 branches in each repository will be returned
* Retry on error & circuit breaker
* Implement cache 
* Username validation ([pattern](https://github.com/shinnn/github-username-regex))
* Unit & integration tests
