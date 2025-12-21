package com.example.demo.service;

import com.example.demo.dto.FacilityScoreDto;
import com.example.demo.entity.FacilityScore;
import com.example.demo.entity.Property;
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

    public FacilityScore submitScore(Long propertyId, FacilityScoreDto dto) {
        Property property = propertyService.getProperty(propertyId);

        FacilityScore score = new FacilityScore();
        score.setProperty(property);
        score.setSchoolProximity(dto.getSchoolProximity());
        score.setHospitalProximity(dto.getHospitalProximity());
        score.setTransportAccess(dto.getTransportAccess());
        score.setSafetyScore(dto.getSafetyScore());

        FacilityScore savedScore = facilityScoreRepository.save(score);
        
        ratingLogService.addLog(propertyId, "Facility score submitted: " + 
            "School=" + dto.getSchoolProximity() + 
            ", Hospital=" + dto.getHospitalProximity() + 
            ", Transport=" + dto.getTransportAccess() + 
            ", Safety=" + dto.getSafetyScore());

        return savedScore;
    }

    public FacilityScore getLatestScore(Long propertyId) {
        return facilityScoreRepository.findFirstByPropertyIdOrderBySubmittedAtDesc(propertyId)
                .orElseThrow(() -> new RuntimeException("No facility score found for property: " + propertyId));
    }

    public FacilityScoreDto convertToDto(FacilityScore score) {
        FacilityScoreDto dto = new FacilityScoreDto();
        dto.setId(score.getId());
        dto.setSchoolProximity(score.getSchoolProximity());
        dto.setHospitalProximity(score.getHospitalProximity());
        dto.setTransportAccess(score.getTransportAccess());
        dto.setSafetyScore(score.getSafetyScore());
        return dto;
    }
}