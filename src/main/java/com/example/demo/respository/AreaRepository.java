package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
    List<Area> findByActivoArea(String activoArea);
}
