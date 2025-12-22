package com.example.demo.controller;

import com.example.demo.entity.Property;
import com.example.demo.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<Property> createProperty(@Valid @RequestBody Property property) {
        Property savedProperty = propertyService.createProperty(property);
        return new ResponseEntity<>(savedProperty, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Property>> listProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String city) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Property> properties = propertyService.listProperties(pageable, city);
        
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getProperty(@PathVariable Long id) {
        Property property = propertyService.getProperty(id);
        return ResponseEntity.ok(property);
    }
}