package com.evilcorp.location.domain.repository;

import com.evilcorp.location.domain.model.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {
}
