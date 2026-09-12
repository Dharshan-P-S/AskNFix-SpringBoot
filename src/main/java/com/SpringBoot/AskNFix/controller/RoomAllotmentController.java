package com.SpringBoot.AskNFix.controller;

import com.SpringBoot.AskNFix.entity.RoomAllotment;
import com.SpringBoot.AskNFix.service.RoomAllotmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-allotments")
public class RoomAllotmentController {

    private final RoomAllotmentService roomAllotmentService;

    public RoomAllotmentController(RoomAllotmentService roomAllotmentService) {
        this.roomAllotmentService = roomAllotmentService;
    }

    @PostMapping("/student/{studentId}/room/{roomId}")
    public RoomAllotment allotRoom(
            @PathVariable Long studentId,
            @PathVariable Long roomId) {

        return roomAllotmentService.allotRoom(studentId, roomId);
    }

    @GetMapping("/student/{studentId}/current")
    public RoomAllotment getCurrentAllotment(
            @PathVariable Long studentId) {

        return roomAllotmentService.getCurrentAllotment(studentId);
    }

    @GetMapping("/student/{studentId}")
    public List<RoomAllotment> getStudentAllotments(
            @PathVariable Long studentId) {

        return roomAllotmentService.getStudentAllotments(studentId);
    }

    @GetMapping("/room/{roomId}")
    public List<RoomAllotment> getRoomAllotments(
            @PathVariable Long roomId) {

        return roomAllotmentService.getRoomAllotments(roomId);
    }

    @PutMapping("/{allotmentId}/deactivate")
    public String deactivateAllotment(
            @PathVariable Long allotmentId) {

        roomAllotmentService.deactivateAllotment(allotmentId);

        return "Room allotment deactivated successfully";
    }
}