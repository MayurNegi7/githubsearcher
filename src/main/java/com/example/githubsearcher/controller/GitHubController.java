package com.example.githubsearcher.controller;

import com.example.githubsearcher.dto.GitHubSearchRequest;
import com.example.githubsearcher.model.RepositoryEntity;
import com.example.githubsearcher.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/github")
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService gitHubService;

    @PostMapping("/search")
    public Map<String, Object> search(@RequestBody GitHubSearchRequest request) {
        List<RepositoryEntity> repos = gitHubService.searchAndSaveRepositories(request);
        return Map.of("message", "Repositories fetched and saved successfully", "repositories", repos);
    }

    @GetMapping("/repositories")
    public Map<String, Object> getRepos(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Integer minStars,
            @RequestParam(defaultValue = "stars") String sort
    ) {
        List<RepositoryEntity> repos = gitHubService.filterRepositories(language, minStars, sort);
        return Map.of("repositories", repos);
    }
}
