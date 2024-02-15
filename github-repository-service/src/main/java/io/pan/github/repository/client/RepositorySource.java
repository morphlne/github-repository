package io.pan.github.repository.client;

import io.pan.github.repository.dto.BranchDTO;
import io.pan.github.repository.dto.RepositoryDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RepositorySource {

    Mono<List<RepositoryDTO>> getRepositories(String username);

    Mono<List<BranchDTO>> getBranches(
            String username,
            String repository
    );

}
