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
