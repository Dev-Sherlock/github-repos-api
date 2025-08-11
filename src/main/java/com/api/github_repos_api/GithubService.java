package com.api.github_repos_api;
import com.api.github_repos_api.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.*;

@Service
public class GithubService {

    private final RestTemplate restTemplate;
    private final String githubApiBaseUrl;

    public GithubService(@Value("${github.api.base-url}") String githubApiBaseUrl) {
        this.restTemplate = new RestTemplate();
        this.githubApiBaseUrl = githubApiBaseUrl;
    }

    public List<RepositoryResponse> getNonForkRepositories(String username) {
        String url = githubApiBaseUrl + "/users/" + username + "/repos";
        ResponseEntity<GithubRepo[]> response;
        try {
            response = restTemplate.getForEntity(url, GithubRepo[].class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new GithubUserNotFoundException("User not found");
        }
        GithubRepo[] repos = response.getBody();
        if (repos == null) return Collections.emptyList();

        List<RepositoryResponse> result = new ArrayList<>();
        for (GithubRepo repo : repos) {
            if (!repo.fork) {
                List<BranchResponse> branches = getBranches(repo.owner.login, repo.name);
                result.add(new RepositoryResponse(repo.name, repo.owner.login, branches));
            }
        }
        return result;
    }

    private List<BranchResponse> getBranches(String owner, String repoName) {
        String url = githubApiBaseUrl + "/repos/" + owner + "/" + repoName + "/branches";
        ResponseEntity<GithubBranch[]> response = restTemplate.getForEntity(url, GithubBranch[].class);
        GithubBranch[] branches = response.getBody();
        if (branches == null) return Collections.emptyList();

        List<BranchResponse> branchResponses = new ArrayList<>();
        for (GithubBranch branch : branches) {
            branchResponses.add(new BranchResponse(branch.name, branch.commit.sha));
        }
        return branchResponses;
    }

    // Internal DTOs for GitHub API mapping
    private static class GithubRepo {
        public String name;
        public Owner owner;
        public boolean fork;
        static class Owner { public String login; }
    }
    private static class GithubBranch {
        public String name;
        public Commit commit;
        static class Commit { public String sha; }
    }

    public static class GithubUserNotFoundException extends RuntimeException {
        public GithubUserNotFoundException(String message) { super(message); }
    }
}
