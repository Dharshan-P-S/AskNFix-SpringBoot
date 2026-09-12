package com.SpringBoot.AskNFix.service;

import com.SpringBoot.AskNFix.entity.Room;
import com.SpringBoot.AskNFix.entity.RoomAllotment;
import com.SpringBoot.AskNFix.entity.Student;
import com.SpringBoot.AskNFix.repository.RoomAllotmentRepository;
import com.SpringBoot.AskNFix.repository.RoomRepository;
import com.SpringBoot.AskNFix.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomAllotmentService {

    private final RoomAllotmentRepository roomAllotmentRepository;
    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;

    public RoomAllotmentService(RoomAllotmentRepository roomAllotmentRepository,
                                StudentRepository studentRepository,
                                RoomRepository roomRepository) {
        this.roomAllotmentRepository = roomAllotmentRepository;
        this.studentRepository = studentRepository;
        this.roomRepository = roomRepository;
    }

    public RoomAllotment allotRoom(Long studentId, Long roomId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        RoomAllotment allotment = RoomAllotment.builder()
                .student(student)
                .room(room)
                .allottedAt(LocalDateTime.now())
                .active(true)
                .build();

        return roomAllotmentRepository.save(allotment);
    }

    public RoomAllotment getCurrentAllotment(Long studentId) {

        return roomAllotmentRepository
                .findByStudentStudentIdAndActiveTrue(studentId)
                .orElseThrow(() -> new RuntimeException("Active room allotment not found"));
    }

    public List<RoomAllotment> getStudentAllotments(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found");
        }

        return roomAllotmentRepository.findByStudentStudentId(studentId);
    }

    public List<RoomAllotment> getRoomAllotments(Long roomId) {

        if (!roomRepository.existsById(roomId)) {
            throw new RuntimeException("Room not found");
        }

        return roomAllotmentRepository.findByRoomRoomId(roomId);
    }

    public void deactivateAllotment(Long allotmentId) {

        RoomAllotment allotment = roomAllotmentRepository.findById(allotmentId)
                .orElseThrow(() -> new RuntimeException("Room allotment not found"));

        allotment.setActive(false);

        roomAllotmentRepository.save(allotment);
    }
}