# github-repos-api

## Overview

This Spring Boot 3.5 (Java 21) application exposes a REST API that lists all **non-fork** repositories for a given GitHub user. For each repository, it returns:

- Repository name
- Owner login
- For each branch: branch name and last commit SHA

If the GitHub user does not exist, the API returns a 404 response with a JSON error message.

---

## API Endpoint

### List User Repositories

GET /api/github/users/{username}/repos

- **Path parameter:**  
  `username` — GitHub username to query

### Successful Response Example

```json
[
  {
    "repositoryName": "Hello-World",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "e5bd3914e2e596debea16f433f57875b5b90bcd6"
      }
    ]
  }
]
```
### Error Response Example (User Not Found)
```json
{
  "status": 404,
  "message": "User not found"
}
```
## How to Run
### Prerequisites
* Java 21
* Maven 3.x

### Steps
1) Clone the repository:
```
git clone https://github.com/Dev-Sherlock/github-repos-api
cd github-repo-api
```
2) Build and run the application:
```
mvn spring-boot:run
```
3) Access the API at:
```
http://localhost:8080/api/github/users/{username}/repos
```
Replace {username} with the GitHub username you want to query.
## Testing
The project includes one integration test that verifies the succesfull path scenario by querying a real GitHub user and checking the response structure.
Run tests with:
```
mvn test
```
