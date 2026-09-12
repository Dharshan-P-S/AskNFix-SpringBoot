package com.SpringBoot.AskNFix.controller;

import com.SpringBoot.AskNFix.entity.Building;
import com.SpringBoot.AskNFix.service.BuildingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buildings")
public class BuildingController {

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @PostMapping
    public Building createBuilding(@RequestBody Building building) {
        return buildingService.createBuilding(building);
    }

    @GetMapping
    public List<Building> getAllBuildings() {
        return buildingService.getAllBuildings();
    }

    @GetMapping("/{buildingId}")
    public Building getBuildingById(@PathVariable Long buildingId) {
        return buildingService.getBuildingById(buildingId);
    }

    @GetMapping("/student/{studentId}")
    public List<Building> getBuildingsForStudent(
            @PathVariable Long studentId) {

        return buildingService.getBuildingsForStudent(studentId);
    }

    @PutMapping("/{buildingId}")
    public Building updateBuilding(
            @PathVariable Long buildingId,
            @RequestBody Building building) {

        return buildingService.updateBuilding(buildingId, building);
    }

    @DeleteMapping("/{buildingId}")
    public String deleteBuilding(@PathVariable Long buildingId) {

        buildingService.deleteBuilding(buildingId);

        return "Building deleted successfully";
    }
}