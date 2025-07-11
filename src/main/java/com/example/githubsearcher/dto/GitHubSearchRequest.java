package com.example.githubsearcher.dto;

import lombok.Data;

@Data
public class GitHubSearchRequest {
    private String query;
    private String language;
    private String sort; // stars, forks, updated
}
