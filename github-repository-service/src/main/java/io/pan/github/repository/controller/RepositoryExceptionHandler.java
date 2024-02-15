package io.pan.github.repository.controller;

import io.pan.github.repository.api.model.Error;
import io.pan.github.repository.client.GithubException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RepositoryExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Error> handleGithubException(GithubException exception) {
        var status = exception.getStatus().value() == 404
                ? 404
                : 502;
        return ResponseEntity.status(status)
                .body(new Error()
                        .status(exception.getStatus().value())
                        .message("Github API error: " + exception.getMessage())
                );
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleThrowable(Throwable throwable) {
        return ResponseEntity.status(500)
                .body(new Error()
                        .status(500)
                        .message(throwable.getMessage())
                );
    }

}
