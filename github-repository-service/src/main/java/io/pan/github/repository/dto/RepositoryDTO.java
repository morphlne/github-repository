package io.pan.github.repository.dto;

public record RepositoryDTO(
        String name,
        boolean fork,
        RepositoryOwnerDTO owner
) {}
