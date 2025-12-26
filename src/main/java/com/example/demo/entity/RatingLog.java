package com.example.demo.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "rating_logs")
public class RatingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime loggedAt;

    @PrePersist
    protected void onCreate() {
        loggedAt = LocalDateTime.now();
    }

    public RatingLog() {}

    public RatingLog(Property property, String message) {
        this.property = property;
        this.message = message;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getLoggedAt() { return loggedAt; }
    public void setLoggedAt(LocalDateTime loggedAt) { this.loggedAt = loggedAt; }
}