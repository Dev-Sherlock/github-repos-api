package com.api.github_repos_api.model;

public record ErrorResponse(
        int status,
        String message
) {}
