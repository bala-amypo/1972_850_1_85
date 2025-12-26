package com.example.demo.service.impl;

import com.example.demo.entity.Property;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.service.PropertyService;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository repo;

    public PropertyServiceImpl(PropertyRepository repo) {
        this.repo = repo;
    }

    @Override
    public Property addProperty(Property property) {
        if (property.getPrice() <= 0) {
            throw new IllegalArgumentException("Invalid price");
        }
        return repo.save(property);
    }
}
