package io.pan.github.repository.service;

import io.pan.github.repository.api.model.RepositoryList;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RepositoryService {

    public Mono<RepositoryList> getRepositories(String username) {
        return Mono.just(
                new RepositoryList()
                        .login(username)
                        .repositories(List.of())
        );
    }

}
