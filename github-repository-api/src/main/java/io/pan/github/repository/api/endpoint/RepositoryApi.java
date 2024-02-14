package io.pan.github.repository.api.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface RepositoryApi {

    @GetMapping("/test")
    ResponseEntity<String> test();

}