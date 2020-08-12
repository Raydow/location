package com.evilcorp.location.domain.service;

import com.evilcorp.location.domain.exception.EntityNotFoundException;
import com.evilcorp.location.domain.model.Coordinate;
import com.evilcorp.location.domain.repository.CoordinateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.el.util.ReflectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CoordinateService {

    @Autowired
    private CoordinateRepository coordinateRepository;

    public List<Coordinate> getAll() {
        return coordinateRepository.findAll();
    }

    public Optional<Coordinate> getById(Long coordinateId) {
        return coordinateRepository.findById(coordinateId);
    }

    public Coordinate save(Coordinate coordinate) {
        return coordinateRepository.save(coordinate);
    }

    public Optional<Coordinate> update(Long coordinateId, Coordinate coordinate) {
        Optional<Coordinate> actualCoordinate = getById(coordinateId);

        if (actualCoordinate.isPresent()) {
            BeanUtils.copyProperties(coordinate, actualCoordinate.get(), "id");

            return Optional.of(save(actualCoordinate.get()));
        }

        return actualCoordinate;
    }

    public Optional<Coordinate> updatePartial(Long coordinateId, Map<String, Object> fields) {
        Optional<Coordinate> actualCoordinate = getById(coordinateId);

        if (actualCoordinate.isPresent()) {
            merge(fields, actualCoordinate.get());

            return Optional.of(save(actualCoordinate.get()));
        }

        return actualCoordinate;
    }

    public void delete(Long coordinateId) {
        try {
            coordinateRepository.deleteById(coordinateId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format("Coordinate not found with Id: %d", coordinateId)
            );
        }
    }

    private void merge(Map<String, Object> originFields, Coordinate destinyCoordinate) {
        ObjectMapper objectMapper = new ObjectMapper();
        Coordinate originCoordinate = objectMapper.convertValue(originFields, Coordinate.class);

        originFields.forEach((name, value) -> {
            Field field = ReflectionUtils.findField(Coordinate.class, name);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, originCoordinate);

            ReflectionUtils.setField(field, destinyCoordinate, newValue);
        });
    }
}