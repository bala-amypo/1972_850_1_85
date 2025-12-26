package com.example.demo.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "rating_results")
public class RatingResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(nullable = false)
    private Double finalRating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RatingCategory ratingCategory;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(nullable = false)
    private LocalDateTime ratedAt;

    @Column(nullable = false)
    private String calculatorVersion = "1.0";

    @PrePersist
    protected void onCreate() {
        ratedAt = LocalDateTime.now();
    }

    public enum RatingCategory {
        POOR, AVERAGE, GOOD, EXCELLENT
    }

    public RatingResult() {}

    public RatingResult(Property property, Double finalRating, RatingCategory ratingCategory, String details) {
        this.property = property;
        this.finalRating = finalRating;
        this.ratingCategory = ratingCategory;
        this.details = details;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }

    public Double getFinalRating() { return finalRating; }
    public void setFinalRating(Double finalRating) { this.finalRating = finalRating; }

    public RatingCategory getRatingCategory() { return ratingCategory; }
    public void setRatingCategory(RatingCategory ratingCategory) { this.ratingCategory = ratingCategory; }
    
    public void setRatingCategory(String ratingCategory) {
        this.ratingCategory = RatingCategory.valueOf(ratingCategory);
    }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getRatedAt() { return ratedAt; }
    public void setRatedAt(LocalDateTime ratedAt) { this.ratedAt = ratedAt; }

    public String getCalculatorVersion() { return calculatorVersion; }
    public void setCalculatorVersion(String calculatorVersion) { this.calculatorVersion = calculatorVersion; }
}