package com.SpringBoot.AskNFix.repository;

import com.SpringBoot.AskNFix.entity.RoomAllotment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomAllotmentRepository extends JpaRepository<RoomAllotment, Long> {

    List<RoomAllotment> findByStudentStudentId(Long studentId);

    Optional<RoomAllotment> findByStudentStudentIdAndActiveTrue(Long studentId);

    List<RoomAllotment> findByRoomRoomId(Long roomId);
}