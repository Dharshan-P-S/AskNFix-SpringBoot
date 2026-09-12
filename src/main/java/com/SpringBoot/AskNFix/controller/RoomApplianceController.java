package com.SpringBoot.AskNFix.controller;

import com.SpringBoot.AskNFix.entity.RoomAppliance;
import com.SpringBoot.AskNFix.service.RoomApplianceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-appliances")
public class RoomApplianceController {

    private final RoomApplianceService roomApplianceService;

    public RoomApplianceController(RoomApplianceService roomApplianceService) {
        this.roomApplianceService = roomApplianceService;
    }

    @PostMapping("/room/{roomId}/appliance/{applianceId}")
    public RoomAppliance addApplianceToRoom(
            @PathVariable Long roomId,
            @PathVariable Long applianceId,
            @RequestParam Integer quantity) {

        return roomApplianceService.addApplianceToRoom(
                roomId,
                applianceId,
                quantity
        );
    }

    @GetMapping("/room/{roomId}")
    public List<RoomAppliance> getAppliancesByRoom(
            @PathVariable Long roomId) {

        return roomApplianceService.getAppliancesByRoom(roomId);
    }

    @GetMapping("/{roomApplianceId}")
    public RoomAppliance getRoomApplianceById(
            @PathVariable Long roomApplianceId) {

        return roomApplianceService.getRoomApplianceById(roomApplianceId);
    }

    @PutMapping("/{roomApplianceId}")
    public RoomAppliance updateQuantity(
            @PathVariable Long roomApplianceId,
            @RequestParam Integer quantity) {

        return roomApplianceService.updateQuantity(
                roomApplianceId,
                quantity
        );
    }

    @DeleteMapping("/{roomApplianceId}")
    public String removeApplianceFromRoom(
            @PathVariable Long roomApplianceId) {

        roomApplianceService.removeApplianceFromRoom(roomApplianceId);

        return "Appliance removed from room successfully";
    }
}