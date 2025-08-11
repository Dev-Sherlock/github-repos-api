package com.api.github_repos_api;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubControllerIntegrationTest {

    @LocalServerPort
    int port;

    @Test
    void shouldReturnNonForkReposWithBranchesForExistingUser() {
        // given
        String username = "octocat"; // public user with public repos
        String url = "http://localhost:" + port + "/api/github/users/" + username + "/repos";
        RestTemplate restTemplate = new RestTemplate();

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("repositoryName");
        assertThat(response.getBody()).contains("ownerLogin");
        assertThat(response.getBody()).contains("branches");
        assertThat(response.getBody()).doesNotContain("\"fork\":true");
    }
}
