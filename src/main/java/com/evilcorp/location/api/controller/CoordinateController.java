package com.evilcorp.location.api.controller;

import com.evilcorp.location.domain.exception.EntityNotFoundException;
import com.evilcorp.location.domain.model.Coordinate;
import com.evilcorp.location.domain.service.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/coordinates")
public class CoordinateController {

    @Autowired
    private CoordinateService coordinateService;

    @GetMapping
    public List<Coordinate> getAll() {
        return coordinateService.getAll();
    }

    @GetMapping("/{coordinateId}")
    public ResponseEntity<Coordinate> getById(@PathVariable Long coordinateId) {
        Optional<Coordinate> coordinate = coordinateService.getById(coordinateId);

        if (coordinate.isPresent()) {
            return ResponseEntity.ok(coordinate.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Coordinate insert(@RequestBody Coordinate coordinate) {
        return coordinateService.save(coordinate);
    }

    @PutMapping("/{coordinateId}")
    public ResponseEntity<Coordinate> update(@PathVariable Long coordinateId, @RequestBody Coordinate coordinate) {
        Optional<Coordinate> updatedCoordinate = coordinateService.update(coordinateId, coordinate);

        if (updatedCoordinate.isPresent()) {
            return ResponseEntity.ok(updatedCoordinate.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{coordinateId}")
    public ResponseEntity<Coordinate> updatePartial(@PathVariable Long coordinateId,
                                                    @RequestBody Map<String, Object> fields) {
        Optional<Coordinate> updatedCoordinate = coordinateService.updatePartial(coordinateId, fields);

        if (updatedCoordinate.isPresent()) {
            return ResponseEntity.ok(updatedCoordinate.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{coordinateId}")
    public ResponseEntity<?> delete(@PathVariable Long coordinateId) {
        try {
            coordinateService.delete(coordinateId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
