package com.SpringBoot.AskNFix.repository;

import com.SpringBoot.AskNFix.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    Optional<Building> findByName(String name);

    List<Building> findByIsHostelFalse();

    boolean existsByName(String name);
}