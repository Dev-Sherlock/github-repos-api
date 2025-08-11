package com.api.github_repos_api.model;

public record BranchResponse(
        String name,
        String lastCommitSha
) {}
