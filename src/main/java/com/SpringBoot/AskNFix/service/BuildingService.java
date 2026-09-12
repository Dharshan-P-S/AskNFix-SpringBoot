package com.SpringBoot.AskNFix.service;

import com.SpringBoot.AskNFix.entity.Building;
import com.SpringBoot.AskNFix.repository.BuildingRepository;
import org.springframework.stereotype.Service;
import com.SpringBoot.AskNFix.entity.RoomAllotment;
import com.SpringBoot.AskNFix.repository.RoomAllotmentRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final RoomAllotmentRepository roomAllotmentRepository;

    public BuildingService(BuildingRepository buildingRepository, RoomAllotmentRepository roomAllotmentRepository) {
        this.buildingRepository = buildingRepository;
        this.roomAllotmentRepository = roomAllotmentRepository;
    }

    public Building createBuilding(Building building) {
        return buildingRepository.save(building);
    }

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public Building getBuildingById(Long buildingId) {
        return buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found"));
    }

    public Building updateBuilding(Long buildingId, Building updatedBuilding) {

        Building building = getBuildingById(buildingId);

        building.setName(updatedBuilding.getName());
        building.setDescription(updatedBuilding.getDescription());

        return buildingRepository.save(building);
    }

    public void deleteBuilding(Long buildingId) {

        Building building = getBuildingById(buildingId);

        buildingRepository.delete(building);
    }

    public List<Building> getBuildingsForStudent(Long studentId) {

        List<Building> buildings = new ArrayList<>(
                buildingRepository.findByIsHostelFalse()
        );

        RoomAllotment allotment =
                roomAllotmentRepository
                        .findByStudentStudentIdAndActiveTrue(studentId)
                        .orElse(null);

        if (allotment != null &&
                allotment.getRoom().getBuilding().getIsHostel()) {

            buildings.add(allotment.getRoom().getBuilding());
        }

        return buildings;
    }
}