package com.SpringBoot.AskNFix.repository;

import com.SpringBoot.AskNFix.entity.QueryAppliance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryApplianceRepository
        extends JpaRepository<QueryAppliance, Long> {

    List<QueryAppliance> findByQueryQueryId(Long queryId);

    List<QueryAppliance> findByQueryRoomRoomId(Long roomId);

    List<QueryAppliance> findByQueryRoomRoomIdAndApplianceApplianceId(
            Long roomId,
            Long applianceId
    );
}