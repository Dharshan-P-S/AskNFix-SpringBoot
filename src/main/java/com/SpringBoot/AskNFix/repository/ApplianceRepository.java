package com.SpringBoot.AskNFix.repository;

import com.SpringBoot.AskNFix.entity.Appliance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplianceRepository extends JpaRepository<Appliance, Long> {

    Optional<Appliance> findByName(String name);

    boolean existsByName(String name);
}