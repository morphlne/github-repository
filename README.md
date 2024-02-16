### Github token

If you reached [rate limit for unauthenticated user](https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-api?apiVersion=2022-11-28#primary-rate-limit-for-unauthenticated-users), you can provide token to get a higher rate limit

```
github.header.auth: YOUR_TOKEN_HERE
```

The simplest way to get token

1. Go to https://github.com/settings/tokens
2. Click `Generate new token`

More about authentication on [docs.github.com](https://docs.github.com/en/rest/authentication/authenticating-to-the-rest-api)