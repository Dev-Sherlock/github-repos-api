package com.api.github_repos_api.model;
import java.util.List;



public record RepositoryResponse(
        String repositoryName,
        String ownerLogin,
        List<BranchResponse> branches
) {}
