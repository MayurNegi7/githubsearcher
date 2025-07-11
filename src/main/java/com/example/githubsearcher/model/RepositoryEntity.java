package com.example.githubsearcher.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepositoryEntity {

    @Id
    private Long id;

    private String name;
    private String description;
    private String owner;
    private String language;
    private int stars;
    private int forks;

    private ZonedDateTime lastUpdated;
}
