package com.example.githubsearcher.service;

import com.example.githubsearcher.dto.GitHubSearchRequest;
import com.example.githubsearcher.repository.RepositoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GitHubServiceTest {

    @Mock
    private RepositoryRepository repositoryRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitHubService gitHubService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnExceptionOnInvalidAPI() {
        // Arrange
        GitHubSearchRequest request = new GitHubSearchRequest();
        request.setQuery("invalidsearch");
        request.setLanguage("Java");
        request.setSort("stars");

        ResponseEntity<Map> mockResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Map.class)
        )).thenReturn(mockResponse);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            gitHubService.searchAndSaveRepositories(request);
        });
    }
}
