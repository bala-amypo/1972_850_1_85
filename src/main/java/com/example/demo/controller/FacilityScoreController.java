package com.example.demo.controller;

import com.example.demo.entity.FacilityScore;
import com.example.demo.service.FacilityScoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scores")
public class FacilityScoreController {

    @Autowired
    private FacilityScoreService facilityScoreService;

    @PostMapping("/{propertyId}")
    public ResponseEntity<FacilityScore> submitScore(
            @PathVariable Long propertyId,
            @Valid @RequestBody FacilityScore score) {
        
        FacilityScore savedScore = facilityScoreService.submitScore(propertyId, score);
        return new ResponseEntity<>(savedScore, HttpStatus.CREATED);
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<FacilityScore> getLatestScore(@PathVariable Long propertyId) {
        FacilityScore score = facilityScoreService.getLatestScore(propertyId);
        return ResponseEntity.ok(score);
    }
}