package com.example.demo.controller;

import com.example.demo.entity.RatingLog;
import com.example.demo.service.RatingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
public class RatingLogController {

    @Autowired
    private RatingLogService ratingLogService;

    @PostMapping("/{propertyId}")
    public ResponseEntity<RatingLog> addLog(
            @PathVariable Long propertyId,
            @RequestParam String message) {
        
        RatingLog log = ratingLogService.addLog(propertyId, message);
        return new ResponseEntity<>(log, HttpStatus.CREATED);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<Page<RatingLog>> getPropertyLogs(
            @PathVariable Long propertyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<RatingLog> logs = ratingLogService.getLogsByProperty(propertyId, pageable);
        
        return ResponseEntity.ok(logs);
    }
}