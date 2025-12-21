package com.example.demo.service;

import com.example.demo.dto.PropertyDto;
import com.example.demo.entity.Property;
import com.example.demo.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public Property createProperty(PropertyDto dto) {
        Property property = new Property();
        property.setTitle(dto.getTitle());
        property.setAddress(dto.getAddress());
        property.setCity(dto.getCity());
        property.setPrice(dto.getPrice());
        property.setAreaSqFt(dto.getAreaSqFt());
        return propertyRepository.save(property);
    }

    public Property getProperty(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + id));
    }

    public Page<Property> listProperties(Pageable pageable, String city) {
        if (city != null && !city.trim().isEmpty()) {
            return propertyRepository.findByCityContainingIgnoreCase(city.trim(), pageable);
        }
        return propertyRepository.findAll(pageable);
    }

    public PropertyDto convertToDto(Property property) {
        PropertyDto dto = new PropertyDto();
        dto.setId(property.getId());
        dto.setTitle(property.getTitle());
        dto.setAddress(property.getAddress());
        dto.setCity(property.getCity());
        dto.setPrice(property.getPrice());
        dto.setAreaSqFt(property.getAreaSqFt());
        return dto;
    }
}