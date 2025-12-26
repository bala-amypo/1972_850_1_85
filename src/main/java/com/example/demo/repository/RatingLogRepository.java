package com.example.demo.repository;

import com.example.demo.entity.Property;
import com.example.demo.entity.RatingLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RatingLogRepository extends JpaRepository<RatingLog, Long> {
    List<RatingLog> findByPropertyIdOrderByLoggedAtDesc(Long propertyId);
    Page<RatingLog> findByPropertyIdOrderByLoggedAtDesc(Long propertyId, Pageable pageable);
    List<RatingLog> findByProperty(Property property);
}