package com.api.github_repos_api;

import com.api.github_repos_api.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/users/{username}/repos")
    public ResponseEntity<?> getUserRepos(@PathVariable String username) {
        try {
            List<RepositoryResponse> repos = githubService.getNonForkRepositories(username);
            return ResponseEntity.ok(repos);
        } catch (GithubService.GithubUserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, ex.getMessage()));
        }
    }
}

