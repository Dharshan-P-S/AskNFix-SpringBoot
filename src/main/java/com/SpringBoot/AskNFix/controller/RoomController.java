package com.SpringBoot.AskNFix.controller;

import com.SpringBoot.AskNFix.entity.Room;
import com.SpringBoot.AskNFix.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/building/{buildingId}")
    public Room createRoom(
            @PathVariable Long buildingId,
            @RequestBody Room room) {

        return roomService.createRoom(buildingId, room);
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{roomId}")
    public Room getRoomById(@PathVariable Long roomId) {
        return roomService.getRoomById(roomId);
    }

    @GetMapping("/building/{buildingId}")
    public List<Room> getRoomsByBuilding(
            @PathVariable Long buildingId) {

        return roomService.getRoomsByBuilding(buildingId);
    }

    @GetMapping("/building/{buildingId}/floor/{floor}")
    public List<Room> getRoomsByBuildingAndFloor(
            @PathVariable Long buildingId,
            @PathVariable String floor) {

        return roomService.getRoomsByBuildingAndFloor(buildingId, floor);
    }

    @PutMapping("/{roomId}/building/{buildingId}")
    public Room updateRoom(
            @PathVariable Long roomId,
            @PathVariable Long buildingId,
            @RequestBody Room room) {

        return roomService.updateRoom(roomId, buildingId, room);
    }

    @DeleteMapping("/{roomId}")
    public String deleteRoom(@PathVariable Long roomId) {

        roomService.deleteRoom(roomId);

        return "Room deleted successfully";
    }
}