package com.example.githubsearcher.controller;

import com.example.githubsearcher.service.GitHubService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.githubsearcher.dto.GitHubSearchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GitHubController.class)
class GitHubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GitHubService gitHubService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSearchEndpoint() throws Exception {
        GitHubSearchRequest req = new GitHubSearchRequest();
        req.setQuery("spring");
        req.setLanguage("Java");
        req.setSort("stars");

        when(gitHubService.searchAndSaveRepositories(req)).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/github/search")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Repositories fetched and saved successfully"));
    }
}
