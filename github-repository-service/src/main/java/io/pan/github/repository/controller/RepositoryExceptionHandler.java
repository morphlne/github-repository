package io.pan.github.repository.controller;

import io.pan.github.repository.api.model.Error;
import io.pan.github.repository.client.GithubException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MissingRequestValueException;

@ControllerAdvice
public class RepositoryExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RepositoryExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<Error> handleGithubException(GithubException exception) {
        logger.error("Github API error: " + exception.getMessage());
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
    public ResponseEntity<Error> handleMissingRequestValueException(MissingRequestValueException exception) {
        logger.error("Missing required parameter: " + exception.getMessage());
        return ResponseEntity.status(400)
                .body(new Error()
                        .status(400)
                        .message(exception.getMessage())
                );
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleThrowable(Throwable throwable) {
        logger.error("General error: " + throwable.getMessage());
        return ResponseEntity.status(500)
                .body(new Error()
                        .status(500)
                        .message(throwable.getMessage())
                );
    }

}
