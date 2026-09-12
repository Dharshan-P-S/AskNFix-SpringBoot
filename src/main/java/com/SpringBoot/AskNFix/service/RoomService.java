package com.SpringBoot.AskNFix.service;

import com.SpringBoot.AskNFix.entity.Building;
import com.SpringBoot.AskNFix.entity.Room;
import com.SpringBoot.AskNFix.repository.BuildingRepository;
import com.SpringBoot.AskNFix.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BuildingRepository buildingRepository;

    public RoomService(RoomRepository roomRepository,
                       BuildingRepository buildingRepository) {
        this.roomRepository = roomRepository;
        this.buildingRepository = buildingRepository;
    }

    public Room createRoom(Long buildingId, Room room) {

        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found"));

        room.setBuilding(building);

        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getRoomsByBuildingAndFloor(Long buildingId, String floor) {

        if (!buildingRepository.existsById(buildingId)) {
            throw new RuntimeException("Building not found");
        }

        return roomRepository.findByBuildingBuildingIdAndFloor(buildingId, floor);
    }

    public List<Room> getRoomsByBuilding(Long buildingId) {

        if (!buildingRepository.existsById(buildingId)) {
            throw new RuntimeException("Building not found");
        }

        return roomRepository.findByBuildingBuildingId(buildingId);
    }

    public Room getRoomById(Long roomId) {

        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public Room updateRoom(Long roomId, Long buildingId, Room updatedRoom) {

        Room room = getRoomById(roomId);

        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found"));

        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setFloor(updatedRoom.getFloor());
        room.setBuilding(building);

        return roomRepository.save(room);
    }

    public void deleteRoom(Long roomId) {

        Room room = getRoomById(roomId);

        roomRepository.delete(room);
    }
}