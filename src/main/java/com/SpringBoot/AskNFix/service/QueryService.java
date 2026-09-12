package com.SpringBoot.AskNFix.service;

import com.SpringBoot.AskNFix.dto.CreateQueryRequest;
import com.SpringBoot.AskNFix.dto.QueryApplianceRequest;
import com.SpringBoot.AskNFix.entity.Appliance;
import com.SpringBoot.AskNFix.entity.Building;
import com.SpringBoot.AskNFix.entity.Query;
import com.SpringBoot.AskNFix.entity.QueryAppliance;
import com.SpringBoot.AskNFix.entity.Room;
import com.SpringBoot.AskNFix.entity.RoomAllotment;
import com.SpringBoot.AskNFix.entity.RoomAppliance;
import com.SpringBoot.AskNFix.entity.Staff;
import com.SpringBoot.AskNFix.entity.Student;
import com.SpringBoot.AskNFix.repository.ApplianceRepository;
import com.SpringBoot.AskNFix.repository.BuildingRepository;
import com.SpringBoot.AskNFix.repository.QueryRepository;
import com.SpringBoot.AskNFix.repository.RoomAllotmentRepository;
import com.SpringBoot.AskNFix.repository.RoomApplianceRepository;
import com.SpringBoot.AskNFix.repository.RoomRepository;
import com.SpringBoot.AskNFix.repository.StaffRepository;
import com.SpringBoot.AskNFix.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class QueryService {

    private final QueryRepository queryRepository;
    private final StudentRepository studentRepository;
    private final BuildingRepository buildingRepository;
    private final RoomRepository roomRepository;
    private final StaffRepository staffRepository;
    private final RoomAllotmentRepository roomAllotmentRepository;
    private final RoomApplianceRepository roomApplianceRepository;
    private final ApplianceRepository applianceRepository;

    public QueryService(
            QueryRepository queryRepository,
            StudentRepository studentRepository,
            BuildingRepository buildingRepository,
            RoomRepository roomRepository,
            StaffRepository staffRepository,
            RoomAllotmentRepository roomAllotmentRepository,
            RoomApplianceRepository roomApplianceRepository,
            ApplianceRepository applianceRepository) {

        this.queryRepository = queryRepository;
        this.studentRepository = studentRepository;
        this.buildingRepository = buildingRepository;
        this.roomRepository = roomRepository;
        this.staffRepository = staffRepository;
        this.roomAllotmentRepository = roomAllotmentRepository;
        this.roomApplianceRepository = roomApplianceRepository;
        this.applianceRepository = applianceRepository;
    }

    public Query createQuery(
            String email,
            CreateQueryRequest request) {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Building building = buildingRepository.findById(request.getBuildingId())
                .orElseThrow(() -> new RuntimeException("Building not found"));

        if (Boolean.TRUE.equals(building.getIsHostel())) {

            if ("DAY_SCHOLAR".equalsIgnoreCase(student.getResidingStatus())) {
                throw new RuntimeException(
                        "Day scholars cannot raise queries in hostel buildings"
                );
            }

            RoomAllotment allotment = roomAllotmentRepository
                    .findByStudentStudentIdAndActiveTrue(student.getStudentId())
                    .orElseThrow(() -> new RuntimeException(
                            "Hosteller does not have an active room allotment"
                    ));

            Building studentHostel = allotment.getRoom().getBuilding();

            if (!studentHostel.getBuildingId().equals(building.getBuildingId())) {
                throw new RuntimeException(
                        "Hostellers can only raise queries in their own hostel"
                );
            }
        }

        Room room = null;

        if (request.getRoomId() != null) {

            room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new RuntimeException("Room not found"));

            if (!room.getBuilding().getBuildingId()
                    .equals(request.getBuildingId())) {

                throw new RuntimeException(
                        "Room does not belong to the selected building"
                );
            }
        }

        if (request.getDescription() == null ||
                request.getDescription().trim().isEmpty()) {

            throw new RuntimeException("Description is required");
        }

        Query query = Query.builder()
                .student(student)
                .building(building)
                .room(room)
                .description(request.getDescription())
                .status("PENDING")
                .raisedAt(LocalDateTime.now())
                .build();

        query = queryRepository.save(query);

        if (request.getAppliances() != null &&
                !request.getAppliances().isEmpty()) {

            if (room == null) {
                throw new RuntimeException(
                        "Appliances can only be selected for a room"
                );
            }

            for (QueryApplianceRequest applianceRequest :
                    request.getAppliances()) {

                if (applianceRequest.getQuantity() == null ||
                        applianceRequest.getQuantity() <= 0) {
                    continue;
                }

                RoomAppliance roomAppliance =
                        roomApplianceRepository
                                .findByRoomRoomIdAndApplianceApplianceId(
                                        room.getRoomId(),
                                        applianceRequest.getApplianceId()
                                )
                                .orElseThrow(() -> new RuntimeException(
                                        "Appliance is not available in this room"
                                ));

                if (applianceRequest.getQuantity() >
                        roomAppliance.getQuantity()) {

                    throw new RuntimeException(
                            "Requested quantity exceeds available quantity for "
                                    + roomAppliance.getAppliance().getName()
                    );
                }

                Appliance appliance =
                        applianceRepository.findById(
                                applianceRequest.getApplianceId()
                        ).orElseThrow(() ->
                                new RuntimeException("Appliance not found"));

                QueryAppliance queryAppliance =
                        QueryAppliance.builder()
                                .query(query)
                                .appliance(appliance)
                                .quantity(applianceRequest.getQuantity())
                                .build();

                query.getQueryAppliances().add(queryAppliance);
            }
        }

        return queryRepository.save(query);
    }

    public Query getQueryById(Long queryId) {

        return queryRepository.findById(queryId)
                .orElseThrow(() -> new RuntimeException("Query not found"));
    }

    public Query getQueryByIdForStudent(
            Long queryId,
            String email) {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Query query = getQueryById(queryId);

        if (!Objects.equals(
                query.getStudent().getStudentId(),
                student.getStudentId())) {

            throw new RuntimeException(
                    "You can only view your own queries"
            );
        }

        return query;
    }

    public List<Query> getAllQueries() {
        return queryRepository.findAll();
    }

    public List<Query> getStudentQueries(String email) {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return queryRepository.findByStudentStudentId(
                student.getStudentId()
        );
    }

    public List<Query> getPendingQueries() {
        return queryRepository.findByStatus("PENDING");
    }

    public List<Query> getCompletedQueries() {
        return queryRepository.findByStatus("COMPLETED");
    }

    public List<Query> getStaffQueries(String email) {

        Staff staff = staffRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (!"WORKER".equalsIgnoreCase(staff.getRole())) {
            throw new RuntimeException(
                    "Only workers can view assigned queries"
            );
        }

        return queryRepository.findByAssignedStaffStaffId(
                staff.getStaffId()
        );
    }

    public Query assignQuery(Long queryId, Long staffId) {

        Query query = getQueryById(queryId);

        if ("COMPLETED".equalsIgnoreCase(query.getStatus())) {
            throw new RuntimeException(
                    "Completed query cannot be assigned"
            );
        }

        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (!"WORKER".equalsIgnoreCase(staff.getRole())) {
            throw new RuntimeException(
                    "Query can only be assigned to a worker"
            );
        }

        query.setAssignedStaff(staff);
        query.setStatus("ASSIGNED");

        return queryRepository.save(query);
    }

    public Query startQuery(
            Long queryId,
            String email) {

        Query query = getQueryById(queryId);

        Staff staff = staffRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (!"WORKER".equalsIgnoreCase(staff.getRole())) {
            throw new RuntimeException(
                    "Only workers can start queries"
            );
        }

        if (query.getAssignedStaff() == null ||
                !query.getAssignedStaff()
                        .getStaffId()
                        .equals(staff.getStaffId())) {

            throw new RuntimeException(
                    "You can only start queries assigned to you"
            );
        }

        if (!"ASSIGNED".equalsIgnoreCase(query.getStatus())) {
            throw new RuntimeException(
                    "Only assigned queries can be started"
            );
        }

        query.setStatus("IN_PROGRESS");

        return queryRepository.save(query);
    }

    public Query completeQuery(
            Long queryId,
            String completionDescription,
            String email) {

        Query query = getQueryById(queryId);

        Staff staff = staffRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (!"WORKER".equalsIgnoreCase(staff.getRole())) {
            throw new RuntimeException(
                    "Only workers can complete queries"
            );
        }

        if (query.getAssignedStaff() == null ||
                !query.getAssignedStaff()
                        .getStaffId()
                        .equals(staff.getStaffId())) {

            throw new RuntimeException(
                    "You can only complete queries assigned to you"
            );
        }

        if (!"IN_PROGRESS".equalsIgnoreCase(query.getStatus())) {
            throw new RuntimeException(
                    "Only in-progress queries can be completed"
            );
        }

        query.setStatus("COMPLETED");
        query.setCompletedAt(LocalDateTime.now());
        query.setCompletionDescription(completionDescription);

        return queryRepository.save(query);
    }

    public List<Query> getAllAssignedQueries() {

        return queryRepository.findByAssignedStaffIsNotNull();
    }
}