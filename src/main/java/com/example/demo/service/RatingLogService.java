package com.example.demo.service;

import com.example.demo.entity.Property;
import com.example.demo.entity.RatingLog;
import com.example.demo.repository.RatingLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RatingLogService {

    @Autowired
    private RatingLogRepository ratingLogRepository;

    @Autowired
    private PropertyService propertyService;

    public RatingLog addLog(Long propertyId, String message) {
        Property property = propertyService.getProperty(propertyId);
        RatingLog log = new RatingLog(property, message);
        return ratingLogRepository.save(log);
    }

    public List<RatingLog> getLogsByProperty(Long propertyId) {
        return ratingLogRepository.findByPropertyIdOrderByLoggedAtDesc(propertyId);
    }

    public Page<RatingLog> getLogsByProperty(Long propertyId, Pageable pageable) {
        return ratingLogRepository.findByPropertyIdOrderByLoggedAtDesc(propertyId, pageable);
    }
}