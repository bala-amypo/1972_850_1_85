package com.example.demo.repository;

import com.example.demo.entity.Property;
import com.example.demo.entity.RatingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RatingResultRepository extends JpaRepository<RatingResult, Long> {
    Optional<RatingResult> findTopByPropertyIdOrderByRatedAtDesc(Long propertyId);
    Optional<RatingResult> findByProperty(Property property);
}