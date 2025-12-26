package com.example.demo.service;

import com.example.demo.entity.FacilityScore;
import com.example.demo.entity.Property;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FacilityScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FacilityScoreService {

    @Autowired
    private FacilityScoreRepository facilityScoreRepository;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private RatingLogService ratingLogService;

    public FacilityScore submitScore(Long propertyId, FacilityScore score) {
        Property property = propertyService.getProperty(propertyId);
        score.setProperty(property);

        FacilityScore savedScore = facilityScoreRepository.save(score);
        
        ratingLogService.addLog(propertyId, "Facility score submitted: " + 
            "School=" + score.getSchoolProximity() + 
            ", Hospital=" + score.getHospitalProximity() + 
            ", Transport=" + score.getTransportAccess() + 
            ", Safety=" + score.getSafetyScore());

        return savedScore;
    }

    public FacilityScore getLatestScore(Long propertyId) {
        return facilityScoreRepository.findFirstByPropertyIdOrderBySubmittedAtDesc(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("No facility score found for property: " + propertyId));
    }
}