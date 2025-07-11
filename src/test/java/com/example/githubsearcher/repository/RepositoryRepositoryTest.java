package com.example.githubsearcher.repository;

import com.example.githubsearcher.model.RepositoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // 👈 uses your real PostgreSQL DB

class RepositoryRepositoryTest {

    @Autowired
    private RepositoryRepository repo;

    @Test
    void testSaveAndFind() {
        repo.deleteAll(); // clear database before test

        RepositoryEntity entity = RepositoryEntity.builder()
                .id(999L)
                .name("test-repo")
                .description("test")
                .owner("test-owner")
                .language("Java")
                .stars(123)
                .forks(45)
                .lastUpdated(ZonedDateTime.now())
                .build();

        repo.save(entity);

        List<RepositoryEntity> results = repo.findByLanguageIgnoreCase("Java");

        assertFalse(results.isEmpty());
        assertEquals("test-repo", results.get(0).getName());
    }

}
