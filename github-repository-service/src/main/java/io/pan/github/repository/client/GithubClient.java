package io.pan.github.repository.client;

import io.pan.github.repository.dto.BranchDTO;
import io.pan.github.repository.dto.ErrorDTO;
import io.pan.github.repository.dto.RepositoryDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class GithubClient implements RepositorySource {

    private final WebClient webClient = WebClient.create("https://api.github.com");

    @Override
    public Mono<List<RepositoryDTO>> getRepositories(String username) {
        return webClient
                .get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        this::wrapIntoGithubException
                )
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    @Override
    public Mono<List<BranchDTO>> getBranches(String username, String repository) {
        return webClient.get()
                .uri("/repos/{username}/{repository}/branches", username, repository)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        this::wrapIntoGithubException
                )
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    private Mono<Error> wrapIntoGithubException(ClientResponse response) {
        return response.bodyToMono(ErrorDTO.class)
                .flatMap(error -> Mono.error(
                        new GithubException(
                                response.statusCode(),
                                error.message()
                        )
                ));
    }

}
