package com.example.demo.util;

import com.example.demo.entity.RatingResult.RatingCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class RatingCalculatorUtil {

    @Value("${app.rating.facility-weight:0.4}")
    private double facilityWeight;

    @Value("${app.rating.price-weight:0.35}")
    private double priceWeight;

    @Value("${app.rating.area-weight:0.25}")
    private double areaWeight;

    @Value("${app.rating.threshold.poor:50}")
    private double poorThreshold;

    @Value("${app.rating.threshold.average:70}")
    private double averageThreshold;

    @Value("${app.rating.threshold.good:85}")
    private double goodThreshold;

    public double calculateRating(double facilityAvg, BigDecimal price, Double areaSqFt) {
        // Normalize facility score (already 0-10, convert to 0-100)
        double facilityNorm = facilityAvg * 10;

        // Normalize price (inverse relationship - lower price is better)
        // Using a simple normalization assuming price range 50k-500k
        double priceNorm = Math.max(0, 100 - ((price.doubleValue() - 50000) / 4500));
        priceNorm = Math.max(0, Math.min(100, priceNorm));

        // Normalize area (more area is better)
        // Using a simple normalization assuming area range 500-3000 sqft
        double areaNorm = Math.max(0, ((areaSqFt - 500) / 25));
        areaNorm = Math.max(0, Math.min(100, areaNorm));

        // Calculate weighted final score
        double finalScore = (facilityWeight * facilityNorm) + 
                           (priceWeight * priceNorm) + 
                           (areaWeight * areaNorm);

        return Math.round(finalScore * 100.0) / 100.0; // Round to 2 decimal places
    }

    public double calculateFacilityAverage(int schoolProximity, int hospitalProximity, 
                                         int transportAccess, int safetyScore) {
        return (schoolProximity + hospitalProximity + transportAccess + safetyScore) / 4.0;
    }

    public RatingCategory determineCategory(double finalRating) {
        if (finalRating >= goodThreshold) {
            return RatingCategory.EXCELLENT;
        } else if (finalRating >= averageThreshold) {
            return RatingCategory.GOOD;
        } else if (finalRating >= poorThreshold) {
            return RatingCategory.AVERAGE;
        } else {
            return RatingCategory.POOR;
        }
    }

    public String generateDetails(double facilityAvg, BigDecimal price, Double areaSqFt, double finalRating) {
        return String.format(
            "{\"facilityAverage\":%.2f,\"price\":%s,\"areaSqFt\":%.2f,\"finalRating\":%.2f,\"weights\":{\"facility\":%.2f,\"price\":%.2f,\"area\":%.2f}}",
            facilityAvg, price.toString(), areaSqFt, finalRating, facilityWeight, priceWeight, areaWeight
        );
    }
}