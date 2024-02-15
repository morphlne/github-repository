package io.pan.github.repository.dto;

public record BranchDTO(
        String name,
        CommitDTO commit
) {}
