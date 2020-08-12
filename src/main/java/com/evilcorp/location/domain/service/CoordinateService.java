package com.evilcorp.location.domain.service;

import com.evilcorp.location.domain.model.Coordinate;
import com.evilcorp.location.domain.repository.CoordinateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoordinateService {

    @Autowired
    private CoordinateRepository coordinateRepository;

    public List<Coordinate> getAll() {
        return coordinateRepository.findAll();
    }

    public Coordinate getById(Long coordinateId) {
        return coordinateRepository.findById(coordinateId).get();
    }

    public Coordinate save(Coordinate coordinate) {
        return coordinateRepository.save(coordinate);
    }

    public void delete(Long coordinateId) {
        coordinateRepository.deleteById(coordinateId);
    }
}