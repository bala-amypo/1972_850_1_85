package com.example.demo.service;

import com.example.demo.entity.FacilityScore;
import com.example.demo.entity.Property;
import com.example.demo.entity.RatingResult;
import com.example.demo.entity.RatingResult.RatingCategory;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RatingResultRepository;
import com.example.demo.util.RatingCalculatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RatingService {

    @Autowired
    private RatingResultRepository ratingResultRepository;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private FacilityScoreService facilityScoreService;

    @Autowired
    private RatingLogService ratingLogService;

    @Autowired
    private RatingCalculatorUtil ratingCalculator;

    public RatingResult generateRating(Long propertyId) {
        Property property = propertyService.getProperty(propertyId);
        
        FacilityScore facilityScore;
        try {
            facilityScore = facilityScoreService.getLatestScore(propertyId);
        } catch (ResourceNotFoundException e) {
            throw new BadRequestException("No facility score found for property. Please submit facility scores first.");
        }

        ratingLogService.addLog(propertyId, "Starting rating generation process");

        double facilityAvg = ratingCalculator.calculateFacilityAverage(
            facilityScore.getSchoolProximity(),
            facilityScore.getHospitalProximity(),
            facilityScore.getTransportAccess(),
            facilityScore.getSafetyScore()
        );

        ratingLogService.addLog(propertyId, "Facility average calculated: " + facilityAvg);

        double finalRating = ratingCalculator.calculateRating(facilityAvg, property.getPrice(), property.getAreaSqFt());
        RatingCategory category = ratingCalculator.determineCategory(finalRating);
        String details = ratingCalculator.generateDetails(facilityAvg, property.getPrice(), property.getAreaSqFt(), finalRating);

        RatingResult result = new RatingResult();
        result.setProperty(property);
        result.setFinalRating(finalRating);
        result.setRatingCategory(category);
        result.setDetails(details);
        result.setCalculatorVersion("1.0");

        RatingResult savedResult = ratingResultRepository.save(result);

        ratingLogService.addLog(propertyId, "Rating generated successfully: " + finalRating + " (" + category + ")");

        return savedResult;
    }

    public RatingResult getLatestRating(Long propertyId) {
        return ratingResultRepository.findTopByPropertyIdOrderByRatedAtDesc(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("No rating found for property: " + propertyId));
    }
}