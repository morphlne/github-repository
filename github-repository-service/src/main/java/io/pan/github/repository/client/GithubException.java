package io.pan.github.repository.client;

import org.springframework.http.HttpStatusCode;

public class GithubException extends RuntimeException {

    private final HttpStatusCode status;

    public GithubException(HttpStatusCode status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

}
