package com.example.demo.repository;

import com.example.demo.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Page<Property> findByCity(String city, Pageable pageable);
    Page<Property> findByCityContainingIgnoreCase(String city, Pageable pageable);
}