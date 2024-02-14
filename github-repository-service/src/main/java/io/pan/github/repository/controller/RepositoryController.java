package io.pan.github.repository.controller;

import io.pan.github.repository.api.endpoint.RepositoryApi;
import io.pan.github.repository.api.model.RepositoryList;
import io.pan.github.repository.service.RepositoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class RepositoryController implements RepositoryApi {

    private final RepositoryService repositoryService;

    public RepositoryController(
            RepositoryService repositoryService
    ) {
        this.repositoryService = repositoryService;
    }

    @Override
    public Mono<ResponseEntity<RepositoryList>> getRepositoriesByUsername(
            String username,
            ServerWebExchange exchange
    ) {
        return repositoryService.getRepositories(username)
                .map(ResponseEntity::ok);
    }

}
