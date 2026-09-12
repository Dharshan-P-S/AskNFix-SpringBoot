package com.SpringBoot.AskNFix.repository;

import com.SpringBoot.AskNFix.entity.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryRepository extends JpaRepository<Query, Long> {

    List<Query> findByStudentStudentId(Long studentId);

    List<Query> findByStatus(String status);

    List<Query> findByAssignedStaffStaffId(Long staffId);

    List<Query> findByBuildingBuildingId(Long buildingId);

    List<Query> findByRoomRoomId(Long roomId);
}