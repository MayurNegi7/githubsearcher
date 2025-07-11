package com.example.githubsearcher.service;

import com.example.githubsearcher.dto.GitHubSearchRequest;
import com.example.githubsearcher.model.RepositoryEntity;
import com.example.githubsearcher.repository.RepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final RepositoryRepository repo;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${github.token}")
    private String GITHUB_TOKEN;

    public List<RepositoryEntity> searchAndSaveRepositories(GitHubSearchRequest request) {
        String url = "https://api.github.com/search/repositories?q=" + request.getQuery()
                + "+language:" + request.getLanguage()
                + "&sort=" + request.getSort()
                + "&per_page=10&page=1"; // Only fetch 10 repos

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github.v3+json");
        headers.set("Authorization", "Bearer " + GITHUB_TOKEN);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        // ✅ Updated condition to correctly throw on non-2xx or null body
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("GitHub API error");
        }

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");
        List<RepositoryEntity> resultList = new ArrayList<>();

        for (Map<String, Object> item : items) {
            Map<String, Object> ownerMap = (Map<String, Object>) item.get("owner");

            RepositoryEntity repoEntity = RepositoryEntity.builder()
                    .id(((Number) item.get("id")).longValue())
                    .name((String) item.get("name"))
                    .description((String) item.get("description"))
                    .owner((String) ownerMap.get("login"))
                    .language((String) item.get("language"))
                    .stars((Integer) item.get("stargazers_count"))
                    .forks((Integer) item.get("forks_count"))
                    .lastUpdated(ZonedDateTime.parse((String) item.get("updated_at")))
                    .build();

            repo.save(repoEntity); // Save or update
            resultList.add(repoEntity);
        }

        return resultList;
    }

    public List<RepositoryEntity> filterRepositories(String language, Integer minStars, String sort) {
        if (language != null && minStars != null) {
            return repo.findByLanguageIgnoreCaseAndStarsGreaterThanEqualOrderByStarsDesc(language, minStars);
        } else if (language != null) {
            return repo.findByLanguageIgnoreCase(language);
        } else if (minStars != null) {
            return repo.findByStarsGreaterThanEqual(minStars);
        }

        return switch (sort) {
            case "forks" -> repo.findAllByOrderByForksDesc();
            case "updated" -> repo.findAllByOrderByLastUpdatedDesc();
            default -> repo.findAllByOrderByStarsDesc();
        };
    }
}
