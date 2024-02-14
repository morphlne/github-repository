package io.pan.github.repository.controller;

import io.pan.github.repository.api.endpoint.RepositoryApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepositoryController implements RepositoryApi {

    @Override
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

}
