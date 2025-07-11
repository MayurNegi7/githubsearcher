package com.example.githubsearcher.repository;

import com.example.githubsearcher.model.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryRepository extends JpaRepository<RepositoryEntity, Long> {
    List<RepositoryEntity> findByLanguageIgnoreCaseAndStarsGreaterThanEqualOrderByStarsDesc(String language, int stars);
    List<RepositoryEntity> findByLanguageIgnoreCase(String language);
    List<RepositoryEntity> findByStarsGreaterThanEqual(int stars);
    List<RepositoryEntity> findAllByOrderByStarsDesc();
    List<RepositoryEntity> findAllByOrderByForksDesc();
    List<RepositoryEntity> findAllByOrderByLastUpdatedDesc();
}
