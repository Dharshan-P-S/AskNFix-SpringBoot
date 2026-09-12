package com.SpringBoot.AskNFix.repository;

import com.SpringBoot.AskNFix.entity.RoomAppliance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomApplianceRepository
        extends JpaRepository<RoomAppliance, Long> {

    List<RoomAppliance> findByRoomRoomId(Long roomId);

    Optional<RoomAppliance>
    findByRoomRoomIdAndApplianceApplianceId(
            Long roomId,
            Long applianceId
    );
}