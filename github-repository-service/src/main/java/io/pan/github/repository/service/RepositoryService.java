package io.pan.github.repository.service;

import io.pan.github.repository.api.model.Branch;
import io.pan.github.repository.api.model.Repository;
import io.pan.github.repository.api.model.RepositoryList;
import io.pan.github.repository.client.RepositorySource;
import io.pan.github.repository.dto.BranchDTO;
import io.pan.github.repository.dto.RepositoryDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RepositoryService {

    private final RepositorySource repositorySource;

    public RepositoryService(
            RepositorySource repositorySource
    ) {
        this.repositorySource = repositorySource;
    }

    public Mono<RepositoryList> getRepositories(String username) {
        return repositorySource.getRepositories(username)
                .map(list -> list.stream()
                        .filter(repository -> !repository.fork())
                        .toList()
                )
                .flatMap(repos -> Flux.fromIterable(repos)
                        .flatMap(this::enrichWithBranches)
                        .collectList()
                        .map(withBranches -> new RepositoryList().repositories(withBranches))
                );
    }

    private Mono<Repository> enrichWithBranches(RepositoryDTO repository) {
        return repositorySource.getBranches(
                        repository.owner().login(),
                        repository.name()
                )
                .map(branches -> new Repository()
                        .repositoryOwnerLogin(repository.owner().login())
                        .repositoryName(repository.name())
                        .branches(mapBranches(branches))
                );
    }

    private List<Branch> mapBranches(List<BranchDTO> branches) {
        return branches.stream()
                .map(branch -> new Branch()
                        .branchName(branch.name())
                        .lastCommitSha(branch.commit().sha()))
                .toList();
    }

}
