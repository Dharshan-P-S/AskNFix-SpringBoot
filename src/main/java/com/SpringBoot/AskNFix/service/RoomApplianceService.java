package com.SpringBoot.AskNFix.service;

import com.SpringBoot.AskNFix.entity.Appliance;
import com.SpringBoot.AskNFix.entity.Room;
import com.SpringBoot.AskNFix.entity.RoomAppliance;
import com.SpringBoot.AskNFix.repository.ApplianceRepository;
import com.SpringBoot.AskNFix.repository.RoomApplianceRepository;
import com.SpringBoot.AskNFix.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomApplianceService {

    private final RoomApplianceRepository roomApplianceRepository;
    private final RoomRepository roomRepository;
    private final ApplianceRepository applianceRepository;

    public RoomApplianceService(RoomApplianceRepository roomApplianceRepository,
                                RoomRepository roomRepository,
                                ApplianceRepository applianceRepository) {
        this.roomApplianceRepository = roomApplianceRepository;
        this.roomRepository = roomRepository;
        this.applianceRepository = applianceRepository;
    }

    public RoomAppliance addApplianceToRoom(Long roomId,
                                            Long applianceId,
                                            Integer quantity) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Appliance appliance = applianceRepository.findById(applianceId)
                .orElseThrow(() -> new RuntimeException("Appliance not found"));

        RoomAppliance roomAppliance = RoomAppliance.builder()
                .room(room)
                .appliance(appliance)
                .quantity(quantity)
                .build();

        return roomApplianceRepository.save(roomAppliance);
    }

    public List<RoomAppliance> getAppliancesByRoom(Long roomId) {

        if (!roomRepository.existsById(roomId)) {
            throw new RuntimeException("Room not found");
        }

        return roomApplianceRepository.findByRoomRoomId(roomId);
    }

    public RoomAppliance getRoomApplianceById(Long roomApplianceId) {

        return roomApplianceRepository.findById(roomApplianceId)
                .orElseThrow(() -> new RuntimeException("Room appliance not found"));
    }

    public RoomAppliance updateQuantity(Long roomApplianceId,
                                        Integer quantity) {

        RoomAppliance roomAppliance =
                getRoomApplianceById(roomApplianceId);

        roomAppliance.setQuantity(quantity);

        return roomApplianceRepository.save(roomAppliance);
    }

    public void removeApplianceFromRoom(Long roomApplianceId) {

        RoomAppliance roomAppliance =
                getRoomApplianceById(roomApplianceId);

        roomApplianceRepository.delete(roomAppliance);
    }
}