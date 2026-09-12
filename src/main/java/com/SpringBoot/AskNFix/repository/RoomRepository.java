package com.SpringBoot.AskNFix.repository;

import com.SpringBoot.AskNFix.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByBuildingBuildingId(Long buildingId);

    List<Room> findByBuildingBuildingIdAndFloor(Long buildingId, String floor);
}