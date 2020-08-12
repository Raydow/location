package com.evilcorp.location.api.controller;

import com.evilcorp.location.domain.model.Coordinate;
import com.evilcorp.location.domain.service.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Coordinate getById(@PathVariable Long coordinateId) {
        return coordinateService.getById(coordinateId);
    }

    @PostMapping
    public Coordinate save(@RequestBody Coordinate coordinate) {
        return coordinateService.save(coordinate);
    }

    @DeleteMapping("/{coordinateId}")
    public void delete(@PathVariable Long coordinateId) {
        coordinateService.delete(coordinateId);
    }
}
